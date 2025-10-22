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
package com.jiuqi.nr.finalaccountsaudit.multcheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.controller.IMultCheckController;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_MULTCHECK", groupTitle="\u7efc\u5408\u5ba1\u6838")
public class MultCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MultCheckAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        IMultCheckController iMultCheckController = (IMultCheckController)BeanUtil.getBean(IMultCheckController.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String finishInfo = "task_success_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_MULTCHECK.getName(), jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                OneKeyCheckInfo oneKeyCheckInfo = (OneKeyCheckInfo)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                boolean res = iMultCheckController.oneKeyCheck(oneKeyCheckInfo, (AsyncTaskMonitor)asyncTaskMonitor, jobContext);
                asyncTaskMonitor.finish(finishInfo, (Object)res);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
                }
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_MULTCHECK.getName();
    }
}

