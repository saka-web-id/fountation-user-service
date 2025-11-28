package id.web.saka.fountation.user;

import id.web.saka.fountation.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Mono<User> accountsForUser(@PathVariable String userId) {
        log.info("Retrieving accounts for user {}", userId);

        return webClient.get()
                .uri("/accounts").retrieve()
                .bodyToMono(Account.class)
                .map(account -> new User(userId, "testuser@email.com", "Joe", "Bloggs", List.of(account)));
    }

    @GetMapping("/user/{userId}")
    public Mono<User> getUser(@PathVariable String userId) {
        log.info("Retrieving accounts for user {}", userId);
        return Mono.just(new User(userId, "testuser@email.com", "Joe", "Bloggs", Collections.emptyList()));
    }

    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("UP");
    }

}
