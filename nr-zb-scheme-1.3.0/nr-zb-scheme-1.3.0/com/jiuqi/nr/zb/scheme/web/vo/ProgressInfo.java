/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.TaskState;

public class ProgressInfo {
    private double percent;
    private String prompt;
    private String result;
    private TaskState state;

    public ProgressInfo(AsyncTask asyncTask) {
        this.percent = asyncTask.getProcess();
        this.result = asyncTask.getResult();
        this.state = asyncTask.getState();
    }

    public ProgressInfo(AsyncTask asyncTask, String result, String prompt) {
        this.percent = asyncTask.getProcess();
        this.state = asyncTask.getState();
        this.result = result;
        this.prompt = prompt;
    }

    public ProgressInfo(AsyncTask asyncTask, String prompt) {
        this.percent = asyncTask.getProcess();
        this.prompt = prompt;
        this.result = asyncTask.getResult();
        this.state = asyncTask.getState();
    }

    public double getPercent() {
        return this.percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}

