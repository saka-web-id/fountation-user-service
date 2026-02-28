package id.web.saka.fountation.authorization.company.role.permission.client;

import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionDTO;
import reactor.core.publisher.Mono;

public interface CompanyRolePermissionClient {
    Mono<CompanyRolePermissionDTO> getCompanyRolePermissionByCompanyIdAndUserId(Long companyId, Long userId);
}
