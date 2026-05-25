package id.web.saka.fountation.config;

import io.micrometer.context.ContextRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservationConfig {

    @PostConstruct
    public void init() {
        // Ensure Observation propagation is registered in the ContextRegistry
        // This allows Reactor to move the current Observation between threads
        ContextRegistry.getInstance()
                .registerThreadLocalAccessor(new ObservationThreadLocalAccessor());
        
        // Note: Hooks.enableAutomaticContextPropagation() is already called in UserServiceApplication
    }
}
