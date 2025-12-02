package id.web.saka.fountation.user.registration;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/registration")
public class UserRegistrationController {



    @RequestMapping("/new")
    public Mono<String> registerUser(@AuthenticationPrincipal Jwt jwt) {

        /*TODO : Check Username and password are already use or not*/

        /*TODO : If no exist in database Insert new Username to database*/

        /*TODO : IF Exist send Error notification, user already exist */
        return Mono.just("User Registration Endpoint");
    }


}
