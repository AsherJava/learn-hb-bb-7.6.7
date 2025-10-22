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
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport;

import com.jiuqi.gcreport.consolidatedsystem.listener.GcSubjectDataSyncCacheMsgReciver;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value={@ComponentScan(basePackages={"com.jiuqi.gcreport.consolidatedsystem"}), @ComponentScan(basePackages={"com.jiuqi.gcreport.unionrule"})})
public class ConsolidatedschemeConfiguration {
    private static String gcBaseDataSyncCachePub = null;

    @Value(value="${spring.redis.database:0}")
    public void setCachePub(int database) {
        gcBaseDataSyncCachePub = "BaseDataSyncCachePub@" + database;
    }

    @Bean(value={"gcBaseDataSyncCacheContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisMessageListenerContainer gcBaseDataSyncCacheContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter gcSubjectDataSyncCacheMsgReciver) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor((Executor)Executors.newWorkStealingPool());
        container.addMessageListener((MessageListener)gcSubjectDataSyncCacheMsgReciver, (Topic)new ChannelTopic(gcBaseDataSyncCachePub));
        return container;
    }

    @Bean(value={"gcSubjectDataSyncCacheMsgReciver"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    MessageListenerAdapter gcSubjectDataSyncCacheMsgReciver(GcSubjectDataSyncCacheMsgReciver gcSubjectDataSyncCacheMsgReciver) {
        return new MessageListenerAdapter((Object)gcSubjectDataSyncCacheMsgReciver, "receiveMessage");
    }
}

