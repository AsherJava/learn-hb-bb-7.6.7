/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  org.springframework.data.redis.listener.adapter.MessageListenerAdapter
 */
package com.jiuqi.common.subject.impl.subject.listener.config;

import com.jiuqi.common.subject.impl.subject.cache.config.SubjectSyncCacheConfig;
import com.jiuqi.common.subject.impl.subject.listener.SubjectSyncCacheMessageListener;
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
@ComponentScan(basePackages={"com.jiuqi.common.subject.impl.subject.listener"})
public class SubjectListenerAutoConfiguration {
    @Autowired
    private SubjectSyncCacheConfig cacheConfig;

    @Bean
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    public MessageListenerAdapter subjectSyncCacheMessageListenerAdapter(SubjectSyncCacheMessageListener subjectSyncCacheMessageListener) {
        return new MessageListenerAdapter((Object)subjectSyncCacheMessageListener, "receiveMessage");
    }

    @Bean
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    public RedisMessageListenerContainer subjectSyncCacheContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter subjectSyncCacheMessageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)subjectSyncCacheMessageListenerAdapter, (Topic)new ChannelTopic(this.cacheConfig.getSyncCacheChannel()));
        return container;
    }
}

