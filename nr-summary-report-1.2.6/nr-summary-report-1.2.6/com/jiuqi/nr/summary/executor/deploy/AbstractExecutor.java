/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.executor.deploy.IDeployExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

abstract class AbstractExecutor
implements IDeployExecutor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final DeployContext context;
    private final AsyncTaskMonitor monitor;
    private double progress;

    AbstractExecutor(DeployContext context, AsyncTaskMonitor monitor) {
        this.context = context;
        this.monitor = monitor;
    }

    @Override
    public abstract boolean execute();

    protected void logInfo(String message) {
        this.monitor.progressAndMessage(this.progress, message);
        this.logger.info(message);
    }

    protected void logInfo(String message, Object ... param) {
        String formatMessage = String.format(message, param);
        this.logInfo(formatMessage);
    }

    protected void step(double step, String message) {
        this.progress += step;
        this.monitor.progressAndMessage(this.progress, message);
        this.logger.info(message);
    }

    protected void step(double step, String message, Object ... params) {
        String formatMessage = String.format(message, params);
        this.step(step, formatMessage);
    }

    protected void error(String errorMsg) {
        this.monitor.error("ERROR", null, errorMsg);
        this.logger.error(errorMsg);
    }

    protected void error(String errorMsg, Object ... params) {
        String formatMsg = String.format(errorMsg, params);
        this.error(formatMsg);
    }

    protected void error(Throwable t) {
        this.monitor.error("ERROR", t, t.getMessage());
        this.logger.error(t.getMessage(), t);
    }

    protected void logProgressAndMessage(double progress, String message) {
        this.monitor.progressAndMessage(progress, message);
        this.logger.info(message);
    }

    protected void logProgressAndMessage(double progress, String formatMessage, Object ... params) {
        String message = String.format(formatMessage, params);
        this.logProgressAndMessage(progress, message);
    }

    protected void logProgressAndMessage(double progress, Exception e, String message) {
        this.logProgressAndMessage(progress, StringUtils.hasLength(message) ? message : e.getMessage());
        this.logger.error(e.getMessage(), e);
    }

    protected void logFinished(String message) {
        this.progress = 1.0;
        this.logProgressAndMessage(this.progress, message);
        this.monitor.finish(message, (Object)this.context.getSolutionModel().getTitle());
    }

    protected boolean isCancelling() {
        return this.monitor.isCancel();
    }

    protected void logError(Throwable t) {
        this.monitor.error(t.getMessage(), t);
        this.logger.error(t.getMessage(), t);
    }
}

