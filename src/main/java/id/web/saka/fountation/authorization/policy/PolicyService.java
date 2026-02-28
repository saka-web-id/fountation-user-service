package id.web.saka.fountation.authorization.policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PolicyService {

    Logger logger = LoggerFactory.getLogger(PolicyService.class);

    private final Mono<WebClient> webClientAuthority;

    public PolicyService(@Qualifier("webClientAuthorization") Mono<WebClient> webClientAuthority) {
        this.webClientAuthority = webClientAuthority;
    }

    public Mono<PolicyResponseDTO> evaluate(Jwt jwt, Long userId, Long companyId, PolicyRequestDTO authRequest) {
        return webClientAuthority.flatMap(webClient ->
                webClient.post()
                        .uri("/api/v0/authorization/policy/check/{companyId}/{userId}", companyId, userId)
                        .bodyValue(authRequest) // send PolicyRequestDTO in POST body
                        .retrieve()
                        .bodyToMono(PolicyResponseDTO.class)
                        .doOnNext(json -> logger.info("Raw JSON: {}", json))
        );

    }
}
