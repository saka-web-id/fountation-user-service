package id.web.saka.fountation.account.membership.plan;

import io.grpc.stub.StreamObserver;
import io.micrometer.context.ContextSnapshot;
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
        log.info("[getAccountMembershipPlanDetailByUserId] Initiated request to fetch account membership plan detail via gRPC for valueUserId: {} in companyId: {}", valueUserId, companyId);

        AccountMembershipPlanRequest request = AccountMembershipPlanRequest.newBuilder()
                .setCompanyId(companyId)
                .setUserId(userId)
                .setValueUserId(valueUserId)
                .build();

        return Mono.create(sink -> {
            accountMembershipServiceStub.getAccountMembershipPlanDetailByUserId(request, new StreamObserver<AccountMembershipPlanResponse>() {
                @Override
                public void onNext(AccountMembershipPlanResponse response) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        AccountMembershipPlanDTO dto = accountMembershipPlanMapper.toDTO(response);
                        log.info("[getAccountMembershipPlanDetailByUserId] Successfully retrieved account membership plan detail via gRPC for valueUserId: {} in companyId: {}", valueUserId, companyId);
                        sink.success(dto);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        log.error("[getAccountMembershipPlanDetailByUserId] Failed to retrieve account membership plan detail via gRPC for valueUserId: {} in companyId: {} due to error: {}", valueUserId, companyId, t.getMessage(), t);
                        sink.error(t);
                    }
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
        log.info("[updateAccountMembershipPlan] Initiated request to update account membership plan via gRPC for valueUserId: {} in companyId: {}", valueUserId, companyId);

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
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        AccountMembershipPlanDTO dto = accountMembershipPlanMapper.toDTO(response);
                        log.info("[updateAccountMembershipPlan] Successfully updated account membership plan via gRPC for valueUserId: {} in companyId: {}", valueUserId, companyId);
                        sink.success(dto);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        log.error("[updateAccountMembershipPlan] Failed to update account membership plan via gRPC for valueUserId: {} in companyId: {} due to error: {}", valueUserId, companyId, t.getMessage(), t);
                        sink.error(t);
                    }
                }

                @Override
                public void onCompleted() {
                    // Completed
                }
            });
        });
    }

}
