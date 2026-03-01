package id.web.saka.fountation.authorization.policy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PolicyGrpcMapper {

    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "resource", source = "resource")
    PolicyRequest toProto(PolicyRequestDTO dto);

    @Mapping(target = "isAllow", source = "isAllow")
    @Mapping(target = "reason", source = "reason")
    PolicyResponseDTO toDTO(PolicyResponse proto);
}
