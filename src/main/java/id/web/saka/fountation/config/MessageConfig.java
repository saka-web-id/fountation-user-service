package id.web.saka.fountation.config;

import id.web.saka.fountation.locale.WebFluxCookieLocaleContextResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.Locale;

@Configuration
public class MessageConfig {

    @Bean
    public LocaleContextResolver localeContextResolver() {
        WebFluxCookieLocaleContextResolver resolver = new WebFluxCookieLocaleContextResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);

        return resolver;
    }

}
