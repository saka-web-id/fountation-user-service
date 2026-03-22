package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class
})
public interface MembershipPlanGrpcMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "name", source = "name", qualifiedByName = "enumToString")
    @Mapping(target = "features", source = "features", qualifiedByName = "stringToJson")
    MembershipPlanDTO toDTO(MembershipPlanProto proto);

}
