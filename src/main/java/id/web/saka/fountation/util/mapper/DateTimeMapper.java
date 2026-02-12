package id.web.saka.fountation.util.mapper;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class DateTimeMapper {

    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Jakarta"); // GMT+7

    // Instant → ZonedDateTime (GMT+7)
    public ZonedDateTime toOffset(Instant instant) {
        return instant == null ? null : instant.atZone(DEFAULT_ZONE);
    }

    // ZonedDateTime (GMT+7) → Instant (UTC)
    public Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }
}

