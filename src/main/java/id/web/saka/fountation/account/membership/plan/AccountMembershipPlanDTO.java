package id.web.saka.fountation.account.membership.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public record AccountMembershipPlanDTO(
        @JsonProperty("accountNumber") String accountNumber,
        @JsonProperty("accountStatus") String accountStatus,
        @JsonProperty("accountType") String accountType,
        @JsonProperty("accountCreatedAt") ZonedDateTime accountCreatedAt,
        @JsonProperty("membershipPlanId") Long membershipPlanId,
        @JsonProperty("membershipStatus") String membershipStatus,
        @JsonProperty("membershipStartDate") ZonedDateTime membershipStartDate,
        @JsonProperty("membershipEndDate") ZonedDateTime membershipEndDate,
        @JsonProperty("membershipPlan") MembershipPlanDTO membershipPlan
) {}
