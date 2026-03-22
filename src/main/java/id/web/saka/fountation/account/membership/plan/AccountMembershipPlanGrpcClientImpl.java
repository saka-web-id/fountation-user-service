package id.web.saka.fountation.account.membership.plan;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("accountMembershipPlanGrpcClient")
public class AccountMembershipPlanGrpcClientImpl implements AccountMembershipPlanClient {

    Logger log = LoggerFactory.getLogger(AccountMembershipPlanGrpcClientImpl.class);

    @GrpcClient("fountation-account-service")
    private AccountMembershipPlanServiceGrpc.AccountMembershipPlanServiceStub accountMembershipServiceStub;

    /*private final MembershipPlanGrpcMapper membershipPlanGrpcMapper;*/

    private final AccountMembershipPlanMapper accountMembershipPlanMapper;

    public AccountMembershipPlanGrpcClientImpl(AccountMembershipPlanMapper accountMembershipPlanMapper) {
        this.accountMembershipPlanMapper = accountMembershipPlanMapper;
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
            accountMembershipServiceStub.getAccountMembershipPlanDetailByUserId(request, new StreamObserver<AccountMembershipPlanResponse>() {
                @Override
                public void onNext(AccountMembershipPlanResponse response) {
                    sink.success(accountMembershipPlanMapper.toDTO(response));
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
    public Mono<AccountMembershipPlanDTO> updateAccountMembershipPlan(Long companyId, Long userId, Long valueUserId, id.web.saka.fountation.user.account.UserAccountDTO payload) {
        log.info("Updating AccountMembershipPlan via gRPC for valueUserId: {} in companyId: {}", valueUserId, companyId);

        UpdateAccountMembershipPlanRequest request = accountMembershipPlanMapper.toUpdateProto(
                companyId, userId, valueUserId,
                payload.getAccountStatus(),
                payload.getMembershipStatus(),
                payload.getMembershipPlanId()
        );

        return Mono.create(sink -> {
            accountMembershipServiceStub.updateAccountMembershipPlan(request, new StreamObserver<AccountMembershipPlanResponse>() {
                @Override
                public void onNext(AccountMembershipPlanResponse response) {
                    sink.success(accountMembershipPlanMapper.toDTO(response));
                }

                @Override
                public void onError(Throwable t) {
                    log.error("gRPC error during account membership plan update", t);
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    // Completed
                }
            });
        });
    }

}
