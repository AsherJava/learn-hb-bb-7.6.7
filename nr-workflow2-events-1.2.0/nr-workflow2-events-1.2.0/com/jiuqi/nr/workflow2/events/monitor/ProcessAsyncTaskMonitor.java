/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 */
package com.jiuqi.nr.workflow2.events.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;

public class ProcessAsyncTaskMonitor
implements AsyncTaskMonitor {
    private IProcessAsyncMonitor monitor;
    private boolean finished = false;

    public ProcessAsyncTaskMonitor(IProcessAsyncMonitor monitor) {
        this.monitor = monitor;
    }

    public String getTaskId() {
        return this.monitor.getAsyncTaskId();
    }

    public String getTaskPoolTask() {
        return "";
    }

    public void progressAndMessage(double progress, String message) {
        this.monitor.setJobProgress(progress, message);
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }

    public void finish(String result, Object detail) {
        this.monitor.setJobResult(AsyncJobResult.SUCCESS, result, detail);
        this.finished = true;
    }

    public void canceling(String result, Object detail) {
        this.monitor.setJobResult(AsyncJobResult.CANCELED, result, detail);
    }

    public void canceled(String result, Object detail) {
        this.monitor.setJobResult(AsyncJobResult.CANCELED, "", detail);
        this.finished = true;
    }

    public void error(String result, Throwable t) {
        this.monitor.error(result, t);
        this.monitor.setJobResult(AsyncJobResult.FAILURE, result, (Object)"");
        this.finished = true;
    }

    public boolean isFinish() {
        return this.finished;
    }
}

