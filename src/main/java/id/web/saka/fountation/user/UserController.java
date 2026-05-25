package id.web.saka.fountation.user;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import id.web.saka.fountation.user.account.UserAccountDTO;
import id.web.saka.fountation.user.account.UserAccountService;
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

    private final UserAccountService userAccountService;

    private final FountationProperties fountationProperties;

    public UserController(UserService userService, UserAccountService userAccountService, FountationProperties fountationProperties) {
       this.userService = userService;
       this.userAccountService = userAccountService;
       this.fountationProperties = fountationProperties;
    }

    @GetMapping("/user/login")
    public Mono<Void> loginSuccess(ServerWebExchange exchange) {
        log.info("[loginSuccess] Successfully logged in, redirecting to Vue SPA dashboard");

        // Redirect to Vue SPA
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(fountationProperties.getService().getUi().getUrl() + "/dashboard"));

        //todo user history login
        return exchange.getResponse().setComplete();
    }

    @GetMapping({"/user/detail", "/user/detail/"}) //NOTE excluded from authorization filter
    public Mono<UserAccountDTO> getUser(@AuthenticationPrincipal Jwt jwt) {
        log.info("[getUser] Initiated request to fetch user details for email: {}", jwt.getClaimAsString("https://example.com/email"));

        return userAccountService.getUserAccountDTOByEmail(jwt.getClaimAsString("https://example.com/email"));
    }

    @GetMapping("/user/detail/{userId}") //NOTE excluded from authorization filter
    public Mono<UserDTO> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/user/update/companyId/{companyId}/userId/{userId}")
    public Mono<UserDTO> updateUser(@RequestBody Mono<UserDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        log.info("[updateUser] Initiated request to update user in companyId: {} and userId: {}", companyId, userId);

        return payload
                .flatMap(userService::saveUser);
    }

    @PostMapping("/user/add/companyId/{companyId}/departementId/{departmentId}")
    public Mono<UserDTO> addUser(@RequestBody Mono<UserRequestDTO> payload, @PathVariable Long companyId, @PathVariable Long departmentId) {

        return payload
                .flatMap(userService::addUser);
    }

    @GetMapping("/user/health")
    public Mono<String> health(@AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();   // <-- full JWT access token

        log.info("[health] HEALTH CHECK: Bearer Token: {}", token);
        log.info("[health] HEALTH CHECK: preferred_username: {}", jwt.getClaimAsString("preferred_username"));
        log.info("[health] HEALTH CHECK: name: {}", jwt.getClaimAsString("https://example.com/name"));
        log.info("[health] HEALTH CHECK: email: {}", jwt.getClaimAsString("https://example.com/email"));
        log.info("[health] HEALTH CHECK: nickname: {}", jwt.getClaimAsString("https://example.com/nickname"));

        jwt.getClaims().forEach((k, v) ->
                log.info("[health] HEALTH CHECK: {}: {}", k, v));

        return Mono.just("User Service UP");
    }

}
