/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageSender
 *  org.springframework.amqp.core.AmqpTemplate
 */
package com.jiuqi.nvwa.sf.adapter.spring.message.amqp;

import com.jiuqi.bi.core.messagequeue.IMessageSender;
import com.jiuqi.nvwa.sf.adapter.spring.message.amqp.SFAMQPMessageListener;
import org.springframework.amqp.core.AmqpTemplate;

public class SFRabbitMessageSender
implements IMessageSender {
    private final String groupId;
    private final AmqpTemplate amqpTemplate;
    private final SFAMQPMessageListener sfamqpMessageListener;

    public SFRabbitMessageSender(String groupId, AmqpTemplate amqpTemplate, SFAMQPMessageListener sfamqpMessageListener) {
        this.groupId = groupId;
        this.amqpTemplate = amqpTemplate;
        this.sfamqpMessageListener = sfamqpMessageListener;
    }

    public String getGroupId() {
        return null;
    }

    public void send(String s) throws Exception {
        this.amqpTemplate.convertAndSend(this.groupId, this.groupId, (Object)s);
    }
}

