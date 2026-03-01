package id.web.saka.fountation.authorization.policy;

import id.web.saka.fountation.authorization.policy.client.PolicyClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PolicyService {

    private final PolicyClient policyClient;

    public PolicyService(PolicyClient policyClient) {
        this.policyClient = policyClient;
    }

    public Mono<PolicyResponseDTO> evaluate(Jwt jwt, Long userId, Long companyId, PolicyRequestDTO authRequest) {
        return policyClient.evaluate(userId, companyId, authRequest);
    }
}
