package id.web.saka.fountation.user.registration;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class UserRegistrationController {

    /*private MessageSource messageSource;*/

    Logger log = org.slf4j.LoggerFactory.getLogger(UserRegistrationController.class);

    private final UserRegistrationService userRegistrationService;

    public  UserRegistrationController(UserRegistrationService userRegistrationService1) {
        this.userRegistrationService = userRegistrationService1;
    }

    @PostMapping("/user/registration")
    public Mono<UserRegistrationDTO> postRegisterUser(@RequestBody Mono<UserRegistrationDTO> payload) {
        log.info("Registering new user: {}", payload.toString());

        return userRegistrationService.registerUser(payload);
    }

}
