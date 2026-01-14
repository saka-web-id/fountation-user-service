package id.web.saka.fountation.user.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserRoleService {

    Logger log = LoggerFactory.getLogger(UserRoleService.class);

    private Mono<WebClient> webClientAuthority;

    public UserRoleService(@Qualifier("webClientAuthorization") Mono<WebClient> webClientAuthority) {
        this.webClientAuthority = webClientAuthority;
    }

    public Mono<UserRole> updateUserRoles(Long companyId, Long userId, UserRole userRoleEntity) {
        log.info("Updating UserRole for companyId {} userId: {}", userRoleEntity.getUserId());

        return webClientAuthority.flatMap(webClient ->
                webClient.post()
                        .uri("/api/v0/authorization/user/role/update/companyId/{companyId}/userId/{userId}", companyId, userId)
                        .bodyValue(userRoleEntity)
                        .retrieve()
                        .bodyToMono(UserRole.class)
        );
    }

    public Mono<UserRole> addUserRole(Long companyId, Long userId, UserRole userRole) {
        log.info("Adding UserRole for companyId {} userId: {}", userRole.getUserId());

        return webClientAuthority.flatMap(webClient ->
                webClient.put()
                        .uri("/api/v0/authorization/user/role/add/companyId/{companyId}/userId/{userId}", companyId, userId)
                        .bodyValue(userRole)
                        .retrieve()
                        .bodyToMono(UserRole.class)
        );
    }
}
