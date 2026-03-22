package id.web.saka.fountation.user.registration;

import reactor.core.publisher.Mono;

public interface UserRegistrationClient {
    Mono<UserRegistrationDTO> assignAccountToNewUser(UserRegistrationDTO dto);
}
