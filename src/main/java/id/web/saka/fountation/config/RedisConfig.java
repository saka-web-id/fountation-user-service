package id.web.saka.fountation.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import id.web.saka.fountation.user.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import id.web.saka.fountation.user.account.UserAccountDTO;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
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

    @Bean
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



}
