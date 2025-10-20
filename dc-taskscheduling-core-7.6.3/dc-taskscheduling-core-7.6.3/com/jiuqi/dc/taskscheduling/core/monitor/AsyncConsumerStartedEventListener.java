/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.listener.AsyncConsumerStartedEvent
 *  org.springframework.amqp.rabbit.listener.BlockingQueueConsumer
 *  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
 */
package com.jiuqi.dc.taskscheduling.core.monitor;

import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMqQueueMonitor;
import org.springframework.amqp.rabbit.listener.AsyncConsumerStartedEvent;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncConsumerStartedEventListener {
    @EventListener
    @Async
    public void onApplicationEvent(AsyncConsumerStartedEvent event) {
        SimpleMessageListenerContainer container = (SimpleMessageListenerContainer)event.getSource();
        BlockingQueueConsumer consumer = (BlockingQueueConsumer)event.getConsumer();
        RabbitMqQueueMonitor.getInstance().regisiterContainer(container, consumer);
    }
}

