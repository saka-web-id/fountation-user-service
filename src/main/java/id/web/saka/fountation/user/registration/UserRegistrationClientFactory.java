package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRegistrationClientFactory {

    private final UserRegistrationClient userRegistrationWebClient;
    private final UserRegistrationClient userRegistrationGrpcClient;

    private final FountationProperties fountationProperties;

    public UserRegistrationClientFactory(
            @Qualifier("userRegistrationWebClient") UserRegistrationClient userRegistrationWebClient,
            @Qualifier("userRegistrationGrpcClient") UserRegistrationClient userRegistrationGrpcClient,
            FountationProperties fountationProperties
    ) {
        this.userRegistrationWebClient = userRegistrationWebClient;
        this.userRegistrationGrpcClient = userRegistrationGrpcClient;
        this.fountationProperties = fountationProperties;
    }

    @Bean
    public UserRegistrationClient userRegistrationClient() {
        if ("grpc".equalsIgnoreCase(fountationProperties.getService().getAccount().getClientType())) {
            return userRegistrationGrpcClient;
        }
        return userRegistrationWebClient;
    }

}
