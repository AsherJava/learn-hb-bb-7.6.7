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
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.customExcelBatchImport.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelOptionInfo;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelBatchImportService;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_CUSTOMIMPORT", groupTitle="\u81ea\u5b9a\u4e49\u5bfc\u5165", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class CustomExcelBatchImportExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CustomExcelBatchImportExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        ICustomExcelBatchImportService customExcelBatchImportService = (ICustomExcelBatchImportService)BeanUtil.getBean(ICustomExcelBatchImportService.class);
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String taskId = jobContext.getInstanceId();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHCOPY.getName(), jobContext);
        try {
            String args = this.getArgs();
            if (Objects.nonNull(args)) {
                CustomExcelOptionInfo customExcelOptionInfo = (CustomExcelOptionInfo)SimpleParamConverter.SerializationUtils.deserialize((String)args);
                customExcelBatchImportService.beforeImport(customExcelOptionInfo, (AsyncTaskMonitor)asyncTaskMonitor);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled("task_cancel_info", (Object)"task_cancel_info");
                }
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error("task_error_info", (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }
}

