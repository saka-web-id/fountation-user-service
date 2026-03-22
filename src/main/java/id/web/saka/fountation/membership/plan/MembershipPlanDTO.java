package id.web.saka.fountation.membership.plan;

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
