package id.web.saka.fountation.authorization.company.role;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {
    public enum RoleName { SUPERADMIN, ADMIN, USER, MANAGER, GUEST }

    @JsonProperty("roleName")
    private RoleName roleName;

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
