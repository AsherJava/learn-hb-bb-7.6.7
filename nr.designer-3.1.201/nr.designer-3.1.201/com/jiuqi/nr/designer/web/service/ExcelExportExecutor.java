/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.designer.web.rest.vo.ExcelExportVO;
import com.jiuqi.nr.designer.web.service.ExcelExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_EXCELEXPORT", groupTitle="\u8868\u6837\u5bfc\u51fa")
public class ExcelExportExecutor
extends NpRealTimeTaskExecutor {
    public static final String ASYNC_EXCEL = "ASYNCTASK_EXCELEXPORT";
    private static final Logger log = LoggerFactory.getLogger(ExcelExportExecutor.class);

    public void execute(JobContext jobContext) throws JobExecutionException {
        log.info("\u8868\u6837\u5bfc\u51fa\u5f00\u59cb");
        ExcelExportService excelExportService = (ExcelExportService)SpringBeanUtils.getBean(ExcelExportService.class);
        String params = (String)jobContext.getRealTimeJob().getParams().get("NR_ARGS");
        ExcelExportVO paramExportInfo = (ExcelExportVO)SimpleParamConverter.SerializationUtils.deserialize((String)params);
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), ASYNC_EXCEL, jobContext);
        try {
            excelExportService.exportParamAsync(paramExportInfo, (AsyncTaskMonitor)monitor);
        }
        catch (Exception e2) {
            monitor.error(e2.getMessage(), (Throwable)e2);
            log.error("\u8868\u6837\u5bfc\u51fa\u5f02\u5e38", e2);
        }
        log.info("\u8868\u6837\u5bfc\u51fa\u7ed3\u675f");
    }
}

