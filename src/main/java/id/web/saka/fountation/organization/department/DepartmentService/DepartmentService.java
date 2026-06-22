package id.web.saka.fountation.organization.department.DepartmentService;

import id.web.saka.fountation.common.messaging.outbox.OutboxService;
import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.department.Department;
import id.web.saka.fountation.organization.department.DepartmentMapper;
import id.web.saka.fountation.organization.department.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final OutboxService outboxService;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, OutboxService outboxService, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.outboxService = outboxService;
        this.departmentMapper = departmentMapper;
    }


    @Transactional
    public Mono<Department> saveDepartment(Company company, Department departmentEntity) {
        departmentEntity.setCompanyId(company.getId());
        return departmentRepository.save(departmentEntity)
                .flatMap(savedDept -> outboxService.writeOutbox("DEPARTMENT", "DEPT-" + savedDept.getId(), "DEPARTMENT_SAVED", departmentMapper.toDto(savedDept))
                        .thenReturn(savedDept));
    }
}
