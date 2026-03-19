package id.web.saka.fountation.authorization.auth0;

import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class Auth0ReactiveService {

    private final WebClient webClient;

    public Auth0ReactiveService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://YOUR_DOMAIN/api/v2/").build();
    }

    public Mono<String> registerToAuth0(UserRegistrationDTO dto, String accessToken) {
        // Persiapkan Metadata
        Map<String, Object> appMetadata = Map.of(
                "organization_name", dto.company().name(),
                "source", "fountation_internal_reg"
        );

        Map<String, Object> userMetadata = Map.of(
                "registration_date", LocalDateTime.now().toString()
        );

        // Persiapkan Payload Utama (Sesuai dokumentasi Auth0 API v2)
        Map<String, Object> payload = new HashMap<>();
        payload.put("connection", "Username-Password-Authentication");
        payload.put("email", dto.user().email());
        payload.put("password", "Fdsafgdsafsad"); //TODO testing environment
        payload.put("name", dto.user().name());
        payload.put("verify_email", true);
        payload.put("app_metadata", appMetadata);
        payload.put("user_metadata", userMetadata);

        return webClient.post()
                .uri("/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("user_id"));
    }

}
