package id.web.saka.fountation.organization.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class CompanyDTO {

    public CompanyDTO(Company company) {
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyAddress = company.getAddress();
        this.companyPhone = company.getPhone();
        this.companyEmail = company.getEmail();
        this.companyWebsite = company.getWebsite();
        this.companyDescription = company.getDescription();
        this.companyLogoUrl = company.getLogoUrl();
        this.companyTaxId = company.getTaxId();
        this.companyRegistrationId = company.getRegistrationNumber();
        this.companyStatus = company.getStatus();
        this.companyIndustry = company.getIndustry();
        this.companyType = company.getType();
        this.companyCreatedAt = company.getCreatedAt();
        this.companyUpdatedAt = company.getUpdatedAt();
    }

    @JsonProperty("companyId")
    private Long companyId;

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

    @JsonProperty("companyLogoUrl")
    private String companyLogoUrl;

    @JsonProperty("companyTaxId")
    private String companyTaxId;

    @JsonProperty("companyRegistrationId")
    private String companyRegistrationId;

    @JsonProperty("companyStatus")
    private String companyStatus;

    @JsonProperty("companyIndustry")
    private String companyIndustry;

    @JsonProperty("companyType")
    private String companyType;

    @JsonProperty("companyCreatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm z")
    private OffsetDateTime companyCreatedAt;

    @JsonProperty("companyUpdatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm z")
    private OffsetDateTime companyUpdatedAt;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

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

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    public String getCompanyTaxId() {
        return companyTaxId;
    }

    public void setCompanyTaxId(String companyTaxId) {
        this.companyTaxId = companyTaxId;
    }

    public String getCompanyRegistrationId() {
        return companyRegistrationId;
    }

    public void setCompanyRegistrationId(String companyRegistrationId) {
        this.companyRegistrationId = companyRegistrationId;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public OffsetDateTime getCompanyCreatedAt() {
        return companyCreatedAt;
    }

    public void setCompanyCreatedAt(OffsetDateTime companyCreatedAt) {
        this.companyCreatedAt = companyCreatedAt;
    }

    public OffsetDateTime getCompanyUpdatedAt() {
        return companyUpdatedAt;
    }

    public void setCompanyUpdatedAt(OffsetDateTime companyUpdatedAt) {
        this.companyUpdatedAt = companyUpdatedAt;
    }
}
