package id.web.saka.fountation.user.organization.company;

import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.company.CompanyRepository;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.UserRepository;
import id.web.saka.fountation.user.UserRequestDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserCompanyService {

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

    public Flux<CompanyDTO> getUserCompaniesByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMapMany(user ->
                        userCompanyRepository.findAllByUserId(user.getId())
                                .flatMap(userCompany ->
                                        companyRepository.findById(userCompany.getCompanyId())
                                                .map(companyMapper::toDto)
                                )
                );
    }

    public Mono<Void> setCompanyForUser(Long userId, UserRequestDTO userRequestDTO) {

        return userCompanyRepository.save(new UserCompany(userId, userRequestDTO.getCompanyId(), true))
                .then(Mono.empty());
    }
}
