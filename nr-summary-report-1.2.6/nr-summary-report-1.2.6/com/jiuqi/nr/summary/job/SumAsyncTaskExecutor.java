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
package com.jiuqi.nr.summary.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.summary.common.bean.FSumParam;
import com.jiuqi.nr.summary.executor.sum.SumExecutor;
import com.jiuqi.nr.summary.executor.sum.SumParam;
import com.jiuqi.nr.summary.utils.SumParamUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCSUMMARY_SUM", groupTitle="\u81ea\u5b9a\u4e49\u6c47\u603b")
public class SumAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(SumAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        SumExecutor sumExecutor = (SumExecutor)BeanUtil.getBean(SumExecutor.class);
        SumParamUtil sumParamUtil = (SumParamUtil)BeanUtil.getBean(SumParamUtil.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, "ASYNCSUMMARY_SUM", jobContext);
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            FSumParam sumParam = (FSumParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            try {
                SumParam sumExeParam = sumParamUtil.buildSumParam(sumParam);
                sumExecutor.executeSum(sumExeParam, sumParam.isAfterCalculate(), (AsyncTaskMonitor)monitor);
                monitor.finish("sum_exe_success", null);
                log.info("\u81ea\u5b9a\u4e49\u6c47\u603b\u6267\u884c\u5b8c\u6bd5");
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                monitor.error("sum_exe_error", (Throwable)e, e.getMessage());
            }
        }
    }
}

