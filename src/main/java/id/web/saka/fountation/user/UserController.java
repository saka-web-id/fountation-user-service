package id.web.saka.fountation.user;

import id.web.saka.fountation.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    private final WebClient webClient;

    @Autowired
    private UserService userService;

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

    @GetMapping("/user/login/success")
    public Mono<Void> loginSuccess(ServerWebExchange exchange) {

        System.out.println("Login Success, redirecting to Vue SPA...");

        // Redirect to Vue SPA
        exchange.getResponse().setStatusCode(HttpStatus.FOUND); // 302 redirect
        exchange.getResponse().getHeaders().setLocation(URI.create("http://www.myproject.local:5173/dashboard"));

        //todo user history login
        return exchange.getResponse().setComplete();
    }

    @GetMapping("/user/detail")
    public Mono<User> getUser(@AuthenticationPrincipal Jwt jwt) {
        User user;
        //User user = new User();
        //user.setName(jwt.getClaimAsString("https://example.com/name"));
        //user.setEmail(jwt.getClaimAsString("https://example.com/email"));

        user = userService.getUserByEmail(jwt.getClaimAsString("https://example.com/email"));

        return Mono.just(user);
    }

    @GetMapping("/user/health")
    public Mono<String> health(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("Health check for User Service" );

        String token = jwt.getTokenValue();   // <-- full JWT access token

        System.out.println("BEARER TOKEN = " + token);
        System.out.println("preferred_username=" + jwt.getClaimAsString("preferred_username"));
        System.out.println("name=" + jwt.getClaimAsString("https://example.com/name"));
        System.out.println("email=" + jwt.getClaimAsString("https://example.com/email"));
        System.out.println("nickname=" + jwt.getClaimAsString("https://example.com/nickname"));

        jwt.getClaims().forEach((k, v) ->
                System.out.println(k + ": " + v));

        return Mono.just("User Service UP");
    }

}
