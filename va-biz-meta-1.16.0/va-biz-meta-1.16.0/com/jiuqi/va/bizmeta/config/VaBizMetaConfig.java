/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.bizmeta.config;

import com.jiuqi.va.bizmeta.service.join.VaMetaSyncCacheMsgReciver;
import com.jiuqi.va.domain.common.EnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.bizmeta"})
@MapperScan(basePackages={"com.jiuqi.va.bizmeta.dao"})
@PropertySource(value={"classpath:va-biz-meta.properties"})
@Lazy(value=false)
public class VaBizMetaConfig {
    private static boolean redisEnable = true;
    private static String metaDataSyncCachePub = null;

    @Autowired
    public void setRedisEnable(Environment environment) {
        redisEnable = EnvConfig.getRedisEnable((Environment)environment);
    }

    public static boolean isRedisEnable() {
        return redisEnable;
    }

    private static void setCachePub(int database) {
        metaDataSyncCachePub = "MetaDataSyncCachePub@" + database;
    }

    public static String getMetaDataSyncCachePub() {
        return metaDataSyncCachePub;
    }

    @Bean(value={"vaMetaSyncCacheContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    public RedisMessageListenerContainer vaMetaSyncCacheContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaMetaSyncCacheMsgReciver vaMetaSyncCacheMsgReciver) {
        VaBizMetaConfig.setCachePub(redisProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)vaMetaSyncCacheMsgReciver, (Topic)new ChannelTopic(metaDataSyncCachePub));
        return container;
    }
}

