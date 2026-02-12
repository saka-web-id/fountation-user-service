package id.web.saka.fountation.account;

import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.User;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private Mono<WebClient> webClientAccount;

    public AccountService(@Qualifier("webClientAccount") Mono<WebClient> webClientAccount) {
        this.webClientAccount = webClientAccount;
    }

    public Mono<AccountDTO> getAccountById(Long companyId, Long userId) {
        return webClientAccount.flatMap(webClient ->
                webClient.get()
                        .uri("/api/v0/account/user/membership/plan/detail/companyId/" + companyId + "/userId/" + userId + "/valueUserId/" + userId)
                        .retrieve()
                        .bodyToMono(AccountDTO.class)
                        .doOnNext(json -> log.info("Raw JSON: {}", json))
        );
    }

    public Mono<UserRegistrationDTO> assignAccountToNewUser(UserRegistrationDTO dto, UserDTO userDTO, CompanyDTO companyDTO, DepartmentDTO departmentDTO) {
        log.info("Adding New Account for user: {}", dto.toString());

        return webClientAccount.flatMap(webClient ->
                webClient.post()
                        .uri("/api/v0/account/user/registration")
                        .bodyValue(new UserRegistrationDTO(userDTO, dto.account(), companyDTO, departmentDTO)) // send DTO in request body
                        .retrieve()
                        .bodyToMono(UserRegistrationDTO.class)
                        .doOnNext(json -> log.info("Raw JSON: {}", json))
        );
    }
}
