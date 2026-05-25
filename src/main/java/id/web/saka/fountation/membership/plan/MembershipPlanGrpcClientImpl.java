package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanGrpcClientImpl;
import io.grpc.stub.StreamObserver;
import io.micrometer.context.ContextSnapshot;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("membershipPlanGrpcClient")
public class MembershipPlanGrpcClientImpl implements MembershipPlanClient {

    Logger log = LoggerFactory.getLogger(AccountMembershipPlanGrpcClientImpl.class);

    private final MembershipPlanGrpcMapper membershipPlanGrpcMapper;

    @GrpcClient("fountation-account-service")
    private MembershipPlanServiceGrpc.MembershipPlanServiceStub membershipPlanServiceStub;

    public MembershipPlanGrpcClientImpl(MembershipPlanGrpcMapper membershipPlanGrpcMapper) {
        this.membershipPlanGrpcMapper = membershipPlanGrpcMapper;
    }

    /**
     * @param companyId
     * @param userId
     * @param valueCompanyId
     * @return
     */
    @Override
    public reactor.core.publisher.Flux<MembershipPlanDTO> getMembershipPlanListByCompanyId(Long companyId, Long userId, Long valueCompanyId) {
        log.info("[getMembershipPlanListByCompanyId] Initiated request to fetch membership plan list via gRPC for valueCompanyId: {} in companyId: {}", valueCompanyId, companyId);

        MembershipPlanListRequest request = MembershipPlanListRequest.newBuilder()
                .setCompanyId(companyId)
                .setUserId(userId)
                .setValueCompanyId(valueCompanyId)
                .build();

        return reactor.core.publisher.Flux.create(sink -> {
            membershipPlanServiceStub.getMembershipPlanListByCompanyId(request, new StreamObserver<MembershipPlanListResponse>() {
                @Override
                public void onNext(MembershipPlanListResponse response) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        log.info("[getMembershipPlanListByCompanyId] Successfully retrieved {} membership plans via gRPC for valueCompanyId: {} in companyId: {}", response.getMembershipPlansCount(), valueCompanyId, companyId);
                        response.getMembershipPlansList().forEach(proto -> sink.next(membershipPlanGrpcMapper.toDTO(proto)));
                    }
                }

                @Override
                public void onError(Throwable t) {
                    try (ContextSnapshot.Scope scope = ContextSnapshot.setAllThreadLocalsFrom(sink.contextView())) {
                        log.error("[getMembershipPlanListByCompanyId] Failed to retrieve membership plan list via gRPC for valueCompanyId: {} in companyId: {} due to error: {}", valueCompanyId, companyId, t.getMessage(), t);
                        sink.error(t);
                    }
                }

                @Override
                public void onCompleted() {
                    sink.complete();
                }
            });
        });
    }
}
