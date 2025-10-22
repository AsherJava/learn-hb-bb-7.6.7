/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import org.springframework.context.ApplicationEvent;

public class TaskCompleteEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String messageId;
    private String userId;

    public TaskCompleteEvent() {
        super(0);
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

