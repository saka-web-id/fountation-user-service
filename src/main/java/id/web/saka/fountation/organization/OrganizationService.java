package id.web.saka.fountation.organization;

import id.web.saka.fountation.authority.AuthorityService;
import id.web.saka.fountation.authority.role.Role;
import id.web.saka.fountation.organization.company.CompanyRepository;
import id.web.saka.fountation.organization.department.Department;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.organization.department.DepartmentRepository;
import id.web.saka.fountation.user.UserService;
import id.web.saka.fountation.user.organization.company.UserCompanyRepository;
import id.web.saka.fountation.user.organization.department.UserDepartmentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
public class OrganizationService {

    private final UserService userService;
    private final UserCompanyRepository userCompanyRepository;
    private final UserDepartmentRepository userDepartmentRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    private final AuthorityService authorityService;

    public OrganizationService(UserService userService,
                               UserCompanyRepository userCompanyRepository,
                               UserDepartmentRepository userDepartmentRepository,
                               CompanyRepository companyRepository,
                               DepartmentRepository departmentRepository,
                               AuthorityService authorityService) {
        this.userService = userService;
        this.userCompanyRepository = userCompanyRepository;
        this.userDepartmentRepository = userDepartmentRepository;
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
        this.authorityService = authorityService;
    }


    public Mono<OrganizationDTO> getOrganizationDetail(String email) {

        return userService.getUserByEmail(email)
                .flatMap(user ->
                    userCompanyRepository.findByUserIdAndIsDefault(user.getId() , true)
                        .flatMap(userCompany ->
                            companyRepository.findById(userCompany.getCompanyId())
                                .zipWith(
                                    userDepartmentRepository.findByUserIdAndCompanyIdAndDefault(user.getId(), userCompany.getCompanyId(), true)
                                        .flatMap(userDepartment ->
                                            departmentRepository.findById(userDepartment.getDepartmentId())
                                        ),
                                    OrganizationDTO::new
                                )
                        )
                );

    }

    public Flux<OrganizationStructureDTO> getAllOrganizations(String email) {

        return userService.getUserByEmail(email)
                .flatMap(user ->
                        authorityService.getAuthorityByUserId(user.getId())
                                .map(authority -> Tuples.of(user, authority))
                )
                .flatMapMany(tuple -> {

                    var user = tuple.getT1();
                    var authority = tuple.getT2();
                    var role = authority.getRoleName();

                    if (!role.equals(Role.RoleName.ADMIN.name()) &&
                            !role.equals(Role.RoleName.SUPERADMIN.name())) {
                        return Flux.error(new RuntimeException("User has no authority"));
                    }

                    // ✅ ADMIN → only see their own companies
                    if (role.equals(Role.RoleName.ADMIN.name())) {
                        return userCompanyRepository.findAllByUserId(user.getId())
                                .flatMap(userCompany ->
                                        companyRepository.findById(userCompany.getCompanyId())
                                                .flatMap(company ->
                                                        departmentRepository.findAllByCompanyId(company.getId())
                                                                .map(DepartmentDTO::new)
                                                                .collectList()
                                                                .map(departments -> new OrganizationStructureDTO(company, departments))
                                                )
                                );
                    }

                    // ✅ SUPERADMIN → see all companies
                    return companyRepository.findAll()
                            .flatMap(company ->
                                    departmentRepository.findAllByCompanyId(company.getId())
                                            .map(DepartmentDTO::new)
                                            .collectList()
                                            .map(departments -> new OrganizationStructureDTO(company, departments))
                            );
                });
    }

    private Flux<DepartmentDTO> mapToDepartmentDTO(Flux<Department> departments) {
        return departments.map(DepartmentDTO::new);
    }

}
