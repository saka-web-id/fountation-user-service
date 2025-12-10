package id.web.saka.fountation.organization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrganizationDTO {

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

    @JsonProperty("departmentName")
    private String departmentName;

    @JsonProperty("departmentDescription")
    private String departmentDescription;

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }
}
