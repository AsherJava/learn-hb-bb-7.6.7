/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.analysisreport.async.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.analysisreport.service.INrArBatchExportServie;
import com.jiuqi.nr.analysisreport.vo.ReportExportVO;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="nrArBatchExport", groupTitle="\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa", defaultMaxConcurrency=1)
public class NrArBatchExportAsyncTaskExecutor
extends AbstractRealTimeJob {
    private static final Logger logger = LoggerFactory.getLogger(NrArBatchExportAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHCHECK.getName(), jobContext);
        INrArBatchExportServie nrArBatchExportServie = (INrArBatchExportServie)BeanUtil.getBean(INrArBatchExportServie.class);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                ReportExportVO reportExportVO = (ReportExportVO)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                nrArBatchExportServie.batchExport(reportExportVO, (AsyncTaskMonitor)asyncTaskMonitor);
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error("\u6279\u91cf\u5bfc\u51fa\u5206\u6790\u62a5\u544a\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
            logger.info("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

