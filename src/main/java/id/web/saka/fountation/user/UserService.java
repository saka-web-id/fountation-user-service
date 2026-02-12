package id.web.saka.fountation.user;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.authority.RolePermissionService;
import id.web.saka.fountation.user.account.UserAccountDTO;
import id.web.saka.fountation.user.organization.company.UserCompanyService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final RolePermissionService rolePermissionService;

    private MessageSource messageSource;

    private final UserMapper userMapper;

    private final UserDepartmentService userDepartmentService;

    private final UserCompanyService userCompanyService;

    private final AccountService accountService;

    public UserService(UserRepository userRepository,
                       RolePermissionService rolePermissionService,
                       MessageSource messageSource,
                       UserMapper userMapper,
                       UserDepartmentService userDepartmentService,
                       UserCompanyService userCompanyService,
                       AccountService accountService) {
        this.userRepository = userRepository;
        this.rolePermissionService = rolePermissionService;
        this.messageSource = messageSource;
        this.userMapper = userMapper;
        this.userDepartmentService = userDepartmentService;
        this.userCompanyService = userCompanyService;
        this.accountService = accountService;
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<UserAccountDTO> getUserAccountDTOByEmail(String email) {
        return getUserByEmail(email)
                .doOnNext(user -> log.info("Fetched User for email {}: {}", email, user))
                .flatMap(user -> getUserAccountByUserMono(Mono.just(user)));
    }

    public Mono<UserDTO> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto);
    }

    public Mono<? extends UserDTO> saveUser(UserDTO userDTO) {
        log.info("Saving user: {}", userDTO);

        return userRepository.save(userMapper.toEntity(userDTO))
                .map(userMapper::toDto);
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
                );
    }

    public Mono<UserAccountDTO> getUserAccountByUserMono(Mono<User> userMono) {
        return userMono
                .flatMap(user ->
                        userCompanyService.getUserCompanyDefaultByUserId(user.getId())
                                .doOnNext(userCompany -> log.info("Fetched UserCompany for email {}: {}", user.getEmail(), userCompany))
                                .flatMap(userCompany ->
                                        rolePermissionService.getAuthorityByCompanyIdAndUserId(userCompany.id(), user.getId())
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

    public Mono<Boolean> isUserNameExists(UserDTO user) {
        return userRepository.findByName(user.name())
                .hasElements(); // ✅ returns Mono<Boolean>
    }

}
