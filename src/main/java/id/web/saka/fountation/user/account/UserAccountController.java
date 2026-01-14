package id.web.saka.fountation.user.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class UserAccountController {

    Logger log = LoggerFactory.getLogger(UserAccountController.class);

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/user/account/detail/companyId/{companyId}/userId/{userId}/valueUserId/{valueUserId}")
    public Mono<UserAccountDTO> getUserAccountDTOByUserId(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueUserId) {

        return userAccountService.getUserAccountDTOByUserId(valueUserId);
    }

    @PostMapping("/user/account/update/companyId/{companyId}/userId/{userId}/valueUserId/{valueUserId}")
    public Mono<UserAccountDTO> updateUserAccount(
            @RequestBody Mono<UserAccountDTO> payload,
            @PathVariable Long companyId,
            @PathVariable Long userId,
            @PathVariable Long valueUserId) {
        log.info("Updating UserAccount for valueUserId: " + valueUserId + " in companyId: " + companyId + " by userId: " + userId);

        return payload
                .doOnNext(dto -> log.info("Incoming updateUserAccount payload: {}", dto))
                .flatMap(payloadDTO ->
                userAccountService.updateUserAccount(companyId, userId, valueUserId, payloadDTO)
        ).doOnError(error -> log.error("Error updating UserAccount: " + error.getMessage()));
    }

    @PostMapping("/user/account/add/companyId/{companyId}/userId/{userId}")
    public Mono<UserAccountDTO> addUserAccount(@RequestBody Mono<UserAccountDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        log.info("Adding UserAccount in companyId: " + companyId + " by userId: " + userId);

        return payload
                .doOnNext(dto -> log.info("Incoming addUserAccount payload: {}", dto))
                .flatMap(payloadDTO ->
                        userAccountService.addUserAccount(companyId, userId, payloadDTO)
                ).doOnError(error -> log.error("Error updating UserAccount: " + error.getMessage()));
    }

}
