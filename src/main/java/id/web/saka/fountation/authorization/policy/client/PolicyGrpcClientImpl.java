package id.web.saka.fountation.authorization.policy.client;

import id.web.saka.fountation.authorization.policy.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("policyGrpcClient")
public class PolicyGrpcClientImpl implements PolicyClient {

    private static final Logger logger = LoggerFactory.getLogger(PolicyGrpcClientImpl.class);
    private final PolicyGrpcMapper mapper;

    @GrpcClient("fountation-authorization-service")
    private PolicyServiceGrpc.PolicyServiceStub policyServiceStub;

    public PolicyGrpcClientImpl(PolicyGrpcMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<PolicyResponseDTO> evaluate(Long userId, Long companyId, PolicyRequestDTO authRequest) {
        logger.info("Evaluating policy via gRPC: companyId={}, userId={}", companyId, userId);

        PolicyRequest.Builder requestBuilder = mapper.toProto(authRequest).toBuilder()
                .setCompanyId(companyId);

        if (userId != null) {
            requestBuilder.setUserId(userId);
        }

        PolicyRequest request = requestBuilder.build();

        return Mono.create(sink -> {
            policyServiceStub.checkPolicy(request, new StreamObserver<PolicyResponse>() {
                @Override
                public void onNext(PolicyResponse response) {
                    sink.success(mapper.toDTO(response));
                }

                @Override
                public void onError(Throwable t) {
                    logger.error("gRPC error during policy check", t);
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    // Completed
                }
            });
        });
    }
}
