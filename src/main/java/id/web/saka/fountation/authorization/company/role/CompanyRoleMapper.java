package id.web.saka.fountation.authorization.company.role;

import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionDTO;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface CompanyRoleMapper {

    CompanyRoleDTO companyRoleDTOFromCompanyRolePermissionDTO(CompanyRolePermissionDTO companyRolePermissionDTO);

}
