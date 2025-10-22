/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.data.logic.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;

public class ProgressMonitor
implements IFmlMonitor {
    private final AsyncTaskMonitor asyncTaskMonitor;

    public ProgressMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    @Override
    public String getTaskId() {
        return this.asyncTaskMonitor.getTaskId();
    }

    @Override
    public void progressAndMessage(double currProgress, String message) {
        this.asyncTaskMonitor.progressAndMessage(currProgress, message);
    }

    @Override
    public void error(String message, Throwable sender) {
        this.asyncTaskMonitor.error(message, sender);
    }

    @Override
    public void finish(String result, Object detail) {
        this.asyncTaskMonitor.finish(result, detail);
    }

    @Override
    public void cancel(String message, Object detail) {
        this.asyncTaskMonitor.canceling(message, detail);
    }

    @Override
    public void error(String message, Throwable sender, Object detail) {
        this.asyncTaskMonitor.error(message, sender, detail == null ? null : detail.toString());
    }

    @Override
    public boolean isCancel() {
        return this.asyncTaskMonitor.isCancel();
    }

    @Override
    public void canceled(String result, Object detail) {
        this.asyncTaskMonitor.canceled(result, detail);
    }
}

