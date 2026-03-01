package id.web.saka.fountation.account.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountClientFactory {

    @Value("${fountation.service.account.clientType:webClient}")
    private String clientType;

    private final AccountClient webClient;
    private final AccountClient grpcClient;

    public AccountClientFactory(@Qualifier("accountWebClient") AccountClient webClient,
                               @Qualifier("accountGrpcClient") AccountClient grpcClient) {
        this.webClient = webClient;
        this.grpcClient = grpcClient;
    }

    @Bean
    public AccountClient accountClient() {
        if ("grpc".equalsIgnoreCase(clientType)) {
            return grpcClient;
        }
        return webClient;
    }
}
