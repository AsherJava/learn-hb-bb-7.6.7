/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.service.IDataentryWorkflowService;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_WORKFLOWEXE", groupTitle="\u4e0a\u62a5", subject="\u62a5\u8868")
public class WorkflowAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(WorkflowAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        IDataentryWorkflowService dataentryWorkflowService = (IDataentryWorkflowService)BeanUtils.getBean(IDataentryWorkflowService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_WORKFLOWEXE.getName(), jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                ExecuteTaskParam executeTaskParam = (ExecuteTaskParam)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                dataentryWorkflowService.dataentryExecuteTask(executeTaskParam, (AsyncTaskMonitor)monitor);
                if (monitor.isCancel()) {
                    monitor.canceled(cancelInfo, (Object)cancelInfo);
                }
            }
        }
        catch (NrCommonException nrCommonException) {
            monitor.error(errorInfo, (Throwable)nrCommonException);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            monitor.error(errorInfo, (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_WORKFLOWEXE.getName();
    }
}

