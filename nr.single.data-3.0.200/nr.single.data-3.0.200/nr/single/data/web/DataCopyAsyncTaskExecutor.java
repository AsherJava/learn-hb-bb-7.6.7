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
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package nr.single.data.web;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import java.util.Map;
import nr.single.data.bean.TaskCopyParam;
import nr.single.data.datacopy.ITaskDataCopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_TASKDATACOPY", groupTitle="\u4efb\u52a1\u6570\u636e\u590d\u5236")
public class DataCopyAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DataCopyAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        ITaskDataCopyService dataCopyService = (ITaskDataCopyService)BeanUtil.getBean(ITaskDataCopyService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName(), jobContext);
        try {
            if (params != null && params.get("NR_ARGS") != null) {
                Map argsMap = (Map)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                TaskCopyParam copyParam = (TaskCopyParam)argsMap.get("copyParam");
                String string = dataCopyService.copyDataByParam(copyParam, (AsyncTaskMonitor)monitor);
            }
        }
        catch (Exception e) {
            monitor.error("\u590d\u5236\u6570\u636e\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
            logger.error("\u590d\u5236\u6570\u636e\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_TASKDATACOPY.getName();
    }
}

