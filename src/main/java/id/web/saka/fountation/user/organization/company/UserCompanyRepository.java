package id.web.saka.fountation.user.organization.company;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserCompanyRepository extends ReactiveCrudRepository<UserCompany, Long> {

    Mono<UserCompany> findByUserIdAndIsDefault(Long userId, boolean isDefault);

    Flux<UserCompany> findAllByUserId(Long userId);

}
