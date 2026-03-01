package id.web.saka.fountation.authorization.policy.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PolicyClientFactory {

    @Value("${fountation.service.authorization.clientType:webClient}")
    private String clientType;

    private final PolicyClient webClient;
    private final PolicyClient grpcClient;

    public PolicyClientFactory(@Qualifier("policyWebClient") PolicyClient webClient,
                               @Qualifier("policyGrpcClient") PolicyClient grpcClient) {
        this.webClient = webClient;
        this.grpcClient = grpcClient;
    }

    @Bean
    public PolicyClient policyClient() {
        if ("grpc".equalsIgnoreCase(clientType)) {
            return grpcClient;
        }
        return webClient;
    }
}
