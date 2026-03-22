package id.web.saka.fountation.user.registration;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("userRegistrationGrpcClient")
public class UserRegistrationGrpcClientImpl implements UserRegistrationClient {

    Logger log = LoggerFactory.getLogger(UserRegistrationGrpcClientImpl.class);

    private final UserRegistrationMapper userRegistrationMapper;

    @GrpcClient("fountation-account-service")
    private UserRegistrationServiceGrpc.UserRegistrationServiceStub userRegistrationServiceStub;

    public UserRegistrationGrpcClientImpl(UserRegistrationMapper userRegistrationMapper) {
        this.userRegistrationMapper = userRegistrationMapper;
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public Mono<UserRegistrationDTO> assignAccountToNewUser(UserRegistrationDTO dto) {
        log.info("Adding New Account via gRPC for user: {}", dto.user().email());

        id.web.saka.fountation.user.registration.UserRegistrationRequest request = userRegistrationMapper.toProto(dto);

        return Mono.create(sink -> {
            userRegistrationServiceStub.registerUser(request, new StreamObserver<UserRegistrationResponse>() {
                @Override
                public void onNext(UserRegistrationResponse response) {
                    sink.success(userRegistrationMapper.toDTO(response));
                }

                @Override
                public void onError(Throwable t) {
                    log.error("gRPC error during user registration", t);
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
