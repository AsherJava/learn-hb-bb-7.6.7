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
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.internal.service.ICalculateExecuteService;
import com.jiuqi.nr.data.logic.monitor.ProgressMonitor;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_TASK_BATCH_CALCULATE", groupTitle="\u6570\u636e\u670d\u52a1\u6279\u91cf\u8fd0\u7b97")
public class BatchCalculateAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchCalculateAsyncTaskExecutor.class);
    private static final long serialVersionUID = -6610327042093111935L;

    public String getTaskPoolType() {
        return "ASYNC_TASK_BATCH_CALCULATE";
    }

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        block5: {
            ICalculateExecuteService calculateExecuteService = (ICalculateExecuteService)BeanUtil.getBean(ICalculateExecuteService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, this.getTaskPoolType(), jobContext);
            try {
                if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                    Object args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                    if (args instanceof CalculateParam) {
                        CalculateParam calculateParam = (CalculateParam)args;
                        calculateExecuteService.execute(calculateParam, new ProgressMonitor((AsyncTaskMonitor)monitor));
                        if (monitor.isCancel()) {
                            String retStr = "\u4efb\u52a1\u53d6\u6d88";
                            monitor.canceled(retStr, (Object)retStr);
                        }
                        break block5;
                    }
                    throw new IllegalArgumentException(" args not instanceof CalculateParam");
                }
                throw new IllegalArgumentException(" REALTIME_TASK_PARAMS_KEY_ARGS is null");
            }
            catch (Exception e) {
                monitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }
}

