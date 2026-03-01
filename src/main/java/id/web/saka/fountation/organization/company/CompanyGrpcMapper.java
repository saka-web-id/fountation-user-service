package id.web.saka.fountation.organization.company;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface CompanyGrpcMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    CompanyDTO toDTO(id.web.saka.fountation.organization.company.CompanyProto proto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toProtoTimestamp")
    id.web.saka.fountation.organization.company.CompanyProto toProto(CompanyDTO dto);
}
