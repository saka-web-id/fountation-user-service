package id.web.saka.fountation.user.role.client;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserRoleClientFactory {


    private final UserRoleClient webClientClient;
    private final UserRoleClient grpcClient;

    private FountationProperties fountationProperties;

    public UserRoleClientFactory(
            @Qualifier("userRoleWebClient") UserRoleClient webClientClient,
            @Qualifier("userRoleGrpcClient") UserRoleClient grpcClient,
            FountationProperties fountationProperties) {
        this.webClientClient = webClientClient;
        this.grpcClient = grpcClient;
        this.fountationProperties = fountationProperties;
    }

    @Bean
    @Primary // Mark this as the default bean for CompanyRolePermissionService
    public UserRoleClient userRoleClient() {
        if ("grpc".equalsIgnoreCase(fountationProperties.getService().getAuthorization().getClientType())) {
            return grpcClient;
        }
        return webClientClient;
    }

}
