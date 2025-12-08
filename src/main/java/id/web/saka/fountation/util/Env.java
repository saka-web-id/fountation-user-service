package id.web.saka.fountation.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Env {

    @Value("${fountation.service.account.url}")
    private String fountationServiceAccountUrl;

    public String getFountationServiceAccountUrl() {
        return fountationServiceAccountUrl;
    }

    public void setFountationServiceAccountUrl(String fountationServiceAccountUrl) {
        this.fountationServiceAccountUrl = fountationServiceAccountUrl;
    }

}
