/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.event;

import org.springframework.context.ApplicationEvent;

public class TaskCancelEvent
extends ApplicationEvent {
    private String taskId;
    private String taskPoolType;

    public TaskCancelEvent() {
        super(0);
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskPoolType() {
        return this.taskPoolType;
    }

    public void setTaskPoolType(String taskPoolType) {
        this.taskPoolType = taskPoolType;
    }
}

