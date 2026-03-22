package id.web.saka.fountation.membership.plan;

import org.springframework.stereotype.Service;

@Service
public class MembershipPlanService {

    private final MembershipPlanClient membershipPlanClient;

    public MembershipPlanService(MembershipPlanClient membershipPlanClient) {
        this.membershipPlanClient = membershipPlanClient;
    }


    public reactor.core.publisher.Flux<MembershipPlanDTO> getMembershipPlanListByCompanyId(Long companyId, Long userId, Long valueCompanyId) {
        return membershipPlanClient.getMembershipPlanListByCompanyId(companyId, userId, valueCompanyId);
    }

}
