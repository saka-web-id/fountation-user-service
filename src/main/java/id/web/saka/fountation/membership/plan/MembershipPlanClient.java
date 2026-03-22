package id.web.saka.fountation.membership.plan;

public interface MembershipPlanClient {
    reactor.core.publisher.Flux<MembershipPlanDTO> getMembershipPlanListByCompanyId(Long companyId, Long userId, Long valueCompanyId);
}
