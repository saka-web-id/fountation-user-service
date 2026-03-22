package id.web.saka.fountation.user.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("userRegistrationWebClient")
public class UserRegistrationWebClientImpl implements UserRegistrationClient {

    Logger log = LoggerFactory.getLogger(UserRegistrationWebClientImpl.class);
    private final WebClient webClientAccount;

    public UserRegistrationWebClientImpl(@Qualifier("webClientAccount") WebClient webClientAccount) {
        this.webClientAccount = webClientAccount;
    }

    /**
     * @param dto
     * @return
     */
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
}
