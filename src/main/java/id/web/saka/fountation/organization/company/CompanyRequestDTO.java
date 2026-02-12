package id.web.saka.fountation.organization.company;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyRequestDTO (
        @JsonProperty("companyName") String name,
        @JsonProperty("companyAddress") String address,
        @JsonProperty("companyPhone") String phone,
        @JsonProperty("companyEmail") String email,
        @JsonProperty("companyWebsite") String website,
        @JsonProperty("companyDescription") String description,
        @JsonProperty("companyLogoUrl") String logoUrl,
        @JsonProperty("companyTaxId") String taxId,
        @JsonProperty("companyRegistrationId") String registrationNumber,
        @JsonProperty("companyStatus") String status,
        @JsonProperty("companyIndustry") String industry,
        @JsonProperty("companyType") String type
) {

}
