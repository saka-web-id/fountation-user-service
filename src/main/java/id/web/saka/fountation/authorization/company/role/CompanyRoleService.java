package id.web.saka.fountation.authorization.company.role;

import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CompanyRoleService {

    Logger logger = LoggerFactory.getLogger(CompanyRoleService.class);

    private final CompanyRolePermissionService companyRolePermissionService;

    private final CompanyRoleMapper companyRoleMapper;

    public CompanyRoleService(CompanyRolePermissionService companyRolePermissionService, CompanyRoleMapper companyRoleMapper) {
        this.companyRolePermissionService = companyRolePermissionService;
        this.companyRoleMapper = companyRoleMapper;
    }

    public Mono<CompanyRoleDTO> getAuthorityByCompanyIdAndUserId(Long companyId, Long userId) {
        return companyRolePermissionService.getAuthorityByCompanyIdAndUserId(companyId, userId)
                .map(companyRoleMapper::companyRoleDTOFromCompanyRolePermissionDTO
                );
    }

}
