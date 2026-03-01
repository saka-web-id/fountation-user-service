package id.web.saka.fountation.account.client;

import id.web.saka.fountation.account.AccountDTO;
import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanDTO;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import reactor.core.publisher.Mono;

public interface AccountClient {
    Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(Long companyId, Long userId, Long valueUserId);
    Mono<UserRegistrationDTO> assignAccountToNewUser(UserRegistrationDTO dto);
    Mono<AccountDTO> getAccountById(Long companyId, Long userId);
}
