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
package com.jiuqi.va.biz.config;

import com.jiuqi.va.biz.impl.join.VaBizBindingMsgReciver;
import com.jiuqi.va.biz.impl.join.VaSyncCacheMsgReciver;
import com.jiuqi.va.domain.common.EnvConfig;
import java.util.Locale;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableScheduling
@ComponentScan(basePackages={"com.jiuqi.va.biz"})
@MapperScan(basePackages={"com.jiuqi.va.biz.dao"})
public class VaBizBindingConfig {
    private static boolean redisEnable = true;
    private static String bizBindingCachePub = null;
    private static String bizBindingSyncCachePub = null;
    private static final String currNodeId = UUID.randomUUID().toString();

    public static String getCurrNodeId() {
        return currNodeId;
    }

    @Autowired
    public void setRedisEnable(Environment environment) {
        redisEnable = EnvConfig.getRedisEnable((Environment)environment);
    }

    public static boolean isRedisEnable() {
        return redisEnable;
    }

    private static void initRedisPub(int database) {
        bizBindingCachePub = "BizBindingCachePub@" + database;
        bizBindingSyncCachePub = "bizBindingSyncCachePub@" + database;
    }

    public static String getBizBindingCachePub() {
        return bizBindingCachePub;
    }

    public static String getBizBindingSyncCachePub() {
        return bizBindingSyncCachePub;
    }

    @Bean(value={"vaBizBindingContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    public RedisMessageListenerContainer vaBizBindingContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaBizBindingMsgReciver vaBizBindingMsgReciver, VaSyncCacheMsgReciver vaSyncCacheMsgReciver) {
        VaBizBindingConfig.initRedisPub(redisProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)vaBizBindingMsgReciver, (Topic)new ChannelTopic(bizBindingCachePub));
        container.addMessageListener((MessageListener)vaSyncCacheMsgReciver, (Topic)new ChannelTopic(bizBindingSyncCachePub));
        return container;
    }

    @Bean(name={"bizBindingMessageSource"})
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasenames("classpath:messages/messages", "classpath:messages/VaBizBinding");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

