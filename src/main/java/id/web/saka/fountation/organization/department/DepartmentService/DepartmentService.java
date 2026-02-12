package id.web.saka.fountation.organization.department.DepartmentService;
import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.department.Department;
import id.web.saka.fountation.organization.department.DepartmentRepository;
import id.web.saka.fountation.organization.department.DepartmentStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public Mono<Department> saveDepartment(Company company, Department departmentMono) {
        departmentMono.setCompanyId(company.getId());
        return departmentRepository.save(departmentMono);
    }
}
