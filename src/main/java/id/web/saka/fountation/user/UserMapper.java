package id.web.saka.fountation.user;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface UserMapper {
    @Mapping(target = "status", expression = "java(UserStatus.fromValue(dto.status()))")
    User toEntity(UserDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus().getValue())")
    UserDTO toDto(User entity);

    User requestToEntity(UserRequestDTO dto);

}
