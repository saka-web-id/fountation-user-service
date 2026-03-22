package id.web.saka.fountation.user;

import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanService;
import id.web.saka.fountation.authorization.company.role.CompanyRoleService;
import id.web.saka.fountation.configbase.fountation.FountationProperties;
import id.web.saka.fountation.user.account.UserAccountDTO;
import id.web.saka.fountation.user.organization.company.UserCompanyService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ReactiveRedisTemplate<String, UserDTO> redisTemplateUserDTO;
    private final UserCompanyService userCompanyService;
    /*private final CompanyRolePermissionService companyRolePermissionService;*/
    private final CompanyRoleService companyRoleService;
    private final UserDepartmentService userDepartmentService;
    private final MessageSource messageSource;
    private final FountationProperties fountationProperties;
    private final AccountMembershipPlanService accountMembershipPlanService;

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       @Qualifier("redisUserDTOTemplate") ReactiveRedisTemplate<String, UserDTO> redisTemplateUserDTO,
                       UserCompanyService userCompanyService,
                       /*CompanyRolePermissionService companyRolePermissionService,*/
                       CompanyRoleService companyRoleService,
                       UserDepartmentService userDepartmentService,
                       MessageSource messageSource, FountationProperties fountationProperties,
                       AccountMembershipPlanService accountMembershipPlanService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.redisTemplateUserDTO = redisTemplateUserDTO;
        this.userCompanyService = userCompanyService;
        /*this.companyRolePermissionService = companyRolePermissionService;*/
        this.companyRoleService = companyRoleService;
        this.userDepartmentService = userDepartmentService;
        this.messageSource = messageSource;
        this.fountationProperties = fountationProperties;
        this.accountMembershipPlanService = accountMembershipPlanService;
    }

    public Mono<UserDTO> getUserById(Long id) {
        String key = "userDTO:userId:" + id;
        return redisTemplateUserDTO.opsForValue().get(key)
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(userRepository.findById(id)
                        .map(userMapper::toDto)
                        .flatMap(dto -> cacheUserDTO(key, dto)));
    }

    public Mono<User> getUserEntityById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserDTO> getUserByEmail(String email) {
        String key = "userDTO:email:" + email;
        return redisTemplateUserDTO.opsForValue().get(key)
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(userRepository.findByEmail(email)
                        .map(userMapper::toDto)
                        .flatMap(dto -> cacheUserDTO(key, dto)));
    }

    public Mono<Long> getUserIdByEmail(String email) {
        return userRepository.findByEmail(email).map(User::getId);
    }

    public Mono<UserDTO> saveUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userRepository.save(user)
                .map(userMapper::toDto)
                .flatMap(dto -> {
                    String keyId = "userDTO:userId:" + dto.id();
                    String keyEmail = "userDTO:email:" + dto.email();
                    return cacheUserDTO(keyId, dto)
                            .then(cacheUserDTO(keyEmail, dto))
                            .thenReturn(dto);
                });
    }

    public Mono<? extends UserDTO> addUser(UserRequestDTO userRequestDTO) {
        return userRepository
                .save(userMapper.requestToEntity(userRequestDTO))
                .map(userMapper::toDto)
                .flatMap(userDTO ->
                        Mono.when(
                                userCompanyService.setCompanyForUser(userDTO.id(), userRequestDTO),
                                userDepartmentService.setDepartmentForUser(userDTO.id(), userRequestDTO)
                        ).thenReturn(userDTO)
                ).flatMap(savedUserDTO -> cacheUserDTO(buildCacheKey(savedUserDTO.id()), savedUserDTO));
    }

    public Mono<UserAccountDTO> getUserAccountByUserDTOMono(Mono<UserDTO> userDTOMono) {
        return getUserAccountByUserMono(userDTOMono.map(userMapper::toEntity));
    }

    public Mono<Boolean> checkUserExist(UserDTO user) {
        return userRepository.findByName(user.name())
                .hasElements(); // ✅ returns Mono<Boolean>
    }

    public Mono<UserAccountDTO> getUserAccountByUserMono(Mono<User> userMono) {
        return userMono
                .flatMap(user ->
                        userCompanyService.getUserCompanyDefaultByUserId(user.getId())
                                .doOnNext(userCompany -> log.info("Fetched UserCompany for : {}", userCompany))
                                .flatMap(userCompany ->
                                        companyRoleService.getAuthorityByCompanyIdAndUserId(userCompany.id(), user.getId())
                                                .doOnNext(rolePermissionDTO -> log.info("Fetched RolePermission for : {}", rolePermissionDTO))
                                                .flatMap(rolePermissionDTO -> {
                                                    if (rolePermissionDTO == null) {
                                                        return Mono.error(new RuntimeException(
                                                                messageSource.getMessage("error.user.no.authority", null, null)
                                                        ));
                                                    }
                                                    // ✅ return the chain here
                                                    return userDepartmentService
                                                            .getUserDepartmentDefaultByCompanyIdAndUserId(userCompany.id(), user.getId())
                                                            .doOnNext(departmentDTO -> log.info("Fetched Department for email {}: {}", user.getEmail(), departmentDTO))
                                                            .flatMap(departmentDTO ->
                                                                    accountMembershipPlanService.getAccountMembershipPlanDetailByUserId(userCompany.id(), user.getId(), user.getId())
                                                                            .doOnNext(accountDTO -> log.info("Fetched Account for email {}: {}", user.getEmail(), accountDTO))
                                                                            .map(accountDTO -> {
                                                                                UserAccountDTO dto = new UserAccountDTO(
                                                                                        user, accountDTO, rolePermissionDTO, userCompany, departmentDTO
                                                                                );
                                                                                log.info("Built UserAccountDTO for email {}: {}", user.getEmail(), dto);
                                                                                return dto;
                                                                            })
                                                            );
                                                })
                                )
                );

    }

    private Mono<UserDTO> cacheUserDTO(String key, UserDTO dto) {
        log.info("Redis cache user {} with dto {} ", key, dto.toString() );

        return redisTemplateUserDTO.opsForValue()
                .set(
                        key,
                        dto,
                        Duration.ofMinutes(fountationProperties.getService().getRedis().getStore().getDuration().getMinutes())
                )
                .onErrorResume(err -> {
                    log.warn("Failed to cache in Redis: {}", err.getMessage());
                    return Mono.empty();
                })
                .thenReturn(dto);
    }

    private String buildCacheKey(Long valueUserId) {
        return "userDTO:userId:" + valueUserId;
    }



}
