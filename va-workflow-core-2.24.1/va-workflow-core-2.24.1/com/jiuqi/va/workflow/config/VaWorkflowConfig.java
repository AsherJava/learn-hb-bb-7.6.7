/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
 *  org.springframework.amqp.rabbit.connection.ConnectionFactory
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.workflow.config;

import java.util.Locale;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.workflow"})
@MapperScan(basePackages={"com.jiuqi.va.workflow.dao"})
@PropertySource(value={"classpath:va-workflow-core.properties"})
public class VaWorkflowConfig {
    public static final int DEFAULT_CONCURRENT = 10;

    @Bean(name={"workflowMessageSource"})
    public ReloadableResourceBundleMessageSource workflowMessageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        source.setBasenames("classpath:messages/messages", "classpath:messages/VaWorkflowCore");
        source.setDefaultEncoding("utf-8");
        return source;
    }

    @Bean(value={"customContainerFactory"})
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(Integer.valueOf(10));
        factory.setMaxConcurrentConsumers(Integer.valueOf(10));
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}

