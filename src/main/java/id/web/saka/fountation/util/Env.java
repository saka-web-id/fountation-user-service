package id.web.saka.fountation.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Env {

    @Value("${fountation.service.account.url}")
    private String fountationServiceAccountUrl;

    @Value("${spring.security.oauth2.client.registration.internal-service.client-id}")
    private String clientRegistrationInternalServiceClientId;

    @Value("${spring.security.oauth2.client.registration.internal-service.client-secret}")
    private String clientRegistrationInternalServiceClientSecret;

    @Value("${spring.security.oauth2.client.registration.internal-service.authorization-grant-type}")
    private String clientRegistrationInternalServiceGrantType;

    @Value("${spring.security.oauth2.client.registration.internal-service.scope}")
    private String clientRegistrationInternalServiceScope;

    @Value("${spring.security.oauth2.client.provider.auth0.token-uri}")
    private String clientRegistrationInternalServiceTokenUri;

    /*@Value("${spring.security.oauth2.resourceserver.jwt.audiences}")
    private List<String> clientRegistrationInternalServiceAudiences;*/

    public String getFountationServiceAccountUrl() {
        return fountationServiceAccountUrl;
    }

    public void setFountationServiceAccountUrl(String fountationServiceAccountUrl) {
        this.fountationServiceAccountUrl = fountationServiceAccountUrl;
    }

    public String getClientRegistrationInternalServiceClientId() {
        return clientRegistrationInternalServiceClientId;
    }

    public void setClientRegistrationInternalServiceClientId(String clientRegistrationInternalServiceClientId) {
        this.clientRegistrationInternalServiceClientId = clientRegistrationInternalServiceClientId;
    }

    public String getClientRegistrationInternalServiceClientSecret() {
        return clientRegistrationInternalServiceClientSecret;
    }

    public void setClientRegistrationInternalServiceClientSecret(String clientRegistrationInternalServiceClientSecret) {
        this.clientRegistrationInternalServiceClientSecret = clientRegistrationInternalServiceClientSecret;
    }

    public String getClientRegistrationInternalServiceGrantType() {
        return clientRegistrationInternalServiceGrantType;
    }

    public void setClientRegistrationInternalServiceGrantType(String clientRegistrationInternalServiceGrantType) {
        this.clientRegistrationInternalServiceGrantType = clientRegistrationInternalServiceGrantType;
    }

    public String getClientRegistrationInternalServiceScope() {
        return clientRegistrationInternalServiceScope;
    }

    public void setClientRegistrationInternalServiceScope(String clientRegistrationInternalServiceScope) {
        this.clientRegistrationInternalServiceScope = clientRegistrationInternalServiceScope;
    }

    public String getClientRegistrationInternalServiceTokenUri() {
        return clientRegistrationInternalServiceTokenUri;
    }

    public void setClientRegistrationInternalServiceTokenUri(String clientRegistrationInternalServiceTokenUri) {
        this.clientRegistrationInternalServiceTokenUri = clientRegistrationInternalServiceTokenUri;
    }

    /*public List<String> getClientRegistrationInternalServiceAudiences() {
        return clientRegistrationInternalServiceAudiences;
    }

    public void setClientRegistrationInternalServiceAudiences(List<String> clientRegistrationInternalServiceAudiences) {
        this.clientRegistrationInternalServiceAudiences = clientRegistrationInternalServiceAudiences;
    }*/
}
