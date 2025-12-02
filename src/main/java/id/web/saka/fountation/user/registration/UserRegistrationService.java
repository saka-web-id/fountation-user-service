package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.user.User;
import id.web.saka.fountation.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public boolean isUserNameExist(String userName) {
    }

    public boolean isEmailExist(String email) {
    }

    public Mono<User> save(Mono<User> userMono) {

        return userMono
                .map(user -> {
                    user.setName(user.getName().toUpperCase());
                    return user;
                })
                .flatMap(userRepository::save);

    }
}
