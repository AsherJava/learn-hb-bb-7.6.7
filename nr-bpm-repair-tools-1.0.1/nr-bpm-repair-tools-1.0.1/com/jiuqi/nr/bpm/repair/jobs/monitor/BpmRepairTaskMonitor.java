/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeLogManager
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 */
package com.jiuqi.nr.bpm.repair.jobs.monitor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.bi.core.jobs.realtime.RealTimeLogManager;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.bpm.repair.jobs.monitor.AsyncJobResult;
import com.jiuqi.nr.bpm.repair.jobs.monitor.IBpmRepairTaskMonitor;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BpmRepairTaskMonitor
implements IBpmRepairTaskMonitor {
    protected static final int PROGRESS_MAX = 100;
    protected static final String SPELL_STR = "\uff0cthrow:";
    private double progress;
    private final String asyncTaskId;
    protected final JobContext jobContext;

    public BpmRepairTaskMonitor(String asyncTaskId, JobContext jobContext) {
        this.jobContext = jobContext;
        this.asyncTaskId = asyncTaskId;
    }

    @Override
    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    @Override
    public String getProcessPercent() {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(2);
        return percentInstance.format(this.progress);
    }

    @Override
    public void setJobProgress(double progress) {
        this.updateProgress(progress);
        try {
            this.jobContext.updateProgress((int)this.progress);
        }
        catch (JobExecutionException e) {
            this.error(e.getMessage(), e);
        }
    }

    protected void setJobProgress(double progress, String message) {
        this.updateProgress(progress);
        try {
            this.jobContext.updateProgress((int)this.progress, message);
        }
        catch (JobExecutionException e) {
            this.error(e.getMessage(), e);
        }
    }

    @Override
    public void setJobDetail(Object detail) {
        try {
            this.jobContext.setInstanceDetail(this.convertDetailToString(detail));
        }
        catch (JobsException e) {
            this.error(e.getMessage(), e);
        }
    }

    @Override
    public void setJobResult(AsyncJobResult jobResult, String resultMessage) {
        if (AsyncJobResult.FAILURE == jobResult) {
            this.error(resultMessage);
        } else {
            this.info(resultMessage);
        }
        this.setJobProgress(100.0, resultMessage);
        this.jobContext.setResult(jobResult.value, resultMessage);
    }

    @Override
    public void setJobResultAndDetail(AsyncJobResult jobResult, String resultMessage, Object detail) {
        this.setJobResult(jobResult, resultMessage);
        this.setJobDetail(detail);
    }

    @Override
    public ILogGenerator getLogGenerator() {
        return RealTimeLogManager.getInstance().getLogGenerator();
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
        return "[" + format.format(new Date()) + "]" + type.showText + message;
    }

    @Override
    public void debug(String message) {
        String msg = this.newActiveMessage(Type.DEBUG, message);
        this.jobContext.getDefaultLogger().debug(msg);
    }

    @Override
    public void debug(String message, double progress) throws JobExecutionException {
        String msg = this.newActiveMessage(Type.DEBUG, message);
        this.jobContext.getDefaultLogger().debug(msg);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void debug(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.DEBUG, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().debug(msg, e);
    }

    @Override
    public void debug(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.DEBUG, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().debug(msg, e);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void error(String message) {
        String msg = this.newActiveMessage(Type.ERROR, message);
        this.jobContext.getDefaultLogger().error(msg);
    }

    @Override
    public void error(String message, double progress) {
        String msg = this.newActiveMessage(Type.ERROR, message);
        this.jobContext.getDefaultLogger().error(msg);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void error(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.ERROR, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().error(msg, e);
    }

    @Override
    public void error(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.ERROR, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().error(msg, e);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void info(String message) {
        this.jobContext.getDefaultLogger().info(this.newActiveMessage(Type.INFO, message));
    }

    @Override
    public void info(String message, double progress) {
        String msg = this.newActiveMessage(Type.INFO, message);
        this.jobContext.getDefaultLogger().info(msg);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void info(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.INFO, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().info(msg, e);
    }

    @Override
    public void info(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.INFO, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().info(msg, e);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void trace(String message) {
        this.jobContext.getDefaultLogger().trace(this.newActiveMessage(Type.TRACE, message));
    }

    @Override
    public void trace(String message, double progress) {
        String msg = this.newActiveMessage(Type.TRACE, message);
        this.jobContext.getDefaultLogger().trace(msg);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void trace(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.TRACE, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().trace(msg, e);
    }

    @Override
    public void trace(String message, double progress, Throwable e) {
        String msg = this.newActiveMessage(Type.TRACE, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().trace(msg, e);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void warn(String message) {
        this.jobContext.getDefaultLogger().warn(this.newActiveMessage(Type.WARN, message));
    }

    @Override
    public void warn(String message, double progress) {
        String msg = this.newActiveMessage(Type.WARN, message);
        this.jobContext.getDefaultLogger().warn(msg);
        this.setJobProgress(progress, msg);
    }

    @Override
    public void warn(String message, Throwable e) {
        String msg = this.newActiveMessage(Type.WARN, message + SPELL_STR + e.getMessage());
        this.jobContext.getDefaultLogger().warn(msg, e);
    }

    @Override
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

