package id.web.saka.fountation.organization.company;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Mono<CompanyDTO> getCompanyById (Long companyId) {
        System.out.println("Service called with companyId: " + companyId);

        return companyRepository.findById(companyId)
                .map(companyMapper::toDto);
    }

    public Mono<Company> saveCompany(Company company) {

        return companyRepository.save(company);
    }
}
