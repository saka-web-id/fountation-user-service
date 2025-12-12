package id.web.saka.fountation.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.openid.connect.sdk.assurance.evidences.Organization;
import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.department.Department;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import reactor.core.publisher.Flux;

import java.util.List;

public class OrganizationStructureDTO {

    public OrganizationStructureDTO(Company company, List<DepartmentDTO> departments) {
        this.companyName = company.getName();
        this.companyAddress = company.getAddress();
        this.companyPhone = company.getPhone();
        this.companyEmail = company.getEmail();
        this.companyWebsite = company.getWebsite();
        this.companyDescription = company.getDescription();
        this.departments = departments;
    }

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("companyAddress")
    private String companyAddress;

    @JsonProperty("companyPhone")
    private String companyPhone;

    @JsonProperty("companyEmail")
    private String companyEmail;

    @JsonProperty("companyWebsite")
    private String companyWebsite;

    @JsonProperty("companyDescription")
    private String companyDescription;

    @JsonProperty("departments")
    private List<DepartmentDTO> departments;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public List<DepartmentDTO> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentDTO> departments) {
        this.departments = departments;
    }
}
