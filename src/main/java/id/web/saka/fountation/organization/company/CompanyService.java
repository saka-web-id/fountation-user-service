package id.web.saka.fountation.organization.company;

import id.web.saka.fountation.user.UserRepository;
import id.web.saka.fountation.user.organization.company.UserCompany;
import id.web.saka.fountation.user.organization.company.UserCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompanyService {

    Logger log = LoggerFactory.getLogger(CompanyService.class);

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
        log.info("Service called with companyId: " + companyId);

        return companyRepository.findById(companyId)
                .map(companyMapper::toDto);
    }

    public Mono<Company> saveCompany(Company company) {

        return companyRepository.save(company);
    }

    public Mono<Company> createCompanyForUser(Company company, String email) {

        log.info("Creating company for user with email: " + email);

        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
                ))
                .flatMap(user ->
                        companyRepository.save(company)
                                .flatMap(savedCompany -> {
                                    UserCompany uc = new UserCompany(
                                            user.getId(),
                                            savedCompany.getId(),
                                            true
                                    );

                                    return userCompanyRepository.save(uc)
                                            .thenReturn(savedCompany);
                                })
                )
                .as(transactionalOperator::transactional);
    }

    public Flux<CompanyDTO> getCompaniesByEmailAdmin(String email) {
        log.info("Fetching companies for admin user with email: {}", email);

        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
                ))
                .flatMapMany(user ->
                        userCompanyRepository.findAllByUserId(user.getId())
                                .flatMap(userCompany ->
                                        companyRepository.findById(userCompany.getCompanyId())
                                                .map(company -> {
                                                    CompanyDTO dto = companyMapper.toDto(company);
                                                    dto.setDefault(userCompany.isDefault()); // ✅ add flag
                                                    return dto;
                                                })
                                )
                );
    }
}
