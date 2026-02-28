package id.web.saka.fountation.organization.department;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface DepartmentMapper {

    @Mapping(target = "status", expression = "java(DepartmentStatus.fromValue(dto.status()))")
    @Mapping(target = "createdAt", qualifiedByName = "toInstant")
    @Mapping(target = "updatedAt", qualifiedByName = "toInstant")
    Department toEntity(DepartmentDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    @Mapping(target = "createdAt", qualifiedByName = "toOffset")
    @Mapping(target = "updatedAt", qualifiedByName = "toOffset")
    DepartmentDTO toDto(Department entity);

    Department requestToEntity(DepartmentRequestDTO dto);

}
