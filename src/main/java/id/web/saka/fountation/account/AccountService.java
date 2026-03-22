package id.web.saka.fountation.account;

import id.web.saka.fountation.account.client.AccountClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountClient accountClient;

    public AccountService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }
}
