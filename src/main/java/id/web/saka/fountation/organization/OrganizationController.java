package id.web.saka.fountation.organization;

import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.company.CompanyRequestDTO;
import id.web.saka.fountation.organization.company.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v0")
public class OrganizationController {

    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);

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

    @GetMapping("/user/organization/company/list/{companyId}")
    public Flux<CompanyDTO> getCompanyById(@AuthenticationPrincipal Jwt jwt, @PathVariable Long companyId) {
        log.info("Controller called with companyId: " + companyId);

        if(companyId == 0) {
            return companyService.getCompaniesByEmailAdmin(jwt.getClaimAsString("https://example.com/email"));
        } else {
            return companyService.getCompanyById(companyId).flux();
        }
    }

    @PostMapping("/user/organization/company/update")
    public Mono<ResponseEntity<CompanyDTO>> updateCompany(@RequestBody Mono<CompanyDTO> payload) {

        return payload
                .map(companyMapper::toEntity)
                .flatMap(companyService::saveCompany)
                .map(companyMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/user/organization/company/add")
    public Mono<ResponseEntity<CompanyDTO>> addCompany(@AuthenticationPrincipal Jwt jwt, @RequestBody Mono<CompanyRequestDTO> payload) {

        String email = jwt.getClaimAsString("https://example.com/email");

        return payload
                .map(companyMapper::requestToEntity)
                .flatMap(company -> companyService.createCompanyForUser(company, email))
                .map(companyMapper::toDto)
                .map(saved ->
                        ResponseEntity
                                .created(URI.create(
                                        "/api/v0/user/organization/company/detail/" + saved.getId()
                                ))
                                .body(saved)
                );
    }

    @GetMapping("/user/organization/company/detail/{companyId}")
    public Mono<CompanyDTO> getCompanyDetail(@PathVariable Long companyId) {

        return companyService.getCompanyById(companyId);
    }

}
