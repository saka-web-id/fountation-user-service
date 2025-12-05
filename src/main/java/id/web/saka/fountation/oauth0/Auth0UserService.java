package id.web.saka.fountation.oauth0;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class Auth0UserService {

    /*
    private final WebClient webClient;
    private final String managementApiToken;

    public Auth0UserService(@Value("${auth0.domain}") String domain,
                            @Value("${auth0.mgmt-token}") String managementApiToken) {
        this.webClient = WebClient.builder()
                .baseUrl("https://" + domain + "/api/v2")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + managementApiToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.managementApiToken = managementApiToken;
    }

    public Mono<String> registerUser(String email, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        body.put("connection", "Username-Password-Authentication");

        return webClient.post()
                .uri("/users")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class); // return full user JSON
    }*/

}
