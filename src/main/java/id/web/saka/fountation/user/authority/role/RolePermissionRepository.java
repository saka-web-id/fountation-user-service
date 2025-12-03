package id.web.saka.fountation.user.authority.role;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends ReactiveCrudRepository<RolePermission, long> {
}
