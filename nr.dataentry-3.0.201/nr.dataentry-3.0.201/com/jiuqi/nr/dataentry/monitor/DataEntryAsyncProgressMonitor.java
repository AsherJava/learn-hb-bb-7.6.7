/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.monitor;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;

public class DataEntryAsyncProgressMonitor
implements AsyncTaskMonitor {
    private AsyncTaskMonitor asyncTaskMonitor;
    private double coefficient;
    private double progress;

    public DataEntryAsyncProgressMonitor(AsyncTaskMonitor asyncTaskMonitor, double coefficient, double progress) {
        this.asyncTaskMonitor = asyncTaskMonitor;
        this.coefficient = coefficient;
        this.progress = progress;
    }

    public String getTaskId() {
        return this.asyncTaskMonitor.getTaskId();
    }

    public String getTaskPoolTask() {
        return this.asyncTaskMonitor.getTaskPoolTask();
    }

    public void progressAndMessage(double progress, String message) {
        this.asyncTaskMonitor.progressAndMessage(this.progress + progress * this.coefficient, message);
    }

    public boolean isCancel() {
        return this.asyncTaskMonitor.isCancel();
    }

    public void finish(String result, Object detail) {
        this.asyncTaskMonitor.progressAndMessage(this.progress + this.coefficient, result);
    }

    public void canceling(String result, Object detail) {
    }

    public void canceled(String result, Object detail) {
    }

    public void error(String cause, Throwable t) {
        throw new RuntimeException(t);
    }

    public boolean isFinish() {
        return false;
    }

    public ILogger getBILogger() {
        return this.asyncTaskMonitor.getBILogger();
    }
}

