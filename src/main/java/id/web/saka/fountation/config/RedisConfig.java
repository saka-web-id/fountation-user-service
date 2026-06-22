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

    // Helper method to radically cut down boilerplate code and enforce standard serialization
    private <T> ReactiveRedisTemplate<String, T> createReactiveTemplate(
            ReactiveRedisConnectionFactory factory,
            Jackson2JsonRedisSerializer<T> serializer) {

        RedisSerializationContext<String, T> context = RedisSerializationContext
                .<String, T>newSerializationContext(new StringRedisSerializer())
                .value(serializer)
                .hashValue(serializer) // Good practice to declare for hashes as well
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean(name = "redisUserAccountTemplate")
    public ReactiveRedisTemplate<String, UserAccountDTO> redisUserAccountTemplate(
            ReactiveRedisConnectionFactory factory, ObjectMapper objectMapper) {
        return createReactiveTemplate(factory, new Jackson2JsonRedisSerializer<>(objectMapper, UserAccountDTO.class));
    }

    @Bean(name = "redisUserDTOTemplate")
    public ReactiveRedisTemplate<String, UserDTO> redisUserDTOTemplate(
            ReactiveRedisConnectionFactory factory, ObjectMapper objectMapper) {
        return createReactiveTemplate(factory, new Jackson2JsonRedisSerializer<>(objectMapper, UserDTO.class));
    }

    @Bean(name = "redisUserListTemplate")
    public ReactiveRedisTemplate<String, List<UserDTO>> redisUserListTemplate(
            ReactiveRedisConnectionFactory factory, ObjectMapper objectMapper) {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, UserDTO.class);
        return createReactiveTemplate(factory, new Jackson2JsonRedisSerializer<>(objectMapper, type));
    }

    @Bean(name = "redisDepartmentListTemplate")
    public ReactiveRedisTemplate<String, List<DepartmentDTO>> redisDepartmentListTemplate(
            ReactiveRedisConnectionFactory factory, ObjectMapper objectMapper) {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, DepartmentDTO.class);
        return createReactiveTemplate(factory, new Jackson2JsonRedisSerializer<>(objectMapper, type));
    }

    @Bean(name = "redisCompanyListTemplate")
    public ReactiveRedisTemplate<String, List<CompanyDTO>> redisCompanyListTemplate(
            ReactiveRedisConnectionFactory factory, ObjectMapper objectMapper) {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, CompanyDTO.class);
        return createReactiveTemplate(factory, new Jackson2JsonRedisSerializer<>(objectMapper, type));
    }
}