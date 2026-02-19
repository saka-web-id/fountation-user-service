package id.web.saka.fountation.user.account;

import id.web.saka.fountation.user.UserService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import id.web.saka.fountation.user.role.UserRoleService;
import id.web.saka.fountation.util.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserAccountService {

    Logger log = LoggerFactory.getLogger(UserAccountService.class);

    private final UserService userService;

    private final UserAccountMapper userAccountMapper;

    private final UserRoleService userRoleService;

    private final UserDepartmentService userDepartmentService;

    private final ReactiveRedisTemplate<String, UserAccountDTO> redisTemplateUserAccountDTO;

    private final Env env;


    public UserAccountService(UserService userService, UserAccountMapper userAccountMapper, UserRoleService userRoleService, UserDepartmentService userDepartmentService,
                              ReactiveRedisTemplate<String, UserAccountDTO> redisTemplateUserAccountDTO, Env env) {
        this.userService = userService;
        this.userAccountMapper = userAccountMapper;
        this.userRoleService = userRoleService;
        this.userDepartmentService = userDepartmentService;
        this.redisTemplateUserAccountDTO = redisTemplateUserAccountDTO;
        this.env = env;
    }

    public Mono<UserAccountDTO> getUserAccountDTOByEmail(String email) {
        return userService.getUserByEmail(email)
                .doOnNext(user -> log.info("Fetched User for email {}: {}", email, user))
                .flatMap(user -> {
                    log.info("Fetching UserAccountDTO for userId: {}", user.getId());

                    return redisTemplateUserAccountDTO.opsForValue().get(buildCacheKey(user.getId()))
                            .doOnNext(cachedDto -> log.info("Cache hit for userId {}: {}", user.getId(), cachedDto))
                            .onErrorResume(e -> {
                                log.warn("Redis unavailable, fallback to DB: {}", e.getMessage());
                                return Mono.empty();
                            })
                            .switchIfEmpty(
                                    userService.getUserAccountByUserMono(Mono.just(user))
                                            .flatMap(dto ->
                                                    cacheUserAccountDTO(buildCacheKey(user.getId()), dto)
                                                            .onErrorResume(err -> {
                                                                log.warn("Failed to cache in Redis: {}", err.getMessage());
                                                                return Mono.just(dto); // fallback to original DTO
                                                            })
                                            )
                            );
                });
    }

    public Mono<UserAccountDTO> getUserAccountDTOByUserId(Long valueUserId) {

        return redisTemplateUserAccountDTO.opsForValue().get(buildCacheKey(valueUserId))
                .onErrorResume(e -> {
                    log.warn("Redis unavailable, fallback to DB: {}", e.getMessage());
                    return Mono.empty();
                })
                .switchIfEmpty(
                        userService.getUserById(valueUserId)
                                .flatMap(userDTO -> {
                                    return userService.getUserAccountByUserDTOMono(Mono.just(userDTO))
                                            .flatMap(dto -> cacheUserAccountDTO(buildCacheKey(valueUserId), dto));
                                })
                );
    }

    public Mono<UserAccountDTO> updateUserAccount(Long companyId, Long userId, Long valueUserId, UserAccountDTO payload) {
        log.info("updateUserAccount|userId:{}|payload:{}", valueUserId, payload);

        return userService.saveUser(userAccountMapper.toUserDto(payload))
                .flatMap(savedUserDTO ->
                        Mono.when(
                                userRoleService.updateUserRoles(companyId, userId, userAccountMapper.toUserRoleEntity(payload)),
                                userDepartmentService.updateUserDepartment(userAccountMapper.toUserDepartmentEntity(payload))
                        ).thenReturn(payload) // or map back to DTO if needed
                )
                .flatMap(savedUserDTO -> cacheUserAccountDTO(buildCacheKey(savedUserDTO.getId()), savedUserDTO));
    }

    public Mono<UserAccountDTO> addUserAccount(Long companyId, Long userId, UserAccountDTO payload) {
        log.info("addUserAccount|payload:{}", payload);
        return userService.saveUser(userAccountMapper.toUserDto(payload))
                .flatMap(savedUserDTO ->
                        Mono.when(
                                        userRoleService.addUserRole(companyId, userId, userAccountMapper.toUserRoleEntity(payload)),
                                        userDepartmentService.addUserDepartment(userAccountMapper.toUserDepartmentEntity(payload))
                                )
                                .thenReturn(payload)
                )
                .flatMap(savedUserDTO -> cacheUserAccountDTO(buildCacheKey(savedUserDTO.getId()), savedUserDTO));
    }


    private Mono<UserAccountDTO>  cacheUserAccountDTO(String key, UserAccountDTO dto) {
        log.info("Redis cache user {} with dto {} ", key, dto.toString() );

        return redisTemplateUserAccountDTO.opsForValue()
                .set(key, dto, Duration.ofMinutes(env.getFountationServiceRedisStoreDurationInMinutes()))
                .onErrorResume(err -> {
                    log.warn("Failed to cache in Redis: {}", err.getMessage());
                    return Mono.empty();
                })
                .thenReturn(dto);
    }

    private String buildCacheKey(Long valueUserId) {
        return "userAccountDTO:userId:" + valueUserId;
    }

}
