/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.converter.schedule;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor.IProcessStartupAsyncMonitor;
import com.jiuqi.util.StringUtils;

public class StartupAsyncTaskMonitor
implements AsyncTaskMonitor {
    protected IProcessStartupAsyncMonitor monitor;
    protected String taskPoolType;
    private boolean finished = false;

    public StartupAsyncTaskMonitor(IProcessStartupAsyncMonitor monitor, String taskPoolType) {
        this.monitor = monitor;
        this.taskPoolType = taskPoolType;
    }

    public String getTaskId() {
        return this.monitor.getAsyncTaskId();
    }

    public String getTaskPoolTask() {
        return this.taskPoolType;
    }

    public void progressAndMessage(double progress, String message) {
        if (StringUtils.isNotEmpty((String)message)) {
            this.monitor.setJobProgress(progress, message);
        } else {
            this.monitor.setJobProgress(progress);
        }
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }

    public void finish(String result, Object detail) {
        this.monitor.setExecuteResult(AsyncJobResult.SUCCESS, result, detail);
        this.finished = true;
    }

    public void canceling(String result, Object detail) {
        this.finished = true;
        this.monitor.setExecuteResult(AsyncJobResult.FAILURE, result);
    }

    public void canceled(String result, Object detail) {
        this.finished = true;
        this.monitor.setExecuteResult(AsyncJobResult.FAILURE, result);
    }

    public void error(String result, Throwable t) {
        this.monitor.error(result, t);
        this.monitor.setExecuteResult(AsyncJobResult.EXCEPTION, result);
        this.finished = true;
    }

    public boolean isFinish() {
        return this.finished;
    }
}

