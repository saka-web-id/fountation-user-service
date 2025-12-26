package id.web.saka.fountation.user;

import id.web.saka.fountation.user.account.UserAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v0")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private UserController(UserService userService) {
       this.userService = userService;
    }

    @GetMapping("/user/login")
    public Mono<Void> loginSuccess(ServerWebExchange exchange) {

        // Redirect to Vue SPA
        exchange.getResponse().setStatusCode(HttpStatus.FOUND); // 302 redirect
        exchange.getResponse().getHeaders().setLocation(URI.create("http://www.myproject.local:5173/dashboard"));

        //todo user history login
        return exchange.getResponse().setComplete();
    }

    @GetMapping("/user/detail")
    public Mono<UserAccountDTO> getUser(@AuthenticationPrincipal Jwt jwt) {

        return userService.getUserAccountDTOByEmail(jwt.getClaimAsString("https://example.com/email"));
    }

    @GetMapping("/user/detail/{userId}")
    public Mono<UserDTO> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/user/update")
    public Mono<UserDTO> updateUser(@RequestBody Mono<UserDTO> payload) {

        return payload
                .flatMap(userService::saveUser);
    }

    @PostMapping("/user/add/{companyId}/{departmentId}")
    public Mono<UserDTO> addUser(@RequestBody Mono<UserRequestDTO> payload, @PathVariable Long companyId, @PathVariable Long departmentId) {

        return payload
                .flatMap(userService::addUser);
    }

    @GetMapping("/user/health")
    public Mono<String> health(@AuthenticationPrincipal Jwt jwt) {

        String token = jwt.getTokenValue();   // <-- full JWT access token

        log.info("BEARER TOKEN = " + token);
        log.info("preferred_username=" + jwt.getClaimAsString("preferred_username"));
        log.info("name=" + jwt.getClaimAsString("https://example.com/name"));
        log.info("email=" + jwt.getClaimAsString("https://example.com/email"));
        log.info("nickname=" + jwt.getClaimAsString("https://example.com/nickname"));

        jwt.getClaims().forEach((k, v) ->
                log.info(k + ": " + v));

        return Mono.just("User Service UP");
    }

}
