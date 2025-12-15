package id.web.saka.fountation.organization.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class CompanyDTO {

    public CompanyDTO() {
    }

    /*public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.phone = company.getPhone();
        this.email = company.getEmail();
        this.website = company.getWebsite();
        this.description = company.getDescription();
        this.logoUrl = company.getLogoUrl();
        this.taxId = company.getTaxId();
        this.registrationId = company.getRegistrationNumber();
        this.status = company.getStatus();
        this.industry = company.getIndustry();
        this.type = company.getType();
        this.createdAt = company.getCreatedAt();
        this.updatedAt = company.getUpdatedAt();
    }*/

    @JsonProperty("companyId")
    private Long id;

    @JsonProperty("companyName")
    private String name;

    @JsonProperty("companyAddress")
    private String address;

    @JsonProperty("companyPhone")
    private String phone;

    @JsonProperty("companyEmail")
    private String email;

    @JsonProperty("companyWebsite")
    private String website;

    @JsonProperty("companyDescription")
    private String description;

    @JsonProperty("companyLogoUrl")
    private String logoUrl;

    @JsonProperty("companyTaxId")
    private String taxId;

    @JsonProperty("companyRegistrationId")
    private String registrationId;

    @JsonProperty("companyStatus")
    private String status;

    @JsonProperty("companyIndustry")
    private String industry;

    @JsonProperty("companyType")
    private String type;

    @JsonProperty("companyCreatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm z")
    private OffsetDateTime createdAt;

    @JsonProperty("companyUpdatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm z")
    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
