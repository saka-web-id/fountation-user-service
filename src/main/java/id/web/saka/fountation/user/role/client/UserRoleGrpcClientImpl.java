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
        log.info("Assigning role to new user via gRPC: {}", userRegistrationDTO);
        UserRegistrationProto request = mapper.toProto(userRegistrationDTO);

        return Mono.create(sink -> userRoleServiceStub.assignRoleToNewUser(request, new StreamObserver<UserRegistrationProto>() {
            @Override
            public void onNext(UserRegistrationProto response) {
                sink.success(mapper.toDto(response));
            }

            @Override
            public void onError(Throwable t) {
                log.error("Error in assignRoleToNewUser gRPC call", t);
                sink.error(t);
            }

            @Override
            public void onCompleted() {}
        }));
    }
}