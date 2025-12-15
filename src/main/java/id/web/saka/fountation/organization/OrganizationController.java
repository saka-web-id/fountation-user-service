package id.web.saka.fountation.organization;

import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.company.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class OrganizationController {

    private final OrganizationService organizationService;

    private final CompanyService companyService;

    private final CompanyMapper companyMapper;

    public OrganizationController(OrganizationService organizationService, CompanyService companyService, CompanyMapper companyMapper) {
        this.organizationService = organizationService;
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @GetMapping("/user/organization/detail")
    public Mono<OrganizationDTO> getOrganizationDetail(@AuthenticationPrincipal Jwt jwt) {

        return organizationService.getOrganizationDetail(jwt.getClaimAsString("https://example.com/email"));
    }

    @GetMapping("/user/organization/all")
    public Flux<OrganizationStructureDTO> getAllOrganizations(@AuthenticationPrincipal Jwt jwt) {
        // Implementation for retrieving all organizations
        return organizationService.getAllOrganizations(jwt.getClaimAsString("https://example.com/email")); // Placeholder
    }

    @GetMapping("/user/organization/company/getCompanyById/{companyId}")
    public Mono<CompanyDTO> getCompanyById(@AuthenticationPrincipal Jwt jwt, @org.springframework.web.bind.annotation.PathVariable Long companyId) {
        System.out.println("Controller called with companyId: " + companyId);

        return companyService.getCompanyById(companyId)
                .doOnNext(dto -> System.out.println("Controller received: " + dto.toString()));
    }

    @PostMapping("/user/organization/company/update")
    public Mono<ResponseEntity<CompanyDTO>> updateCompany(@RequestBody Mono<CompanyDTO> payload) {

        return payload
                .map(companyMapper::toEntity)
                .flatMap(companyService::saveCompany)
                .map(companyMapper::toDto)
                .map(ResponseEntity::ok);
    }

}
