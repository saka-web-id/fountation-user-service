package id.web.saka.fountation.util.mapper;

import org.springframework.stereotype.Component;

import com.google.protobuf.Timestamp;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class DateTimeMapper {

    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Jakarta"); // GMT+7

    // Instant → ZonedDateTime (GMT+7)
    @Named("toOffset")
    public ZonedDateTime toOffset(Instant instant) {
        return instant == null ? null : instant.atZone(DEFAULT_ZONE);
    }

    // ZonedDateTime (
    // GMT+7) → Instant (UTC)
    @Named("toInstant")
    public Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }

    // Protobuf Timestamp → ZonedDateTime (GMT+7)
    @Named("toZonedDateTime")
    public ZonedDateTime toZonedDateTime(Timestamp ts) {
        return ts == null ? null :
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos()), DEFAULT_ZONE);
    }

    // ZonedDateTime (GMT+7) → Protobuf Timestamp
    @Named("toProtoTimestamp")
    public Timestamp toProtoTimestamp(ZonedDateTime zdt) {
        return zdt == null ? null :
                Timestamp.newBuilder()
                        .setSeconds(zdt.toEpochSecond())
                        .setNanos(zdt.getNano())
                        .build();
    }

}

