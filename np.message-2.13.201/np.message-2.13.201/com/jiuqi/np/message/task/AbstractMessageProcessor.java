/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.task;

import com.jiuqi.np.message.pojo.MagResult;
import com.jiuqi.np.message.pojo.MessageDTO;

public abstract class AbstractMessageProcessor {
    private AbstractMessageProcessor messageProcessor;

    protected abstract boolean process(MessageDTO var1);

    AbstractMessageProcessor setNext(AbstractMessageProcessor messageProcessor) {
        if (messageProcessor != null) {
            this.messageProcessor = messageProcessor;
            return messageProcessor;
        }
        return this;
    }

    protected abstract boolean support(MessageDTO var1);

    public final void handle(MessageDTO messageDTO, MagResult handle) {
        if (this.support(messageDTO)) {
            boolean flag = this.process(messageDTO);
            if (flag) {
                handle.success();
            } else {
                handle.fail();
            }
        }
        if (handle.isFail()) {
            return;
        }
        if (this.messageProcessor != null) {
            this.messageProcessor.handle(messageDTO, handle);
        }
    }
}

