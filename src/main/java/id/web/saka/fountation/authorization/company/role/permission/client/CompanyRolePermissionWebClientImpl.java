package id.web.saka.fountation.authorization.company.role.permission.client;

import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("rolePermissionWebClient")
public class CompanyRolePermissionWebClientImpl implements CompanyRolePermissionClient {

    private final WebClient webClientAuthority;

    public CompanyRolePermissionWebClientImpl(@Qualifier("webClientAuthorization") WebClient webClientAuthority) {
        this.webClientAuthority = webClientAuthority;
    }

    @Override
    public Mono<CompanyRolePermissionDTO> getCompanyRolePermissionByCompanyIdAndUserId(Long companyId, Long userId) {
        return webClientAuthority.get()
                .uri("/api/v0/authorization/company/role/permission/detail/companyId/" + companyId +
                        "/userId/" + userId +
                        "/valueCompanyId/" + companyId +
                        "/valueUserId/" + userId)
                .retrieve()
                .bodyToMono(CompanyRolePermissionDTO.class);
    }
}
