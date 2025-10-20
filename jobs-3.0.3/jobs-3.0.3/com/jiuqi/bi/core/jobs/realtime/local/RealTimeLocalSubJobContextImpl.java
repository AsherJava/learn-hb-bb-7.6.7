/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.local;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.core.SubJobProgressMonitor;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.local.RealTimeLocalJobContextImpl;

public class RealTimeLocalSubJobContextImpl
extends RealTimeLocalJobContextImpl {
    private String parentInstanceId = this.getParameterValue("_SYS_PARENT_INSTANCE_ID");
    private SubJobProgressMonitor monitor = new SubJobProgressMonitor(this.getInstanceId(), this.parentInstanceId);

    public RealTimeLocalSubJobContextImpl(AbstractRealTimeJob realTimeJob) {
        super(realTimeJob);
    }

    @Override
    public SubJobProgressMonitor getMonitor() {
        return this.monitor;
    }

    @Override
    public String executeRealTimeSubJob(AbstractRealTimeJob subJob) throws JobExecutionException {
        throw new JobExecutionException("\u5373\u65f6\u4efb\u52a1\u7684\u5b50\u4efb\u52a1\u4e0d\u652f\u6301\u518d\u521b\u5efa\u5b50\u4efb\u52a1");
    }
}

