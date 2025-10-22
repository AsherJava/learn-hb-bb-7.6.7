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
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.dataentry.attachment.service.IBatchDownLoadAttachment;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_BATCHDOWNLOADATTACHMENT", groupTitle="\u6279\u91cf\u9644\u4ef6\u4e0b\u8f7d", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class BatchDownloadAttachmentExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchDownloadAttachmentExecutor.class);
    public static final String NAME = "ASYNCTASK_BATCHDOWNLOADATTACHMENT";

    public void executeWithNpContext(JobContext jobContext) {
        IBatchDownLoadAttachment batchDownLoadAttachment = (IBatchDownLoadAttachment)SpringBeanUtils.getBean(IBatchDownLoadAttachment.class);
        String cancelInfo = "task_cancel_info";
        String errorInfo = "task_error_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, NAME, jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(this.getArgs())) {
                BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo = (BatchDownLoadEnclosureInfo)SimpleParamConverter.SerializationUtils.deserialize((String)this.getArgs());
                batchDownLoadAttachment.batchDownloadAttachments(batchDownLoadEnclosureInfo, (AsyncTaskMonitor)asyncTaskMonitor);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled("stop_execute", (Object)cancelInfo);
                    LogHelper.info((String)"\u6279\u91cf\u9644\u4ef6\u4e0b\u8f7d", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
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
}

