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
 *  com.jiuqi.nr.common.exception.NrCommonException
 */
package com.jiuqi.nr.midstore2.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.midstore2.dataentry.bean.MidstoreParam;
import com.jiuqi.nr.midstore2.dataentry.service.IMidstoreService;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_MIDSTORE2_ENTRY", cancellable=true, groupTitle="\u4e2d\u95f4\u5e93-\u5f55\u5165 ")
public class MidstorePullAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(MidstorePullAsyncTaskExecutor.class);
    public static final String ASYNCTASK_MIDSTORE_ENTRY = "ASYNCTASK_MIDSTORE2_ENTRY";
    public static final String TASK_CANCEL_INFO = "task_cancel_info";
    public static final String TASK_ERROR_INFO = "task_error_info";

    public void execute(JobContext jobContext) {
        String errorInfo = TASK_ERROR_INFO;
        String cancelInfo = TASK_CANCEL_INFO;
        IMidstoreService midstoreService = (IMidstoreService)BeanUtil.getBean(IMidstoreService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, ASYNCTASK_MIDSTORE_ENTRY, jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                MidstoreParam midstoreInfo = (MidstoreParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                midstoreService.midstorePullPush(midstoreInfo, (AsyncTaskMonitor)asyncTaskMonitor);
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
        return ASYNCTASK_MIDSTORE_ENTRY;
    }
}

