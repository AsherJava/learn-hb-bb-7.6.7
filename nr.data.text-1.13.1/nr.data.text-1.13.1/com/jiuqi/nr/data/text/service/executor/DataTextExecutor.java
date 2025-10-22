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
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.exception.DataCommonException
 */
package com.jiuqi.nr.data.text.service.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.exception.DataCommonException;
import com.jiuqi.nr.data.text.param.TextAsyncContext;
import com.jiuqi.nr.data.text.service.impl.DataTextService;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_TEXT_IMPORT", groupTitle="\u6570\u636e\u670d\u52a1-\u6587\u672c\u5bfc\u51fa")
public class DataTextExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(DataTextExecutor.class);
    private static final String ASYNC_TEXT_IMPORT = "ASYNC_TEXT_IMPORT";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        DataTextService dataTextService = (DataTextService)BeanUtil.getBean(DataTextService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, ASYNC_TEXT_IMPORT, jobContext);
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            TextAsyncContext text = (TextAsyncContext)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            try {
                text.getParams().setMonitor((AsyncTaskMonitor)asyncTaskMonitor);
                Message<CommonMessage> uploadTextData = dataTextService.uploadTextData(text.getParams(), text.getCommonParams());
                asyncTaskMonitor.finish("\u5bfc\u5165\u6210\u529f", uploadTextData);
            }
            catch (Exception e) {
                log.error("\u5f02\u6b65\u5bfc\u5165\u6267\u884c\u5f02\u5e38{}", (Object)e.getMessage());
                asyncTaskMonitor.finish("\u5bfc\u5165\u5931\u8d25", (Object)e.getMessage());
                throw new DataCommonException(e.getMessage(), (Throwable)e);
            }
        }
    }
}

