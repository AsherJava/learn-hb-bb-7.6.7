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
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.attachment.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.attachment.input.DeleteMarkFileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.common.exception.NrCommonException;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_DELETE_MARKFILE", groupTitle="\u5220\u9664\u6807\u8bb0\u9644\u4ef6", subject="\u62a5\u8868", tags={"\u77ed\u4efb\u52a1"})
public class DeleteMarkFileTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = -4709278207274876248L;
    private static final Logger logger = LoggerFactory.getLogger(DeleteMarkFileTaskExecutor.class);
    public static final String NAME = "ASYNCTASK_DELETE_MARKFILE";

    public void execute(JobContext jobContext) {
        FileOperationService fileOperationService = (FileOperationService)SpringBeanUtils.getBean(FileOperationService.class);
        String cancelInfo = "task_cancel_info";
        String errorInfo = "task_error_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, NAME, jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(this.getArgs())) {
                DeleteMarkFileInfo deleteMarkFileInfo = (DeleteMarkFileInfo)SimpleParamConverter.SerializationUtils.deserialize((String)this.getArgs());
                fileOperationService.deleteMarkFile(deleteMarkFileInfo, (AsyncTaskMonitor)asyncTaskMonitor);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
                }
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
        return NAME;
    }
}

