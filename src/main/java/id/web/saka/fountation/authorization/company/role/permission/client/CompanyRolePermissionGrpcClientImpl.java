package id.web.saka.fountation.authorization.company.role.permission.client;

import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionDTO;
import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionProto;
import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionServiceGrpc;
import id.web.saka.fountation.authorization.company.role.permission.GetCompanyRolePermissionRequest;
import id.web.saka.fountation.permission.PermissionDTO;
import io.micrometer.context.ContextSnapshot;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component("rolePermissionGrpcClient")
public class CompanyRolePermissionGrpcClientImpl implements CompanyRolePermissionClient {

    private final Logger log = LoggerFactory.getLogger(CompanyRolePermissionGrpcClientImpl.class);

    @GrpcClient("fountation-authorization-service")
    private CompanyRolePermissionServiceGrpc.CompanyRolePermissionServiceStub stub;

    @Override
    public Mono<CompanyRolePermissionDTO> getCompanyRolePermissionByCompanyIdAndUserId(Long companyId, Long userId) {
        log.info("[CompanyRolePermissionGrpcClientImpl - getCompanyRolePermissionByCompanyIdAndUserId] Initiated request to fetch company role permissions via gRPC for companyId: {} and userId: {}", companyId, userId);

        GetCompanyRolePermissionRequest request = GetCompanyRolePermissionRequest.newBuilder()
                .setCompanyId(companyId)
                .setUserId(userId)
                .build();

        return Mono.create(sink -> {
            stub.getCompanyRolePermission(request, new io.grpc.stub.StreamObserver<CompanyRolePermissionProto>() {
                @Override
                public void onNext(CompanyRolePermissionProto proto) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        CompanyRolePermissionDTO dto = mapToDto(proto);
                        log.info("[CompanyRolePermissionGrpcClientImpl - getCompanyRolePermissionByCompanyIdAndUserId] Successfully retrieved company role permissions via gRPC for companyId: {} and userId: {}", companyId, userId);
                        sink.success(dto);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        log.error("[CompanyRolePermissionGrpcClientImpl - getCompanyRolePermissionByCompanyIdAndUserId] Failed to retrieve company role permissions via gRPC for companyId: {} and userId: {} due to error: {}", companyId, userId, t.getMessage(), t);
                        sink.error(t);
                    }
                }

                @Override
                public void onCompleted() {
                    // Reaktif Mono selesai setelah success dipanggil
                }
            });
        });
    }

    private CompanyRolePermissionDTO mapToDto(CompanyRolePermissionProto response) {
        return new CompanyRolePermissionDTO(
                response.getRoleId(),
                response.getCompanyId(),
                response.getRoleName(),
                response.getRoleDescription(),
                response.getPermissionsList().stream()
                        .map(p -> new PermissionDTO(
                                p.getId(),
                                p.getName(),
                                p.getSuperAdmin(),
                                p.getResource(),
                                p.getAction(),
                                p.getDescription(),
                                p.getIsAssigned()
                        ))
                        .collect(Collectors.toList())
        );
    }
}