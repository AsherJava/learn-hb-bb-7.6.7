/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 */
package com.jiuqi.gcreport.nr.impl.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.nr.impl.service.GcFormulaCheckDesGatherService;
import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import java.util.Map;
import java.util.Objects;

@RealTimeJob(group="ASYNCTASK_BATCHCHECKDESGATHER", groupTitle="\u51fa\u9519\u8bf4\u660e\u9009\u62e9\u6c47\u603b")
public class GcBatchFormulaCheckDesGatherTaskExcutor
extends NpRealTimeTaskExecutor {
    public void executeWithNpContext(JobContext jobContext) {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, GcAsyncTaskPoolType.ASYNCTASK_BATCHCHECKDESGATHER.getName(), jobContext);
        String resultStr = GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.success");
        if (Objects.isNull(params)) {
            monitor.finish(resultStr, (Object)GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.paramsEmpty"));
            return;
        }
        String args = (String)params.get("NR_ARGS");
        if (Objects.isNull(args)) {
            monitor.finish(resultStr, (Object)GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.contextEmpty"));
            return;
        }
        GcFormulaCheckDesGatherParam gcFormulaCheckDesGatherParam = (GcFormulaCheckDesGatherParam)SimpleParamConverter.SerializationUtils.deserialize((String)args);
        GcFormulaCheckDesGatherService gcFormulaCheckDesGatherService = (GcFormulaCheckDesGatherService)SpringContextUtils.getBean(GcFormulaCheckDesGatherService.class);
        gcFormulaCheckDesGatherService.formulaCheckDesGather(gcFormulaCheckDesGatherParam, (AsyncTaskMonitor)monitor);
        monitor.finish(resultStr, (Object)resultStr);
    }

    public String getTaskPoolType() {
        return GcAsyncTaskPoolType.ASYNCTASK_BATCHCHECKDESGATHER.getName();
    }
}

