package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MembershipPlanClientFactory {

    private final MembershipPlanClient membershipPlanWebClient;
    private final MembershipPlanClient membershipPlanGrpcClient;

    private final FountationProperties fountationProperties;

    public MembershipPlanClientFactory(
            @Qualifier("membershipPlanWebClient") MembershipPlanClient membershipPlanWebClient,
            @Qualifier("membershipPlanGrpcClient") MembershipPlanClient membershipPlanGrpcClient,
            FountationProperties fountationProperties
    ) {
        this.membershipPlanWebClient = membershipPlanWebClient;
        this.membershipPlanGrpcClient = membershipPlanGrpcClient;
        this.fountationProperties = fountationProperties;
    }

    @Bean
    public MembershipPlanClient membershipPlanClient() {
        if ("grpc".equalsIgnoreCase(fountationProperties.getService().getAccount().getClientType())) {
            return membershipPlanGrpcClient;
        }
        return membershipPlanWebClient;
    }

}
