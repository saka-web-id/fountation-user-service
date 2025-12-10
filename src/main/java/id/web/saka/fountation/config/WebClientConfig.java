package id.web.saka.fountation.config;

import id.web.saka.fountation.util.Env;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServerBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.Map;


@Configuration
public class WebClientConfig {

    private final Env env;

    public WebClientConfig(Env env) {
        this.env = env;
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .filter(new ServerBearerExchangeFilterFunction())
                .baseUrl("http://www.myproject.local:8083")
                .build();
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

    private Mono<String> getAccessToken() {
        WebClient webClient = WebClient.builder().build();

        return webClient.post()
                .uri(env.getClientRegistrationInternalServiceTokenUri())
                .bodyValue(Map.of(
                        "client_id", env.getClientRegistrationInternalServiceClientId(),
                        "client_secret", env.getClientRegistrationInternalServiceClientSecret(),
                        "audience", "https://myproject.local/api", // YOUR_API_IDENTIFIER
                        "grant_type", env.getClientRegistrationInternalServiceGrantType(),
                        "scope", env.getClientRegistrationInternalServiceScope()
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"));
    }


}
