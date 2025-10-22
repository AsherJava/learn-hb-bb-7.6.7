/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.efdc.monitor;

import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;

public class EfdcMonitor
implements AsyncTaskMonitor {
    private double weight;
    private double lastProgress = 0.0;
    private AsyncTaskMonitor monitor;
    private AsyncTaskManager asyncTaskManger;

    public EfdcMonitor(AsyncTaskMonitor monitor, double weight, AsyncTaskManager asyncTaskManger) {
        this.monitor = monitor;
        this.weight = weight;
        this.asyncTaskManger = asyncTaskManger;
    }

    public String getTaskId() {
        return this.monitor.getTaskId();
    }

    public String getTaskPoolTask() {
        return this.monitor.getTaskPoolTask();
    }

    public void progressAndMessage(double progress, String message) {
        if (progress <= 0.0) {
            return;
        }
        this.lastProgress = this.weight * progress + this.lastProgress;
        this.monitor.progressAndMessage(Math.min(this.lastProgress, 0.95), message);
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }

    public void finish(String result, Object detail) {
        this.monitor.finish(result, detail);
    }

    public void canceling(String result, Object detail) {
        this.monitor.canceling(result, detail);
    }

    public void canceled(String result, Object detail) {
        this.monitor.canceled(result, detail);
    }

    public void error(String cause, Throwable t) {
        this.monitor.error(cause, t);
    }

    public boolean isFinish() {
        return this.monitor.isFinish();
    }
}

