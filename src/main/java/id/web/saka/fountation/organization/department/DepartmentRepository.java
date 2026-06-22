package id.web.saka.fountation.organization.department;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DepartmentRepository extends ReactiveCrudRepository<Department, Long> {

    public Flux<Department> findAllByCompanyId(Long companyId);
}
