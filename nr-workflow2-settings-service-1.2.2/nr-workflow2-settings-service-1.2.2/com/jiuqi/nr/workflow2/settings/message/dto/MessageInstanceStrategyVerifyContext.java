/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.dto;

import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceBaseContext;

public class MessageInstanceStrategyVerifyContext
extends MessageInstanceBaseContext {
    private Object receiver;

    public Object getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Object receiver) {
        this.receiver = receiver;
    }
}

