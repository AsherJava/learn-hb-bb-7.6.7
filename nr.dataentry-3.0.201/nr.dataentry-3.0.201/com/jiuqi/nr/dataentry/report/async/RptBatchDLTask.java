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
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.dataentry.report.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.dataentry.report.helper.ReportHelper;
import com.jiuqi.nr.dataentry.report.rest.vo.DownloadReportParamsObj;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="RPT_BATCH_DL", groupTitle="\u62a5\u544a\u6279\u91cf\u4e0b\u8f7d")
public class RptBatchDLTask
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = -7143222908702494872L;
    private static final Logger logger = LoggerFactory.getLogger(RptBatchDLTask.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, "RPT_BATCH_DL", jobContext);
        try {
            Object args;
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                if (!(args instanceof DownloadReportParamsObj)) {
                    throw new IllegalArgumentException(" args not instanceof DownloadReportParamsObj");
                }
            } else {
                throw new IllegalArgumentException(" REALTIME_TASK_PARAMS_KEY_ARGS is null");
            }
            ReportHelper reportHelper = (ReportHelper)BeanUtil.getBean(ReportHelper.class);
            DownloadReportParamsObj downloadReportParamsObj = (DownloadReportParamsObj)args;
            reportHelper.batchExport(downloadReportParamsObj, (AsyncTaskMonitor)monitor);
        }
        catch (Exception e) {
            monitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

