package id.web.saka.fountation.user.role.client;

import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import id.web.saka.fountation.user.role.UserRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("userRoleWebClient")
public class UserRoleWebClientImpl implements UserRoleClient {

    Logger log = LoggerFactory.getLogger(UserRoleWebClientImpl.class);

    private WebClient webClientAuthority;

    public UserRoleWebClientImpl(@Qualifier("webClientAuthorization") WebClient webClientAuthority) {
        this.webClientAuthority = webClientAuthority;
    }

    @Override
    public Mono<UserRoleDTO> getRoleByUserIdAndCompanyId(Long companyId, Long userId) {
        log.info("Fetching user role via WebClient: companyId={}, userId={}", companyId, userId);

        return webClientAuthority.get()
                .uri("/api/v0/authorization/user/role/detail/companyId/{companyId}/userId/{userId}", companyId, userId)
                .retrieve()
                .bodyToMono(UserRoleDTO.class);
    }

    /**
     * @param companyId
     * @param userId
     * @param userRoleDTOEntity
     * @return
     */
    @Override
    public Mono<UserRoleDTO> updateUserRoles(Long companyId, Long userId, UserRoleDTO userRoleDTOEntity) {
        log.info("Updating UserRoleDTO for companyId {} userId: {}", companyId, userRoleDTOEntity.userId());

        return webClientAuthority.post()
                .uri("/api/v0/authorization/user/role/update/companyId/{companyId}/userId/{userId}", companyId, userId)
                .bodyValue(userRoleDTOEntity)
                .retrieve()
                .bodyToMono(UserRoleDTO.class);
    }

    /**
     * @param companyId
     * @param userId
     * @param userRoleDTO
     * @return
     */
    @Override
    public Mono<UserRoleDTO> addUserRole(Long companyId, Long userId, UserRoleDTO userRoleDTO) {
        log.info("Adding UserRoleDTO for companyId {} userId: {}", companyId, userRoleDTO.userId());

        return webClientAuthority.post()
                .uri("/api/v0/authorization/user/role/add/companyId/{companyId}/userId/{userId}", companyId, userId)
                .bodyValue(userRoleDTO)
                .retrieve()
                .bodyToMono(UserRoleDTO.class);
    }

    /**
     * @param userRegistrationDTO
     * @return
     */
    @Override
    public Mono<UserRegistrationDTO> assignRoleToNewUser(UserRegistrationDTO userRegistrationDTO) {
        log.info("Adding New UserRoleDTO for user: {} ", userRegistrationDTO);

        return webClientAuthority.post()
                .uri("/api/v0/authorization/user/registration")
                .bodyValue(userRegistrationDTO)
                .retrieve()
                .bodyToMono(UserRegistrationDTO.class);
    }
}
