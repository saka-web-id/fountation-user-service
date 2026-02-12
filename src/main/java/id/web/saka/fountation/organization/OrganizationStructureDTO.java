package id.web.saka.fountation.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.department.DepartmentDTO;

import java.util.List;

public record OrganizationStructureDTO(
        @JsonProperty("companyName") String companyName,
        @JsonProperty("companyAddress") String companyAddress,
        @JsonProperty("companyPhone") String companyPhone,
        @JsonProperty("companyEmail") String companyEmail,
        @JsonProperty("companyWebsite") String companyWebsite,
        @JsonProperty("companyDescription") String companyDescription,
        @JsonProperty("departments") List<DepartmentDTO> departments
) {
    // Custom constructor to build directly from Company and DepartmentDTO list
    public OrganizationStructureDTO(Company company, List<DepartmentDTO> departments) {
        this(
                company.getName(),
                company.getAddress(),
                company.getPhone(),
                company.getEmail(),
                company.getWebsite(),
                company.getDescription(),
                departments
        );
    }
}
