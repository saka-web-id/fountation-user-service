package id.web.saka.fountation.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Env {

    /* FOUNTATION ENVIRONMENT SETTING */

    @Value("${fountation.service.account.url}")
    private String fountationServiceAccountUrl;

    @Value("${fountation.service.account.clientType}")
    private String fountationServiceAccountClientType;

    @Value("${fountation.service.authorization.url}")
    private String fountationServiceAuthorizationUrl;

    @Value("${fountation.service.authorization.clientType}")
    private String fountationServiceAuthorizationClientType;

    @Value("${fountation.service.redis.store.duration.minutes}")
    private int fountationServiceRedisStoreDurationInMinutes;

    @Value("${fountation.service.ui.url}")
    private String fountationServiceUiUrl;

    @Value("${fountation.service.security.jwt.audience}")
    private String fountationServiceSecurityJwtAudience;

    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}api/v2/")
    private String auth0ManagementApiAudience;


    /* SPRINT SECURITY ENVIRONMENT SETTING */

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

    public String getFountationServiceAccountUrl() {
        return fountationServiceAccountUrl;
    }

    public void setFountationServiceAccountUrl(String fountationServiceAccountUrl) {
        this.fountationServiceAccountUrl = fountationServiceAccountUrl;
    }

    public String getFountationServiceAccountClientType() {
        return fountationServiceAccountClientType;
    }

    public void setFountationServiceAccountClientType(String fountationServiceAccountClientType) {
        this.fountationServiceAccountClientType = fountationServiceAccountClientType;
    }

    public String getFountationServiceAuthorizationUrl() {
        return fountationServiceAuthorizationUrl;
    }

    public void setFountationServiceAuthorizationUrl(String fountationServiceAuthorizationUrl) {
        this.fountationServiceAuthorizationUrl = fountationServiceAuthorizationUrl;
    }

    public String getFountationServiceAuthorizationClientType() {
        return fountationServiceAuthorizationClientType;
    }

    public void setFountationServiceAuthorizationClientType(String fountationServiceAuthorizationClientType) {
        this.fountationServiceAuthorizationClientType = fountationServiceAuthorizationClientType;
    }

    public int getFountationServiceRedisStoreDurationInMinutes() {
        return fountationServiceRedisStoreDurationInMinutes;
    }

    public void setFountationServiceRedisStoreDurationInMinutes(int fountationServiceRedisStoreDurationInMinutes) {
        this.fountationServiceRedisStoreDurationInMinutes = fountationServiceRedisStoreDurationInMinutes;
    }

    public String getFountationServiceUiUrl() {
        return fountationServiceUiUrl;
    }

    public void setFountationServiceUiUrl(String fountationServiceUiUrl) {
        this.fountationServiceUiUrl = fountationServiceUiUrl;
    }

    public String getFountationServiceSecurityJwtAudience() {
        return fountationServiceSecurityJwtAudience;
    }

    public void setFountationServiceSecurityJwtAudience(String fountationServiceSecurityJwtAudience) {
        this.fountationServiceSecurityJwtAudience = fountationServiceSecurityJwtAudience;
    }

    public String getAuth0ManagementApiAudience() {
        return auth0ManagementApiAudience;
    }

    public void setAuth0ManagementApiAudience(String auth0ManagementApiAudience) {
        this.auth0ManagementApiAudience = auth0ManagementApiAudience;
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
}
