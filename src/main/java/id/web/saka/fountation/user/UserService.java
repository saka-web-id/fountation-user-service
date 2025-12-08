package id.web.saka.fountation.user;

import id.web.saka.fountation.util.Env;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final WebClient webClientAccount;

    private final UserRepository userRepository;

    private MessageSource messageSource;

    public UserService(UserRepository userRepository, @Qualifier("loadBalancedWebClientBuilder") WebClient.Builder builder, Env env, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.webClientAccount = builder.baseUrl(env.getFountationServiceAccountUrl()).build();
        this.messageSource = messageSource;
    }

    public Mono<UserAccountDTO> getUserAccountDTOByEmail(String token, String email) {
        return userRepository.getUsersByEmail(email)
                .flatMap(user ->
                        webClientAccount.get()
                                .uri("/account/membership/detail/" + user.getId())
                                .headers(headers -> headers.setBearerAuth(token)) // tambahkan JWT
                                .retrieve()
                                .bodyToMono(UserAccountDTO.class)
                                .map(userAccountDTO -> {
                                    // enrich DTO with local user info
                                    userAccountDTO.setName(user.getName());
                                    userAccountDTO.setEmail(user.getEmail());
                                    return userAccountDTO;
                                })
                );
    }

}
