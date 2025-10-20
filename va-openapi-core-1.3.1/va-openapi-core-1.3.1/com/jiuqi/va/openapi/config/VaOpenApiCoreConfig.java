/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.openapi.config;

import com.jiuqi.va.openapi.mq.VaOpenApiSyncCacheMsgReciver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.openapi"})
@MapperScan(basePackages={"com.jiuqi.va.openapi.dao"})
public class VaOpenApiCoreConfig {
    private static String openApiSyncCachePub = null;

    private static void initReidsPub(int database) {
        openApiSyncCachePub = "OpenApiSyncCachePub@" + database;
    }

    public static String getOpenApiSyncCachePub() {
        return openApiSyncCachePub;
    }

    @Bean(value={"vaOpenApiSyncCacheContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisMessageListenerContainer vaOpenApiSyncCacheContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaOpenApiSyncCacheMsgReciver vaOpenApiSyncCacheMsgReciver) {
        VaOpenApiCoreConfig.initReidsPub(redisProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)vaOpenApiSyncCacheMsgReciver, (Topic)new ChannelTopic(openApiSyncCachePub));
        return container;
    }
}

