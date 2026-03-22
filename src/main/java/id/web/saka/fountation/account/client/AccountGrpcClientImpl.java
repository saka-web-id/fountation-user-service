package id.web.saka.fountation.account.client;

import id.web.saka.fountation.account.AccountGrpcMapper;
import id.web.saka.fountation.account.AccountServiceGrpc;
import id.web.saka.fountation.user.registration.UserRegistrationMapper;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("accountGrpcClient")
public class AccountGrpcClientImpl implements AccountClient {

    private static final Logger log = LoggerFactory.getLogger(AccountGrpcClientImpl.class);
    private final AccountGrpcMapper accountMapper;
    private final UserRegistrationMapper userRegistrationMapper;

    @GrpcClient("fountation-account-service")
    private AccountServiceGrpc.AccountServiceStub accountServiceStub;

    public AccountGrpcClientImpl(AccountGrpcMapper accountMapper, UserRegistrationMapper userRegistrationMapper) {
        this.accountMapper = accountMapper;
        this.userRegistrationMapper = userRegistrationMapper;
    }
}
