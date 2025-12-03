package id.web.saka.fountation.user.authority.permission;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionManagerRepository extends ReactiveCrudRepository<PermissionManager, long> {
}
