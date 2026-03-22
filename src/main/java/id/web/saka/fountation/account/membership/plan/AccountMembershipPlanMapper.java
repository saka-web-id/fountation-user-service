package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class
})
public interface AccountMembershipPlanMapper {

    @Mapping(target = "companyId", source = "companyId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "valueUserId", source = "valueUserId")
    @Mapping(target = "accountStatus", source = "accountStatus", qualifiedByName = "stringToAccountStatus")
    @Mapping(target = "membershipStatus", source = "membershipStatus", qualifiedByName = "stringToMembershipStatus")
    @Mapping(target = "membershipPlanId", source = "membershipPlanId")
    UpdateAccountMembershipPlanRequest toUpdateProto(Long companyId, Long userId, Long valueUserId, String accountStatus, String membershipStatus, Long membershipPlanId);

    @Mapping(target = "accountStatus", source = "accountStatus", qualifiedByName = "stringFromAccountStatus")
    @Mapping(target = "membershipPlan.features", source = "membershipPlan.features", qualifiedByName = "stringToJson")
    AccountMembershipPlanDTO toDTO(AccountMembershipPlanResponse response);



}
