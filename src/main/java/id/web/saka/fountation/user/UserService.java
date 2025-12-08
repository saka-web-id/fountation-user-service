package id.web.saka.fountation.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final WebClient webClientAccount;

    private final UserRepository userRepository;

    private MessageSource messageSource;

    public UserService(UserRepository userRepository, @Qualifier("webClientAccount") WebClient webClientAccount, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.webClientAccount = webClientAccount;
        this.messageSource = messageSource;
    }

    public Flux<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    public Mono<UserAccountDTO> getUserAccountDTOByEmail(String email) {
        User user = userRepository.getUsersByEmail(email);

        return webClientAccount.get()
                .uri("/account/membership/detail/"+user.getAccountId()).retrieve()
                .bodyToMono(UserAccountDTO.class)
                .map(userAccountDTO -> {
                    // enrich DTO with local user info
                    userAccountDTO.setName(user.getName());
                    userAccountDTO.setEmail(user.getEmail());
                    return userAccountDTO;
                });

    }
}
