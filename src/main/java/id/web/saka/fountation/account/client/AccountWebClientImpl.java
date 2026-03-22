package id.web.saka.fountation.account.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component("accountWebClient")
public class AccountWebClientImpl implements AccountClient {

    private static final Logger log = LoggerFactory.getLogger(AccountWebClientImpl.class);
    private final WebClient webClientAccount;

    public AccountWebClientImpl(@Qualifier("webClientAccount") WebClient webClientAccount) {
        this.webClientAccount = webClientAccount;
    }
}
