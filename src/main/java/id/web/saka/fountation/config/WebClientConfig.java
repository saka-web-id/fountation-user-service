package id.web.saka.fountation.config;

import id.web.saka.fountation.util.Env;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Configuration
public class WebClientConfig {

    private final Env env;

    public WebClientConfig(Env env) {
        this.env = env;
    }

    @Bean
    public Mono<WebClient> webClientAccount(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return getAccessToken()
                .map(token ->
                        WebClient.builder()
                                .filter(lbFunction)
                                .baseUrl(env.getFountationServiceAccountUrl())
                                .defaultHeaders(headers -> {
                                    headers.setBearerAuth(token);
                                    headers.set("Accept", "application/json");
                                    headers.set("Content-Type", "application/json");
                                })
                                .build()
                );
    }

    @Bean
    public Mono<WebClient> webClientAuthorization(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return getAccessToken()
                .map(token ->
                        WebClient.builder()
                                .filter(lbFunction)
                                .baseUrl(env.getFountationServiceAuthorizationUrl())
                                .defaultHeaders(headers -> {
                                    headers.setBearerAuth(token);
                                    headers.set("Accept", "application/json");
                                    headers.set("Content-Type", "application/json");
                                })
                                .build()
                );
    }

    private Mono<String> getAccessToken() {
        WebClient webClient = WebClient.builder().build();

        return webClient.post()
                .uri(env.getClientRegistrationInternalServiceTokenUri())
                .bodyValue(Map.of(
                        "client_id", env.getClientRegistrationInternalServiceClientId(),
                        "client_secret", env.getClientRegistrationInternalServiceClientSecret(),
                        "audience", env.getFountationServiceSecurityJwtAudience(),
                        "grant_type", env.getClientRegistrationInternalServiceGrantType(),
                        "scope", env.getClientRegistrationInternalServiceScope()
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"));
    }


}
