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
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckParam;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.service.ExplainInfoCheckService;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.web.CheckExplainController;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_EXPLAININFOCHECK", groupTitle="\u51fa\u9519\u8bf4\u660e\u5ba1\u6838")
public class ExplainInfoCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ExplainInfoCheckAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        ExplainInfoCheckService service = (ExplainInfoCheckService)BeanUtil.getBean(ExplainInfoCheckService.class);
        CheckExplainController aa = (CheckExplainController)BeanUtil.getBean(CheckExplainController.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String finishInfo = "task_success_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_EXPLAININFOCHECK.getName(), jobContext);
        try {
            ExplainInfoCheckParam explainInfoCheckParam = new ExplainInfoCheckParam();
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                explainInfoCheckParam = (ExplainInfoCheckParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            }
            asyncTaskMonitor.progressAndMessage(0.1, "running");
            if (explainInfoCheckParam == null) {
                throw new Exception("\u53c2\u6570\u683c\u5f0f\u4e0d\u6b63\u786e");
            }
            ExplainInfoCheckReturnInfo result = service.checkExplainInfo(explainInfoCheckParam, (AsyncTaskMonitor)asyncTaskMonitor, jobContext);
            asyncTaskMonitor.progressAndMessage(1.0, "running");
            asyncTaskMonitor.finish(finishInfo, (Object)result);
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_EXPLAININFOCHECK.getName();
    }
}

