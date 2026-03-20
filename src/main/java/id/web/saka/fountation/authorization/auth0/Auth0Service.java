package id.web.saka.fountation.authorization.auth0;

import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.util.Env;
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
    private final Env env;

    public Auth0Service(WebClient.Builder webClientBuilder, Env env) {
        this.webClient = webClientBuilder.build();
        this.env = env;
    }

    public Mono<String> getManagementToken() {
        Map<String, String> body = new HashMap<>();
        body.put("client_id", env.getClientRegistrationInternalServiceClientId());
        body.put("client_secret", env.getClientRegistrationInternalServiceClientSecret());
        body.put("audience", env.getAuth0ManagementApiAudience());
        body.put("grant_type", "client_credentials");

        return webClient.post()
                .uri(env.getClientRegistrationInternalServiceTokenUri()) // https://.../oauth/token
                .bodyValue(Map.of(
                        "client_id", env.getClientRegistrationAuth0ClientId(),
                        "client_secret", env.getClientRegistrationAuth0ClientSecret(),
                        "audience", env.getAuth0ManagementApiAudience(),
                        "grant_type", "client_credentials",
                        "scope", "create:users read:users"
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"))
                .doOnError(e -> log.error("Failed to get Auth0 Management Token", e));
    }

    public Mono<String> registerUser(UserDTO user) {
        log.info("registerUser: {}", user);

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
                            .uri(env.getAuth0ManagementApiAudience() + "users")
                            .headers(h -> h.setBearerAuth(token))
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(body)
                            .retrieve()
                            .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                                    clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("AUTH0 SAYS: {}", errorBody); // This prints the EXACT reason
                                        return Mono.error(new RuntimeException("Auth0 Error: " + errorBody));
                                    })
                            )
                            .bodyToMono(Map.class)
                            .map(response -> (String) response.get("user_id"))
                            .doOnError(e -> log.error("Failed to register user in Auth0", e));
                });
    }
}
