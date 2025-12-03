package id.web.saka.fountation.user;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, long> {

    Flux<User> findByName(String name);

}
