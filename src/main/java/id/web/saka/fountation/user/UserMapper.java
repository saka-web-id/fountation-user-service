package id.web.saka.fountation.user;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface UserMapper {
    @Mapping(target = "status", expression = "java(UserStatus.fromValue(dto.status()))")
    @Mapping(target = "createdAt", qualifiedByName = "toInstant")
    @Mapping(target = "updateAt", qualifiedByName = "toInstant")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toInstant")
    User toEntity(UserDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    @Mapping(target = "createdAt", qualifiedByName = "toOffset")
    @Mapping(target = "updateAt", qualifiedByName = "toOffset")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toOffset")
    UserDTO toDto(User entity);

    User requestToEntity(UserRequestDTO dto);

    @Mapping(target = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updateAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toZonedDateTime")
    UserDTO toDto(UserProto proto);

    @Mapping(target = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updateAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toProtoTimestamp")
    UserProto toProto(UserDTO dto);

}
