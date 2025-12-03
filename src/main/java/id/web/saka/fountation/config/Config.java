package id.web.saka.fountation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServerBearerExchangeFilterFunction;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
public class Config {

    @Value("${auth0.audience:defaultAudience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:defaultIssuer}")
    private String issuer;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(
                auth -> auth
                        .pathMatchers("/user/health").permitAll()
                        .pathMatchers("/user/registration/**").permitAll()
                )
                .authorizeExchange(auth -> auth.anyExchange().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .filter(new ServerBearerExchangeFilterFunction())
                .baseUrl("http://localhost:8083")
                .build();
    }

}
