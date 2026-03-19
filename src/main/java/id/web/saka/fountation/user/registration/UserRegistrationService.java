package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.authorization.auth0.Auth0Service;
import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.company.CompanyService;
import id.web.saka.fountation.organization.department.DepartmentMapper;
import id.web.saka.fountation.organization.department.DepartmentService.DepartmentService;
import id.web.saka.fountation.user.User;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.UserMapper;
import id.web.saka.fountation.user.UserService;
import id.web.saka.fountation.user.organization.company.UserCompanyService;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import id.web.saka.fountation.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

    private final Auth0Service auth0Service;

    private final CompanyMapper companyMapper;

    private final DepartmentMapper departmentMapper;

    private final UserMapper userMapper;

    private MessageSource messageSource;

    private final TransactionalOperator txOperator;

    public UserRegistrationService(CompanyService companyService,
                                   DepartmentService departmentService,
                                   UserService userService,
                                   UserCompanyService userCompanyService,
                                   UserDepartmentService userDepartmentService,
                                   UserRoleService userRoleService,
                                   AccountService accountService,
                                   Auth0Service auth0Service,
                                   CompanyMapper companyMapper, DepartmentMapper departmentMapper, UserMapper userMapper, MessageSource messageSource, TransactionalOperator txOperator) {
        this.companyService = companyService;
        this.departmentService = departmentService;
        this.userService = userService;
        this.userCompanyService = userCompanyService;
        this.userDepartmentService = userDepartmentService;
        this.userRoleService = userRoleService;
        this.accountService = accountService;
        this.auth0Service = auth0Service;
        this.companyMapper = companyMapper;
        this.departmentMapper = departmentMapper;
        this.userMapper = userMapper;
        this.messageSource = messageSource;
        this.txOperator = txOperator;
    }

    public Mono<UserRegistrationDTO> registerUser(Mono<UserRegistrationDTO> payload) {
        return payload
                .flatMap(dto -> {
                    log.info("Registering new user: {}", dto.user().email());
                    return Mono.just(dto);
                })
                .flatMap(this::auth0Registration)     // Registration Value to Auth0*/
                .flatMap(this::validateAndPrepareOrg) // Validasi & Simpan Company/Dept
                .flatMap(this::validateAndSaveUser)   // Validasi & Simpan User
                .flatMap(this::assignRelationships)   // Mapping User to Org & Role
                .flatMap(this::finalizeRegistration)  // Account & Result Mapping
                .as(txOperator::transactional)
                .doOnSuccess(user -> log.info("Successfully registered user and organization: {}", user.user().email()))
                .doOnError(e -> log.error("Registration failed for user", e));
    }

    private Mono<UserRegistrationDTO> auth0Registration(UserRegistrationDTO dto) {
        log.info("auth0Registration: {}", dto);

        return auth0Service.registerUser(dto.user())
                .map(iamId -> {
                    UserDTO updatedUser = new UserDTO(
                            dto.user().id(),
                            dto.user().email(),
                            dto.user().name(),
                            dto.user().phone(),
                            "ACTIVE", // Default status
                            false,    // Default verified
                            dto.user().lastLoginAt(),
                            dto.user().createdAt(),
                            dto.user().updatedAt(),
                            dto.user().leaderId(),
                            dto.user().note() != null ? dto.user().note() : "",
                            iamId,
                            dto.user().password()
                    );
                    return new UserRegistrationDTO(updatedUser, dto.account(), dto.company(), dto.department());
                });
    }

    // Tahap 1: Validasi dan Simpan Organisasi (Company & Dept)
    private Mono<UserRegistrationContextDTO> validateAndPrepareOrg(UserRegistrationDTO dto) {
        Company companyEntity = companyMapper.toEntity(dto.company());

        log.info("validateAndPrepareOrg| userRegistrationDTO:{} |companyEntity:{} ", dto, companyEntity);

        return companyService.isCompanyNameExists(companyEntity)
                .flatMap(exists -> exists
                        ? Mono.error(new RuntimeException("Company name already exists"))
                        : companyService.saveCompany(companyEntity))
                .flatMap(company -> departmentService.saveDepartment(company, departmentMapper.toEntity(dto.department()))
                        .map(dept -> new UserRegistrationContextDTO(dto, company, dept, null))
                );
    }

    // Tahap 2: Validasi dan Simpan Identitas User
    private Mono<UserRegistrationContextDTO> validateAndSaveUser(UserRegistrationContextDTO ctx) {
        log.info("validateAndSaveUser| UserRegistrationContextDTO:{} ", ctx);

        return userService.checkUserExist(ctx.originalDto().user())
                .flatMap(exists -> exists
                        ? Mono.error(new RuntimeException("User email already exists"))
                        : userService.saveUser(ctx.originalDto().user()))
                .map(savedUser -> new UserRegistrationContextDTO(ctx.originalDto(), ctx.company(), ctx.department(), savedUser));
    }

    // Tahap 3: Menghubungkan User dengan Relasi (Company, Dept, Role)
    private Mono<UserRegistrationContextDTO> assignRelationships(UserRegistrationContextDTO ctx) {
        User userEntity = userMapper.toEntity(ctx.savedUser());
        log.info("assignRelationships| UserRegistrationContextDTO: {} | userEntity: {}", ctx, userEntity);

        UserRegistrationDTO regDto = new UserRegistrationDTO(
                ctx.savedUser(),
                ctx.originalDto().account(),
                companyMapper.toDto(ctx.company()),
                departmentMapper.toDto(ctx.department())
        );

        return userCompanyService.addUserToCompany(userEntity, ctx.company())
                .then(userDepartmentService.addUserToDepartment(userEntity, ctx.department()))
                .flatMap(unused -> {
                    // --- START BACKGROUND ROLE ASSIGNMENT ---
                    // We don't use .then() or return this Mono.
                    // We subscribe to it so it runs on its own.
                    userRoleService.assignRoleToNewUser(regDto)
                            .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())
                            .subscribe(
                                    success -> log.info("Background Role Assignment SUCCESS for: {}", ctx.savedUser().email()),
                                    error -> log.error("Background Role Assignment FAILED for: {}", ctx.savedUser().email(), error)
                            );
                    // ------------------------------------------

                    // Return the context immediately so the transaction can COMMIT
                    return Mono.just(ctx);
                });
    }

    // Tahap 4: Assign Account dan Finalisasi
    private Mono<UserRegistrationDTO> finalizeRegistration(UserRegistrationContextDTO ctx) {
        log.info("finalizeRegistration| UserRegistrationContextDTO: {} ", ctx);

        // Fire and Forget Account Service so it doesn't block the DB commit
        accountService.assignAccountToNewUser(
                        ctx.originalDto(),
                        ctx.savedUser(),
                        companyMapper.toDto(ctx.company()),
                        departmentMapper.toDto(ctx.department())
                )
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        success -> log.info("Background Account SUCCESS for: {}", ctx.savedUser().email()),
                        error -> log.error("Background Account FAILED: {}", error.getMessage())
                );

        // Return the DTO immediately to commit the transaction
        return Mono.just(new UserRegistrationDTO(
                ctx.savedUser(),
                ctx.originalDto().account(),
                companyMapper.toDto(ctx.company()),
                departmentMapper.toDto(ctx.department())
        ));
    }
}
