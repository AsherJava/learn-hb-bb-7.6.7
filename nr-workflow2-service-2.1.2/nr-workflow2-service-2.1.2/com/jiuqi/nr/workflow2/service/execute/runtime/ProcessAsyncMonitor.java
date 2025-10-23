/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.slf4j.Logger;

public class ProcessAsyncMonitor
implements IProcessAsyncMonitor {
    protected static final int PROGRESS_MAX = 100;
    protected static final String SPELL_STR = "\uff0cthrow:";
    private double progress;
    private int weight;
    protected Logger logger;
    protected JobContext jobContext;

    public ProcessAsyncMonitor(JobContext jobContext, Logger logger, int weight) {
        if (weight <= 0 || weight > 100) {
            throw new IllegalArgumentException("The weight must be between 0 and 100");
        }
        this.weight = weight;
        this.logger = logger;
        this.jobContext = jobContext;
    }

    public String getAsyncTaskId() {
        return this.jobContext.getInstanceId();
    }

    public int getWeight() {
        return this.weight;
    }

    public double getProgress() {
        return this.progress * (double)this.weight / 100.0;
    }

    public String getProcessPercent() {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(2);
        return percentInstance.format(this.progress);
    }

    public void setJobProgress(double progress) {
        this.updateProgress(progress);
        try {
            this.jobContext.updateProgress((int)this.progress);
        }
        catch (JobExecutionException e) {
            this.error(e.getMessage(), e);
        }
    }

    public void setJobProgress(double progress, String message) {
        this.updateProgress(progress);
        try {
            this.info(message);
            this.jobContext.updateProgress((int)this.progress, message);
        }
        catch (JobExecutionException e) {
            this.error(e.getMessage(), e);
        }
    }

    public void setJobResult(AsyncJobResult jobResult, String resultMessage) {
        String msg = this.newResultMessage(jobResult, resultMessage);
        this.setJobProgress(100.0, msg);
        this.jobContext.setResult(jobResult.value, msg);
    }

    public void setJobResult(AsyncJobResult jobResult, String resultMessage, Object detail) {
        this.setJobResult(jobResult, resultMessage);
        this.setJobDetail(detail);
    }

    protected void setJobDetail(Object detail) {
        try {
            this.jobContext.setInstanceDetail(this.convertDetailToString(detail));
        }
        catch (JobsException e) {
            this.error(e.getMessage(), e);
        }
    }

    protected String convertDetailToString(Object detail) {
        if (Objects.isNull(detail)) {
            return null;
        }
        return detail instanceof String ? (String)detail : SimpleParamConverter.SerializationUtils.serializeToString((Object)detail);
    }

    protected void updateProgress(double progress) {
        if (progress < 0.0 || progress > 100.0) {
            throw new IllegalArgumentException("Progress must be between 0 and 100.");
        }
        this.progress = Math.max(this.progress, progress);
    }

    protected String newActiveMessage(Type type, String message) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + format.format(new Date()) + " - " + this.getProcessPercent() + " ]" + type.showText + message;
    }

    protected String newResultMessage(AsyncJobResult jobResult, String message) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + format.format(new Date()) + " - " + this.getProcessPercent() + " ]" + jobResult.name + message;
    }

    public void error(String message) {
        this.jobContext.getDefaultLogger().error(this.newActiveMessage(Type.ERROR, message));
    }

    public void error(String message, double progress) {
        String msg = this.newActiveMessage(Type.ERROR, message);
        this.jobContext.getDefaultLogger().error(msg);
        this.setJobProgress(progress, msg);
    }

    public void error(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.ERROR, message + SPELL_STR + e.getMessage());
        this.logger.error(msg, e);
        this.jobContext.getDefaultLogger().error(msg, e);
    }

    public void error(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.ERROR, message + SPELL_STR + e.getMessage());
        this.logger.error(msg, e);
        this.jobContext.getDefaultLogger().error(msg, e);
        this.setJobProgress(progress, msg);
    }

    public void info(String message) {
        this.jobContext.getDefaultLogger().info(this.newActiveMessage(Type.INFO, message));
    }

    public void info(String message, double progress) {
        String msg = this.newActiveMessage(Type.INFO, message);
        this.jobContext.getDefaultLogger().info(msg);
        this.setJobProgress(progress, msg);
    }

    public void info(String message, Throwable e) {
        this.jobContext.getDefaultLogger().info(this.newActiveMessage(Type.INFO, message + SPELL_STR + e.getMessage()), e);
    }

    public boolean isCancel() {
        return this.jobContext.getMonitor().isCanceled();
    }

    public void info(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.INFO, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().info(msg, e);
        this.setJobProgress(progress, msg);
    }

    public void debug(String message) {
        String msg = this.newActiveMessage(Type.DEBUG, message);
        this.jobContext.getDefaultLogger().debug(msg);
    }

    public void debug(String message, double progress) throws JobExecutionException {
        String msg = this.newActiveMessage(Type.DEBUG, message);
        this.jobContext.getDefaultLogger().debug(msg);
        this.setJobProgress(progress, msg);
    }

    public void debug(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.DEBUG, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().debug(msg, e);
    }

    public void debug(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.DEBUG, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().debug(msg, e);
        this.setJobProgress(progress, msg);
    }

    public void trace(String message) {
        this.jobContext.getDefaultLogger().trace(this.newActiveMessage(Type.TRACE, message));
    }

    public void trace(String message, double progress) {
        String msg = this.newActiveMessage(Type.TRACE, message);
        this.jobContext.getDefaultLogger().trace(msg);
        this.setJobProgress(progress, msg);
    }

    public void trace(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.TRACE, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().trace(msg, e);
    }

    public void trace(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.TRACE, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().trace(msg, e);
        this.setJobProgress(progress, msg);
    }

    public void warn(String message) {
        this.jobContext.getDefaultLogger().warn(this.newActiveMessage(Type.WARN, message));
    }

    public void warn(String message, double progress) {
        String msg = this.newActiveMessage(Type.WARN, message);
        this.jobContext.getDefaultLogger().warn(msg);
        this.setJobProgress(progress, msg);
    }

    public void warn(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.WARN, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().warn(msg, e);
    }

    public void warn(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.WARN, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().warn(msg, e);
        this.setJobProgress(progress, msg);
    }

    protected static enum Type {
        INFO("[\u63d0\u793a] "),
        WARN("[\u8b66\u544a] "),
        ERROR("[\u9519\u8bef] "),
        TRACE("[\u8ffd\u8e2a] "),
        DEBUG("[\u8c03\u8bd5] ");

        public final String showText;

        private Type(String title) {
            this.showText = title;
        }
    }
}

