/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 */
package com.jiuqi.gcreport.bde.fetch.impl.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;

@RealTimeJob(group="ASYNCTASK_BDE", groupTitle="BDE\u53d6\u6570")
public class GcFetchAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 8055660102780069992L;

    public void execute(JobContext jobContext) throws JobExecutionException {
        throw new BusinessRuntimeException("\u53d6\u6570\u5373\u65f6\u4efb\u52a1\u5df2\u5e9f\u5f03");
    }
}

