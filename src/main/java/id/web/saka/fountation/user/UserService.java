package id.web.saka.fountation.user;

import id.web.saka.fountation.util.Env;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {


    private final UserRepository userRepository;

    private MessageSource messageSource;

    private Mono<WebClient> webClientAccount;

    public UserService(UserRepository userRepository, @Qualifier("webClientAccount") Mono<WebClient> webClientAccount, Env env, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.webClientAccount = webClientAccount;
        this.messageSource = messageSource;
    }

    public Mono<UserAccountDTO> getUserAccountDTOByEmail(String email) {
        return userRepository.getUsersByEmail(email)
                .flatMap(user ->
                        webClientAccount.flatMap(webClient ->
                            webClient.get()
                                .uri("/account/membership/detail/" + user.getId())
                                .retrieve()
                                .bodyToMono(UserAccountDTO.class)
                                .map(userAccountDTO -> {
                                    userAccountDTO.setName(user.getName());
                                    userAccountDTO.setEmail(user.getEmail());
                                    return userAccountDTO;
                                })
                        )
                );
    }


}
