package id.web.saka.fountation.account.membership.plan;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountMembershipPlanService {

    private final AccountMembershipPlanClient accountMembershipPlanClient;

    public AccountMembershipPlanService(final AccountMembershipPlanClient accountMembershipPlanClient) {
        this.accountMembershipPlanClient = accountMembershipPlanClient;
    }

    public Mono<AccountMembershipPlanDTO> updateAccountMembershipPlan(Long companyId, Long userId, Long valueUserId, id.web.saka.fountation.user.account.UserAccountDTO payload) {
        return accountMembershipPlanClient.updateAccountMembershipPlan(companyId, userId, valueUserId, payload);
    }

    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(Long companyId, Long userId, Long valueUserId) {
        return accountMembershipPlanClient.getAccountMembershipPlanDetailByUserId(companyId, userId, valueUserId);
    }

}
