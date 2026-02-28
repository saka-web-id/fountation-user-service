package id.web.saka.fountation.organization;

import id.web.saka.fountation.authorization.company.role.permission.CompanyRolePermissionService;
import id.web.saka.fountation.organization.company.CompanyRepository;
import id.web.saka.fountation.organization.department.DepartmentMapper;
import id.web.saka.fountation.organization.department.DepartmentRepository;
import id.web.saka.fountation.user.UserService;
import id.web.saka.fountation.user.organization.company.UserCompanyRepository;
import id.web.saka.fountation.user.organization.department.UserDepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    private final UserService userService;
    private final UserCompanyRepository userCompanyRepository;
    private final UserDepartmentRepository userDepartmentRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final CompanyRolePermissionService companyRolePermissionService;



    public OrganizationService(UserService userService,
                               UserCompanyRepository userCompanyRepository,
                               UserDepartmentRepository userDepartmentRepository,
                               CompanyRepository companyRepository,
                               DepartmentRepository departmentRepository,
                               DepartmentMapper departmentMapper,
                               CompanyRolePermissionService companyRolePermissionService) {
        this.userService = userService;
        this.userCompanyRepository = userCompanyRepository;
        this.userDepartmentRepository = userDepartmentRepository;
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.companyRolePermissionService = companyRolePermissionService;
    }


    /*public Mono<OrganizationDTO> getOrganizationDetail(String email) {

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

    }*/

}
