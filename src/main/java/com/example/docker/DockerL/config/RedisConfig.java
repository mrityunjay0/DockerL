package com.example.docker.DockerL.config;

import com.example.docker.DockerL.dto.StudentResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory,
            ObjectMapper objectMapper) {

        // --------------------------------------------------
        // Serializer for List<StudentResponseDto>
        // --------------------------------------------------

        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(
                        List.class,
                        StudentResponseDto.class
                );

        JacksonJsonRedisSerializer<List<StudentResponseDto>> listSerializer =
                new JacksonJsonRedisSerializer<>(
                        objectMapper,
                        listType
                );

        // --------------------------------------------------
        // Serializer for StudentResponseDto
        // --------------------------------------------------

        JavaType studentType = objectMapper.getTypeFactory()
                .constructType(StudentResponseDto.class);

        JacksonJsonRedisSerializer<StudentResponseDto> studentSerializer =
                new JacksonJsonRedisSerializer<>(
                        objectMapper,
                        studentType
                );

        // --------------------------------------------------
        // Common configuration
        // --------------------------------------------------

        RedisCacheConfiguration commonConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10))
                        .disableCachingNullValues()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(
                                                new StringRedisSerializer()
                                        )
                        );

        // --------------------------------------------------
        // Configuration for "students" cache
        // --------------------------------------------------

        RedisCacheConfiguration studentsCacheConfiguration =
                commonConfiguration.serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(listSerializer)
                );

        // --------------------------------------------------
        // Configuration for "student" cache
        // --------------------------------------------------

        RedisCacheConfiguration studentCacheConfiguration =
                commonConfiguration.serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(studentSerializer)
                );

        // --------------------------------------------------
        // Cache Manager
        // --------------------------------------------------

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(
                        redisConnectionFactory
                ))
                .cacheDefaults(commonConfiguration)
                .withInitialCacheConfigurations(
                        Map.of(
                                "students", studentsCacheConfiguration,
                                "student", studentCacheConfiguration
                        )
                )
                .build();
    }
}