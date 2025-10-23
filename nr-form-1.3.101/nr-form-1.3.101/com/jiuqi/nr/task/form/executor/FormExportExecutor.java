/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.task.form.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.task.form.dto.FormExportDTO;
import com.jiuqi.nr.task.form.formio.IFormExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@RealTimeJob(group="ASYNCTASK_PARAM_EXPORT_EXCEL", groupTitle="\u5bfc\u51faexcel\u8868\u6837", isolate=true, subject="\u62a5\u8868", tags={"\u77ed\u4efb\u52a1"})
public class FormExportExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(FormExportExecutor.class);
    public static final String ASYNC_EXCEL = "ASYNCTASK_EXCELEXPORT_NEW";

    public void execute(JobContext jobContext) {
        IFormExportService formExportService = (IFormExportService)SpringBeanUtils.getBean(IFormExportService.class);
        String logTitle = "excel\u5bfc\u51fa";
        String instanceId = jobContext.getInstanceId();
        String argStr = super.getArgs();
        Object args = null;
        if (StringUtils.hasText(argStr)) {
            args = SimpleParamConverter.SerializationUtils.deserialize((String)argStr);
        }
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(instanceId, ASYNC_EXCEL, jobContext);
        try {
            if (args instanceof FormExportDTO) {
                FormExportDTO paramExportInfo = (FormExportDTO)args;
                formExportService.exportFormAsync(paramExportInfo, (AsyncTaskMonitor)asyncTaskMonitor);
                log.info(logTitle);
            }
        }
        catch (Exception e2) {
            asyncTaskMonitor.error(e2.getMessage(), (Throwable)e2);
            log.error(e2.getMessage(), e2);
        }
    }
}

