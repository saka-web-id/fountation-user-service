package id.web.saka.fountation.authority;

import id.web.saka.fountation.user.role.UserRoleRepository;
import id.web.saka.fountation.authority.permission.PermissionRepository;
import id.web.saka.fountation.authority.role.RolePermission;
import id.web.saka.fountation.authority.role.RolePermissionRepository;
import id.web.saka.fountation.authority.role.RoleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorityService {

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final PermissionRepository permissionRepository;

    public AuthorityService(UserRoleRepository userRoleRepository, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository) {
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    public Mono<AuthorityDTO> getAuthorityByUserId(Long userId) {

        return userRoleRepository.findByUserId(userId)                 // Mono<UserRole>
                .flatMap(userRole ->
                        roleRepository.findById(userRole.getRoleId())          // Mono<Role>
                                .flatMap(role ->
                                        getPermissionIdsByRoleId(role.getId())         // Flux<Long>
                                                .collectList()                             // Mono<List<Long>>
                                                .flatMapMany(permissionIds ->
                                                        permissionRepository.findAllById(permissionIds)  // Flux<Permission>
                                                )
                                                .collectList()                             // Mono<List<Permission>>
                                                .map(permissions ->
                                                        new AuthorityDTO(
                                                                userId,
                                                                role,
                                                                Flux.fromIterable(permissions)     // or pass list directly
                                                        )
                                                )
                                )
                );
    }



    private Flux<Long> getPermissionIdsByRoleId(Long roleId) {
        return rolePermissionRepository.findAllByRoleId(roleId)
                .map(RolePermission::getPermissionId);
    }






}
