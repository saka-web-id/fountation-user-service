package id.web.saka.fountation.account;

import id.web.saka.fountation.account.client.AccountClient;
import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanDTO;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.registration.UserRegistrationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private final AccountClient accountClient;

    public AccountService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(Long companyId, Long userId, Long valueUserId) {
        return accountClient.getAccountMembershipPlanDetailByUserId(companyId, userId, valueUserId);
    }

    @Deprecated
    public Mono<AccountDTO> getAccountById(Long companyId, Long userId) {
        return accountClient.getAccountById(companyId, userId);
    }

    public Mono<UserRegistrationDTO> assignAccountToNewUser(UserRegistrationDTO dto, UserDTO userDTO, CompanyDTO companyDTO, DepartmentDTO departmentDTO) {
        UserRegistrationDTO fullDto = new UserRegistrationDTO(userDTO, dto.account(), companyDTO, departmentDTO);
        return accountClient.assignAccountToNewUser(fullDto);
    }
}
