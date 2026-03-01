package id.web.saka.fountation.user.role.client;

import id.web.saka.fountation.account.AccountGrpcMapper;
import id.web.saka.fountation.authorization.user.role.UserRegistrationProto;
import id.web.saka.fountation.authorization.user.role.UserRoleProto;
import id.web.saka.fountation.organization.company.CompanyGrpcMapper;
import id.web.saka.fountation.organization.department.DepartmentGrpcMapper;
import id.web.saka.fountation.user.UserGrpcMapper;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import id.web.saka.fountation.user.role.UserRoleDTO;
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
        AccountGrpcMapper.class,
        CompanyGrpcMapper.class,
        DepartmentGrpcMapper.class
})
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

    // --- UserRoleDTO <-> UserRoleProto ---

    UserRoleDTO toDto(UserRoleProto proto);
    UserRoleProto toProto(UserRoleDTO dto);
}
