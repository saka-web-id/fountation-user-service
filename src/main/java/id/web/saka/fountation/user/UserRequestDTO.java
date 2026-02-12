package id.web.saka.fountation.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequestDTO (
        @JsonProperty("userEmail") String email,
        @JsonProperty("userName") String name,
        @JsonProperty("userPhone") String phone,
        @JsonProperty("userStatus") String status,
        @JsonProperty("userIsVerified") boolean isVerified,
        @JsonProperty("companyId") Long companyId,
        @JsonProperty("departmentId") Long departmentId,
        @JsonProperty("userNote") String note
) {
}
