package id.web.saka.fountation.authority.permission;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PermissionRepository extends ReactiveCrudRepository<Permission, Long> {

    Flux<Permission> findAllById(Flux<Long> ids);

}
