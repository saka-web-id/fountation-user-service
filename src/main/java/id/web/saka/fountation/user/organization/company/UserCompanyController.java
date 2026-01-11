package id.web.saka.fountation.user.organization.company;

import id.web.saka.fountation.organization.company.CompanyDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v0")
public class UserCompanyController {

    private final UserCompanyService userCompanyService;

    public UserCompanyController(UserCompanyService userCompanyService) {
        this.userCompanyService = userCompanyService;
    }

    @RequestMapping("/user/companies/list/companyId/{companyId}/userId/{userId}")
    public Flux<CompanyDTO> getUserCompanies(@PathVariable Long companyId, @PathVariable Long userId) {

        return userCompanyService.getUserCompaniesByEmail(companyId, userId);
    }

}
