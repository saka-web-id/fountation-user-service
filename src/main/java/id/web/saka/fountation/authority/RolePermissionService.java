package id.web.saka.fountation.authority;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RolePermissionService {
     Logger log = org.slf4j.LoggerFactory.getLogger(RolePermissionService.class);

    private Mono<WebClient> webClientAuthority;

    public RolePermissionService(@Qualifier("webClientAuthorization") Mono<WebClient> webClientAuthority) {
        this.webClientAuthority = webClientAuthority;
    }

    public Mono<RolePermissionDTO> getAuthorityByCompanyIdAndUserId(Long companyId, Long userId) {
        return webClientAuthority.flatMap(webClient ->
                webClient.get()
                        .uri("/api/v0/authorization/role/permission/detail//byUserId/"  + userId)
                        .retrieve()
                        .bodyToMono(RolePermissionDTO.class)
                        .doOnNext(json -> log.info("Raw JSON: {}", json))

        );
    }

}
