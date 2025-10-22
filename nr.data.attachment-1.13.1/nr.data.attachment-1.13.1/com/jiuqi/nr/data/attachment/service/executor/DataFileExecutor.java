/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.exception.DataCommonException
 */
package com.jiuqi.nr.data.attachment.service.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.data.attachment.param.FileAsyncContext;
import com.jiuqi.nr.data.attachment.service.impl.DataFileService;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.exception.DataCommonException;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_FILE_IMPORT", groupTitle="\u6570\u636e\u670d\u52a1-\u9644\u4ef6\u5bfc\u51fa")
public class DataFileExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(DataFileExecutor.class);
    private static final String ASYNC_FILE_IMPORT = "ASYNC_FILE_IMPORT";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        DataFileService dataFileService = (DataFileService)BeanUtil.getBean(DataFileService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, ASYNC_FILE_IMPORT, jobContext);
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            FileAsyncContext text = (FileAsyncContext)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            try {
                Message<CommonMessage> uploadTextData = dataFileService.uploadFileds(text.getParams(), text.getCommonParams());
                asyncTaskMonitor.finish("\u5bfc\u5165\u6210\u529f", uploadTextData);
            }
            catch (IOException e) {
                log.error("\u5f02\u6b65\u5bfc\u5165\u6267\u884c\u5f02\u5e38{}", (Object)e.getMessage());
                asyncTaskMonitor.finish("\u5bfc\u5165\u5931\u8d25", (Object)e.getMessage());
                throw new DataCommonException(e.getMessage(), (Throwable)e);
            }
        }
    }
}

