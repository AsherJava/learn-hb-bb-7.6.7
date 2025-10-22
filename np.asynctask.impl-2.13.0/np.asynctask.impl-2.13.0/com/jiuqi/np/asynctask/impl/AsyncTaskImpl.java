/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.np.asynctask.impl;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.TaskState;
import java.time.Instant;

public class AsyncTaskImpl
implements AsyncTask {
    private String taskId;
    private TaskState state;
    private Instant createTime;
    private Instant waitingTime;
    private Instant processTime;
    private Instant finishTime;
    private Instant cancelTime;
    private Instant effectTime;
    private Object args;
    private String dependTaskId;
    private Double process;
    private String result;
    private Object detail;
    private String taskPoolType;
    private String createUserId;
    private String dimensionIdentify;
    private String serveId;
    private Integer priority = 2;
    private Object context;
    private long effectTimeLong;
    private String taskKey;
    private String formSchemeKey;
    private String publishType;

    public String getTaskId() {
        return this.taskId;
    }

    public TaskState getState() {
        return this.state;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public Instant getWaitingTime() {
        return this.waitingTime;
    }

    public Instant getProcessTime() {
        return this.processTime;
    }

    public Instant getFinishTime() {
        return this.finishTime;
    }

    public Instant getCancelTime() {
        return this.cancelTime;
    }

    public Instant getEffectTime() {
        return this.effectTime;
    }

    public Object getArgs() {
        return this.args;
    }

    public String getDependTaskId() {
        return this.dependTaskId;
    }

    public Double getProcess() {
        return this.process;
    }

    public String getResult() {
        return this.result;
    }

    public Object getDetail() {
        return this.detail;
    }

    public String getTaskPoolType() {
        return this.taskPoolType;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }

    public String getDimensionIdentify() {
        return this.dimensionIdentify;
    }

    public String getServeId() {
        return this.serveId;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getPublishType() {
        return this.publishType;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public void setWaitingTime(Instant waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setProcessTime(Instant processTime) {
        this.processTime = processTime;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public void setCancelTime(Instant cancelTime) {
        this.cancelTime = cancelTime;
    }

    public void setEffectTime(Instant effectTime) {
        this.effectTime = effectTime;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    public void setDependTaskId(String dependTaskId) {
        this.dependTaskId = dependTaskId;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public void setTaskPoolType(String taskPoolType) {
        this.taskPoolType = taskPoolType;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public void setDimensionIdentify(String dimensionIdentify) {
        this.dimensionIdentify = dimensionIdentify;
    }

    public void setServeId(String serveId) {
        this.serveId = serveId;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public Object getContext() {
        return this.context;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public long getEffectTimeLong() {
        return this.effectTimeLong;
    }

    public void setEffectTimeLong(long effectTimeLong) {
        this.effectTimeLong = effectTimeLong;
    }
}

