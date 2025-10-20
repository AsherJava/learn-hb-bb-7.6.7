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
 */
package com.jiuqi.va.context;

import com.jiuqi.va.context.DataModelFeignI18nUtil;
import com.jiuqi.va.context.VaDataModelSyncCacheMsgReciver;
import com.jiuqi.va.domain.common.EnvConfig;
import java.util.Locale;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

@Configuration
@AutoConfigureAfter(value={EnvConfig.class})
@ComponentScan(basePackageClasses={VaDataModelSyncCacheMsgReciver.class, DataModelFeignI18nUtil.class})
public class VaDataModelContextConfig {
    private static String dataModelSyncCachePub;

    private static void initReidsPub(int database) {
        dataModelSyncCachePub = "DataModelSyncCachePub@" + database;
    }

    public static String getDataModelSyncCachePub() {
        return dataModelSyncCachePub;
    }

    @Bean(value={"vaDataModelSyncCacheContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisMessageListenerContainer vaDataModelSyncCacheContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaDataModelSyncCacheMsgReciver vaDataModelSyncCacheMsgReciver) {
        VaDataModelContextConfig.initReidsPub(redisProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)vaDataModelSyncCacheMsgReciver, (Topic)new ChannelTopic(dataModelSyncCachePub));
        return container;
    }

    @Bean(name={"vaDataModelFeignMessageSource"})
    MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setBasenames("messages/messages", "messages/VaDataModelFeign");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

