package id.web.saka.fountation.user.organization.department;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserDepartmentRepository extends ReactiveCrudRepository<UserDepartment, Long> {

    Mono<UserDepartment> findByUserIdAndCompanyIdAndDefault(Long userId, Long companyId, boolean isDefault);
}
