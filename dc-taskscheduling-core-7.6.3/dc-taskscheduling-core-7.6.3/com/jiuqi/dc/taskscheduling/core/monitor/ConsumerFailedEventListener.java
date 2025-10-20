/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.listener.ListenerContainerConsumerFailedEvent
 *  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
 */
package com.jiuqi.dc.taskscheduling.core.monitor;

import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMqQueueMonitor;
import org.springframework.amqp.rabbit.listener.ListenerContainerConsumerFailedEvent;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerFailedEventListener
implements ApplicationListener<ListenerContainerConsumerFailedEvent> {
    @Override
    public void onApplicationEvent(ListenerContainerConsumerFailedEvent event) {
        if (event.isFatal()) {
            SimpleMessageListenerContainer container = (SimpleMessageListenerContainer)event.getSource();
            for (String queueName : container.getQueueNames()) {
                RabbitMqQueueMonitor.getInstance().reocrdQueueInfo(queueName);
            }
        }
    }
}

