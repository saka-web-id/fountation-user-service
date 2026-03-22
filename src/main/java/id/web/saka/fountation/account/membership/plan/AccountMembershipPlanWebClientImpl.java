package id.web.saka.fountation.account.membership.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("accountMembershipPlanWebClient")
public class AccountMembershipPlanWebClientImpl implements AccountMembershipPlanClient {

    Logger log = LoggerFactory.getLogger(AccountMembershipPlanWebClientImpl.class);
    private final WebClient webClientAccount;

    public AccountMembershipPlanWebClientImpl(@Qualifier("webClientAccount") WebClient webClientAccount) {
        this.webClientAccount = webClientAccount;
    }

    @Override
    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(Long companyId, Long userId, Long valueUserId) {
        log.info("Fetching AccountMembershipPlanDTO via REST for valueUserId: {} in companyId: {}", valueUserId, companyId);
        return webClientAccount.get()
                .uri("/api/v0/account/user/membership/plan/detail/companyId/" + companyId + "/userId/" + userId + "/valueUserId/" + valueUserId)
                .retrieve()
                .bodyToMono(AccountMembershipPlanDTO.class)
                .doOnNext(json -> log.info("REST Response: {}", json));
    }

    @Override
    public Mono<AccountMembershipPlanDTO> updateAccountMembershipPlan(Long companyId, Long userId, Long valueUserId, id.web.saka.fountation.user.account.UserAccountDTO payload) {
        log.info("Updating AccountMembershipPlan via REST for valueUserId: {} in companyId: {}", valueUserId, companyId);
        return webClientAccount.post()
                .uri("/api/v0/account/membership/plan/update/companyId/" + companyId + "/userId/" + userId + "/valueUserId/" + valueUserId)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(AccountMembershipPlanDTO.class)
                .doOnNext(json -> log.info("REST Response: {}", json));
    }

}
