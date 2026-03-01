package id.web.saka.fountation.account.membership.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.ZonedDateTime;

public record MembershipPlanDTO(
        Long id,
        Long companyId,
        String name,
        Double price,
        String billingCycle,
        JsonNode features,
        ZonedDateTime createdAt
) {}
