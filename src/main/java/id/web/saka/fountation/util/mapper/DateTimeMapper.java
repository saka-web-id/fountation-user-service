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

    // ZonedDateTime (GMT+7) → Instant (UTC)
    @Named("toInstant")
    public Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }

    // Protobuf Timestamp → ZonedDateTime (GMT+7)
    @Named("toZonedDateTime")
    public ZonedDateTime toZonedDateTime(Timestamp ts) {
        if (ts == null || (ts.getSeconds() == 0 && ts.getNanos() == 0)) return null;
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos()), DEFAULT_ZONE);
    }

    // ZonedDateTime (GMT+7) → Protobuf Timestamp
    @Named("toProtoTimestamp")
    public Timestamp toProtoTimestamp(ZonedDateTime zdt) {
        if (zdt == null) return Timestamp.getDefaultInstance();
        return Timestamp.newBuilder()
                        .setSeconds(zdt.toEpochSecond())
                        .setNanos(zdt.getNano())
                        .build();
    }

    @Named("instantToZonedDateTime")
    public ZonedDateTime toZonedDateTime(Instant instant) {
        return instant == null ? null : instant.atZone(DEFAULT_ZONE);
    }

    // LocalDateTime → ZonedDateTime (Attach Asia/Jakarta)
    @Named("localDateTimeToZonedDateTime")
    public ZonedDateTime localDateTimeToZonedDateTime(java.time.LocalDateTime ldt) {
        return ldt == null ? null : ldt.atZone(DEFAULT_ZONE);
    }

    // LocalDateTime → Protobuf Timestamp
    @Named("localDateTimeToProtoTimestamp")
    public Timestamp localDateTimeToProtoTimestamp(java.time.LocalDateTime ldt) {
        if (ldt == null) return Timestamp.getDefaultInstance();
        ZonedDateTime zdt = ldt.atZone(DEFAULT_ZONE);
        return Timestamp.newBuilder()
                .setSeconds(zdt.toEpochSecond())
                .setNanos(zdt.getNano())
                .build();
    }

    // ADD THIS: Default mapper for ZonedDateTime to Proto Timestamp
    public Timestamp map(ZonedDateTime zdt) {
        return toProtoTimestamp(zdt);
    }

    // ADD THIS: Default mapper for Proto Timestamp to ZonedDateTime
    public ZonedDateTime map(Timestamp ts) {
        return toZonedDateTime(ts);
    }

    // ADD THIS: Default mapper for LocalDateTime to Proto Timestamp
    public Timestamp map(java.time.LocalDateTime ldt) {
        return localDateTimeToProtoTimestamp(ldt);
    }

}
