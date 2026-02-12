package id.web.saka.fountation.organization.company;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface CompanyMapper {
    @Mapping(target = "status", expression = "java(CompanyStatus.fromValue(dto.status()))")
    Company toEntity(CompanyDTO dto);
    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    CompanyDTO toDto(Company entity);

    @Mapping(target = "isDefault", source = "defaultFlag")
    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    CompanyDTO toDto(Company entity, boolean defaultFlag);

    @Mapping(target = "status", expression = "java(CompanyStatus.fromValue(dto.status()))")
    Company requestToEntity(CompanyRequestDTO dto);

}


