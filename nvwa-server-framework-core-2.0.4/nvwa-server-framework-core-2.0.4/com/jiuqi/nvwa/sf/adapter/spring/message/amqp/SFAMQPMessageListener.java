/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.Message
 *  com.jiuqi.bi.core.messagequeue.MessageItem
 *  org.springframework.amqp.core.Message
 *  org.springframework.amqp.core.MessageListener
 */
package com.jiuqi.nvwa.sf.adapter.spring.message.amqp;

import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.Message;
import com.jiuqi.bi.core.messagequeue.MessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;

public class SFAMQPMessageListener
implements MessageListener {
    private final Logger logger = LoggerFactory.getLogger(SFAMQPMessageListener.class);
    private final IMessageReceiver receiver;

    public SFAMQPMessageListener(IMessageReceiver iMessageReceiver) {
        this.receiver = iMessageReceiver;
    }

    public void onMessage(org.springframework.amqp.core.Message message) {
        long l = System.currentTimeMillis();
        Message sfMessage = new Message(l, l, true);
        MessageItem e = new MessageItem();
        e.setTimestamp(l);
        e.setResourceId(new String(message.getBody()));
        sfMessage.getItems().add(e);
        try {
            this.receiver.receive(sfMessage);
        }
        catch (Exception e1) {
            this.logger.error(e1.getMessage(), e1);
        }
    }

    public IMessageReceiver getReceiver() {
        return this.receiver;
    }
}

