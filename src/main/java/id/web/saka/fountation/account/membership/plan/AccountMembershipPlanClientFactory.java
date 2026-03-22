package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountMembershipPlanClientFactory {

    private final AccountMembershipPlanClient webClient;
    private final AccountMembershipPlanClient grpcClient;

    private final FountationProperties fountationProperties;

    public AccountMembershipPlanClientFactory(@Qualifier("accountMembershipPlanWebClient") AccountMembershipPlanClient webClient,
                                @Qualifier("accountMembershipPlanGrpcClient") AccountMembershipPlanClient grpcClient,
                                FountationProperties fountationProperties) {
        this.webClient = webClient;
        this.grpcClient = grpcClient;
        this.fountationProperties = fountationProperties;
    }

    @Bean
    public AccountMembershipPlanClient accountMembershipPlanClient() {
        if ("grpc".equalsIgnoreCase(fountationProperties.getService().getAccount().getClientType())) {
            return grpcClient;
        }
        return webClient;
    }
}
