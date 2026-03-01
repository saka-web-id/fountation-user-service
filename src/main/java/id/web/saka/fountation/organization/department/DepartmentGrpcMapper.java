package id.web.saka.fountation.organization.department;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface DepartmentGrpcMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    DepartmentDTO toDTO(id.web.saka.fountation.organization.department.DepartmentProto proto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toProtoTimestamp")
    id.web.saka.fountation.organization.department.DepartmentProto toProto(DepartmentDTO dto);
}
