/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobExecutionContext
 */
package com.jiuqi.bi.core.jobs.realtime.quartz;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.quartz.RealTimeQuartzJobContextImpl;
import org.quartz.JobExecutionContext;

public class RealTimeQuartzSubJobContextImpl
extends RealTimeQuartzJobContextImpl {
    public RealTimeQuartzSubJobContextImpl(AbstractRealTimeJob realTimeJob, JobExecutionContext quartzContext) {
        super(realTimeJob, quartzContext);
    }

    @Override
    public String executeRealTimeSubJob(AbstractRealTimeJob subJob) throws JobExecutionException {
        throw new JobExecutionException("\u5373\u65f6\u4efb\u52a1\u7684\u5b50\u4efb\u52a1\u4e0d\u652f\u6301\u518d\u521b\u5efa\u5b50\u4efb\u52a1");
    }
}

