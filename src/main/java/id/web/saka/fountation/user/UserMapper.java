package id.web.saka.fountation.user;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface UserMapper {
    @Mapping(target = "status", expression = "java(UserStatus.fromValue(dto.status()))")
    @Mapping(target = "createdAt", qualifiedByName = "toInstant")
    @Mapping(target = "updatedAt", qualifiedByName = "toInstant")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toInstant")
    @Mapping(target = "iamId", source = "iamId")
    User toEntity(UserDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    @Mapping(target = "createdAt", qualifiedByName = "toOffset")
    @Mapping(target = "updatedAt", qualifiedByName = "toOffset")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toOffset")
    @Mapping(target = "iamId", source = "iamId")
    UserDTO toDto(User entity);

    User requestToEntity(UserRequestDTO dto);

    @Mapping(target = "createdAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toZonedDateTime")
    @Mapping(target = "iamId", source = "iamId")
    UserDTO toDto(UserProto proto);

    @Mapping(target = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "lastLoginAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "iamId", source = "iamId")
    UserProto toProto(UserDTO dto);

}
