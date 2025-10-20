/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.gcreport.intermediatelibrary.condition;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;

public class ILClearCondition {
    private int startYear;
    private int endYear;
    private String sn;
    private double currentProgress;
    private double stepProgress;
    private AsyncTaskMonitor asyncTaskMonitor;

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getCurrentProgress() {
        return this.currentProgress;
    }

    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = currentProgress;
    }

    public double getStepProgress() {
        return this.stepProgress;
    }

    public void setStepProgress(double stepProgress) {
        this.stepProgress = stepProgress;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return this.endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}

