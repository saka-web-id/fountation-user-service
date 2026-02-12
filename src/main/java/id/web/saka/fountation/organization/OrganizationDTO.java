package id.web.saka.fountation.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.department.Department;

public record OrganizationDTO(
        @JsonProperty("companyName") String companyName,
        @JsonProperty("companyAddress") String companyAddress,
        @JsonProperty("companyPhone") String companyPhone,
        @JsonProperty("companyEmail") String companyEmail,
        @JsonProperty("companyWebsite") String companyWebsite,
        @JsonProperty("companyDescription") String companyDescription,
        @JsonProperty("departmentName") String departmentName,
        @JsonProperty("departmentDescription") String departmentDescription
) {
    // Custom constructor to build directly from Company and Department
    public OrganizationDTO(Company company, Department department) {
        this(
                company.getName(),
                company.getAddress(),
                company.getPhone(),
                company.getEmail(),
                company.getWebsite(),
                company.getDescription(),
                department.getName(),
                department.getDescription()
        );
    }
}
