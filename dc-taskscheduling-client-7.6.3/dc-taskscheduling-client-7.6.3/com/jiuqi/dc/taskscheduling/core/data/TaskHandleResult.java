/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.data;

public class TaskHandleResult {
    private StringBuilder logger;
    private String preParam;
    private Boolean success = true;
    private Boolean sendPostTaskMsgWhileHandleTask = false;
    private Boolean rejoin = false;

    public TaskHandleResult() {
        this.logger = new StringBuilder();
    }

    public TaskHandleResult appendLog(String log) {
        this.logger.append(log);
        return this;
    }

    public TaskHandleResult(StringBuilder logger) {
        this.logger = logger;
    }

    public String getLog() {
        return this.logger.toString();
    }

    public StringBuilder getLogger() {
        return this.logger;
    }

    public String getPreParam() {
        return this.preParam;
    }

    public void setPreParam(String preParam) {
        this.preParam = preParam;
    }

    public Boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSendPostTaskMsgWhileHandleTask() {
        return this.sendPostTaskMsgWhileHandleTask;
    }

    public void setSendPostTaskMsgWhileHandleTask(Boolean sendPostTaskMsgWhileHandleTask) {
        this.sendPostTaskMsgWhileHandleTask = sendPostTaskMsgWhileHandleTask;
    }

    public Boolean getRejoin() {
        return this.rejoin;
    }

    public void setRejoin(Boolean rejoin) {
        this.rejoin = rejoin;
    }
}

