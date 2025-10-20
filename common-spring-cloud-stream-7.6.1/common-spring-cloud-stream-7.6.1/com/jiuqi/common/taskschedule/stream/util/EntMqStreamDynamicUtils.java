/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.springframework.cloud.function.context.FunctionCatalog
 *  org.springframework.cloud.function.context.catalog.SimpleFunctionRegistry$FunctionInvocationWrapper
 *  org.springframework.cloud.function.context.message.MessageUtils
 *  org.springframework.cloud.stream.binder.Binding
 *  org.springframework.cloud.stream.binder.ConsumerProperties
 *  org.springframework.cloud.stream.binding.BindingService
 *  org.springframework.cloud.stream.binding.SubscribableChannelBindingTargetFactory
 *  org.springframework.cloud.stream.config.BindingProperties
 *  org.springframework.integration.handler.AbstractMessageHandler
 *  org.springframework.integration.support.MessageBuilder
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.SubscribableChannel
 */
package com.jiuqi.common.taskschedule.stream.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.taskschedule.stream.core.config.EntMqStreamConfig;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.catalog.SimpleFunctionRegistry;
import org.springframework.cloud.function.context.message.MessageUtils;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.binding.SubscribableChannelBindingTargetFactory;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public class EntMqStreamDynamicUtils {
    private static final String GROUP_NAME = "queue";
    private static final Map<String, Collection<Binding<SubscribableChannel>>> consumerBindingMap = new ConcurrentHashMap<String, Collection<Binding<SubscribableChannel>>>();
    private static final Map<Object, String> consumerBeanToQueueNameMap = new ConcurrentHashMap<Object, String>();
    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static String getQueueNameByBean(Object bean) {
        try {
            readWriteLock.readLock().lock();
            String string = consumerBeanToQueueNameMap.get(bean);
            return string;
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static Map<Object, String> getConsumerBeanToQueueNameMap() {
        try {
            readWriteLock.readLock().lock();
            Map<Object, String> map = consumerBeanToQueueNameMap;
            return map;
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static void addQueueToStream(String destination, boolean bindingOutPut, Class<?> beanClass) {
        EntMqStreamDynamicUtils.addQueueToStream(destination, GROUP_NAME, bindingOutPut, beanClass);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addQueueToStream(String destination, String group, boolean bindingOutPut, Class<?> beanClass) {
        try {
            readWriteLock.writeLock().lock();
            if (consumerBindingMap.containsKey(destination)) {
                return;
            }
            ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext)SpringContextUtils.getApplicationContext();
            if (!applicationContext.containsBean(destination)) {
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
                BeanDefinitionRegistry registry = (BeanDefinitionRegistry)((Object)applicationContext.getBeanFactory());
                registry.registerBeanDefinition(destination, beanDefinitionBuilder.getBeanDefinition());
            }
            BindingService bindingService = applicationContext.getBean(BindingService.class);
            SubscribableChannelBindingTargetFactory bindingTargetFactory = applicationContext.getBean(SubscribableChannelBindingTargetFactory.class);
            Map bindings = bindingService.getBindingServiceProperties().getBindings();
            String inputChannelName = destination + "-in-0";
            EntMqStreamConfig entMqStreamConfig = applicationContext.getBean(EntMqStreamConfig.class);
            ConsumerProperties consumerProperties = new ConsumerProperties();
            consumerProperties.setConcurrency(entMqStreamConfig.getConcurrency());
            BindingProperties inProperties = new BindingProperties();
            inProperties.setDestination(destination);
            inProperties.setGroup(group);
            inProperties.setConsumer(consumerProperties);
            bindings.put(inputChannelName, inProperties);
            String outputChannelName = destination + "-out-0";
            BindingProperties outProperties = new BindingProperties();
            outProperties.setDestination(destination);
            bindings.put(outputChannelName, outProperties);
            SubscribableChannel newChannel = bindingTargetFactory.createInput(inputChannelName);
            if (bindingOutPut) {
                Collection consumerBindings = bindingService.bindConsumer((Object)newChannel, inputChannelName);
                consumerBindingMap.put(destination, consumerBindings);
            }
            FunctionCatalog functionCatalog = applicationContext.getBean(FunctionCatalog.class);
            SimpleFunctionRegistry.FunctionInvocationWrapper function = (SimpleFunctionRegistry.FunctionInvocationWrapper)functionCatalog.lookup(destination, new String[]{""});
            AbstractMessageHandler handler = EntMqStreamDynamicUtils.createFunctionHandler(applicationContext, function);
            newChannel.subscribe((MessageHandler)handler);
            consumerBeanToQueueNameMap.put(applicationContext.getBean(destination), destination);
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void removeQueueBinding(String destination) {
        try {
            readWriteLock.writeLock().lock();
            ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext)SpringContextUtils.getApplicationContext();
            consumerBeanToQueueNameMap.remove(applicationContext.getBean(destination));
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private static AbstractMessageHandler createFunctionHandler(ConfigurableApplicationContext applicationContext, final SimpleFunctionRegistry.FunctionInvocationWrapper function) {
        AbstractMessageHandler handler = new AbstractMessageHandler(){

            public void handleMessageInternal(Message<?> message) throws MessagingException {
                function.apply((Object)EntMqStreamDynamicUtils.sanitize(message));
            }
        };
        handler.setBeanFactory((BeanFactory)applicationContext);
        handler.afterPropertiesSet();
        return handler;
    }

    private static Message<?> sanitize(Message<?> inputMessage) {
        MessageBuilder builder = MessageBuilder.fromMessage(inputMessage).removeHeader("spring.cloud.stream.sendto.destination").removeHeader(MessageUtils.SOURCE_TYPE);
        if (builder.getHeaders().containsKey(MessageUtils.TARGET_PROTOCOL)) {
            builder = builder.setHeader(MessageUtils.SOURCE_TYPE, builder.getHeaders().get(MessageUtils.TARGET_PROTOCOL));
        }
        return builder.removeHeader(MessageUtils.TARGET_PROTOCOL).build();
    }
}

