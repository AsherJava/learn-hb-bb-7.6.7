/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.data.gather.refactor.monitor.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.MonitorEventParam;

public class DefaultMonitor
implements IGatherServiceMonitor {
    private AsyncTaskMonitor monitor;

    public DefaultMonitor() {
    }

    public DefaultMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void executeBefore(MonitorEventParam param) {
    }

    @Override
    public void executeAfter(MonitorEventParam param) {
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
            this.monitor.progressAndMessage(progress, message);
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
            this.monitor.finish(result, detail);
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

