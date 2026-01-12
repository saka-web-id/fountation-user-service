package id.web.saka.fountation.user.account;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final UserService userService;

    private final AccountService accountService;


    public UserAccountService(UserService userService,
                              AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }





}
