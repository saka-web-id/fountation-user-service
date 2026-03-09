package id.web.saka.fountation.account.client;

import id.web.saka.fountation.account.AccountDTO;
import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanDTO;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("accountWebClient")
public class AccountWebClientImpl implements AccountClient {

    private static final Logger log = LoggerFactory.getLogger(AccountWebClientImpl.class);
    private final WebClient webClientAccount;

    public AccountWebClientImpl(@Qualifier("webClientAccount") WebClient webClientAccount) {
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
    public Mono<UserRegistrationDTO> assignAccountToNewUser(UserRegistrationDTO dto) {
        log.info("Adding New Account via REST for user: {}", dto.user().email());
        return webClientAccount.post()
                .uri("/api/v0/account/user/registration")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(UserRegistrationDTO.class)
                .doOnNext(json -> log.info("REST Response: {}", json));
    }

    @Override
    public Mono<AccountDTO> getAccountById(Long companyId, Long userId) {
        return webClientAccount.get()
                .uri("/api/v0/account/user/membership/plan/detail/companyId/" + companyId + "/userId/" + userId + "/valueUserId/" + userId)
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .doOnNext(json -> log.info("Raw JSON: {}", json));
    }
}
