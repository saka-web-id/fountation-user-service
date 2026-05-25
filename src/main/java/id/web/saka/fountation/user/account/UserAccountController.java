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

        return userAccountService.getUserAccountDTOByUserId(valueUserId).doOnNext(dto -> {
                    log.info("[getUserAccountDTOByUserId] Successfully retrieved user account for valueUserId: {}: {}", valueUserId, dto);
                })
                .doOnError(e -> {
                    log.error("[getUserAccountDTOByUserId] Failed to retrieve user account for valueUserId: {} due to error: {}", valueUserId, e.getMessage());
                });
    }

    @PostMapping("/user/account/update/companyId/{companyId}/userId/{userId}/valueUserId/{valueUserId}")
    public Mono<UserAccountDTO> updateUserAccount(
            @RequestBody Mono<UserAccountDTO> payload,
            @PathVariable Long companyId,
            @PathVariable Long userId,
            @PathVariable Long valueUserId) {
        log.info("[updateUserAccount] Initiated request to update user account for valueUserId: {} in companyId: {} by userId: {}", valueUserId, companyId, userId);

        return payload
                .doOnNext(dto -> log.info("[updateUserAccount] Received update payload for valueUserId: {}: {}", valueUserId, dto))
                .flatMap(payloadDTO ->
                userAccountService.updateUserAccount(companyId, userId, valueUserId, payloadDTO)
        ).doOnError(error -> log.error("[updateUserAccount] Failed to update user account for valueUserId: {} due to error: {}", valueUserId, error.getMessage()));
    }

    @PostMapping("/user/account/add/companyId/{companyId}/userId/{userId}")
    public Mono<UserAccountDTO> addUserAccount(@RequestBody Mono<UserAccountDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        log.info("[addUserAccount] Initiated request to add user account in companyId: {} by userId: {}", companyId, userId);

        return payload
                .doOnNext(dto -> log.info("[addUserAccount] Received add payload in companyId: {}: {}", companyId, dto))
                .flatMap(payloadDTO ->
                        userAccountService.addUserAccount(companyId, userId, payloadDTO)
                ).doOnError(error -> log.error("[addUserAccount] Failed to add user account in companyId: {} due to error: {}", companyId, error.getMessage()));
    }

}
