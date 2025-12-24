package id.web.saka.fountation.user;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
    UserDTO toDto(User entity);

    User requestToEntity(UserRequestDTO dto);

    default ZonedDateTime toOffset(Instant instant) {
        return instant == null ? null :
                instant.atZone(ZoneOffset.UTC);
    }


    // OffsetDateTime (GMT+7) → Instant (UTC)
    default Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }

}
