/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.io.tz.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.io.tz.service.BatchImportMonitor;

public class JobLoggerMonitor
implements AsyncTaskMonitor {
    private final JobContext jobContext;

    public JobLoggerMonitor(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    public String getTaskId() {
        return null;
    }

    public String getTaskPoolTask() {
        return null;
    }

    public void progressAndMessage(double progress, String message) {
        BatchImportMonitor.loggerMonitor.progressAndMessage(progress, message);
        this.jobContext.getDefaultLogger().info(message);
    }

    public boolean isCancel() {
        return false;
    }

    public void finish(String result, Object detail) {
        BatchImportMonitor.loggerMonitor.finish(result, detail);
        this.jobContext.getDefaultLogger().info(result);
    }

    public void canceling(String result, Object detail) {
    }

    public void canceled(String result, Object detail) {
    }

    public void error(String result, Throwable t) {
        BatchImportMonitor.loggerMonitor.error(result, t);
        this.jobContext.getDefaultLogger().error(result, t);
    }

    public boolean isFinish() {
        return false;
    }
}

