/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.datav.dashboard.monitor.table;

public class DashboardExecTraceInfo {
    private String taskId;
    private String stage;
    private String msg;
    private String time;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return this.stage;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

