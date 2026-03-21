package id.web.saka.fountation.user.account;

import id.web.saka.fountation.configbase.fountation.FountationProperties;
import id.web.saka.fountation.user.UserMapper;
import id.web.saka.fountation.user.UserService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import id.web.saka.fountation.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserAccountService {

    Logger log = LoggerFactory.getLogger(UserAccountService.class);

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserAccountMapper userAccountMapper;

    private final UserRoleService userRoleService;

    private final UserDepartmentService userDepartmentService;

    private final ReactiveRedisTemplate<String, UserAccountDTO> redisTemplateUserAccountDTO;

    private final FountationProperties fountationProperties;


    public UserAccountService(UserService userService,
                              UserMapper userMapper,
                              UserAccountMapper userAccountMapper,
                              UserRoleService userRoleService,
                              UserDepartmentService userDepartmentService,
                              @Qualifier("redisUserAccountTemplate") ReactiveRedisTemplate<String, UserAccountDTO> redisTemplateUserAccountDTO,
                              FountationProperties fountationProperties) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userAccountMapper = userAccountMapper;
        this.userRoleService = userRoleService;
        this.userDepartmentService = userDepartmentService;
        this.redisTemplateUserAccountDTO = redisTemplateUserAccountDTO;
        this.fountationProperties = fountationProperties;
    }

    public Mono<UserAccountDTO> getUserAccountDTOByEmail(String email) {
        return userService.getUserByEmail(email)
                .doOnNext(user -> log.info("Fetched User for email {}: {}", email, user))
                .flatMap(user -> {
                    log.info("Fetching UserAccountDTO for userId: {}", user.id());

                    return redisTemplateUserAccountDTO.opsForValue().get(buildCacheKey(user.id()))
                            .doOnNext(cachedDto -> log.info("Cache hit for userId {}: {}", user.id(), cachedDto))
                            .onErrorResume(e -> {
                                log.warn("Redis unavailable, fallback to DB: {}", e.getMessage());
                                return Mono.empty();
                            })
                            .switchIfEmpty(
                                    userService.getUserAccountByUserMono(Mono.just(userMapper.toEntity(user)))
                                            .flatMap(dto ->
                                                    cacheUserAccountDTO(buildCacheKey(user.id()), dto)
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
        return "userAccountDTO:userId:" + valueUserId;
    }

}
