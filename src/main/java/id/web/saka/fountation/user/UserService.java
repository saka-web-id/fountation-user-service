package id.web.saka.fountation.user;

import id.web.saka.fountation.authority.AuthorityService;
import id.web.saka.fountation.user.account.UserAccountDTO;
import id.web.saka.fountation.user.organization.department.UserDepartmentRepository;
import id.web.saka.fountation.user.organization.department.UserDepartmentService;
import id.web.saka.fountation.util.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final AuthorityService authorityService;

    private MessageSource messageSource;

    private Mono<WebClient> webClientAccount;

    private final UserMapper userMapper;

    private final UserDepartmentService userDepartmentService;

    public UserService(UserRepository userRepository, AuthorityService authorityService, @Qualifier("webClientAccount") Mono<WebClient> webClientAccount, Env env, MessageSource messageSource, UserMapper userMapper, UserDepartmentService userDepartmentService) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.webClientAccount = webClientAccount;
        this.messageSource = messageSource;
        this.userMapper = userMapper;
        this.userDepartmentService = userDepartmentService;
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<UserAccountDTO> getUserAccountDTOByEmail(String email) {
        return getUserByEmail(email)
                .flatMap(user ->
                        authorityService.getAuthorityByUserId(user.getId()).flatMap(authorityDTO -> {
                            if (authorityDTO == null) {
                                return Mono.error(new RuntimeException(messageSource.getMessage("error.user.no.authority", null, null)));
                            } else {
                                log.info("Authority Role: {}", authorityDTO.toString());
                            }

                            return webClientAccount.flatMap(webClient ->
                                    webClient.get()
                                            .uri("/api/v0/account/membership/detail/" + user.getId())
                                            .retrieve()
                                            .bodyToMono(UserAccountDTO.class)
                                            .map(userAccountDTO -> {
                                                userAccountDTO.setName(user.getName());
                                                userAccountDTO.setEmail(user.getEmail());
                                                userAccountDTO.setRole(authorityDTO.getRoleName());
                                                userAccountDTO.setAuthority(authorityDTO);
                                                return userAccountDTO;
                                            })
                            );
                        })

                );
    }

    public Mono<UserDTO> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto);
    }

    public Mono<? extends UserDTO> saveUser(UserDTO userDTO) {

        return userRepository.save(userMapper.toEntity(userDTO))
                .map(userMapper::toDto);
    }

    public Mono<? extends UserDTO> addUser(UserRequestDTO userRequestDTO) {

        return userRepository
                .save(userMapper.requestToEntity(userRequestDTO))
                .map(userMapper::toDto).flatMap(userDTO ->
                        // Set default department for new user
                        userDepartmentService
                                .setDepartmentForUser(userDTO.getId(), userRequestDTO.getDepartmentId())
                                .thenReturn(userDTO)
                );

    }
}
