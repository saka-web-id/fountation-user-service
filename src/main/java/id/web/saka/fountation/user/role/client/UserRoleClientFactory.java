package id.web.saka.fountation.user.role.client;

import id.web.saka.fountation.util.Env;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserRoleClientFactory {

    private final Env env;

    private final UserRoleClient webClientClient;
    private final UserRoleClient grpcClient;

    public UserRoleClientFactory(
            @Qualifier("userRoleWebClient") UserRoleClient webClientClient,
            @Qualifier("userRoleGrpcClient") UserRoleClient grpcClient,
            Env env) {
        this.webClientClient = webClientClient;
        this.grpcClient = grpcClient;
        this.env = env;
    }

    @Bean
    @Primary // Mark this as the default bean for CompanyRolePermissionService
    public UserRoleClient userRoleClient() {
        if ("grpc".equalsIgnoreCase(env.getFountationServiceAuthorizationClientType())) {
            return grpcClient;
        }
        return webClientClient;
    }

}
