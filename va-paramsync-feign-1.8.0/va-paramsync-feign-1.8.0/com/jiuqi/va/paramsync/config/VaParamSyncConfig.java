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
 */
package com.jiuqi.va.paramsync.config;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.paramsync.common.VaParamTransferBusinessNodeMsgReciver;
import com.jiuqi.va.paramsync.common.VaParamTransferCollectorMsgReciver;
import com.jiuqi.va.paramsync.common.VaParamTransferFolderNodeMsgReciver;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.paramsync"})
public class VaParamSyncConfig {
    private static String paramTransferCollectorMsg = null;
    private static String paramTransferFolderNodesMsg = null;
    private static String paramTransferFolderNodeMsg = null;
    private static String paramTransferFolderNodeAddMsg = null;
    private static String paramTransferBusinessNodesMsg = null;
    private static String paramTransferBusinessNodeMsg = null;
    private static String paramTransferPathFoldersMsg = null;
    private static String paramTransferRelatedBusinessMsg = null;
    private static String paramTransferModelImportMsg = null;
    private static String paramTransferModelExportMsg = null;
    private static String paramTransferDataImportMsg = null;
    private static String paramTransferDataExportMsg = null;

    public VaParamSyncConfig(EnvConfig envConfig) {
    }

    public static boolean isRedisEnable() {
        return EnvConfig.getRedisEnable();
    }

    @Bean(value={"VaParamSyncRedisTemplate"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisTemplate<String, byte[]> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(byte[].class);
        template.setValueSerializer((RedisSerializer)genericToStringSerializer);
        template.setKeySerializer((RedisSerializer)new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    private static void initReidsPub(int database) {
        paramTransferCollectorMsg = "VaParamTransferCollectorMsg@" + database;
        paramTransferFolderNodesMsg = "VaParamTransferFolderNodesMsg@" + database;
        paramTransferFolderNodeMsg = "VaParamTransferFolderNodeMsg@" + database;
        paramTransferFolderNodeAddMsg = "VaParamTransferFolderNodeAddMsg@" + database;
        paramTransferBusinessNodesMsg = "VaParamTransferBusinessNodesMsg@" + database;
        paramTransferBusinessNodeMsg = "VaParamTransferBusinessNodeMsg@" + database;
        paramTransferPathFoldersMsg = "VaParamTransferPathFoldersMsg@" + database;
        paramTransferRelatedBusinessMsg = "VaParamTransferRelatedBusinessMsg@" + database;
        paramTransferModelImportMsg = "VaParamTransferModelImportMsg@" + database;
        paramTransferModelExportMsg = "VaParamTransferModelExportMsg@" + database;
        paramTransferDataImportMsg = "VaParamTransferDataImportMsg@" + database;
        paramTransferDataExportMsg = "VaParamTransferDataExportMsg@" + database;
    }

    public static String getParamTransferCollectorMsg() {
        return paramTransferCollectorMsg;
    }

    public static String getParamTransferFolderNodesMsg() {
        return paramTransferFolderNodesMsg;
    }

    public static String getParamTransferFolderNodeMsg() {
        return paramTransferFolderNodeMsg;
    }

    public static String getParamTransferFolderNodeAddMsg() {
        return paramTransferFolderNodeAddMsg;
    }

    public static String getParamTransferBusinessNodesMsg() {
        return paramTransferBusinessNodesMsg;
    }

    public static String getParamTransferBusinessNodeMsg() {
        return paramTransferBusinessNodeMsg;
    }

    public static String getParamTransferPathFoldersMsg() {
        return paramTransferPathFoldersMsg;
    }

    public static String getParamTransferRelatedBusinessMsg() {
        return paramTransferRelatedBusinessMsg;
    }

    public static String getParamTransferModelImportMsg() {
        return paramTransferModelImportMsg;
    }

    public static String getParamTransferModelExportMsg() {
        return paramTransferModelExportMsg;
    }

    public static String getParamTransferDataImportMsg() {
        return paramTransferDataImportMsg;
    }

    public static String getParamTransferDataExportMsg() {
        return paramTransferDataExportMsg;
    }

    @Bean(value={"vaParamTransferCollectorContainer"})
    @ConditionalOnExpression(value="${spring.redis.enabled:true}")
    RedisMessageListenerContainer vaParamTransferCollectorContainer(RedisProperties redisProperties, RedisConnectionFactory connectionFactory, VaParamTransferCollectorMsgReciver vaParamTransferCollectorMsgReciver, VaParamTransferFolderNodeMsgReciver vaParamTransferFolderNodeMsgReciver, VaParamTransferBusinessNodeMsgReciver vaParamTransferBusinessNodeMsgReciver) {
        VaParamSyncConfig.initReidsPub(redisProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((MessageListener)vaParamTransferCollectorMsgReciver, (Topic)new ChannelTopic(paramTransferCollectorMsg));
        List<ChannelTopic> folderMsgList = Arrays.asList(new ChannelTopic(paramTransferFolderNodesMsg), new ChannelTopic(paramTransferFolderNodeMsg), new ChannelTopic(paramTransferFolderNodeAddMsg));
        container.addMessageListener((MessageListener)vaParamTransferFolderNodeMsgReciver, folderMsgList);
        List<ChannelTopic> businessMsgList = Arrays.asList(new ChannelTopic(paramTransferBusinessNodesMsg), new ChannelTopic(paramTransferBusinessNodeMsg), new ChannelTopic(paramTransferPathFoldersMsg), new ChannelTopic(paramTransferRelatedBusinessMsg), new ChannelTopic(paramTransferModelImportMsg), new ChannelTopic(paramTransferModelExportMsg), new ChannelTopic(paramTransferDataImportMsg), new ChannelTopic(paramTransferDataExportMsg));
        container.addMessageListener((MessageListener)vaParamTransferBusinessNodeMsgReciver, businessMsgList);
        return container;
    }
}

