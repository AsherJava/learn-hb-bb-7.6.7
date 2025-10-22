/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel;

import java.io.Serializable;

public class ParallelExeInfo
implements Serializable {
    private static final long serialVersionUID = 2585165101089079626L;
    private String taskKey;
    private String parentKey;
    private double progress = 0.0;
    private double weight;
    private TaskState state = TaskState.WAITING;
    private long updateTime;

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public double getProgress() {
        return this.progress;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public static enum TaskState {
        WAITING,
        RUNNING,
        FINISH,
        CANCELING,
        CANCELED;

    }
}

