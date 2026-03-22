package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.membership.plan.MembershipPlanGrpcMapper;
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
public interface UserRegistrationMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    // Use the full path from the DTO to ensure no ambiguity
    @Mapping(target = "account.accountStatus", source = "account.accountStatus", qualifiedByName = "stringToAccountStatus")
    @Mapping(target = "account.accountType", source = "account.membershipType", qualifiedByName = "stringToAccountType")
    @Mapping(target = "account.membershipStatus", source = "account.membershipStatus", qualifiedByName = "stringToMembershipStatus")
    UserRegistrationRequest toProto(UserRegistrationDTO dto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    UserRegistrationDTO toDTO(UserRegistrationResponse proto);

}
