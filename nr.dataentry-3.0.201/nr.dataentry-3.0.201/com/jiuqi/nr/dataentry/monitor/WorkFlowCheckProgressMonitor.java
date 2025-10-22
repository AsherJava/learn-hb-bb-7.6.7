/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 */
package com.jiuqi.nr.dataentry.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;

public class WorkFlowCheckProgressMonitor
implements IFmlMonitor {
    private AsyncTaskMonitor asyncTaskMonitor;
    private double coefficient;
    private double progress;
    private int num = 0;
    private int count;

    public WorkFlowCheckProgressMonitor(AsyncTaskMonitor asyncTaskMonitor, double coefficient, double progress) {
        this.asyncTaskMonitor = asyncTaskMonitor;
        this.coefficient = coefficient;
        this.progress = progress;
    }

    public String getTaskId() {
        return this.asyncTaskMonitor.getTaskId();
    }

    public void progressAndMessage(double progress, String message) {
        this.asyncTaskMonitor.progressAndMessage(this.progress + progress * this.coefficient, message);
    }

    public boolean isCancel() {
        return this.asyncTaskMonitor.isCancel();
    }

    public void finish(String result, Object detail) {
        if (this.num == this.count - 1) {
            this.asyncTaskMonitor.progressAndMessage(this.progress + this.coefficient, result);
        } else {
            this.asyncTaskMonitor.progressAndMessage(this.progress + this.coefficient, null);
        }
        ++this.num;
    }

    public void error(String cause, Throwable t) {
        this.asyncTaskMonitor.progressAndMessage(this.progress + this.coefficient, cause);
    }

    public void error(String message, Throwable sender, Object detail) {
        this.asyncTaskMonitor.progressAndMessage(this.progress + this.coefficient, message);
    }

    public void cancel(String message, Object detail) {
    }

    public double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

