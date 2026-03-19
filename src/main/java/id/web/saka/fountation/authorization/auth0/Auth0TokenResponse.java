package id.web.saka.fountation.authorization.auth0;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Auth0TokenResponse(

        @JsonProperty("access_token") String accessToken,

        @JsonProperty("expires_in") Long expiresIn,

        @JsonProperty("token_type") String tokenType
) {
}
