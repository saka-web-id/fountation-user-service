package id.web.saka.fountation.config;


import id.web.saka.fountation.locale.WebFluxCookieLocaleContextResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.i18n.LocaleContextResolver;


@Configuration
public class WebfluxConfig implements WebFluxConfigurer {

    /*@Autowired
    private WebFluxCookieLocaleContextResolver localeResolver;*/

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {

    }

    /*@Bean
    public LocaleContextResolver localeContextResolver() {
        return localeResolver;
    }*/

}
