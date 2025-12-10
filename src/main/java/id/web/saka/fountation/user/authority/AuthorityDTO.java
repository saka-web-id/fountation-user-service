package id.web.saka.fountation.user.authority;


import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.user.authority.permission.Permission;
import id.web.saka.fountation.user.authority.role.Role;
import reactor.core.publisher.Flux;

public class AuthorityDTO {

    public AuthorityDTO(Long UserId, Role role, Flux<Permission> permissions) {
        this.userId = UserId;
        this.roleId = role.getId();
        this.roleName = role.getRoleName();
        this.roleDescription = role.getDescription();
        this.permissions = permissions;
    }

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("roleId")
    private Long roleId;

    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("roleDescription")
    private String roleDescription;

    @JsonProperty("permissions")
    private Flux<Permission> permissions;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Flux<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Flux<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "AuthorityDTO{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", permissions=" +
                permissions.subscribe(
                element -> System.out.println(element.toString()), // Consumer for each element
                error -> System.err.println("Error: " + error), // Consumer for errors (optional)
                () -> System.out.println("Flux completed.") // Runnable for completion (optional)
        );
    }
}
