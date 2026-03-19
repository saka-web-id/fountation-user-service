package id.web.saka.fountation.organization.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public record CompanyListDTO(
        @JsonProperty("companyId") Long id,
        @JsonProperty("companyName") String name,
        @JsonIgnore
        @JsonProperty("companyAddress") String address,
        @JsonIgnore
        @JsonProperty("companyPhone") String phone,
        @JsonIgnore
        @JsonProperty("companyEmail") String email,
        @JsonIgnore
        @JsonProperty("companyWebsite") String website,
        @JsonIgnore
        @JsonProperty("companyDescription") String description,
        @JsonIgnore
        @JsonProperty("companyLogoUrl") String logoUrl,
        @JsonIgnore
        @JsonProperty("companyTaxId") String taxId,
        @JsonIgnore
        @JsonProperty("companyRegistrationId") String registrationNumber,
        @JsonProperty("companyStatus") String status,
        @JsonIgnore
        @JsonProperty("companyIndustry") String industry,
        @JsonIgnore
        @JsonProperty("companyType") String type,
        @JsonProperty("companyCreatedAt")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX") ZonedDateTime createdAt,
        @JsonIgnore
        @JsonProperty("companyUpdatedAt")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX") ZonedDateTime updatedAt,
        @JsonProperty("companyIsDefault") boolean isDefault
) {

}
