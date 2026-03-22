package id.web.saka.fountation.user.account;

import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.organization.department.UserDepartment;
import id.web.saka.fountation.user.role.UserRoleDTO;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class, EnumMapper.class })
public interface UserAccountMapper {

    UserDTO toUserDto(UserAccountDTO dto);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "authority.roleId", target = "roleId")
    @Mapping(source = "company.id", target = "companyId")
    UserRoleDTO toUserRoleEntity(UserAccountDTO dto);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "company.id", target = "companyId")
    UserDepartment toUserDepartmentEntity(UserAccountDTO dto);
}
