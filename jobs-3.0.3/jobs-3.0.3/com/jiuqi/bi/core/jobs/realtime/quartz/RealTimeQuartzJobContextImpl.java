/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobExecutionContext
 */
package com.jiuqi.bi.core.jobs.realtime.quartz;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.AbstractRealTimeJobContext;
import org.quartz.JobExecutionContext;

public class RealTimeQuartzJobContextImpl
extends AbstractRealTimeJobContext {
    private final JobExecutionContext quartzContext;

    public RealTimeQuartzJobContextImpl(AbstractRealTimeJob realTimeJob, JobExecutionContext quartzContext) {
        super(realTimeJob);
        this.quartzContext = quartzContext;
    }

    @Override
    public String getParameterValue(String name) {
        Object value;
        if (this.quartzContext != null && (value = this.quartzContext.getMergedJobDataMap().get((Object)name)) != null) {
            return value.toString();
        }
        return super.getParameterValue(name);
    }

    @Override
    public String getFireInstanceId() {
        return this.quartzContext.getFireInstanceId();
    }
}

