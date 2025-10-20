/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageQueuePolicy
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.IMessageSender
 *  org.springframework.amqp.core.AmqpTemplate
 *  org.springframework.amqp.core.Binding
 *  org.springframework.amqp.core.Binding$DestinationType
 *  org.springframework.amqp.core.Exchange
 *  org.springframework.amqp.core.FanoutExchange
 *  org.springframework.amqp.core.MessageListener
 *  org.springframework.amqp.core.Queue
 *  org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
 *  org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint
 *  org.springframework.amqp.rabbit.core.RabbitAdmin
 *  org.springframework.amqp.rabbit.core.RabbitTemplate
 *  org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory
 *  org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint
 *  org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
 */
package com.jiuqi.nvwa.sf.adapter.spring.message.amqp;

import com.jiuqi.bi.core.messagequeue.IMessageQueuePolicy;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.IMessageSender;
import com.jiuqi.nvwa.sf.adapter.spring.message.amqp.SFAMQPMessageListener;
import com.jiuqi.nvwa.sf.adapter.spring.message.amqp.SFRabbitMessageSender;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueAMQPPolicy
implements IMessageQueuePolicy {
    private final Map<String, SFAMQPMessageListener> receivers = new HashMap<String, SFAMQPMessageListener>();
    @Autowired(required=false)
    private RabbitTemplate rabbitTemplate;
    @Autowired(required=false)
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    @Autowired(required=false)
    private List<SimpleRabbitListenerContainerFactory> simpleRabbitListenerContainerFactoryList;
    private boolean isRunning = false;

    public String getPolicy() {
        return "AMQP";
    }

    public IMessageSender getMessageSender(String groupId) {
        SFAMQPMessageListener sfamqpMessageListener = this.receivers.get(groupId);
        return new SFRabbitMessageSender(groupId, (AmqpTemplate)this.rabbitTemplate, sfamqpMessageListener);
    }

    public void registReceiver(IMessageReceiver iMessageReceiver) {
        SFAMQPMessageListener sfamqpMessageListener = new SFAMQPMessageListener(iMessageReceiver);
        RabbitAdmin admin = new RabbitAdmin(this.rabbitTemplate.getConnectionFactory());
        FanoutExchange exchange = new FanoutExchange(iMessageReceiver.getGroupId());
        admin.declareExchange((Exchange)exchange);
        Queue queue = new Queue(iMessageReceiver.getGroupId());
        admin.declareQueue(queue);
        Binding binding = new Binding(iMessageReceiver.getGroupId(), Binding.DestinationType.QUEUE, exchange.getName(), iMessageReceiver.getGroupId(), null);
        admin.declareBinding(binding);
        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
        endpoint.setMessageListener((MessageListener)sfamqpMessageListener);
        endpoint.setId(iMessageReceiver.getGroupId());
        endpoint.setQueueNames(new String[]{iMessageReceiver.getGroupId()});
        this.rabbitListenerEndpointRegistry.registerListenerContainer((RabbitListenerEndpoint)endpoint, (RabbitListenerContainerFactory)this.simpleRabbitListenerContainerFactoryList.get(0), true);
        this.receivers.put(iMessageReceiver.getGroupId(), sfamqpMessageListener);
    }

    public void start() {
        RabbitAdmin admin = new RabbitAdmin(this.rabbitTemplate.getConnectionFactory());
        for (String c : this.receivers.keySet()) {
            FanoutExchange exchange = new FanoutExchange(c);
            admin.declareExchange((Exchange)exchange);
        }
        this.isRunning = true;
    }

    public void stop() {
        RabbitAdmin admin = new RabbitAdmin(this.rabbitTemplate.getConnectionFactory());
        for (String c : this.receivers.keySet()) {
            admin.deleteExchange(c);
        }
        this.isRunning = false;
    }

    public boolean hasStarted() {
        return this.isRunning;
    }
}

