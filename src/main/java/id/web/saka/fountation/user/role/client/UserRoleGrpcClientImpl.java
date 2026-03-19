package id.web.saka.fountation.user.role.client;

import id.web.saka.fountation.authorization.user.role.AddUserRoleRequest;
import id.web.saka.fountation.authorization.user.role.UpdateUserRolesRequest;
import id.web.saka.fountation.authorization.user.role.UserRegistrationProto;
import id.web.saka.fountation.authorization.user.role.UserRoleProto;
import id.web.saka.fountation.authorization.user.role.UserRoleServiceGrpc;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import id.web.saka.fountation.user.role.UserRoleDTO;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("userRoleGrpcClient")
public class UserRoleGrpcClientImpl implements UserRoleClient {

    private final Logger log = LoggerFactory.getLogger(UserRoleGrpcClientImpl.class);
    private final UserRoleGrpcMapper mapper;

    @GrpcClient("fountation-authorization-service")
    private UserRoleServiceGrpc.UserRoleServiceStub userRoleServiceStub;

    public UserRoleGrpcClientImpl(UserRoleGrpcMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<UserRoleDTO> updateUserRoles(Long companyId, Long userId, UserRoleDTO userRoleDTO) {
        log.info("Updating user roles via gRPC: companyId={}, userId={}, userRoleDTO={}", companyId, userId, userRoleDTO);

        UpdateUserRolesRequest request = UpdateUserRolesRequest.newBuilder()
                .setCompanyId(companyId)
                .setUserId(userId)
                .setUserRole(mapper.toProto(userRoleDTO))
                .build();

        return Mono.create(sink -> userRoleServiceStub.updateUserRoles(request, new StreamObserver<UserRoleProto>() {
            @Override
            public void onNext(UserRoleProto response) {
                sink.success(mapper.toDto(response));
            }

            @Override
            public void onError(Throwable t) {
                sink.error(t);
            }

            @Override
            public void onCompleted() {}
        }));
    }

    @Override
    public Mono<UserRoleDTO> addUserRole(Long companyId, Long userId, UserRoleDTO userRoleDTO) {
        log.info("Adding user role via gRPC: companyId={}, userId={}, userRoleDTO={}", companyId, userId, userRoleDTO);

        AddUserRoleRequest request = AddUserRoleRequest.newBuilder()
                .setCompanyId(companyId)
                .setUserId(userId)
                .setUserRole(mapper.toProto(userRoleDTO))
                .build();

        return Mono.create(sink -> userRoleServiceStub.addUserRole(request, new StreamObserver<UserRoleProto>() {
            @Override
            public void onNext(UserRoleProto response) {
                sink.success(mapper.toDto(response));
            }

            @Override
            public void onError(Throwable t) {
                sink.error(t);
            }

            @Override
            public void onCompleted() {}
        }));
    }

    @Override
    public Mono<UserRegistrationDTO> assignRoleToNewUser(UserRegistrationDTO userRegistrationDTO) {
        String userEmail = userRegistrationDTO.user().email();
        log.info("gRPC_START | Starting role assignment via gRPC for user: {}", userEmail);

        UserRegistrationProto request = mapper.toProto(userRegistrationDTO);

        return Mono.<UserRegistrationDTO>create(sink -> {
                    log.debug("gRPC_INVOKE | Calling userRoleServiceStub.assignRoleToNewUser for: {}", userEmail);

                    userRoleServiceStub.assignRoleToNewUser(request, new StreamObserver<UserRegistrationProto>() {
                        @Override
                        public void onNext(UserRegistrationProto response) {
                            log.info("gRPC_ON_NEXT | Received response from Role Service for user: {}", userEmail);
                            sink.success(mapper.toDto(response));
                        }

                        @Override
                        public void onError(Throwable t) {
                            // This is the most important log for your 'Internal Error'
                            log.error("gRPC_ERROR | Failed to assign role via gRPC for user: {}. Error: {} - {}",
                                    userEmail, t.getClass().getName(), t.getMessage());
                            sink.error(t);
                        }

                        @Override
                        public void onCompleted() {
                            log.debug("gRPC_COMPLETED | Stream closed for user: {}", userEmail);
                        }
                    });
                })
                .doOnCancel(() -> log.warn("gRPC_CANCEL | Mono was cancelled (timeout?) for user: {}", userEmail))
                .doOnError(e -> log.error("FLOW_ERROR | Final Mono error state for user: {}", userEmail));
    }
}