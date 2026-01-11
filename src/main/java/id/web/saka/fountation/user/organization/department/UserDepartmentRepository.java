package id.web.saka.fountation.user.organization.department;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserDepartmentRepository extends ReactiveCrudRepository<UserDepartment, Long> {

    Mono<UserDepartment> findByUserIdAndCompanyIdAndIsDefault(Long userId, Long companyId, boolean isDefault);

    Flux<UserDepartment> findAllByCompanyIdAndDepartmentId(Long companyId, Long departmentId);

}
