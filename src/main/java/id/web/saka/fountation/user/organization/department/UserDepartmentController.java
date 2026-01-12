package id.web.saka.fountation.user.organization.department;

import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.organization.department.DepartmentMapper;
import id.web.saka.fountation.organization.department.DepartmentRequestDTO;
import id.web.saka.fountation.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v0")
public class UserDepartmentController {

    private static final Logger log = LoggerFactory.getLogger(UserDepartmentController.class);

    private final UserDepartmentService userDepartmentService;

    private final DepartmentMapper departmentMapper;

    public UserDepartmentController(UserDepartmentService userDepartmentService, DepartmentMapper departmentMapper) {
        this.userDepartmentService = userDepartmentService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("/user/organization/department/list/companyId/{companyId}/userId/{userId}")
    public Flux<DepartmentDTO> getDepartmentsByCompanyId(@PathVariable Long companyId) {

        return userDepartmentService.getDepartmentsByCompanyId(companyId); // Placeholder return
    }

    @GetMapping("/user/organization/department/detail/companyId/{companyId}/userId/{userId}/{departmentId}")
    public Mono<DepartmentDTO> getDepartmentDetail(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long departmentId) {

        return userDepartmentService.getDepartmentDetail(departmentId);
    }

    @PostMapping("/user/organization/department/update/companyId/{companyId}/userId/{userId}")
    public Mono<ResponseEntity<DepartmentDTO>> updateCompany(@RequestBody Mono<DepartmentDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload
                .map(departmentMapper::toEntity)
                .flatMap(userDepartmentService::saveDepartment)
                .map(departmentMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/user/organization/department/add/companyId/{companyId}/userId/{userId}")
    public Mono<ResponseEntity<DepartmentDTO>> addCompany(@RequestBody Mono<DepartmentRequestDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {

        return payload
                .map(departmentMapper::requestToEntity)
                .flatMap(userDepartmentService::saveDepartment)
                .map(departmentMapper::toDto)
                .map(saved ->
                        ResponseEntity
                                .created(URI.create(
                                        "/api/v0/user/organization/department/detail/" + saved.getId()
                                ))
                                .body(saved)
                );
    }

    //TODO Check in database compare with /api/v0/user/list
    @GetMapping("/user/organization/department/users/companyId/{companyId}/userId/{userId}/valueDepartmentId/{departmentId}")
    public Flux<UserDTO> getUsers(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long departmentId) {

        return userDepartmentService.getUsers(companyId, departmentId);
    }

}
