/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.rabbitmq.client.DefaultSaslConfig
 *  com.rabbitmq.client.SaslConfig
 *  javax.annotation.PostConstruct
 *  org.apache.commons.lang3.BooleanUtils
 *  org.springframework.amqp.rabbit.connection.CachingConnectionFactory
 */
package com.jiuqi.bde.taskschedule.config;

import com.rabbitmq.client.DefaultSaslConfig;
import com.rabbitmq.client.SaslConfig;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BdeRabbitMqSslConfig {
    @Autowired
    private RabbitProperties rabbitProperties;
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @PostConstruct
    public void rabbitmqSslExternalPostConstruct() {
        boolean rabbitSslKeyStoreExists;
        boolean rabbitSslEnabled = BooleanUtils.toBoolean((Boolean)this.rabbitProperties.getSsl().getEnabled());
        boolean bl = rabbitSslKeyStoreExists = this.rabbitProperties.getSsl().getKeyStore() != null;
        if (rabbitSslEnabled && rabbitSslKeyStoreExists) {
            this.cachingConnectionFactory.getRabbitConnectionFactory().setSaslConfig((SaslConfig)DefaultSaslConfig.EXTERNAL);
        }
    }
}

