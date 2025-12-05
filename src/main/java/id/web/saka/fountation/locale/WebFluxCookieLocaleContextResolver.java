package id.web.saka.fountation.locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.List;
import java.util.Locale;

public class WebFluxCookieLocaleContextResolver implements LocaleContextResolver {

    private final String cookieName = "LOCALE";
    private Locale defaultLocale = Locale.ENGLISH;

    @Override
    public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
        // 1. Query param
        String param = exchange.getRequest().getQueryParams().getFirst("lang");
        if (param != null) {
            return () -> Locale.forLanguageTag(param);
        }

        // 2. Accept-Language header
        List<Locale.LanguageRange> ranges =
                Locale.LanguageRange.parse(
                        exchange.getRequest().getHeaders().getFirst("Accept-Language")
                                == null ? "en" :
                                exchange.getRequest().getHeaders().getFirst("Accept-Language")
                );

        Locale locale = Locale.lookup(ranges, List.of(Locale.getAvailableLocales()));
        return () -> locale != null ? locale : Locale.ENGLISH;
    }

    @Override
    public void setLocaleContext(ServerWebExchange exchange, LocaleContext localeContext) {
        Locale locale = localeContext.getLocale();
        ResponseCookie cookie = ResponseCookie.from(cookieName, locale.toLanguageTag())
                .path("/")
                .maxAge(3600)
                .build();
        exchange.getResponse().addCookie(cookie);
    }

    public void setDefaultLocale(Locale locale) {
        this.defaultLocale = locale;
    }
}
