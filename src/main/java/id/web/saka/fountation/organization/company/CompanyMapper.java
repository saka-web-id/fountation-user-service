package id.web.saka.fountation.organization.company;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface CompanyMapper {
    @Mapping(target = "status", expression = "java(CompanyStatus.fromValue(dto.status()))")
    @Mapping(target = "createdAt", qualifiedByName = "toInstant")
    @Mapping(target = "updatedAt", qualifiedByName = "toInstant")
    Company toEntity(CompanyDTO dto);
    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    @Mapping(target = "createdAt", qualifiedByName = "toOffset")
    @Mapping(target = "updatedAt", qualifiedByName = "toOffset")
    CompanyDTO toDto(Company entity);

    @Mapping(target = "isDefault", source = "defaultFlag")
    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    @Mapping(target = "createdAt", qualifiedByName = "toOffset")
    @Mapping(target = "updatedAt", qualifiedByName = "toOffset")
    CompanyDTO toDto(Company entity, boolean defaultFlag);

    @Mapping(target = "status", expression = "java(CompanyStatus.fromValue(dto.status()))")
    Company requestToEntity(CompanyRequestDTO dto);

}


