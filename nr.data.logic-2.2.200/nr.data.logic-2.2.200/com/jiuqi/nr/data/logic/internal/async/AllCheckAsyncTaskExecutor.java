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
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.logic.internal.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.helper.CheckDataCollector;
import com.jiuqi.nr.data.logic.monitor.ProgressMonitor;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_TASK_ALL_CHECK", groupTitle="\u6570\u636e\u670d\u52a1\u5168\u5ba1")
public class AllCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AllCheckAsyncTaskExecutor.class);
    private static final long serialVersionUID = -2539830813275549813L;

    public String getTaskPoolType() {
        return "ASYNC_TASK_ALL_CHECK";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        block8: {
            ICheckService checkService = (ICheckService)BeanUtil.getBean(ICheckService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, this.getTaskPoolType(), jobContext);
            String executeId = UUID.randomUUID().toString();
            try {
                if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                    Object args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                    if (args instanceof CheckParam) {
                        CheckParam checkParam = (CheckParam)args;
                        checkService.allCheck(checkParam, new ProgressMonitor((AsyncTaskMonitor)monitor));
                        if (monitor.isCancel()) {
                            String retStr = "\u4efb\u52a1\u53d6\u6d88";
                            monitor.canceled(retStr, (Object)retStr);
                        }
                        break block8;
                    }
                    throw new IllegalArgumentException(" args not instanceof CheckParam");
                }
                throw new IllegalArgumentException(" REALTIME_TASK_PARAMS_KEY_ARGS is null");
            }
            catch (Exception e) {
                monitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                CheckDataCollector.getInstance().remove(executeId);
            }
        }
    }
}

