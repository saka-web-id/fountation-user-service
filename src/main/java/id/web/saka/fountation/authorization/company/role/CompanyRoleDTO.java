package id.web.saka.fountation.authorization.company.role;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyRoleDTO(
        @JsonProperty("roleId") Long roleId,
        @JsonProperty("companyId") Long companyId,
        @JsonProperty("roleName") String roleName,
        @JsonProperty("roleDescription") String roleDescription
) {

}
