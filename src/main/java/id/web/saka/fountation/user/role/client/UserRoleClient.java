package id.web.saka.fountation.user.role.client;

import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import id.web.saka.fountation.user.role.UserRoleDTO;
import reactor.core.publisher.Mono;

public interface UserRoleClient {

    public Mono<UserRoleDTO> updateUserRoles(Long companyId, Long userId, UserRoleDTO userRoleDTOEntity);

    public Mono<UserRoleDTO> addUserRole(Long companyId, Long userId, UserRoleDTO userRoleDTO);

    public Mono<UserRegistrationDTO> assignRoleToNewUser(UserRegistrationDTO userRegistrationDTO);

    public Mono<UserRoleDTO> getRoleByUserIdAndCompanyId(Long companyId, Long userId);

}
