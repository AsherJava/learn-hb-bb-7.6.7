/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.data.text.param;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.text.spi.IFieldDataMonitor;

public class DefaultFieldDataMonitor
implements IFieldDataMonitor {
    private AsyncTaskMonitor monitor;

    public DefaultFieldDataMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public String getTaskId() {
        if (this.monitor != null) {
            return this.monitor.getTaskId();
        }
        return null;
    }

    @Override
    public void progressAndMessage(double progress, String message) {
        if (this.monitor != null) {
            this.monitor.progressAndMessage(progress, message);
        }
    }

    @Override
    public boolean isCancel() {
        if (this.monitor != null) {
            return this.monitor.isCancel();
        }
        return false;
    }

    @Override
    public void finish(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.finish(result, detail);
        }
    }

    @Override
    public void canceling(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.canceling(result, detail);
        }
    }

    @Override
    public void canceled(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.canceled(result, detail);
        }
    }

    @Override
    public void error(String result, Throwable t) {
        if (this.monitor != null) {
            this.monitor.error(result, t);
        }
    }

    @Override
    public void error(String message, Throwable sender, Object detail) {
        if (this.monitor != null) {
            this.monitor.error(message, sender, detail.toString());
        }
    }
}

