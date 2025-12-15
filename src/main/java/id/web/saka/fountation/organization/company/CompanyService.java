package id.web.saka.fountation.organization.company;

import id.web.saka.fountation.user.UserRepository;
import id.web.saka.fountation.user.organization.company.UserCompany;
import id.web.saka.fountation.user.organization.company.UserCompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final UserRepository userRepository;

    private final UserCompanyRepository userCompanyRepository;

    private final TransactionalOperator transactionalOperator;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, UserRepository userRepository, UserCompanyRepository userCompanyRepository, TransactionalOperator transactionalOperator) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRepository = userRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.transactionalOperator = transactionalOperator;
    }

    public Mono<CompanyDTO> getCompanyById (Long companyId) {
        System.out.println("Service called with companyId: " + companyId);

        return companyRepository.findById(companyId)
                .map(companyMapper::toDto);
    }

    public Mono<Company> saveCompany(Company company) {

        return companyRepository.save(company);
    }

    public Mono<Company> createCompanyForUser(Company company, String email) {

        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
                ))
                .flatMap(user ->
                        companyRepository.save(company)
                                .flatMap(savedCompany -> {
                                    UserCompany uc = new UserCompany(
                                            user.getId(),
                                            savedCompany.getId()
                                    );

                                    return userCompanyRepository.save(uc)
                                            .thenReturn(savedCompany);
                                })
                )
                .as(transactionalOperator::transactional);
    }
}
