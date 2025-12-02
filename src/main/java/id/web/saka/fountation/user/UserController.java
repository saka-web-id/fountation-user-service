package id.web.saka.fountation.user;

import id.web.saka.fountation.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    private final WebClient webClient;

    UserController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/user/{userId}/accounts")
    public Mono<User> accountsForUser(@AuthenticationPrincipal Jwt jwt, @PathVariable Long userId) {
        log.info("Retrieving accounts for user {}", userId);

        return webClient.get()
                .uri("/accounts").retrieve()
                .bodyToMono(Account.class)
                .map(account -> new User(userId, "testuser@email.com", "Joe", "Bloggs", List.of(account)));
    }

    @GetMapping("/user/{userId}")
    public Mono<User> getUser(@AuthenticationPrincipal Jwt jwt, @PathVariable Long userId) {
        log.info("Retrieving accounts for user {}", userId);
        return Mono.just(new User(userId, "testuser@email.com", "Joe", "Bloggs", Collections.emptyList()));
    }

    @GetMapping("/user/health")
    public Mono<String> health(@AuthenticationPrincipal Jwt jwt) {
        return Mono.just("User Service UP");
    }

}
