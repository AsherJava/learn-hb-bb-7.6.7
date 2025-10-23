/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.nrdx.adapter.listener.monitor;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;

public class NrdxImportListenerMonitor
implements AsyncTaskMonitor {
    private final String MONITOR_NAME = "NRDXIMPORTLISTENERMONITOR";
    private final String taskId;
    private final IProgressMonitor progressMonitor;
    private double currentProgress = 0.0;
    private boolean cancled = false;
    private boolean finaished = false;
    private boolean finishMonitor = false;

    public NrdxImportListenerMonitor(IProgressMonitor progressMonitor, String taskId) {
        this.progressMonitor = progressMonitor;
        this.taskId = taskId;
        this.progressMonitor.startTask("NRDXIMPORTLISTENERMONITOR", 100);
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskPoolTask() {
        return "NRDXIMPORTLISTENERMONITOR";
    }

    public void progressAndMessage(double progress, String message) {
        double steps = progress - this.currentProgress;
        this.currentProgress = progress;
        if (steps > 0.0) {
            int i = 0;
            while ((double)i < steps * 100.0) {
                this.progressMonitor.stepIn();
                ++i;
            }
        }
    }

    public boolean isCancel() {
        return this.cancled;
    }

    public void finish(String result, Object detail) {
        this.finaished = true;
        this.over();
    }

    public void canceling(String result, Object detail) {
        this.cancled = true;
        this.over();
    }

    public void canceled(String result, Object detail) {
        this.cancled = true;
        this.over();
    }

    public void error(String result, Throwable t) {
        this.over();
    }

    public boolean isFinish() {
        return this.finaished;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void over() {
        double steps = 1.0 - this.currentProgress;
        this.currentProgress = 1.0;
        try {
            if (steps > 0.0) {
                int i = 0;
                while ((double)i < steps * 100.0) {
                    this.progressMonitor.stepIn();
                    ++i;
                }
            }
        }
        finally {
            if (!this.finishMonitor) {
                this.finishMonitor = true;
                this.progressMonitor.finishTask("NRDXIMPORTLISTENERMONITOR");
            }
        }
    }
}

