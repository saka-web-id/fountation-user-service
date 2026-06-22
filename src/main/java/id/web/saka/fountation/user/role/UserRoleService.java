package id.web.saka.fountation.user.role;

import id.web.saka.fountation.common.messaging.outbox.OutboxService;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import id.web.saka.fountation.user.role.client.UserRoleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleService.class);
    private final UserRoleClient userRoleClient;
    private final OutboxService outboxService;

    public UserRoleService(UserRoleClient userRoleClient, OutboxService outboxService) {
        this.userRoleClient = userRoleClient;
        this.outboxService = outboxService;
    }

    @Transactional
    public Mono<UserRoleDTO> updateUserRoles(Long companyId, Long userId, UserRoleDTO userRoleDTO) {
        log.info("Updating UserRole for companyId {} userId: {} via gRPC", companyId, userId);
        return userRoleClient.updateUserRoles(companyId, userId, userRoleDTO)
                .flatMap(result -> outboxService.writeOutbox("USER_ROLE", "USER-" + userId, "USER_ROLE_UPDATED", result)
                        .thenReturn(result));
    }

    @Transactional
    public Mono<UserRoleDTO> addUserRole(Long companyId, Long userId, UserRoleDTO userRoleDTO) {
        log.info("Adding UserRole for companyId {} userId: {} via gRPC", companyId, userId);
        return userRoleClient.addUserRole(companyId, userId, userRoleDTO)
                .flatMap(result -> outboxService.writeOutbox("USER_ROLE", "USER-" + userId, "USER_ROLE_ADDED", result)
                        .thenReturn(result));
    }

    @Transactional
    public Mono<UserRegistrationDTO> assignRoleToNewUser(UserRegistrationDTO userRegistrationDTO) {
        log.info("Assigning role to new user via gRPC: {}", userRegistrationDTO);
        return userRoleClient.assignRoleToNewUser(userRegistrationDTO)
                .flatMap(result -> outboxService.writeOutbox("USER_ROLE", "USER-" + result.user().id(), "USER_ROLE_ASSIGNED", result)
                        .thenReturn(result));
    }
}
