package id.web.saka.fountation.authorization.auth0;

import id.web.saka.fountation.util.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class Auth0TokenService {

    Logger logger = LoggerFactory.getLogger(Auth0TokenService.class);

    private final WebClient webClient;

    private final Env env;

    public Auth0TokenService(WebClient.Builder builder, Env env) {
        this.webClient = builder.build();
        this.env = env;
    }

   /* public Mono<String> getManagementToken() {
        Map<String, String> body = Map.of(
                "client_id", env.getClientRegistrationInternalServiceClientId(),
                "client_secret", env.getClientRegistrationInternalServiceClientSecret(),
                "audience", env.getFountationServiceSecurityJwtAudience(),
                "grant_type", "client_credentials"
        );

        return webClient.post()
                *//*.uri("https://" + domain + "/oauth/token")*//*
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Auth0TokenResponse.class)
                *//*.map(Auth0TokenResponse::getAccessToken)*//*
                .doOnError(e -> logger.error("Gagal mengambil Auth0 Token: {}", e.getMessage()));
    }*/

}
