/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class NotSupportOperation
extends BpmException {
    private String taskId;
    private String actionId;

    public NotSupportOperation(String actionId, String taskId) {
        super(String.format("\u4efb\u52a1\u8282\u70b9\u4e0e\u52a8\u4f5c\u4e0d\u4e00\u81f4\uff01", new Object[0]));
        this.actionId = actionId;
        this.taskId = taskId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }
}

