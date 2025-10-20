/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class FormCopyPushPublishResult {
    boolean success;
    String taskID;
    String deployTaskID;
    String result;

    public FormCopyPushPublishResult() {
    }

    public FormCopyPushPublishResult(boolean success, String taskID, String deployTaskID, String result) {
        this.success = success;
        this.taskID = taskID;
        this.deployTaskID = deployTaskID;
        this.result = result;
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

    public String getDeployTaskID() {
        return this.deployTaskID;
    }

    public void setDeployTaskID(String deployTaskID) {
        this.deployTaskID = deployTaskID;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

