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

public class LogicProgressMonitor
implements IFmlMonitor {
    private final AsyncTaskMonitor asyncTaskMonitor;
    private double progressStart;
    private String progressMessage;
    private final double coefficient;

    public LogicProgressMonitor(AsyncTaskMonitor asyncTaskMonitor, double progressStart, String progressMessage, double coefficient) {
        assert (coefficient > 0.0 && coefficient <= 1.0) : "coefficient must be greater than 0 and not more than 1";
        this.asyncTaskMonitor = asyncTaskMonitor;
        this.progressStart = progressStart;
        this.progressMessage = progressMessage;
        this.coefficient = coefficient;
    }

    public String getTaskId() {
        return this.asyncTaskMonitor.getTaskId();
    }

    public void progressAndMessage(double currProgress, String message) {
        this.asyncTaskMonitor.progressAndMessage(this.progressStart + currProgress * this.coefficient, this.progressMessage);
    }

    public void error(String message, Throwable sender) {
        this.asyncTaskMonitor.progressAndMessage(this.progressStart + 1.0 * this.coefficient, this.progressMessage);
    }

    public void error(String message, Throwable sender, Object detail) {
        this.asyncTaskMonitor.progressAndMessage(this.progressStart + 1.0 * this.coefficient, this.progressMessage);
    }

    public void finish(String result, Object detail) {
        this.asyncTaskMonitor.progressAndMessage(this.progressStart + 1.0 * this.coefficient, this.progressMessage);
    }

    public void cancel(String message, Object detail) {
        this.asyncTaskMonitor.canceling(message, detail);
    }

    public void canceled(String result, Object detail) {
        this.asyncTaskMonitor.canceled(result, detail);
    }

    public boolean isCancel() {
        return this.asyncTaskMonitor.isCancel();
    }

    public void setProgressStart(double progressStart) {
        this.progressStart = progressStart;
    }

    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }

    public double getProgressEnd() {
        return this.progressStart + 1.0 * this.coefficient;
    }
}

