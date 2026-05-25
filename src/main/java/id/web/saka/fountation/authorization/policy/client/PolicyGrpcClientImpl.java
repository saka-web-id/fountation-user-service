package id.web.saka.fountation.authorization.policy.client;

import id.web.saka.fountation.authorization.policy.*;
import io.grpc.stub.StreamObserver;
import io.micrometer.context.ContextSnapshot;
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
        logger.info("[evaluate] Initiated policy evaluation via gRPC for companyId: {} and userId: {}", companyId, userId);

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
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        PolicyResponseDTO dto = mapper.toDTO(response);
                        logger.info("[evaluate] Successfully evaluated policy via gRPC for companyId: {} and userId: {}", companyId, userId);
                        sink.success(dto);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        logger.error("[evaluate] Failed to evaluate policy via gRPC for companyId: {} and userId: {} due to error: {}", companyId, userId, t.getMessage(), t);
                        sink.error(t);
                    }
                }

                @Override
                public void onCompleted() {
                    // Completed
                }
            });
        });
    }
}
