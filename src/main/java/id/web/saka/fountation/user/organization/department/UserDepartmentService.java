package id.web.saka.fountation.user.organization.department;

import id.web.saka.fountation.organization.department.Department;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.organization.department.DepartmentMapper;
import id.web.saka.fountation.organization.department.DepartmentRepository;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.UserMapper;
import id.web.saka.fountation.user.UserRepository;
import id.web.saka.fountation.user.UserRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserDepartmentService {

    Logger log = LoggerFactory.getLogger(UserDepartmentService.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    private final UserDepartmentRepository userDepartmentRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDepartmentService(UserDepartmentRepository userDepartmentRepository, DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, UserRepository userRepository, UserMapper userMapper) {
        this.userDepartmentRepository = userDepartmentRepository;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Flux<DepartmentDTO> getDepartmentsByCompanyId(Long companyId) {

        log.info("Fetching departments for companyId: " + companyId);

        return departmentRepository.findAllByCompanyId(companyId)
                .map(departmentMapper::toDto).doOnNext(deptDto ->
                        log.info("Fetched DepartmentDTO: " + deptDto)
                );
    }

    public Mono<Department> saveDepartment(Department department) {
        log.info("Saving department: " + department.toString());

        return departmentRepository.save(department);
    }

    public Flux<UserDTO> getUsers(Long companyId, Long departmentId) {

        log.info("Fetching users for companyId: " + companyId + " and departmentId: " + departmentId);

        return userDepartmentRepository.findAllByCompanyIdAndDepartmentId(companyId, departmentId)
                .flatMap(userDepartment -> userRepository.findById(userDepartment.getUserId())
                        .map(userMapper::toDto)).doOnNext(userDto -> log.info("Fetched UserDTO: " + userDto.toString()));

    }

    public Mono<DepartmentDTO> getDepartmentDetail(Long departmentId) {

        return departmentRepository.findById(departmentId)
                .map(departmentMapper::toDto);

    }

    public Mono<Void> setDepartmentForUser(Long userId, UserRequestDTO userRequestDTO) {

        return addUserDepartment(new UserDepartment(userId, userRequestDTO.getDepartmentId(), userRequestDTO.getCompanyId(), false))
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
}
