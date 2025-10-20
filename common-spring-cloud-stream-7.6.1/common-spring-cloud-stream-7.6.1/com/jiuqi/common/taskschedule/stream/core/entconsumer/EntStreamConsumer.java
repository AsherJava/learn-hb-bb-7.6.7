/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package com.jiuqi.common.taskschedule.stream.core.entconsumer;

import com.jiuqi.common.taskschedule.stream.util.EntManualAckUtil;
import java.util.function.Consumer;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface EntStreamConsumer
extends Consumer<Message<String>> {
    @Override
    default public void accept(Message<String> message) {
        try {
            this.accept((String)message.getPayload());
        }
        finally {
            EntManualAckUtil.ack(message);
        }
    }

    @Override
    public void accept(String var1);
}

