/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.singlequeryimport.auth.share.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.AuthShareUserParams;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_SINGLEQUERYAUTHSHARE", groupTitle="\u6743\u9650\u5206\u4eab")
public class SingleQueryAuthShareAsyncTaskExecutor
extends AbstractRealTimeJob {
    private static final Logger logger = LoggerFactory.getLogger(SingleQueryAuthShareAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        AuthShareService authShareService = (AuthShareService)SpringBeanUtils.getBean(AuthShareService.class);
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        Object args = new Object();
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
        }
        String taskId = jobContext.getInstanceId();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_SINGLEQUERYAUTHSHARE", jobContext);
        if (args instanceof AuthShareUserParams) {
            AuthShareUserParams authShareUserParams = (AuthShareUserParams)args;
            boolean returnObject = authShareService.batchSetUsersWithModelAuth(authShareUserParams, (AsyncTaskMonitor)monitor);
            if (monitor != null) {
                if (monitor.isCancel()) {
                    monitor.canceled("\u5f02\u6b65\u4efb\u52a1\u53d6\u6d88", (Object)returnObject);
                    return;
                }
                monitor.finish("\u6267\u884c\u6210\u529f", (Object)returnObject);
            }
            if (monitor != null) {
                monitor.progressAndMessage(1.0, "");
            }
        } else {
            logger.error("\u53c2\u6570\u683c\u5f0f\u9519\u8bef");
            if (monitor != null) {
                monitor.finish("", (Object)"");
            }
        }
    }
}

