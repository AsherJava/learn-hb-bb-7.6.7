/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.np.asynctask.TaskState;
import java.time.Instant;

public class AsyncTaskInfo {
    private String id;
    private String taskPoolType;
    private Double process;
    private String result;
    private TaskState state;
    private Object args;
    private Instant createTime;
    private Object detail;

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getArgs() {
        return this.args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getTaskPoolType() {
        return this.taskPoolType;
    }

    public void setTaskPoolType(String taskPoolType) {
        this.taskPoolType = taskPoolType;
    }

    public Object getDetail() {
        return this.detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

