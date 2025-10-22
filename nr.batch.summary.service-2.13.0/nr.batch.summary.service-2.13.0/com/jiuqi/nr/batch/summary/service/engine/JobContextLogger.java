/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.nr.batch.summary.common.StringLogger;

public class JobContextLogger
extends StringLogger {
    private final JobContext jobContext;

    public JobContextLogger(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    @Override
    public StringLogger addProcess(double inc) {
        this.process += inc;
        if (this.process > 100.0) {
            this.process = 100.0;
        }
        try {
            this.jobContext.updateProgress((int)this.process);
        }
        catch (JobExecutionException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    protected String newActiveMessage(StringLogger.Type type, String message) {
        super.newActiveMessage(type, message);
        switch (type) {
            case INFO: {
                this.jobContext.getDefaultLogger().info(message);
                break;
            }
            case WARN: {
                this.jobContext.getDefaultLogger().warn(message);
                break;
            }
            case ERROR: {
                this.jobContext.getDefaultLogger().error(message);
            }
        }
        return message;
    }

    @Override
    public void logError(String msg, Throwable t) {
        super.newActiveMessage(StringLogger.Type.ERROR, msg);
        this.jobContext.getDefaultLogger().error(msg, t);
    }
}

