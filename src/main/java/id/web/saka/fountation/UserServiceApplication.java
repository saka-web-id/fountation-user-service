package id.web.saka.fountation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        // Enable automatic context propagation for Micrometer Tracing in WebFlux
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
