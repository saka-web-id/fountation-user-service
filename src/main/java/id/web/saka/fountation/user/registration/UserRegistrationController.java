package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequestMapping("user/registration")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/new")
    public Mono<User> registerUser(@RequestBody Mono<User> userMono) {

        return userRegistrationService.save(userMono);
    }

    @RequestMapping("/validation/{userName}/{email}")
    public Mono<String> validationUser(ServerWebExchange exchange, @PathVariable String userName, @PathVariable String email) {
        Locale locale = exchange.getLocaleContext().getLocale();

        if(userRegistrationService.isUserNameExist(userName)) {
            return Mono.just(messageSource.getMessage("notification.error.user.exist", null, locale));
        } else if (userRegistrationService.isEmailExist(email)) {
            return Mono.just(messageSource.getMessage("notification.error.user.email.exist", null, locale));
        } else {
            return Mono.just("");
        }
    }

}
