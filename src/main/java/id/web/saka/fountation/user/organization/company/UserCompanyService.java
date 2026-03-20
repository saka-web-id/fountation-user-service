package id.web.saka.fountation.user.organization.company;

import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.company.CompanyRepository;
import id.web.saka.fountation.user.User;
import id.web.saka.fountation.user.UserRepository;
import id.web.saka.fountation.user.UserRequestDTO;
import id.web.saka.fountation.user.role.client.UserRoleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class UserCompanyService {

    Logger log = LoggerFactory.getLogger(UserCompanyService.class);
    private final UserRepository userRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final CompanyRepository companyRepository;
    private final UserRoleClient userRoleClient;
    private final ReactiveRedisTemplate<String, List<CompanyDTO>> redisTemplate;

    private final CompanyMapper companyMapper;

    public UserCompanyService(UserRepository userRepository, 
                              UserCompanyRepository userCompanyRepository, 
                              CompanyRepository companyRepository, 
                              CompanyMapper companyMapper,
                              UserRoleClient userRoleClient,
                              @Qualifier("redisCompanyListTemplate") ReactiveRedisTemplate<String, List<CompanyDTO>> redisTemplate) {
        this.userRepository = userRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRoleClient = userRoleClient;
        this.redisTemplate = redisTemplate;
    }

    public Mono<CompanyDTO> getUserCompanyDefaultByUserId(Long userId) {
        return userCompanyRepository.findByUserIdAndIsDefaultTrue(userId)
                .flatMap(userCompany ->
                        companyRepository.findById(userCompany.getCompanyId())
                                .map(companyMapper::toDto)
                );
    }

    public Flux<CompanyDTO> getUserCompaniesByEmail(Long companyId, Long userId) {
        String cacheKey = "user:companies:userId:" + userId;

        return redisTemplate.opsForValue().get(cacheKey)
                // No more casting! 'data' is already recognized as List<CompanyDTO>
                .flatMapMany(Flux::fromIterable)
                .switchIfEmpty(
                        userRoleClient.getRoleByUserIdAndCompanyId(companyId, userId)
                                .flatMapMany(roleDTO -> {
                                    log.info("getUserCompaniesByEmail|roleDTO:{}", roleDTO);

                                    if (roleDTO.roleId() == 1) {
                                        return companyRepository.findAll();
                                    }
                                    return userCompanyRepository.findAllByUserId(userId)
                                            .flatMap(uc -> companyRepository.findById(uc.getCompanyId()));
                                })
                                .map(companyMapper::toDto)
                                .collectList()
                                .flatMapMany(list ->
                                        redisTemplate.opsForValue().set(cacheKey, list, Duration.ofMinutes(10))
                                                .thenMany(Flux.fromIterable(list))
                                )
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
