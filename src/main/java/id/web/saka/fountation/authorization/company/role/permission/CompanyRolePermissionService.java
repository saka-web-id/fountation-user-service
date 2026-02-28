package id.web.saka.fountation.authorization.company.role.permission;

import id.web.saka.fountation.authorization.company.role.permission.client.CompanyRolePermissionClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CompanyRolePermissionService {
     Logger log = org.slf4j.LoggerFactory.getLogger(CompanyRolePermissionService.class);

    private final CompanyRolePermissionClient client;

    public CompanyRolePermissionService(CompanyRolePermissionClient client) {
        this.client = client;
    }

    public Mono<CompanyRolePermissionDTO> getAuthorityByCompanyIdAndUserId( Long companyId, Long userId) {

        return client.getCompanyRolePermissionByCompanyIdAndUserId(companyId, userId)
                .doOnNext(json -> log.info("Raw JSON: {}", json));
    }

}
