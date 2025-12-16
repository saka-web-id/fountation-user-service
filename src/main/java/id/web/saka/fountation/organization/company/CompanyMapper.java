package id.web.saka.fountation.organization.company;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toEntity(CompanyDTO dto);
    CompanyDTO toDto(Company entity);
    Company requestToEntity(CompanyRequestDTO dto);

    // Instant → OffsetDateTime (GMT+7)
    default ZonedDateTime toOffset(Instant instant) {
        return instant == null ? null :
                instant.atZone(ZoneOffset.UTC);
    }


    // OffsetDateTime (GMT+7) → Instant (UTC)
    default Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }



}


