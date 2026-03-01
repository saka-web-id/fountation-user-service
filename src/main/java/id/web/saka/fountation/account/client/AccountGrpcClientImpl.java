package id.web.saka.fountation.account.client;

import id.web.saka.fountation.account.*;
import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanDTO;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("accountGrpcClient")
public class AccountGrpcClientImpl implements AccountClient {

    private static final Logger log = LoggerFactory.getLogger(AccountGrpcClientImpl.class);
    private final AccountGrpcMapper mapper;

    @GrpcClient("fountation-account-service")
    private AccountServiceGrpc.AccountServiceStub accountServiceStub;

    public AccountGrpcClientImpl(AccountGrpcMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(Long companyId, Long userId, Long valueUserId) {
        log.info("Fetching AccountMembershipPlanDTO via gRPC for valueUserId: {} in companyId: {}", valueUserId, companyId);

        AccountMembershipPlanRequest request = AccountMembershipPlanRequest.newBuilder()
                .setCompanyId(companyId)
                .setUserId(userId)
                .setValueUserId(valueUserId)
                .build();

        return Mono.create(sink -> {
            accountServiceStub.getAccountMembershipPlanDetailByUserId(request, new StreamObserver<AccountMembershipPlanResponse>() {
                @Override
                public void onNext(AccountMembershipPlanResponse response) {
                    sink.success(mapper.toDTO(response));
                }

                @Override
                public void onError(Throwable t) {
                    log.error("gRPC error during account membership plan retrieval", t);
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    // Completed
                }
            });
        });
    }

    @Override
    public Mono<UserRegistrationDTO> assignAccountToNewUser(UserRegistrationDTO dto) {
        log.info("Adding New Account via gRPC for user: {}", dto.user().email());

        UserRegistrationRequest request = mapper.toProto(dto);

        return Mono.create(sink -> {
            accountServiceStub.registerUser(request, new StreamObserver<UserRegistrationResponse>() {
                @Override
                public void onNext(UserRegistrationResponse response) {
                    sink.success(mapper.toDTO(response));
                }

                @Override
                public void onError(Throwable t) {
                    log.error("gRPC error during user registration", t);
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    // Completed
                }
            });
        });
    }

    @Override
    public Mono<AccountDTO> getAccountById(Long companyId, Long userId) {
        // gRPC equivalent for deprecated getAccountById can use the same detail method
        log.warn("getAccountById is deprecated, calling getAccountMembershipPlanDetailByUserId via gRPC instead");
        return getAccountMembershipPlanDetailByUserId(companyId, userId, userId)
                .map(plan -> new AccountDTO(
                        plan.accountNumber(),
                        plan.accountStatus(),
                        plan.accountType(),
                        plan.membershipStatus(),
                        plan.accountCreatedAt(),
                        plan.membershipStartDate(),
                        plan.membershipEndDate()
                ));
    }
}
