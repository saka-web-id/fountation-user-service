package id.web.saka.fountation.authority.permission;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Permission {

    @JsonProperty("permissionName")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
