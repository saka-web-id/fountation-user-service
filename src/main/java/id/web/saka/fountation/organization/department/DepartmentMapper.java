package id.web.saka.fountation.organization.department;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentDTO dto);

    DepartmentDTO toDto(Department entity);

    Department requestToEntity(DepartmentRequestDTO dto);

    default ZonedDateTime toOffset(Instant instant) {
        return instant == null ? null :
                instant.atZone(ZoneOffset.UTC);
    }


    // OffsetDateTime (GMT+7) → Instant (UTC)
    default Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }

}
