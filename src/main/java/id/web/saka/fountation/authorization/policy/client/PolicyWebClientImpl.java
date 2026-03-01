package id.web.saka.fountation.authorization.policy.client;

import id.web.saka.fountation.authorization.policy.PolicyRequestDTO;
import id.web.saka.fountation.authorization.policy.PolicyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("policyWebClient")
public class PolicyWebClientImpl implements PolicyClient {

    private static final Logger logger = LoggerFactory.getLogger(PolicyWebClientImpl.class);
    private final Mono<WebClient> webClientAuthority;

    public PolicyWebClientImpl(@Qualifier("webClientAuthorization") Mono<WebClient> webClientAuthority) {
        this.webClientAuthority = webClientAuthority;
    }

    @Override
    public Mono<PolicyResponseDTO> evaluate(Long userId, Long companyId, PolicyRequestDTO authRequest) {
        logger.info("Evaluating policy via REST: companyId={}, userId={}", companyId, userId);
        return webClientAuthority.flatMap(webClient ->
                webClient.post()
                        .uri("/api/v0/authorization/policy/check/{companyId}/{userId}", companyId, userId)
                        .bodyValue(authRequest)
                        .retrieve()
                        .bodyToMono(PolicyResponseDTO.class)
                        .doOnNext(res -> logger.info("REST Response: {}", res))
        );
    }
}
