package id.web.saka.fountation.user.organization.department;

import id.web.saka.fountation.organization.department.Department;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.organization.department.DepartmentMapper;
import id.web.saka.fountation.organization.department.DepartmentRepository;
import id.web.saka.fountation.user.*;
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
public class UserDepartmentService {

    Logger log = LoggerFactory.getLogger(UserDepartmentService.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    private final UserDepartmentRepository userDepartmentRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserRoleClient userRoleClient;

    private final ReactiveRedisTemplate<String, List<UserDTO>> redisTemplateUserList;

    private final ReactiveRedisTemplate<String, List<DepartmentDTO>> redisTemplateDepartmentList;

    public UserDepartmentService(UserDepartmentRepository userDepartmentRepository,
                                 DepartmentRepository departmentRepository,
                                 DepartmentMapper departmentMapper,
                                 UserRepository userRepository,
                                 UserMapper userMapper,
                                 UserRoleClient userRoleClient,
                                 @Qualifier("redisUserListTemplate") ReactiveRedisTemplate<String, List<UserDTO>> redisTemplateUserList,
                                 @Qualifier("redisDepartmentListTemplate") ReactiveRedisTemplate<String, List<DepartmentDTO>> redisTemplateDepartmentList) {
        this.userDepartmentRepository = userDepartmentRepository;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRoleClient = userRoleClient;
        this.redisTemplateUserList = redisTemplateUserList;
        this.redisTemplateDepartmentList = redisTemplateDepartmentList;
    }

    public Flux<DepartmentDTO> getDepartmentsByCompanyId(Long companyId) {
        String cacheKey = "company:departments:" + companyId;

        return redisTemplateDepartmentList.opsForValue().get(cacheKey)
                // No casting needed! 'data' is already recognized as List<DepartmentDTO>
                .flatMapMany(Flux::fromIterable)
                .switchIfEmpty(
                        departmentRepository.findAllByCompanyId(companyId)
                                .map(departmentMapper::toDto)
                                .collectList()
                                .flatMapMany(list ->
                                        redisTemplateDepartmentList.opsForValue().set(cacheKey, list, Duration.ofMinutes(10))
                                                .thenMany(Flux.fromIterable(list))
                                )
                );
    }

    public Mono<Department> saveDepartment(Department department) {
        log.info("Saving department: " + department.toString());

        return departmentRepository.save(department)
                .flatMap(saved -> redisTemplateDepartmentList.delete("company:departments:" + saved.getCompanyId()).thenReturn(saved));
    }

    public Flux<UserDTO> getUsers(Long companyId, Long departmentId, Long adminUserId) {
        String cacheKey = "company:department:users:" + companyId + ":" + departmentId;

        return redisTemplateUserList.opsForValue().get(cacheKey)
                .flatMapMany(Flux::fromIterable)
                .switchIfEmpty(
                        userRoleClient.getRoleByUserIdAndCompanyId(companyId, adminUserId)
                                .flatMapMany(roleDTO -> {
                                    if (roleDTO.roleId() == 1) { // SUPER_ADMIN
                                        log.info("SUPER ADMIN detected. Fetching all users in department: {}", departmentId);
                                        return userDepartmentRepository.findAllByDepartmentId(departmentId);
                                    }
                                    return userDepartmentRepository.findAllByCompanyIdAndDepartmentId(companyId, departmentId);
                                })
                                .flatMap(userDepartment -> userRepository.findById(userDepartment.getUserId()))
                                .map(userMapper::toDto)
                                .collectList()
                                .flatMapMany(list ->
                                        redisTemplateUserList.opsForValue().set(cacheKey, list, Duration.ofMinutes(10))
                                                .thenMany(Flux.fromIterable(list))
                                )
                );
    }

    public Mono<DepartmentDTO> getDepartmentDetail(Long departmentId) {

        return departmentRepository.findById(departmentId)
                .map(departmentMapper::toDto);

    }

    public Mono<Void> setDepartmentForUser(Long userId, UserRequestDTO userRequestDTO) {

        return addUserDepartment(new UserDepartment(userId, userRequestDTO.departmentId(), userRequestDTO.companyId(), false))
                .then(Mono.empty());
    }

    public Mono<DepartmentDTO> getUserDepartmentDefaultByCompanyIdAndUserId(Long companyId, Long userId) {
        return userDepartmentRepository.findByUserIdAndCompanyIdAndIsDefault(userId, companyId, true)
                .doOnNext(userDepartment ->
                        log.info("Fetched UserDepartment (default) for companyId={}, userId={}: {}", companyId, userId, userDepartment)
                )
                .flatMap(userDepartment ->
                        departmentRepository.findById(userDepartment.getDepartmentId())
                                .doOnNext(department ->
                                        log.info("Fetched Department entity for departmentId={}: {}", userDepartment.getDepartmentId(), department)
                                )
                )
                .map(departmentMapper::toDto)
                .doOnNext(departmentDTO ->
                        log.info("Mapped DepartmentDTO: {}", departmentDTO)
                )
                .doOnSubscribe(sub -> log.info("Starting department lookup for companyId={}, userId={}", companyId, userId))
                .doOnError(err -> log.error("Error during department lookup: {}", err.getMessage(), err));
    }

    public Mono<Integer> updateUserDepartment(UserDepartment userDepartmentEntity) {

        log.info("Updating UserDepartment for userId={} to departmentId={}", userDepartmentEntity.getUserId(), userDepartmentEntity.getDepartmentId());

        return userDepartmentRepository.updateDepartmentIdByUserId(userDepartmentEntity.getDepartmentId(), userDepartmentEntity.getUserId());

    }

    public Mono<UserDepartment> addUserDepartment(UserDepartment userDepartment) {
        return userDepartmentRepository.save(userDepartment)
                .doOnNext(savedUserDepartment -> log.info("Saved UserDepartment: {}", savedUserDepartment));
    }

    public Mono<UserDepartment> addUserToDepartment(User user, Department department) {
        log.info("Adding User (ID: {}) to Department (ID: {})", user.getId(), department.getId());

        return userDepartmentRepository.save(new UserDepartment(user.getId(), department.getId(), department.getCompanyId(), true))
                .doOnNext(savedUserDepartment -> log.info("Added User to Department: {}", savedUserDepartment));
    }
}
