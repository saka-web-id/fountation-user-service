package id.web.saka.fountation.organization;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0/user")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/organization/detail")
    public Mono<OrganizationDTO> getOrganizationDetail(@AuthenticationPrincipal Jwt jwt) {

        return organizationService.getOrganizationDetail(jwt.getClaimAsString("https://example.com/email"));
    }

    @GetMapping("/organization/all")
    public Flux<OrganizationStructureDTO> getAllOrganizations(@AuthenticationPrincipal Jwt jwt) {
        // Implementation for retrieving all organizations
        return organizationService.getAllOrganizations(jwt.getClaimAsString("https://example.com/email")); // Placeholder
    }
}
