/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.blob.util.BeanUtil
 */
package com.jiuqi.nr.summary.executor.deploy.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.summary.executor.deploy.IDeployExecutor;
import com.jiuqi.nr.summary.executor.deploy.SummaryDeployExecutorFactory;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCSUMMARY_DEPLOY", groupTitle="\u81ea\u5b9a\u4e49\u6c47\u603b\u65b9\u6848\u53d1\u5e03")
public class SummaryDeployTaskExecutor
extends NpRealTimeTaskExecutor {
    public void executeWithNpContext(JobContext jobContext) {
        SummaryDeployExecutorFactory deployExecutorFactory = (SummaryDeployExecutorFactory)BeanUtil.getBean(SummaryDeployExecutorFactory.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, "ASYNCSUMMARY_DEPLOY", jobContext);
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            String solutionKey = (String)params.get("NR_ARGS");
            IDeployExecutor deployExecutor = deployExecutorFactory.getDeployExecutor(solutionKey, (AsyncTaskMonitor)monitor);
            deployExecutor.execute();
        }
    }
}

