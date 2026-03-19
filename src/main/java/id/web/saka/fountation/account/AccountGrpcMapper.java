package id.web.saka.fountation.account;

import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanDTO;
import id.web.saka.fountation.account.membership.plan.MembershipPlanGrpcMapper;
import id.web.saka.fountation.organization.company.CompanyGrpcMapper;
import id.web.saka.fountation.organization.department.DepartmentGrpcMapper;
import id.web.saka.fountation.user.UserGrpcMapper;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class,
        UserGrpcMapper.class,
        CompanyGrpcMapper.class,
        DepartmentGrpcMapper.class,
        MembershipPlanGrpcMapper.class
})
public interface AccountGrpcMapper {

    @Mapping(target = "accountCreatedAt", source = "accountCreatedAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "membershipStartDate", source = "membershipStartDate", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "membershipEndDate", source = "membershipEndDate", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "accountStatus", source = "accountStatus", qualifiedByName = "enumToString")
    @Mapping(target = "accountType", source = "accountType", qualifiedByName = "enumToString")
    @Mapping(target = "membershipStatus", source = "membershipStatus", qualifiedByName = "enumToString")
    AccountMembershipPlanDTO toDTO(AccountMembershipPlanResponse proto);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountStatus", source = "accountStatus", qualifiedByName = "stringToAccountStatus")
    @Mapping(target = "accountType", source = "membershipType", qualifiedByName = "stringToAccountType")
    @Mapping(target = "membershipStatus", source = "membershipStatus", qualifiedByName = "stringToMembershipStatus")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipStartDate", source = "membershipStartDate", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipEndDate", source = "membershipEndDate", qualifiedByName = "toProtoTimestamp")
    AccountProto toProto(AccountDTO dto);

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountStatus", source = "accountStatus", qualifiedByName = "enumToString")
    @Mapping(target = "membershipType", source = "accountType", qualifiedByName = "enumToString")
    @Mapping(target = "membershipStatus", source = "membershipStatus", qualifiedByName = "enumToString")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "membershipStartDate", source = "membershipStartDate", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "membershipEndDate", source = "membershipEndDate", qualifiedByName = "toZonedDateTime")
    AccountDTO toDTO(AccountProto proto);
}
