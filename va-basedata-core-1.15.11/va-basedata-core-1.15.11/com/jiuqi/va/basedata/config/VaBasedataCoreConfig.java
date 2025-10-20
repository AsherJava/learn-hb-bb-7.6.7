/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.util.StringUtils
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.basedata.config;

import com.jiuqi.va.basedata.service.join.VaBaseDataDefineSyncCacheMsgReciver;
import com.jiuqi.va.basedata.service.join.VaBaseDataRegisterCacheMsgReciver;
import com.jiuqi.va.basedata.service.join.VaBaseDataSyncCacheMsgReciver;
import com.jiuqi.va.basedata.service.join.VaBaseDataVersionSyncCacheMsgReciver;
import com.jiuqi.va.basedata.service.join.VaEnumDataSyncCacheMsgReciver;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages={"com.jiuqi.va.basedata"})
@MapperScan(basePackages={"com.jiuqi.va.basedata.dao"})
@PropertySource(value={"classpath:va-basedata-core.properties"})
public class VaBasedataCoreConfig {
    @Value(value="${nvwa.basedata.mq.publish.subfix:}")
    private String mqPublishSubfix;
    private static boolean inited = false;
    private static String basedataDefineSyncCachePub = null;
    private static String baseDataRegisterCachePub = null;
    private static String baseDataSyncCachePub = null;
    private static String basedataVersionSyncCachePub = null;
    private static String enumDataSyncCachePub = null;

    public static void initRedisPub(int database) {
        if (inited) {
            return;
        }
        basedataDefineSyncCachePub = "BasedataDefineSyncCachePub@" + database;
        baseDataRegisterCachePub = "BaseDataRegisterCachePub@" + database;
        baseDataSyncCachePub = "BaseDataSyncCachePub@" + database;
        basedataVersionSyncCachePub = "BasedataVersionSyncCachePub@" + database;
        enumDataSyncCachePub = "EnumDataSyncCachePub@" + database;
        inited = true;
    }

    public static String getBasedataDefineSyncCachePub() {
        return basedataDefineSyncCachePub;
    }

    public static String getBaseDataRegisterCachePub() {
        return baseDataRegisterCachePub;
    }

    public static String getBaseDataSyncCachePub() {
        return baseDataSyncCachePub;
    }

    public static String getBasedataVersionSyncCachePub() {
        return basedataVersionSyncCachePub;
    }

    public static String getEnumDataSyncCachePub() {
        return enumDataSyncCachePub;
    }

    @Bean(value={"vaBaseDataSyncCacheContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisMessageListenerContainer vaBaseDataSyncCacheContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaBaseDataDefineSyncCacheMsgReciver vaBaseDataDefineSyncCacheMsgReciver, VaBaseDataRegisterCacheMsgReciver vaBaseDataRegisterCacheMsgReciver, VaBaseDataSyncCacheMsgReciver vaBaseDataSyncCacheMsgReciver, VaBaseDataVersionSyncCacheMsgReciver vaBaseDataVersionSyncCacheMsgReciver, VaEnumDataSyncCacheMsgReciver vaEnumDataSyncCacheMsgReciver) {
        if (StringUtils.hasText((String)this.mqPublishSubfix)) {
            VaBasedataCoreConfig.initRedisPub(this.mqPublishSubfix.hashCode());
        } else {
            VaBasedataCoreConfig.initRedisPub(redisProperties.getDatabase());
        }
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor((Executor)Executors.newWorkStealingPool());
        container.addMessageListener((MessageListener)vaBaseDataDefineSyncCacheMsgReciver, (Topic)new ChannelTopic(basedataDefineSyncCachePub));
        container.addMessageListener((MessageListener)vaBaseDataRegisterCacheMsgReciver, (Topic)new ChannelTopic(baseDataRegisterCachePub));
        container.addMessageListener((MessageListener)vaBaseDataSyncCacheMsgReciver, (Topic)new ChannelTopic(baseDataSyncCachePub));
        container.addMessageListener((MessageListener)vaBaseDataVersionSyncCacheMsgReciver, (Topic)new ChannelTopic(basedataVersionSyncCachePub));
        container.addMessageListener((MessageListener)vaEnumDataSyncCacheMsgReciver, (Topic)new ChannelTopic(enumDataSyncCachePub));
        return container;
    }

    @Bean(name={"vaBaseDataCoreMessageSource"})
    MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setBasenames("messages/messages", "messages/VaBaseDataCore");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }

    @Bean(value={"vaBaseDataAsyncTaskExecutor"})
    Executor vaBaseDataAsyncTaskExecutor() {
        return Executors.newWorkStealingPool();
    }
}

