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
 *  com.jiuqi.xg.print.PrinterDevice
 *  com.jiuqi.xg.print.util.AsyncWorkContainnerUtil
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
import com.jiuqi.nr.dataentry.bean.BatchPrintInfo;
import com.jiuqi.nr.dataentry.service.IBatchExportService;
import com.jiuqi.xg.print.PrinterDevice;
import com.jiuqi.xg.print.util.AsyncWorkContainnerUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_BATCHPRINT", cancellable=true, rollback=true, groupTitle="\u6279\u91cf\u6253\u5370", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class BatchPrintAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchPrintAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        IBatchExportService batchExportService = (IBatchExportService)BeanUtil.getBean(IBatchExportService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHPRINT.getName(), jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                BatchPrintInfo batchPrintInfo = (BatchPrintInfo)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                PrinterDevice printerDevice = AsyncWorkContainnerUtil.getPrinterDevice((String)batchPrintInfo.getPrinterID());
                batchExportService.batchPrintRealtime((AsyncTaskMonitor)asyncTaskMonitor, batchPrintInfo);
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error(errorInfo, (Throwable)nrCommonException);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_BATCHPRINT.getName();
    }
}

