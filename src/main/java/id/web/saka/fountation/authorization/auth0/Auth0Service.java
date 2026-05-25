package id.web.saka.fountation.authorization.auth0;

import id.web.saka.fountation.configbase.spring.security.SpringSecurityProperties;
import id.web.saka.fountation.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class Auth0Service {

    private static final Logger log = LoggerFactory.getLogger(Auth0Service.class);

    private final WebClient webClient;

    private final SpringSecurityProperties springSecurityProperties;

    public Auth0Service(WebClient.Builder webClientBuilder, SpringSecurityProperties springSecurityProperties) {
        this.webClient = webClientBuilder.build();
        this.springSecurityProperties = springSecurityProperties;
    }

    public Mono<String> getManagementToken() {

        return webClient.post()
                .uri(springSecurityProperties.getOauth2().getClient().getProvider().get("auth0").getTokenUri())
                .bodyValue(Map.of(
                        "client_id", springSecurityProperties.getOauth2().getClient().getRegistration().get("auth0").getClientId(),
                        "client_secret", springSecurityProperties.getOauth2().getClient().getRegistration().get("auth0").getClientSecret(),
                        "audience", springSecurityProperties.getOauth2().getClient().getProvider().get("auth0").getIssuerUri() + "api/v2/",
                        "grant_type", "client_credentials",
                        "scope", "create:users read:users"
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"))
                .doOnError(e -> log.error("[getManagementToken] Failed to retrieve Auth0 management token due to error: {}", e.getMessage(), e));
    }

    public Mono<String> registerUser(UserDTO user) {
        log.info("[registerUser] Initiated user registration in Auth0 for user: {}", user.email());

        return getManagementToken()
                .flatMap(token -> {
                    Map<String, Object> body = new HashMap<>();
                    body.put("email", user.email());
                    body.put("password", user.password());
                    body.put("connection", "Username-Password-Authentication"); // Default Auth0 connection
                    body.put("name", user.name());
                    body.put("username", user.name());
                    body.put("user_metadata", Map.of("phone", user.phone()));

                    return webClient.post()
                            .uri(springSecurityProperties.getOauth2().getClient().getProvider().get("auth0").getIssuerUri() + "users")
                            .headers(h -> h.setBearerAuth(token))
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(body)
                            .retrieve()
                            .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                                    clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("[registerUser] Auth0 returned an error during user registration: {}", errorBody); // This prints the EXACT reason
                                        return Mono.error(new RuntimeException("Auth0 Error: " + errorBody));
                                    })
                            )
                            .bodyToMono(Map.class)
                            .map(response -> (String) response.get("user_id"))
                            .doOnNext(userId -> log.info("[registerUser] Successfully registered user in Auth0 with ID: {}", userId))
                            .doOnError(e -> log.error("[registerUser] Failed to register user in Auth0 due to error: {}", e.getMessage(), e));
                });
    }
}
