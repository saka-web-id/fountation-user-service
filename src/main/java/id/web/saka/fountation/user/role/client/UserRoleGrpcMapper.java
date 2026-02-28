package id.web.saka.fountation.user.role.client;

import id.web.saka.fountation.account.Account;
import id.web.saka.fountation.account.AccountDTO;
import id.web.saka.fountation.authorization.user.role.UserRegistrationProto;
import id.web.saka.fountation.authorization.user.role.UserRoleProto;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import id.web.saka.fountation.user.role.UserRoleDTO;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface UserRoleGrpcMapper {

    // --- UserRegistrationDTO <-> UserRegistrationProto ---

    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    UserRegistrationDTO toDto(UserRegistrationProto proto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    UserRegistrationProto toProto(UserRegistrationDTO dto);

    // --- UserDTO <-> UserProto ---

    @Mapping(target = "lastLoginAt", source = "lastLoginAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updateAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "verified", source = "isVerified")
    UserDTO toDto(id.web.saka.fountation.user.UserProto proto);

    @Mapping(target = "lastLoginAt", source = "lastLoginAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updateAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "isVerified", source = "verified")
    id.web.saka.fountation.user.UserProto toProto(UserDTO dto);

    // --- AccountDTO <-> AccountProto ---

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "membershipStartDate", source = "membershipStartDate", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "membershipEndDate", source = "membershipEndDate", qualifiedByName = "toZonedDateTime")
    AccountDTO toDto(Account.AccountProto proto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipStartDate", source = "membershipStartDate", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipEndDate", source = "membershipEndDate", qualifiedByName = "toProtoTimestamp")
    Account.AccountProto toProto(AccountDTO dto);

    // --- CompanyDTO <-> CompanyProto ---

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    CompanyDTO toDto(id.web.saka.fountation.organization.company.CompanyProto proto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toProtoTimestamp")
    id.web.saka.fountation.organization.company.CompanyProto toProto(CompanyDTO dto);

    // --- DepartmentDTO <-> DepartmentProto ---

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    DepartmentDTO toDto(id.web.saka.fountation.organization.department.DepartmentProto proto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toProtoTimestamp")
    id.web.saka.fountation.organization.department.DepartmentProto toProto(DepartmentDTO dto);

    // --- UserRoleDTO <-> UserRoleProto ---

    UserRoleDTO toDto(UserRoleProto proto);
    UserRoleProto toProto(UserRoleDTO dto);
}
