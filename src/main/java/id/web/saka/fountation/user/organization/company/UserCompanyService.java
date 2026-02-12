package id.web.saka.fountation.user.organization.company;

import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.company.CompanyRepository;
import id.web.saka.fountation.user.User;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.UserRepository;
import id.web.saka.fountation.user.UserRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserCompanyService {

    Logger log = LoggerFactory.getLogger(UserCompanyService.class);
    private final UserRepository userRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public UserCompanyService(UserRepository userRepository, UserCompanyRepository userCompanyRepository, CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.userRepository = userRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Mono<CompanyDTO> getUserCompanyDefaultByUserId(Long userId) {
        return userCompanyRepository.findByUserIdAndIsDefaultTrue(userId)
                .flatMap(userCompany ->
                        companyRepository.findById(userCompany.getCompanyId())
                                .map(companyMapper::toDto)
                );
    }

    public Flux<CompanyDTO> getUserCompaniesByEmail(Long companyId, Long userId) {
        return userCompanyRepository.findAllByUserId(userId)
                .flatMap(userCompany ->
                        companyRepository.findById(userCompany.getCompanyId())
                                .map(companyMapper::toDto)
                );
    }

    public Mono<Void> setCompanyForUser(Long userId, UserRequestDTO userRequestDTO) {

        return userCompanyRepository.save(new UserCompany(userId, userRequestDTO.companyId(), true))
                .then(Mono.empty());
    }

    public Mono<UserCompany> addUserToCompany(User user, Company company) {
        log.info("Adding user {} to company {}", user, company);

        return userCompanyRepository.save(new UserCompany(user.getId(), company.getId(), true));
    }
}
