/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.task;

import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.task.AbstractMessageProcessor;
import org.springframework.stereotype.Component;

@Component
public class StarterMessageProcessor
extends AbstractMessageProcessor {
    @Override
    protected boolean support(MessageDTO messageDTO) {
        return false;
    }

    @Override
    public boolean process(MessageDTO messageDTO) {
        return false;
    }
}

