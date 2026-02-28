package id.web.saka.fountation.authorization.company.role.permission;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Permission {

    // ADD THESE CONSTRUCTORS
    public Permission() {} // For Jackson
    public Permission(String name) { this.name = name; }

    @JsonProperty("permissionName")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
