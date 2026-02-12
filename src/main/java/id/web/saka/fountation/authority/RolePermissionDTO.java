package id.web.saka.fountation.authority;


import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.authority.permission.Permission;

import java.util.List;

public record RolePermissionDTO (
        @JsonProperty("roleId") Long roleId,
        @JsonProperty("roleName") String roleName,
        @JsonProperty("roleDescription") String roleDescription,
        @JsonProperty("permissions") List<Permission> permissions
) {

}
