/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.asynctask.entity;

public class SubJobMessageInfo {
    private String taskId;
    private String message;

    public SubJobMessageInfo() {
    }

    public SubJobMessageInfo(String taskId, String message) {
        this.taskId = taskId;
        this.message = message;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

