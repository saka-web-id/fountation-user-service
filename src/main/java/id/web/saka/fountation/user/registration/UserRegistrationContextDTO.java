package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.organization.company.Company;
import id.web.saka.fountation.organization.department.Department;
import id.web.saka.fountation.user.UserDTO;

public record UserRegistrationContextDTO (
        UserRegistrationDTO originalDto,
        Company company,
        Department department,
        UserDTO savedUser
) {
}
