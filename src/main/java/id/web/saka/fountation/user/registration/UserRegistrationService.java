package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.company.CompanyService;
import id.web.saka.fountation.organization.department.DepartmentMapper;
import id.web.saka.fountation.organization.department.DepartmentService.DepartmentService;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.UserMapper;
import id.web.saka.fountation.user.UserService;
import id.web.saka.fountation.user.UserStatus;
import id.web.saka.fountation.user.organization.company.UserCompanyService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import id.web.saka.fountation.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
public class UserRegistrationService {

    Logger log = LoggerFactory.getLogger(UserRegistrationService.class);

    private final CompanyService companyService;

    private final DepartmentService departmentService;

    private final UserService userService;

    private final UserCompanyService  userCompanyService;

    private final UserDepartmentService userDepartmentService;

    private final UserRoleService userRoleService;

    private final AccountService accountService;

    private final CompanyMapper companyMapper;

    private final DepartmentMapper departmentMapper;

    private final UserMapper userMapper;

    private MessageSource messageSource;

    private final TransactionalOperator txOperator;

    public UserRegistrationService(CompanyService companyService, DepartmentService departmentService, UserService userService, UserCompanyService userCompanyService, UserDepartmentService userDepartmentService, UserRoleService userRoleService, AccountService accountService, CompanyMapper companyMapper, DepartmentMapper departmentMapper, UserMapper userMapper, MessageSource messageSource, TransactionalOperator txOperator) {
        this.companyService = companyService;
        this.departmentService = departmentService;
        this.userService = userService;
        this.userCompanyService = userCompanyService;
        this.userDepartmentService = userDepartmentService;
        this.userRoleService = userRoleService;
        this.accountService = accountService;
        this.companyMapper = companyMapper;
        this.departmentMapper = departmentMapper;
        this.userMapper = userMapper;
        this.messageSource = messageSource;
        this.txOperator = txOperator;
    }

    public Mono<UserRegistrationDTO> registerUser(Mono<UserRegistrationDTO> payload) {
        return payload.flatMap(dto ->
                // Validate company existence
                companyService.isCompanyNameExists(companyMapper.toEntity(dto.company()))
                        .flatMap(companyExist -> {
                            if (companyExist) {
                                log.info("Company name already exists: {}", dto.company().name());
                                return Mono.error(new RuntimeException("Company name already exists"));
                            }

                            // Save company
                            return companyService.saveCompany(companyMapper.toEntity(dto.company()))
                                    .flatMap(company ->
                                            // Save department
                                            departmentService.saveDepartment(company, departmentMapper.toEntity(dto.department()))
                                                    .flatMap(department ->
                                                            // Validate user existence
                                                            userService.isUserNameExists(dto.user())
                                                                    .flatMap(userExist -> {
                                                                        if (userExist) {
                                                                            log.info("User email already exists: {}", dto.user().email());
                                                                            return Mono.error(new RuntimeException("User email already exists"));
                                                                        }

                                                                        // Save user
                                                                        return userService.saveUser(dto.user())
                                                                                .flatMap(userDTO ->
                                                                                        // Assign user to company and department
                                                                                        userCompanyService.addUserToCompany(userMapper.toEntity(userDTO), company)
                                                                                                .then(userDepartmentService.addUserToDepartment(userMapper.toEntity(userDTO), department))
                                                                                                .then(userRoleService.assignRoleToNewUser(dto, userDTO, companyMapper.toDto(company), departmentMapper.toDto(department)))
                                                                                                .flatMap(dtoUpdate ->
                                                                                                        // Assign account
                                                                                                        accountService.assignAccountToNewUser(dtoUpdate, userDTO, companyMapper.toDto(company), departmentMapper.toDto(department))
                                                                                                                .map(userRegistrationDTO -> {
                                                                                                                    log.info("Successfully registered user: {}", userDTO);
                                                                                                                    return new UserRegistrationDTO(
                                                                                                                            userDTO,
                                                                                                                            userRegistrationDTO.account(),
                                                                                                                            companyMapper.toDto(company),
                                                                                                                            departmentMapper.toDto(department)
                                                                                                                    );
                                                                                                                })
                                                                                                )
                                                                                );
                                                                    })
                                                    )
                                    );
                        })
        ).as(txOperator::transactional);
    }
}
