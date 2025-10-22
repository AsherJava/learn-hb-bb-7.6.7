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
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.gather.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.data.gather.bean.NodeGatherParam;
import com.jiuqi.nr.data.gather.service.INodeGatherService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="NODE_GATHER_TASK", groupTitle="\u6570\u636e\u670d\u52a1-\u8282\u70b9\u6c47\u603b", isolate=true, subject="\u62a5\u8868")
public class NodeGatherAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private Logger logger = LoggerFactory.getLogger(NodeGatherAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        INodeGatherService nodeGatherService = (INodeGatherService)BeanUtil.getBean(INodeGatherService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "NODE_GATHER_TASK", jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                NodeGatherParam nodeGatherParam = (NodeGatherParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                nodeGatherService.asyncBatchDataGather(nodeGatherParam, (AsyncTaskMonitor)asyncTaskMonitor);
            }
        }
        catch (Exception nrCommonException) {
            asyncTaskMonitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)nrCommonException);
            this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
    }
}

