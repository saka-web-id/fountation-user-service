package id.web.saka.fountation.user.authority.permission;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface PermissionRepository extends ReactiveCrudRepository<Permission, Long> {

    Flux<Permission> findAllById(Flux<Long> ids);

}
