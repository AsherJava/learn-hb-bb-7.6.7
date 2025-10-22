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
 */
package com.jiuqi.nr.parallel.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.BatchTaskExecuteFactoryMgr;
import com.jiuqi.nr.parallel.IBatchTaskExecuteFactory;
import com.jiuqi.nr.parallel.IParallelTaskExecuter;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_POOL_TYPE_PARALLEL", groupTitle="\u62c6\u5206\u5b50\u4efb\u52a1\u5e76\u884c\u6267\u884c")
public class ParallelAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ParallelAsyncTaskExecutor.class);
    private static final long serialVersionUID = -5845817997038047210L;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, this.getTaskPoolType(), jobContext);
        if (!Objects.nonNull(params) || !Objects.nonNull(params.get("NR_ARGS"))) throw new IllegalArgumentException(" REALTIME_TASK_PARAMS_KEY_ARGS is null");
        Object args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
        if (!(args instanceof BatchParallelExeTask)) throw new IllegalArgumentException(" args not instanceof BatchParallelExeTask");
        BatchParallelExeTask task = (BatchParallelExeTask)args;
        IBatchTaskExecuteFactory factory = BatchTaskExecuteFactoryMgr.getInstance().findFactory(task.getType());
        if (factory == null) return;
        IParallelTaskExecuter execute = factory.getIParallelTaskExecuter();
        BatchParallelMonitor parallelMonitor = factory.getMonitor(task);
        parallelMonitor.setAsyncTaskMonitor((AsyncTaskMonitor)monitor);
        try {
            execute.doExecute(task, parallelMonitor);
            return;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }
        finally {
            if (!parallelMonitor.isCancel()) {
                task.setFinished(true);
                parallelMonitor.finish();
            }
        }
    }

    public String getTaskPoolType() {
        return "ASYNCTASK_POOL_TYPE_PARALLEL";
    }
}

