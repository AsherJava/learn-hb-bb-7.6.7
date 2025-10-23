/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo
 */
package com.jiuqi.nr.workflow2.events.executor.sub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_TASK_BATCH_NODE_CHECK_DATA_BUILD", groupTitle="\u6279\u91cf\u8282\u70b9\u68c0\u67e5\u7ed3\u679c\u6570\u636e\u6784\u5efa", subject="\u62a5\u8868", tags={"\u77ed\u4efb\u52a1"})
public class BatchNodeCheckDataBuildTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 10L;
    private static final Logger logger = LoggerFactory.getLogger(BatchNodeCheckDataBuildTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHNODECHECK.getName(), jobContext);
        String args = (String)params.get("NR_ARGS");
        if (Objects.nonNull(args)) {
            NodeCheckResultInfo nodeCheckResultInfo;
            ObjectMapper mapper = new ObjectMapper();
            try {
                nodeCheckResultInfo = (NodeCheckResultInfo)mapper.readValue(args, (TypeReference)new TypeReference<NodeCheckResultInfo>(){});
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if (nodeCheckResultInfo.getUnPassUnit() == 0) {
                asyncTaskMonitor.finish("node_check_finish_info", (Object)args);
            } else {
                asyncTaskMonitor.error("node_check_fail_info", null, args);
            }
        }
    }
}

