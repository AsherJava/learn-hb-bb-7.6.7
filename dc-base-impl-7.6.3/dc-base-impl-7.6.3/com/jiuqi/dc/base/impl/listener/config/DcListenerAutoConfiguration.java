/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.cache.config.SyncCacheConfig
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  org.springframework.data.redis.listener.adapter.MessageListenerAdapter
 */
package com.jiuqi.dc.base.impl.listener.config;

import com.jiuqi.dc.base.common.cache.config.SyncCacheConfig;
import com.jiuqi.dc.base.impl.listener.SyncCacheMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages={"com.jiuqi.dc.base.impl.listener"})
public class DcListenerAutoConfiguration {
    @Autowired
    private SyncCacheConfig cacheConfig;

    @Bean
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    public MessageListenerAdapter dcSyncCacheMessageListenerAdapter(SyncCacheMessageListener dcSyncCacheMessageListener) {
        return new MessageListenerAdapter((Object)dcSyncCacheMessageListener, "receiveMessage");
    }

    @Bean
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    public RedisMessageListenerContainer dcSyncCacheContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter dcSyncCacheMessageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)dcSyncCacheMessageListenerAdapter, (Topic)new ChannelTopic(this.cacheConfig.getSyncCacheChannel()));
        return container;
    }
}

