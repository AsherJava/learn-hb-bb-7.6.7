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
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityDataInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.controller.IIntegrityCheckController;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_INTEGRITYCHECK", groupTitle="\u8868\u5b8c\u6574\u6027\u68c0\u67e5")
public class IntegrityCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityCheckAsyncTaskExecutor.class);
    private static final String REALTIME_TASK_PARAMSKEY_ARGS = "NR_ARGS";

    public void execute(JobContext jobContext) {
        IIntegrityCheckController iIntegrityCheckController = (IIntegrityCheckController)BeanUtil.getBean(IIntegrityCheckController.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String finishInfo = "task_success_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_INTEGRITYCHECK.getName(), jobContext);
        try {
            if (params != null && params.containsKey(REALTIME_TASK_PARAMSKEY_ARGS)) {
                IntegrityCheckInfo integrityCheckInfo = (IntegrityCheckInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get(REALTIME_TASK_PARAMSKEY_ARGS))));
                IntegrityDataInfo res = iIntegrityCheckController.integrityCheck(integrityCheckInfo, (AsyncTaskMonitor)asyncTaskMonitor);
                asyncTaskMonitor.finish(finishInfo, (Object)res);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
                }
            } else {
                String missingParamError = "\u7f3a\u5c11\u5fc5\u8981\u7684\u4efb\u52a1\u53c2\u6570-REALTIME_TASK_PARAMSKEY_ARGS";
                logger.error(missingParamError);
                asyncTaskMonitor.error(errorInfo, (Throwable)new Exception(missingParamError));
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u51fa\u9519", e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_INTEGRITYCHECK.getName();
    }
}

