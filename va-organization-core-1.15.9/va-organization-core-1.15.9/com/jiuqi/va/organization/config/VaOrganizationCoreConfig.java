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
package com.jiuqi.va.organization.config;

import com.jiuqi.va.organization.service.join.VaOrgCategorySyncCacheMsgReciver;
import com.jiuqi.va.organization.service.join.VaOrgDataSyncCacheMsgReciver;
import com.jiuqi.va.organization.service.join.VaOrgVersionSyncCacheMsgReciver;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableAsync
@ComponentScan(basePackages={"com.jiuqi.va.organization"})
@MapperScan(basePackages={"com.jiuqi.va.organization.dao"})
@PropertySource(value={"classpath:va-organization-core.properties"})
public class VaOrganizationCoreConfig {
    private static String orgCategorySyncCachePub = null;
    private static String orgVersionSyncCachePub = null;
    private static String orgDataSyncCachePub = null;

    private static void initRedisPub(int database) {
        orgCategorySyncCachePub = "OrgCategorySyncCachePub@" + database;
        orgVersionSyncCachePub = "orgVersionSyncCachePub@" + database;
        orgDataSyncCachePub = "OrgDataSyncCachePub@" + database;
    }

    public static String getOrgCategorySyncCachePub() {
        return orgCategorySyncCachePub;
    }

    public static String getOrgVersionSyncCachePub() {
        return orgVersionSyncCachePub;
    }

    public static String getOrgDataSyncCachePub() {
        return orgDataSyncCachePub;
    }

    @Bean(value={"vaOrgDataSyncCacheContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisMessageListenerContainer vaOrgDataSyncCacheContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaOrgCategorySyncCacheMsgReciver vaOrgCategorySyncCacheMsgReciver, VaOrgVersionSyncCacheMsgReciver vaOrgVersionSyncCacheMsgReciver, VaOrgDataSyncCacheMsgReciver vaOrgDataSyncCacheMsgReciver) {
        VaOrganizationCoreConfig.initRedisPub(redisProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor((Executor)Executors.newWorkStealingPool());
        container.addMessageListener((MessageListener)vaOrgCategorySyncCacheMsgReciver, (Topic)new ChannelTopic(orgCategorySyncCachePub));
        container.addMessageListener((MessageListener)vaOrgVersionSyncCacheMsgReciver, (Topic)new ChannelTopic(orgVersionSyncCachePub));
        container.addMessageListener((MessageListener)vaOrgDataSyncCacheMsgReciver, (Topic)new ChannelTopic(orgDataSyncCachePub));
        return container;
    }

    @Bean(name={"vaOrganizationCoreMessageSource"})
    MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setBasenames("messages/messages", "messages/VaOrganizationCore");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }

    @Bean(value={"vaOrgDataAsyncTaskExecutor"})
    Executor vaOrgDataAsyncTaskExecutor() {
        return Executors.newWorkStealingPool();
    }
}

