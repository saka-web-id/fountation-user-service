package id.web.saka.fountation.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public record UserDTO (
        @JsonProperty("userId") Long id,
        @JsonProperty("userEmail") String email,
        @JsonProperty("userName") String name,
        @JsonProperty("userPhone") String phone,
        @JsonProperty("userStatus") String status,
        @JsonProperty("userIsVerified") boolean verified,
        @JsonProperty("userLastLoginAt") ZonedDateTime lastLoginAt,
        @JsonProperty("userCreatedAt") ZonedDateTime createdAt,
        @JsonProperty("userUpdatedAt") ZonedDateTime updateAt,
        @JsonProperty("userLeaderId") Long leaderId,
        @JsonProperty("userNote") String note
) {
}
