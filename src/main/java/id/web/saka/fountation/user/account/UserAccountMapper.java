package id.web.saka.fountation.user.account;

import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.organization.department.UserDepartment;
import id.web.saka.fountation.user.role.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    UserDTO toUserDto(UserAccountDTO dto);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "authority.roleId", target = "roleId")
    @Mapping(source = "company.id", target = "companyId")
    UserRole toUserRoleEntity(UserAccountDTO dto);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "company.id", target = "companyId")
    UserDepartment toUserDepartmentEntity(UserAccountDTO dto);

    default ZonedDateTime toOffset(Instant instant) {
        return instant == null ? null :
                instant.atZone(ZoneOffset.UTC);
    }


    // OffsetDateTime (GMT+7) → Instant (UTC)
    default Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }
}
