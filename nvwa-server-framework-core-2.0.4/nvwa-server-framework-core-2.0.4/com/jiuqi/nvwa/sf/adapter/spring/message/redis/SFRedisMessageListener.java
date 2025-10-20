/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.Message
 *  com.jiuqi.bi.core.messagequeue.MessageItem
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.serializer.StringRedisSerializer
 */
package com.jiuqi.nvwa.sf.adapter.spring.message.redis;

import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.Message;
import com.jiuqi.bi.core.messagequeue.MessageItem;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class SFRedisMessageListener
implements MessageListener {
    private final IMessageReceiver receiver;
    private final StringRedisSerializer stringRedisSerializer;

    public SFRedisMessageListener(IMessageReceiver receiver, StringRedisSerializer stringRedisSerializer) {
        this.receiver = receiver;
        this.stringRedisSerializer = stringRedisSerializer;
    }

    public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
        String groupId = this.stringRedisSerializer.deserialize(message.getChannel());
        if (this.receiver.getGroupId().equals(groupId)) {
            long l = System.currentTimeMillis();
            Message sfMessage = new Message(l, l, true);
            MessageItem e = new MessageItem();
            e.setTimestamp(l);
            e.setResourceId(this.stringRedisSerializer.deserialize(message.getBody()));
            sfMessage.getItems().add(e);
            this.receiver.receive(sfMessage);
        }
    }

    public IMessageReceiver getReceiver() {
        return this.receiver;
    }
}

