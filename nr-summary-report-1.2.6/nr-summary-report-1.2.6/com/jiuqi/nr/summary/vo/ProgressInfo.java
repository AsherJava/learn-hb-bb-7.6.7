/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.TaskState;

public class ProgressInfo {
    private double percent;
    private String detail;
    private TaskState state;

    public ProgressInfo() {
    }

    public ProgressInfo(AsyncTask asyncTask) {
        this.percent = asyncTask.getProcess();
        if (asyncTask.getResult() != null) {
            this.detail = asyncTask.getResult();
        }
        this.state = asyncTask.getState();
    }

    public double getPercent() {
        return this.percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}

