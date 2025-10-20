/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 */
package com.jiuqi.common.plantask.extend.job;

import com.jiuqi.bi.core.jobs.JobContext;

public abstract class Runner {
    protected StringBuffer log = new StringBuffer();

    protected boolean excute(String runnerParameter) {
        return false;
    }

    protected boolean excute(JobContext jobContext) {
        return this.excute(jobContext.getJob().getExtendedConfig());
    }

    protected void appendLog(String logContent) {
        this.log.append(logContent);
    }

    protected String getLog() {
        return this.log.toString();
    }

    protected void cleanLog() {
        this.log = new StringBuffer();
    }
}

