/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.bpm.asynctask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.step.BatchUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchUploadBean;
import com.jiuqi.nr.common.exception.NrCommonException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_BATCH_UPLOAD", groupTitle="\u6279\u91cf\u4e0a\u62a5\u5b50\u4efb\u52a1", subject="\u62a5\u8868")
public class BatchUploadAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(BatchUploadAsyncTaskExecutor.class);
    public static final String ASYNC_BATCH_UPLOAD = "ASYNC_BATCH_UPLOAD";

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String retStr = "";
        BatchUpload batchUpload = (BatchUpload)BeanUtils.getBean(BatchUpload.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, ASYNC_BATCH_UPLOAD, jobContext);
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                BatchUploadBean batchUploadBean = (BatchUploadBean)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                List<CompleteMsg> uploadResult = batchUpload.batchUpload(batchUploadBean);
                if (monitor.isCancel()) {
                    retStr = "\u4efb\u52a1\u53d6\u6d88";
                    monitor.canceled(retStr, (Object)retStr);
                }
                retStr = mapper.writeValueAsString(uploadResult);
                monitor.finish("\u6267\u884c\u5b8c\u6210", (Object)retStr);
            }
        }
        catch (NrCommonException nrCommonException) {
            monitor.error("\u51fa\u9519\u539f\u56e0", (Throwable)nrCommonException);
            log.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            monitor.error("\u51fa\u9519\u539f\u56e0", (Throwable)e);
            log.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return ASYNC_BATCH_UPLOAD;
    }
}

