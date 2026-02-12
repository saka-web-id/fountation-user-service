package id.web.saka.fountation.user.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.account.AccountDTO;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.UserDTO;

public record UserRegistrationDTO(
        @JsonProperty("user") UserDTO user,
        @JsonProperty("account") AccountDTO account,

        @JsonProperty("company") CompanyDTO company,

        @JsonProperty("department") DepartmentDTO department
) {
}
