package id.web.saka.fountation.config;

import id.web.saka.fountation.util.Env;
import io.netty.channel.ChannelOption;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.Map;


@Configuration
public class WebClientConfig {

    private final Env env;

    public WebClientConfig(Env env) {
        this.env = env;
    }

    @Bean
    public HttpClient httpClient() {
        ConnectionProvider provider = ConnectionProvider.builder("fountation-user-pool")
                .maxIdleTime(Duration.ofSeconds(60)) // Increased from 20s
                .maxLifeTime(Duration.ofMinutes(5))  // Increased from 1m to match Gateway
                .evictInBackground(Duration.ofSeconds(30))
                .build();

        return HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000) // 30s connection timeout
                .responseTimeout(Duration.ofMinutes(2)); // Increased from 10s to 120s
    }

    @Bean
    public WebClientCustomizer webClientCustomizer(HttpClient httpClient) {
        return webClientBuilder -> webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    public WebClient webClientAccount(HttpClient httpClient) {
        // 1. Buat Cache untuk Token agar tidak membebani server Auth
        Mono<String> tokenCache = getAccessToken(httpClient)
                .cache(
                        token -> Duration.ofMinutes(50), // Cache jika sukses (50 menit)
                        error -> Duration.ZERO,           // Jangan cache jika error (coba lagi langsung)
                        () -> Duration.ZERO               // Jangan cache jika kosong
                );

        // 2. Buat Filter yang menggunakan Cache tersebut
        ExchangeFilterFunction authFilter = (request, next) ->
                tokenCache.flatMap(token -> {
                    ClientRequest filteredRequest = ClientRequest.from(request)
                            .headers(headers -> headers.setBearerAuth(token))
                            .build();
                    return next.exchange(filteredRequest);
                });

        // 3. Bangun WebClient
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(env.getFountationServiceAccountUrl())
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .filter(authFilter)
                .build();
    }

    @Bean
    public WebClient webClientAuthorization(HttpClient httpClient) {
        // 1. Buat Cache untuk Token agar tidak membebani server Auth
        Mono<String> tokenCache = getAccessToken(httpClient)
                .cache(
                        token -> Duration.ofMinutes(50), // Cache jika sukses (50 menit)
                        error -> Duration.ZERO,           // Jangan cache jika error (coba lagi langsung)
                        () -> Duration.ZERO               // Jangan cache jika kosong
                );

        // 2. Buat Filter yang menggunakan Cache tersebut
        ExchangeFilterFunction authFilter = (request, next) ->
                tokenCache.flatMap(token -> {
                    ClientRequest filteredRequest = ClientRequest.from(request)
                            .headers(headers -> headers.setBearerAuth(token))
                            .build();
                    return next.exchange(filteredRequest);
                });

        // 3. Bangun WebClient
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(env.getFountationServiceAuthorizationUrl())
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .filter(authFilter)
                .build();
    }

    private Mono<String> getAccessToken(HttpClient httpClient) {
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

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
                .map(response -> (String) response.get("access_token"))
                .cache(Duration.ofMinutes(50));
    }


}
