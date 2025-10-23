/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.param;

public class BatchReleaseResultParam {
    boolean success;
    String taskID;
    String formSchemeKey;
    String message;

    public BatchReleaseResultParam() {
    }

    public BatchReleaseResultParam(boolean success, String taskID, String formSchemeKey, String message) {
        this.success = success;
        this.taskID = taskID;
        this.formSchemeKey = formSchemeKey;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

