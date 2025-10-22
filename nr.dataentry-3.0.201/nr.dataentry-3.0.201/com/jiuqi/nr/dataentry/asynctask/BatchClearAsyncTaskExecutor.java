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
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.dataentry.paramInfo.BatchClearInfo;
import com.jiuqi.nr.dataentry.service.IBatchClearService;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_BATCHCLEAR", groupTitle="\u6279\u91cf\u6e05\u9664", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class BatchClearAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchClearAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHCLEAR.getName(), jobContext);
        IBatchClearService batchClearService = (IBatchClearService)BeanUtil.getBean(IBatchClearService.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                BatchClearInfo batchClearInfo = (BatchClearInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                batchClearService.batchClearForm(batchClearInfo, (AsyncTaskMonitor)asyncTaskMonitor);
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error(errorInfo, (Throwable)nrCommonException);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_BATCHCLEAR.getName();
    }
}

