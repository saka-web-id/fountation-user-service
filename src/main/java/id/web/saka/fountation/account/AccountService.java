package id.web.saka.fountation.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private Mono<WebClient> webClientAccount;

    public AccountService(@Qualifier("webClientAccount") Mono<WebClient> webClientAccount) {
        this.webClientAccount = webClientAccount;
    }

    public Mono<AccountDTO> getAccountById(Long companyId, Long userId) {
        return webClientAccount.flatMap(webClient ->
                webClient.get()
                        .uri("/api/v0/account/user/membership/plan/detail/companyId/" + companyId + "/userId/" + userId + "/valueUserId/" + userId)
                        .retrieve()
                        .bodyToMono(AccountDTO.class)
                        .doOnNext(json -> log.info("Raw JSON: {}", json))
        );
    }

}
