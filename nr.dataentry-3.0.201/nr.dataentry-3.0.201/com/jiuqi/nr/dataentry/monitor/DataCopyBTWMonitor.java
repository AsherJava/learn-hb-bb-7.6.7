/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datacopy.param.monitor.IDataCopyMonitor
 */
package com.jiuqi.nr.dataentry.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.datacopy.param.monitor.IDataCopyMonitor;

public class DataCopyBTWMonitor
implements IDataCopyMonitor {
    private AsyncTaskMonitor monitor;
    private double coefficient;
    private double progress;

    public DataCopyBTWMonitor(AsyncTaskMonitor asyncTaskMonitor, double coefficient, double progress) {
        this.monitor = asyncTaskMonitor;
        this.coefficient = coefficient;
        this.progress = progress;
    }

    public String getTaskId() {
        if (this.monitor != null) {
            return this.monitor.getTaskId();
        }
        return null;
    }

    public String getTaskPoolTask() {
        if (this.monitor != null) {
            return this.monitor.getTaskPoolTask();
        }
        return null;
    }

    public void progressAndMessage(double progress, String message) {
        if (this.monitor != null) {
            this.monitor.progressAndMessage(this.progress + progress * this.coefficient, message);
        }
    }

    public boolean isCancel() {
        if (this.monitor != null) {
            return this.monitor.isCancel();
        }
        return false;
    }

    public void finish(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.progressAndMessage(this.progress + this.coefficient, result);
        }
    }

    public void canceling(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.canceling(result, detail);
        }
    }

    public void canceled(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.canceled(result, detail);
        }
    }

    public void error(String result, Throwable t) {
        if (this.monitor != null) {
            this.monitor.error(result, t);
        }
    }

    public void error(String result, Throwable t, String detail) {
        if (this.monitor != null) {
            this.monitor.error(result, t, detail);
        }
    }

    public boolean isFinish() {
        if (this.monitor != null) {
            return this.monitor.isFinish();
        }
        return false;
    }
}

