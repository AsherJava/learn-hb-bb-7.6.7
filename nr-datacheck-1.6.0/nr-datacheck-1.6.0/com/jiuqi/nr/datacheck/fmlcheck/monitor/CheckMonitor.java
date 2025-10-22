/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 */
package com.jiuqi.nr.datacheck.fmlcheck.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;

public class CheckMonitor
implements IFmlMonitor {
    private final AsyncTaskMonitor monitor;

    public CheckMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    public String getTaskId() {
        return "";
    }

    public void progressAndMessage(double currProgress, String message) {
    }

    public void error(String message, Throwable sender) {
    }

    public void error(String message, Throwable sender, Object detail) {
    }

    public void finish(String result, Object detail) {
    }

    public void cancel(String message, Object detail) {
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }
}

