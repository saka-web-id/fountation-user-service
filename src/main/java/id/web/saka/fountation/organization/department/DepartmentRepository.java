package id.web.saka.fountation.organization.department;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface DepartmentRepository extends ReactiveCrudRepository<Department, Long> {

    public Flux<Department> findAllByCompanyId(Long companyId);
}
