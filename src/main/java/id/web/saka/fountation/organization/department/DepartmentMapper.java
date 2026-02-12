package id.web.saka.fountation.organization.department;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface DepartmentMapper {

    @Mapping(target = "status", expression = "java(DepartmentStatus.fromValue(dto.status()))")
    Department toEntity(DepartmentDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    DepartmentDTO toDto(Department entity);

    Department requestToEntity(DepartmentRequestDTO dto);

}
