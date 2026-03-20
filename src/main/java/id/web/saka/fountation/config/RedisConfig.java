package id.web.saka.fountation.config;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.UserDTO;
import id.web.saka.fountation.user.account.UserAccountDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean(name = "redisUserAccountTemplate")
    public ReactiveRedisTemplate<String, UserAccountDTO> reactiveRedisTemplateUserAccountDTO(
            ReactiveRedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        Jackson2JsonRedisSerializer<UserAccountDTO> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, UserAccountDTO.class);

        RedisSerializationContext<String, UserAccountDTO> context =
                RedisSerializationContext
                        .<String, UserAccountDTO>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean(name = "redisUserDTOTemplate")
    public ReactiveRedisTemplate<String, UserDTO> reactiveRedisTemplateUserDTO(
            ReactiveRedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        Jackson2JsonRedisSerializer<UserDTO> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, UserDTO.class);

        RedisSerializationContext<String, UserDTO> context =
                RedisSerializationContext
                        .<String, UserDTO>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean(name = "redisUserListTemplate")
    public ReactiveRedisTemplate<String, List<UserDTO>> reactiveRedisTemplateUserList(
            ReactiveRedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        // Construct a type that Jackson understands as List<CompanyDTO>
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, UserDTO.class);

        // Create the serializer with the Collection Type
        Jackson2JsonRedisSerializer<List<UserDTO>> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, type);

        RedisSerializationContext<String, List<UserDTO>> context =
                RedisSerializationContext
                        .<String, List<UserDTO>>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean(name = "redisDepartmentListTemplate")
    public ReactiveRedisTemplate<String, List<DepartmentDTO>> reactiveRedisTemplateDepartmentList(
            ReactiveRedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        // Construct a type that Jackson understands as List<CompanyDTO>
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, DepartmentDTO.class);

        // Create the serializer with the Collection Type
        Jackson2JsonRedisSerializer<List<DepartmentDTO>> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, type);

        RedisSerializationContext<String, List<DepartmentDTO>> context =
                RedisSerializationContext
                        .<String, List<DepartmentDTO>>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }


    @Bean(name = "redisCompanyListTemplate")
    public ReactiveRedisTemplate<String, List<CompanyDTO>> reactiveRedisTemplateCompanyList(
            ReactiveRedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        // Construct a type that Jackson understands as List<CompanyDTO>
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, CompanyDTO.class);

        // Create the serializer with the Collection Type
        Jackson2JsonRedisSerializer<List<CompanyDTO>> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, type);

        RedisSerializationContext<String, List<CompanyDTO>> context =
                RedisSerializationContext
                        .<String, List<CompanyDTO>>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }


}
