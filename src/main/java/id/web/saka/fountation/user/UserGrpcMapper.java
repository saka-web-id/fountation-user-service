package id.web.saka.fountation.user;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface UserGrpcMapper {

    @Mapping(target = "lastLoginAt", source = "lastLoginAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "verified", source = "isVerified")
    UserDTO toDTO(id.web.saka.fountation.user.UserProto proto);

    @Mapping(target = "lastLoginAt", source = "lastLoginAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "isVerified", source = "verified")
    id.web.saka.fountation.user.UserProto toProto(UserDTO dto);
}
