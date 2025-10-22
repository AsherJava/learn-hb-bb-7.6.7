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
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  nr.single.data.bean.CheckParam
 *  nr.single.data.treecheck.service.IEntityTreeCheckService
 */
package com.jiuqi.nr.finalaccountsaudit.entitytreecheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nr.single.data.bean.CheckParam;
import nr.single.data.treecheck.service.IEntityTreeCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_ENTITYTREECHECK", groupTitle="\u6811\u5f62\u7ed3\u6784\u68c0\u67e5")
public class EntityTreeCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EntityTreeCheckAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        IEntityTreeCheckService treeCheckSevice = (IEntityTreeCheckService)BeanUtil.getBean(IEntityTreeCheckService.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String finishInfo = "task_success_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_ENTITYTREECHECK.getName(), jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                CheckParam checkParam = (CheckParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                List result = treeCheckSevice.CheckTreeNodeByTask(checkParam.getTaskKey(), checkParam.getPeriodCode(), true, (AsyncTaskMonitor)asyncTaskMonitor);
                if (asyncTaskMonitor != null) {
                    asyncTaskMonitor.finish(finishInfo, (Object)result.size());
                }
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
                }
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error(errorInfo, (Throwable)nrCommonException);
            logger.error(nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_ENTITYTREECHECK.getName();
    }
}

