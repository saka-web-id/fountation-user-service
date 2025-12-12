package id.web.saka.fountation.user.organization.company;

import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.company.CompanyRepository;
import id.web.saka.fountation.user.User;
import id.web.saka.fountation.user.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserCompanyService {

    private final UserRepository userRepository;
    private final UserCompanyRepository userCompanyRepository;

    private final CompanyRepository companyRepository;

    public UserCompanyService(UserRepository userRepository, UserCompanyRepository userCompanyRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.companyRepository = companyRepository;
    }

    public Flux<CompanyDTO> getUserCompaniesByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMapMany(user ->
                        userCompanyRepository.findAllByUserId(user.getId())
                                .flatMap(userCompany ->
                                        companyRepository.findById(userCompany.getCompanyId())
                                                .map(CompanyDTO::new)
                                )
                );
    }
}
