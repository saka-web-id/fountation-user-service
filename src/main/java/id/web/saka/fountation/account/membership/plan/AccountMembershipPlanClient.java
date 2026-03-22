package id.web.saka.fountation.account.membership.plan;

import reactor.core.publisher.Mono;

public interface AccountMembershipPlanClient {
    Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(Long companyId, Long userId, Long valueUserId);
    Mono<AccountMembershipPlanDTO> updateAccountMembershipPlan(Long companyId, Long userId, Long valueUserId, id.web.saka.fountation.user.account.UserAccountDTO payload);

}
