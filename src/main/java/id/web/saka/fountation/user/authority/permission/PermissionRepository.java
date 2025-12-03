package id.web.saka.fountation.user.authority.permission;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends ReactiveCrudRepository<Permission, long> {
}
