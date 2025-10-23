/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 */
package com.jiuqi.nr.workflow2.converter.dataentry.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;

public class AsyncTaskMonitorConverter
implements IProcessAsyncMonitor {
    private final AsyncTaskMonitor asyncTaskMonitor;

    public AsyncTaskMonitorConverter(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskMonitor.getTaskId();
    }

    public int getWeight() {
        return 100;
    }

    public double getProgress() {
        return this.asyncTaskMonitor.getLastProgress();
    }

    public String getProcessPercent() {
        return "";
    }

    public void setJobProgress(double progress) {
        this.asyncTaskMonitor.progressAndMessage(progress, "");
    }

    public void setJobProgress(double progress, String message) {
        this.asyncTaskMonitor.progressAndMessage(progress, message);
    }

    public void setJobResult(AsyncJobResult jobResult, String resultMessage) {
        if (jobResult.equals((Object)AsyncJobResult.SUCCESS)) {
            this.asyncTaskMonitor.finish(resultMessage, null);
        } else if (jobResult.equals((Object)AsyncJobResult.FAILURE)) {
            this.asyncTaskMonitor.error(resultMessage, null);
        }
        this.asyncTaskMonitor.finish(resultMessage, null);
    }

    public void setJobResult(AsyncJobResult jobResult, String resultMessage, Object detail) {
        if (jobResult.equals((Object)AsyncJobResult.SUCCESS)) {
            this.asyncTaskMonitor.finish(resultMessage, detail);
        } else if (jobResult.equals((Object)AsyncJobResult.FAILURE)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String detailStr = mapper.writeValueAsString(detail);
                this.asyncTaskMonitor.error(resultMessage, null, detailStr);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        this.asyncTaskMonitor.finish(resultMessage, detail);
    }

    public boolean isCancel() {
        return this.asyncTaskMonitor.isCancel();
    }

    public void error(String message, double progress, Throwable e) {
        this.asyncTaskMonitor.error(message, e);
    }

    public void error(String message) {
    }

    public void error(String message, double progress) {
        this.asyncTaskMonitor.progressAndMessage(progress, message);
    }

    public void error(String message, Throwable e) {
        this.asyncTaskMonitor.error(message, e);
    }

    public void info(String message, double progress, Throwable e) {
    }

    public void info(String message) {
    }

    public void info(String message, double progress) {
    }

    public void info(String message, Throwable e) {
    }
}

