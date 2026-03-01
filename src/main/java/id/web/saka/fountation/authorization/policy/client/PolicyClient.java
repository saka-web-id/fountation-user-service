package id.web.saka.fountation.authorization.policy.client;

import id.web.saka.fountation.authorization.policy.PolicyRequestDTO;
import id.web.saka.fountation.authorization.policy.PolicyResponseDTO;
import reactor.core.publisher.Mono;

public interface PolicyClient {
    Mono<PolicyResponseDTO> evaluate(Long userId, Long companyId, PolicyRequestDTO authRequest);
}
