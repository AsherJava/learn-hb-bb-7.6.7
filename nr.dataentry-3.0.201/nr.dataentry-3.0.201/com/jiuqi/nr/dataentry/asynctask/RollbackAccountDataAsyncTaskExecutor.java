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
import com.jiuqi.nr.dataentry.asynctask.NodeCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.internal.service.BatchRollbackAccountDataService;
import com.jiuqi.nr.dataentry.paramInfo.AccountRollBackParam;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_ROLLBACK_ACCOUNT_DATA", groupTitle="\u53f0\u8d26\u6570\u636e\u56de\u6eda", subject="\u62a5\u8868", tags={"\u77ed\u4efb\u52a1"})
public class RollbackAccountDataAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(NodeCheckAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        BatchRollbackAccountDataService batchRollbackAccountDataService = (BatchRollbackAccountDataService)BeanUtil.getBean(BatchRollbackAccountDataService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_ROLLBACK_ACCOUNT_DATA.getName(), jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                AccountRollBackParam accountRollBackParam = (AccountRollBackParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                batchRollbackAccountDataService.asyncRollbackAccountData(accountRollBackParam, (AsyncTaskMonitor)monitor);
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
        return AsynctaskPoolType.ASYNCTASK_ROLLBACK_ACCOUNT_DATA.getName();
    }
}

