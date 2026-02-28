package id.web.saka.fountation.user.role;

import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import id.web.saka.fountation.user.role.client.UserRoleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleService.class);
    private final UserRoleClient userRoleClient;

    public UserRoleService(UserRoleClient userRoleClient) {
        this.userRoleClient = userRoleClient;
    }

    public Mono<UserRoleDTO> updateUserRoles(Long companyId, Long userId, UserRoleDTO userRoleDTO) {
        log.info("Updating UserRole for companyId {} userId: {} via gRPC", companyId, userId);
        return userRoleClient.updateUserRoles(companyId, userId, userRoleDTO);
    }

    public Mono<UserRoleDTO> addUserRole(Long companyId, Long userId, UserRoleDTO userRoleDTO) {
        log.info("Adding UserRole for companyId {} userId: {} via gRPC", companyId, userId);
        return userRoleClient.addUserRole(companyId, userId, userRoleDTO);
    }

    public Mono<UserRegistrationDTO> assignRoleToNewUser(UserRegistrationDTO userRegistrationDTO) {
        log.info("Assigning role to new user via gRPC: {}", userRegistrationDTO);
        return userRoleClient.assignRoleToNewUser(userRegistrationDTO);
    }
}
