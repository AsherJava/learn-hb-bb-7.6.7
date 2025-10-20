/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
 *  org.springframework.data.redis.serializer.RedisSerializer
 *  org.springframework.data.redis.serializer.StringRedisSerializer
 */
package com.jiuqi.budget.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    public static final String STROBJ_REDIS_TEMPLATE = "budStringObjectRedisTemplate";

    @Bean(value={"budStringObjectRedisTemplate"})
    public RedisTemplate<String, Object> restTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        serializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer((RedisSerializer)new StringRedisSerializer());
        redisTemplate.setValueSerializer((RedisSerializer)serializer);
        redisTemplate.setHashKeySerializer((RedisSerializer)new StringRedisSerializer());
        redisTemplate.setHashValueSerializer((RedisSerializer)serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

