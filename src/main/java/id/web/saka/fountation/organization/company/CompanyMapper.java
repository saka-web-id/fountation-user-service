package id.web.saka.fountation.organization.company;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toEntity(CompanyDTO dto);
    CompanyDTO toDto(Company entity);
}


