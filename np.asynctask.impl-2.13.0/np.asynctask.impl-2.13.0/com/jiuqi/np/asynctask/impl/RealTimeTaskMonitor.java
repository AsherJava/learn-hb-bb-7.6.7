/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyRealTimeJobContext
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.np.asynctask.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyRealTimeJobContext;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealTimeTaskMonitor
implements AsyncTaskMonitor {
    private final String taskId;
    private final String taskPoolType;
    private boolean finish = false;
    private double lastProgress = 0.0;
    private final JobContext jobContext;
    private final Logger logger;

    public RealTimeTaskMonitor(String taskId, String taskPoolType, JobContext jobContext) {
        this.taskId = taskId;
        this.taskPoolType = taskPoolType;
        this.jobContext = jobContext;
        this.logger = LoggerFactory.getLogger("NP_REALTIMETASK_MONITOR_FOR_" + taskPoolType);
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskPoolTask() {
        return this.taskPoolType;
    }

    public void progressAndMessage(double progress, String message) {
        this.lastProgress = progress;
        try {
            progress = Math.min(progress, 0.99);
            this.jobContext.updateProgress((int)(progress * 100.0), message);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public boolean isCancel() {
        return this.jobContext.getMonitor().isCanceled();
    }

    public void finish(String result, Object detail) {
        this.finished(result, detail);
    }

    public void finished(String result, Object detail) {
        this.finish = true;
        this.updateResultAndDetail(this.taskId, result, this.convertDetailToResultMsg(detail));
    }

    public void canceling(String result, Object detail) {
        try {
            if (this.jobContext instanceof ImmediatelyRealTimeJobContext) {
                ImmediatelyJobRunner.getInstance().cancel(this.taskId);
            } else {
                RealTimeJobManager.getInstance().cancel(this.taskId);
            }
            this.jobContext.setInstanceDetail(this.convertDetailToResultMsg(detail));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void canceled(String result, Object detail) {
        this.finish = true;
        try {
            this.jobContext.updateProgress((int)(this.lastProgress * 100.0), result);
            this.jobContext.setResult(2, result);
            this.jobContext.setInstanceDetail(this.convertDetailToResultMsg(detail));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void error(String cause, Throwable t) {
        this.error(cause, t, "");
    }

    public void error(String result, Throwable t, String detail) {
        this.finish = true;
        this.updateErrorInfo(result, this.taskId, detail);
        this.logger.error("\u3010\u5f02\u6b65\u4efb\u52a1\u5f02\u5e38\u3011: {}_{}_{}", this.taskPoolType, this.taskId, result, t);
    }

    public boolean isFinish() {
        return this.finish;
    }

    public double getLastProgress() {
        return this.lastProgress;
    }

    public ILogger getBILogger() {
        return this.jobContext.getDefaultLogger();
    }

    private void updateResultAndDetail(String taskId, String result, String detail) {
        try {
            int jobResult = 100;
            if (this.jobContext instanceof AbstractBaseJobContext) {
                jobResult = ((AbstractBaseJobContext)this.jobContext).getResult();
                jobResult = jobResult == 0 ? 100 : jobResult;
            }
            this.jobContext.updateProgress(99, result);
            this.jobContext.setResult(jobResult, result);
            this.jobContext.setInstanceDetail(detail);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private void updateErrorInfo(String result, String taskId, String detail) {
        try {
            this.jobContext.setResult(4, result);
            this.jobContext.updateProgress(99, result);
            this.jobContext.setInstanceDetail(detail);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private String convertDetailToResultMsg(Object detail) {
        if (Objects.isNull(detail)) {
            return null;
        }
        if (detail instanceof String) {
            return (String)detail;
        }
        return "DETAILSTRING:" + SimpleParamConverter.SerializationUtils.serializeToString(detail);
    }
}

