package id.web.saka.fountation.organization.company;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface CompanyMapper {
    @Mapping(target = "status", expression = "java(CompanyStatus.fromValue(dto.status()))")
    @Mapping(target = "createdAt", source = "dto.createdAt", qualifiedByName = "toInstant")
    @Mapping(target = "updatedAt", source = "dto.updatedAt", qualifiedByName = "toInstant")
    Company toEntity(CompanyDTO dto);
    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    @Mapping(target = "createdAt", source = "entity.createdAt", qualifiedByName = "toOffset")
    @Mapping(target = "updatedAt", source = "entity.updatedAt", qualifiedByName = "toOffset")
    CompanyDTO toDto(Company entity);

    @Mapping(target = "isDefault", source = "defaultFlag")
    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    @Mapping(target = "createdAt", source = "entity.createdAt", qualifiedByName = "toOffset")
    @Mapping(target = "updatedAt", source = "entity.updatedAt", qualifiedByName = "toOffset")
    CompanyDTO toDto(Company entity, boolean defaultFlag);

    @Mapping(target = "status", expression = "java(CompanyStatus.fromValue(dto.status()))")
    Company requestToEntity(CompanyRequestDTO dto);

}


