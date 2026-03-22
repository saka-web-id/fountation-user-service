package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanWebClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component("membershipPlanWebClient")
public class MembershipPlanWebClientImpl implements MembershipPlanClient {

    Logger log = LoggerFactory.getLogger(AccountMembershipPlanWebClientImpl.class);
    private final WebClient webClientAccount;

    public MembershipPlanWebClientImpl(@Qualifier("webClientAccount") WebClient webClientAccount) {
        this.webClientAccount = webClientAccount;
    }

    /**
     * @param companyId
     * @param userId
     * @param valueCompanyId
     * @return
     */
    @Override
    public reactor.core.publisher.Flux<MembershipPlanDTO> getMembershipPlanListByCompanyId(Long companyId, Long userId, Long valueCompanyId) {
        log.info("Fetching MembershipPlanDTO list via REST for valueCompanyId: {} in companyId: {}", valueCompanyId, companyId);
        return webClientAccount.get()
                .uri("/api/v0/account/membership/plan/list/companyId/" + companyId + "/userId/" + userId + "/valueCompanyId/" + valueCompanyId)
                .retrieve()
                .bodyToFlux(MembershipPlanDTO.class)
                .doOnNext(json -> log.info("REST Response: {}", json));
    }
}
