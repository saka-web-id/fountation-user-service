package id.web.saka.fountation.user;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionService;
import id.web.saka.fountation.user.account.UserAccountDTO;
import id.web.saka.fountation.user.organization.company.UserCompanyService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import id.web.saka.fountation.util.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final CompanyRolePermissionService companyRolePermissionService;

    private MessageSource messageSource;

    private final UserMapper userMapper;

    private final UserDepartmentService userDepartmentService;

    private final UserCompanyService userCompanyService;

    private final AccountService accountService;

    private final ReactiveRedisTemplate<String, UserDTO> redisTemplateUserDTO;

    private final Env env;

    public UserService(UserRepository userRepository,
                       CompanyRolePermissionService companyRolePermissionService,
                       MessageSource messageSource,
                       UserMapper userMapper,
                       UserDepartmentService userDepartmentService,
                       UserCompanyService userCompanyService,
                       AccountService accountService,
                       ReactiveRedisTemplate<String, UserDTO> redisTemplateUserDTO, Env env) {
        this.userRepository = userRepository;
        this.companyRolePermissionService = companyRolePermissionService;
        this.messageSource = messageSource;
        this.userMapper = userMapper;
        this.userDepartmentService = userDepartmentService;
        this.userCompanyService = userCompanyService;
        this.accountService = accountService;
        this.redisTemplateUserDTO = redisTemplateUserDTO;
        this.env = env;
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<UserDTO> getUserById(Long userId) {

        return redisTemplateUserDTO.opsForValue().get(buildCacheKey(userId))
            .onErrorResume(e -> {
                log.warn("Redis unavailable, fallback to DB: {}", e.getMessage());
                return Mono.empty();
            })
            .switchIfEmpty(
                    userRepository.findById(userId)
                            .map(userMapper::toDto)
            );
    }

    public Mono<? extends UserDTO> saveUser(UserDTO userDTO) {
        log.info("Saving user: {}", userDTO);

        return userRepository.save(userMapper.toEntity(userDTO))
                .map(userMapper::toDto)
                .flatMap(savedUserDTO -> cacheUserDTO(buildCacheKey(savedUserDTO.id()), savedUserDTO));
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

    public Mono<Boolean> isUserNameExists(UserDTO user) {
        return userRepository.findByName(user.name())
                .hasElements(); // ✅ returns Mono<Boolean>
    }

    public Mono<UserAccountDTO> getUserAccountByUserMono(Mono<User> userMono) {
        return userMono
                .flatMap(user ->
                        userCompanyService.getUserCompanyDefaultByUserId(user.getId())
                                .doOnNext(userCompany -> log.info("Fetched UserCompany for email {}: {}", user.getEmail(), userCompany))
                                .flatMap(userCompany ->
                                        companyRolePermissionService.getAuthorityByCompanyIdAndUserId(userCompany.id(), user.getId())
                                                .doOnNext(rolePermissionDTO -> log.info("Fetched RolePermission for email {}: {}", user.getEmail(), rolePermissionDTO))
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
                                                                    accountService.getAccountById(userCompany.id(), user.getId())
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
                .set(key, dto, Duration.ofMinutes(env.getFountationServiceRedisStoreDurationInMinutes()))
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
