package id.web.saka.fountation.authority;


import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.authority.permission.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RolePermissionDTO {

    private static final Logger log = LoggerFactory.getLogger(RolePermissionDTO.class);

    @JsonProperty("roleId")
    private Long roleId;

    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("roleDescription")
    private String roleDescription;

    @JsonProperty("permissions")
    private List<Permission> permissions;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "RolePermissionDTO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
