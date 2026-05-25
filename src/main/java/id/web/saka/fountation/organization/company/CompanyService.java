package id.web.saka.fountation.organization.company;

import id.web.saka.fountation.user.UserRepository;
import id.web.saka.fountation.user.organization.company.UserCompany;
import id.web.saka.fountation.user.organization.company.UserCompanyRepository;
import id.web.saka.fountation.user.role.client.UserRoleClient;
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

    private final UserRoleClient userRoleClient;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, UserRepository userRepository, UserCompanyRepository userCompanyRepository, TransactionalOperator transactionalOperator, UserRoleClient userRoleClient) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRepository = userRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.transactionalOperator = transactionalOperator;
        this.userRoleClient = userRoleClient;
    }

    public Mono<CompanyDTO> getCompanyById (Long companyId) {
        log.info("[getCompanyById] Initiated request to fetch company by ID: {}", companyId);

        return companyRepository.findById(companyId)
                .map(companyMapper::toDto);
    }

    public Mono<Boolean> isCompanyNameExists(Company company) {
        return companyRepository.searchCompanyByName(company.getName())
                .flatMap(existingCompany -> {
                    log.info("[isCompanyNameExists] Validation failed: Company name already exists: {}", existingCompany.getName());
                    return Mono.just(true);
                })
                .defaultIfEmpty(false); // if no company found, return false
    }

    public Mono<Company> saveCompany(Company company) {

        return companyRepository.save(company);
    }

    public Mono<Company> saveCompany(Mono<Company> company) {
        return company.flatMap(companyRepository::save);
    }


    public Mono<Company> createCompanyForUser(Company company, String email) {

        log.info("[createCompanyForUser] Initiated request to create company for user with email: {}", email);

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

    public Flux<CompanyDTO> getCompaniesByCompanyIdAndUserId(Long companyId, Long userId, String email) {
        log.info("[getCompaniesByCompanyIdAndUserId] Initiated request to fetch companies for companyId: {} and userId: {}", companyId, userId);

        return userRoleClient.getRoleByUserIdAndCompanyId(companyId, userId)
            .flatMapMany( userRoleDTO -> {
                    if (userRoleDTO.roleId() == 1) { // SUPER_ADMIN
                        return userCompanyRepository.findAll();
                    } else {
                        return userCompanyRepository.findAllByUserId(userId);
                    }
                })
                .flatMap(userCompany ->
                        companyRepository.findById(userCompany.getCompanyId())
                                .map(company -> {
                                    return companyMapper.toDto(company, userCompany.isDefault());
                                })
                );
    }
}
