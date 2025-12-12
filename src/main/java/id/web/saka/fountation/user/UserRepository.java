package id.web.saka.fountation.user;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Flux<User> findByName(String name);

    Mono<User> findByEmail(String email);
}
