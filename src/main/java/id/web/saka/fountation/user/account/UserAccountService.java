package id.web.saka.fountation.user.account;

import id.web.saka.fountation.user.UserService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import id.web.saka.fountation.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserAccountService {

    Logger log = LoggerFactory.getLogger(UserAccountService.class);

    private final UserService userService;

    private final UserAccountMapper userAccountMapper;

    private final UserRoleService userRoleService;

    private final UserDepartmentService userDepartmentService;


    public UserAccountService(UserService userService, UserAccountMapper userAccountMapper, UserRoleService userRoleService, UserDepartmentService userDepartmentService) {
        this.userService = userService;
        this.userAccountMapper = userAccountMapper;
        this.userRoleService = userRoleService;
        this.userDepartmentService = userDepartmentService;
    }


    public Mono<UserAccountDTO> getUserAccountDTOByUserId(Long valueUserId) {
        return userService.getUserById(valueUserId)
                .flatMap(userDTO -> {
                    return userService.getUserAccountDTOByEmail(userDTO.getEmail());
                });
    }

    public Mono<UserAccountDTO> updateUserAccount(Long companyId, Long userId, Long valueUserId, UserAccountDTO payload) {
        log.info("updateUserAccount|userId:{}|payload:{}", valueUserId, payload);

        return userService.saveUser(userAccountMapper.toUserDto(payload))
                .flatMap(savedUserDTO ->
                        Mono.when(
                                userRoleService.updateUserRoles(companyId, userId, userAccountMapper.toUserRoleEntity(payload)),
                                userDepartmentService.updateUserDepartment(userAccountMapper.toUserDepartmentEntity(payload))
                        ).thenReturn(payload) // or map back to DTO if needed
                );
    }

    public Mono<UserAccountDTO> addUserAccount(Long companyId, Long userId, UserAccountDTO payload) {
        log.info("addUserAccount|payload:{}", payload);
        return userService.saveUser(userAccountMapper.toUserDto(payload))
                .flatMap(savedUserDTO ->
                        Mono.when(
                                userRoleService.addUserRole(companyId, userId, userAccountMapper.toUserRoleEntity(payload)),
                                userDepartmentService.addUserDepartment(userAccountMapper.toUserDepartmentEntity(payload))
                        ).thenReturn(payload) // or map back to DTO if needed
                );
    }
}
