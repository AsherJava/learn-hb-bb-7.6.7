/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.core.TopicExchange
 */
package com.jiuqi.va.join.rabbitmq.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.join.rabbitmq"})
@PropertySource(value={"classpath:va-join-rabbitmq.properties"})
public class VaJoinRabbitmqConfig {
    @Bean(value={"topicExchange"})
    @ConditionalOnMissingBean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }
}

