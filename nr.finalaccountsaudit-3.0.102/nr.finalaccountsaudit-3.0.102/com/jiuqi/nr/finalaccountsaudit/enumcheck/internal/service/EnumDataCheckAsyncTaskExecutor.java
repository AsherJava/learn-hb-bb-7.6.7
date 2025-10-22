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
package com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.service.IEnumDataCheckService;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_ENUMDATACHECK", groupTitle="\u679a\u4e3e\u5b57\u5178\u68c0\u67e5")
public class EnumDataCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EnumDataCheckAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        IEnumDataCheckService enumDataCheckService = (IEnumDataCheckService)BeanUtil.getBean(IEnumDataCheckService.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String finishInfo = "task_success_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_ENUMDATACHECK.getName(), jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                EnumDataCheckInfo enumDataCheckInfo = (EnumDataCheckInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                EnumDataCheckResultInfo enumDataCheck = enumDataCheckService.enumDataCheck(enumDataCheckInfo, (AsyncTaskMonitor)asyncTaskMonitor);
                asyncTaskMonitor.finish(finishInfo, (Object)enumDataCheck);
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
        return AsynctaskPoolType.ASYNCTASK_ENUMDATACHECK.getName();
    }
}

