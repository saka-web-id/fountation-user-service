package id.web.saka.fountation.user.role;

import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.company.CompanyMapper;
import id.web.saka.fountation.organization.department.Department;
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
public class UserRoleService {

    Logger log = LoggerFactory.getLogger(UserRoleService.class);

    private Mono<WebClient> webClientAuthority;

    public UserRoleService(@Qualifier("webClientAuthorization") Mono<WebClient> webClientAuthority) {
        this.webClientAuthority = webClientAuthority;
    }

    public Mono<UserRole> updateUserRoles(Long companyId, Long userId, UserRole userRoleEntity) {
        log.info("Updating UserRole for companyId {} userId: {}", userRoleEntity.getUserId());

        return webClientAuthority.flatMap(webClient ->
                webClient.post()
                        .uri("/api/v0/authorization/user/role/update/companyId/{companyId}/userId/{userId}", companyId, userId)
                        .bodyValue(userRoleEntity)
                        .retrieve()
                        .bodyToMono(UserRole.class)
        );
    }

    public Mono<UserRole> addUserRole(Long companyId, Long userId, UserRole userRole) {
        log.info("Adding UserRole for companyId {} userId: {}", userRole.getUserId());

        return webClientAuthority.flatMap(webClient ->
                webClient.post()
                        .uri("/api/v0/authorization/user/role/add/companyId/{companyId}/userId/{userId}", companyId, userId)
                        .bodyValue(userRole)
                        .retrieve()
                        .bodyToMono(UserRole.class)
        );
    }

    public Mono<UserRegistrationDTO> assignRoleToNewUser(UserRegistrationDTO userRegistrationDTO, UserDTO userDTO, CompanyDTO companyDTO, DepartmentDTO departmentDTO) {
        log.info("Adding New UserRole for user: {} company: {}", userDTO, companyDTO);

        return webClientAuthority.flatMap(webClient ->
                webClient.post()
                        .uri("/api/v0/authorization/user/registration")
                        .bodyValue(new UserRegistrationDTO(userDTO, userRegistrationDTO.account(), companyDTO, departmentDTO))
                        .retrieve()
                        .bodyToMono(UserRegistrationDTO.class)
        );
    }
}
