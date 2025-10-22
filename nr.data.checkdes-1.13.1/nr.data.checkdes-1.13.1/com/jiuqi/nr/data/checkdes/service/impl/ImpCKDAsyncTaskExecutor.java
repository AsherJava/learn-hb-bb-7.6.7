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
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.data.checkdes.obj.ImpAsyncPar;
import com.jiuqi.nr.data.checkdes.service.IImportCKDService;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNC_TASK_IMP_CKD", groupTitle="\u6570\u636e\u670d\u52a1\u5bfc\u5165\u5ba1\u6838\u51fa\u9519\u8bf4\u660e")
public class ImpCKDAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ImpCKDAsyncTaskExecutor.class);
    private static final long serialVersionUID = -4387395048481683659L;

    public String getTaskPoolType() {
        return "ASYNC_TASK_IMP_CKD";
    }

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        block5: {
            IImportCKDService importCKDService = (IImportCKDService)BeanUtil.getBean(IImportCKDService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, this.getTaskPoolType(), jobContext);
            try {
                if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                    Object args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                    if (args instanceof ImpAsyncPar) {
                        ImpAsyncPar impAsyncPar = (ImpAsyncPar)args;
                        CommonParams commonParams = new CommonParams();
                        commonParams.setMapping(impAsyncPar.getParamsMapping());
                        commonParams.setMonitor((AsyncTaskMonitor)monitor);
                        importCKDService.importSync(impAsyncPar.getCkdImpPar(), commonParams);
                        if (monitor.isCancel()) {
                            String retStr = "\u4efb\u52a1\u53d6\u6d88";
                            monitor.canceled(retStr, (Object)retStr);
                        }
                        break block5;
                    }
                    throw new IllegalArgumentException(" args not instanceof ImpAsyncPar");
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

