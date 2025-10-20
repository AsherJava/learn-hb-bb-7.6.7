/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 *  org.quartz.JobListener
 */
package com.jiuqi.bi.core.jobs.core.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalJobListener
implements JobListener {
    private static final Logger logger = LoggerFactory.getLogger(GlobalJobListener.class);
    private String schedulerName;

    public GlobalJobListener(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        logger.trace(this.schedulerName + ">>>\u4efb\u52a1\u5c06\u8981\u88ab\u6267\u884c\uff1a" + context.getJobDetail().getKey().getName());
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        logger.trace("\u4efb\u52a1\u5373\u5c06\u88ab\u6267\u884c\uff0c\u4f46\u88abTriggerListerner\u5426\u51b3\uff1a" + context.getJobDetail().getKey().getName());
    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        logger.trace("\u4efb\u52a1\u5df2\u6267\u884c\u5b8c\u6210\uff1a" + context.getJobDetail().getKey().getName());
    }
}

