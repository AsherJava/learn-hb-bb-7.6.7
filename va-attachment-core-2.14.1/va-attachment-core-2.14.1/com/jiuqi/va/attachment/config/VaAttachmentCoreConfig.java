/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  org.springframework.data.redis.serializer.GenericToStringSerializer
 *  org.springframework.data.redis.serializer.RedisSerializer
 *  org.springframework.data.redis.serializer.StringRedisSerializer
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.attachment.config;

import com.jiuqi.va.attachment.service.join.VaAttachmentSyncCacheMsgReciver;
import com.jiuqi.va.domain.common.EnvConfig;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableScheduling
@ComponentScan(basePackages={"com.jiuqi.va.attachment"})
@MapperScan(basePackages={"com.jiuqi.va.attachment.dao"})
@Lazy(value=false)
public class VaAttachmentCoreConfig {
    private static boolean redisEnable = true;
    private static String vaAttachmentCoreSyncCachePub = null;

    @Autowired
    public void setRedisEnable(Environment environment) {
        EnvConfig.setEnvironment((Environment)environment);
        redisEnable = EnvConfig.getRedisEnable();
    }

    public static boolean getRedisEnable() {
        return redisEnable;
    }

    public static String getCurrNodeId() {
        return EnvConfig.getCurrNodeId();
    }

    public static String getVaAttachmentCoreSyncCachePub() {
        return vaAttachmentCoreSyncCachePub;
    }

    private void initReidsPub(int database) {
        vaAttachmentCoreSyncCachePub = "vaAttachmentCoreSyncCachePub@" + database;
    }

    @Bean(value={"vaAttachmentCoreContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisMessageListenerContainer vaAttachmentCoreContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaAttachmentSyncCacheMsgReciver vaAttachmentSyncCacheMsgReciver) {
        this.initReidsPub(redisProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)vaAttachmentSyncCacheMsgReciver, (Topic)new ChannelTopic(vaAttachmentCoreSyncCachePub));
        return container;
    }

    @Bean(value={"vaAttachmentByteRedisTemplate"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisTemplate<String, byte[]> vaAttachmentByteRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(byte[].class);
        template.setValueSerializer((RedisSerializer)genericToStringSerializer);
        template.setKeySerializer((RedisSerializer)new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name={"vaAttachmentMessageSource"})
    MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setBasenames("messages/messages", "messages/VaAttachmentCore");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

