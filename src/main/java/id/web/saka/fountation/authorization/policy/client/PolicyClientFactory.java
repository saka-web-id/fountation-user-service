package id.web.saka.fountation.authorization.policy.client;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PolicyClientFactory {

    private final PolicyClient webClient;
    private final PolicyClient grpcClient;

    private final FountationProperties fountationProperties;

    public PolicyClientFactory(@Qualifier("policyWebClient") PolicyClient webClient,
                               @Qualifier("policyGrpcClient") PolicyClient grpcClient,
                               FountationProperties fountationProperties) {
        this.webClient = webClient;
        this.grpcClient = grpcClient;
        this.fountationProperties = fountationProperties;
    }

    @Bean
    public PolicyClient policyClient() {
        if ("grpc".equalsIgnoreCase(fountationProperties.getService().getAuthorization().getClientType())) {
            return grpcClient;
        }
        return webClient;
    }
}
