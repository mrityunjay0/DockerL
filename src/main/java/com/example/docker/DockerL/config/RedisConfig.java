package com.example.docker.DockerL.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        // 1. Create a validator that allows all classes to be serialized/deserialized
        BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class)
                .build();

        // 2. Use JsonMapper.builder() for Jackson 3
        // We explicitly set As.WRAPPER_ARRAY to handle List collections securely
        ObjectMapper redisMapper = JsonMapper.builder()
                .activateDefaultTyping(ptv, DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY)
                .build();

        // 3. Pass our custom mapper into the generic serializer
        GenericJacksonJsonRedisSerializer serializer = new GenericJacksonJsonRedisSerializer(redisMapper);

        // 4. Configure the cache settings globally
        RedisCacheConfiguration baseConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .disableCachingNullValues()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                        )
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                        );

        // Individual student cache → 30 minutes
        RedisCacheConfiguration studentConfig =
                baseConfig.entryTtl(Duration.ofMinutes(30));

        // All students list cache → 5 minutes
        RedisCacheConfiguration studentsConfig =
                baseConfig.entryTtl(Duration.ofMinutes(5));

        // Default cache → 10 minutes
        RedisCacheConfiguration defaultConfig =
                baseConfig.entryTtl(Duration.ofMinutes(10));


        // 5. Build and return the Cache Manager
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration("student", studentConfig)
                .withCacheConfiguration("students", studentsConfig)
                .build();
    }
}