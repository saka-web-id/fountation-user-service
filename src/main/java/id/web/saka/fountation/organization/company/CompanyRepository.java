package id.web.saka.fountation.organization.company;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, Long> {
    Mono<Company> save(Company company);

    Mono<Company> searchCompanyByName(String companyName);
}
