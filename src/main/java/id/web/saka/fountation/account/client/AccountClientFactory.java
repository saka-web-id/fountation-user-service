package id.web.saka.fountation.account.client;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountClientFactory {

    private final AccountClient webClient;
    private final AccountClient grpcClient;

    private final FountationProperties fountationProperties;

    public AccountClientFactory(@Qualifier("accountWebClient") AccountClient webClient,
                               @Qualifier("accountGrpcClient") AccountClient grpcClient,
                                FountationProperties fountationProperties) {
        this.webClient = webClient;
        this.grpcClient = grpcClient;
        this.fountationProperties = fountationProperties;
    }

    @Bean
    public AccountClient accountClient() {
        if ("grpc".equalsIgnoreCase(fountationProperties.getService().getAccount().getClientType())) {
            return grpcClient;
        }
        return webClient;
    }
}
