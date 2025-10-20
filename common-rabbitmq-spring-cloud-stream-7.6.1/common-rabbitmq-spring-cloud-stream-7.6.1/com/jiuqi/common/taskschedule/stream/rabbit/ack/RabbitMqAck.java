/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.taskschedule.stream.core.ack.EntManualAck
 *  com.rabbitmq.client.Channel
 *  org.springframework.messaging.Message
 */
package com.jiuqi.common.taskschedule.stream.rabbit.ack;

import com.jiuqi.common.taskschedule.stream.core.ack.EntManualAck;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqAck
implements EntManualAck {
    public void ack(Message message) {
        Channel channel = (Channel)message.getHeaders().get((Object)"amqp_channel", Channel.class);
        if (channel == null) {
            return;
        }
        Long deliveryTag = (Long)message.getHeaders().get((Object)"amqp_deliveryTag", Long.class);
        try {
            channel.basicAck(deliveryTag.longValue(), false);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

