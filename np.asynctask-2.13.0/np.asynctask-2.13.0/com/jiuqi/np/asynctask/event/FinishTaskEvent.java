/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.event;

import org.springframework.context.ApplicationEvent;

public class FinishTaskEvent
extends ApplicationEvent {
    private String taskPoolType;

    public FinishTaskEvent() {
        super(0);
    }

    public String getTaskPoolType() {
        return this.taskPoolType;
    }

    public void setTaskPoolType(String taskPoolType) {
        this.taskPoolType = taskPoolType;
    }
}

