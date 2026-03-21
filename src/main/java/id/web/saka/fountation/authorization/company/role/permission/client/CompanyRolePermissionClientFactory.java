package id.web.saka.fountation.authorization.company.role.permission.client;


import id.web.saka.fountation.configbase.fountation.FountationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CompanyRolePermissionClientFactory {

    /*private final Env env;*/

    private final CompanyRolePermissionClient webClientClient;
    private final CompanyRolePermissionClient grpcClient;

    private final FountationProperties fountationProperties;

    // Use Constructor Injection instead of @Autowired on fields for better testing
    public CompanyRolePermissionClientFactory(
            @Qualifier("rolePermissionWebClient") CompanyRolePermissionClient webClientClient,
            @Qualifier("rolePermissionGrpcClient") CompanyRolePermissionClient grpcClient,
            /*Env env,*/
            FountationProperties fountationProperties) {
        this.webClientClient = webClientClient;
        this.grpcClient = grpcClient;
        /*this.env = env;*/
        this.fountationProperties = fountationProperties;
    }

    @Bean
    @Primary // Mark this as the default bean for CompanyRolePermissionService
    public CompanyRolePermissionClient companyRolePermissionClient() {
        /*if ("grpc".equalsIgnoreCase(env.getFountationServiceAuthorizationClientType())) {*/
        if ("grpc".equalsIgnoreCase(fountationProperties.getService().getAuthorization().getClientType())) {
            return grpcClient;
        }
        return webClientClient;
    }
}
