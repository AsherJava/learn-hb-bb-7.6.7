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
package com.jiuqi.nr.data.excel.export;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.excel.param.BatchExpPar;
import com.jiuqi.nr.data.excel.param.BatchExpParSer;
import com.jiuqi.nr.data.excel.param.ExportAsyncPar;
import com.jiuqi.nr.data.excel.service.IDataExportService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_DATAEXPORT", groupTitle="\u6570\u636e\u670d\u52a1\u5bfc\u51faexcel")
public class ExportAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ExportAsyncTaskExecutor.class);
    private static final long serialVersionUID = 4090347967071884092L;

    public String getTaskPoolType() {
        return "ASYNCTASK_DATAEXPORT";
    }

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        block5: {
            IDataExportService dataExportService = (IDataExportService)BeanUtil.getBean(IDataExportService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, this.getTaskPoolType(), jobContext);
            try {
                if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                    Object args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                    if (args instanceof ExportAsyncPar) {
                        ExportAsyncPar exportAsyncPar = (ExportAsyncPar)args;
                        BatchExpParSer batchExpParSer = exportAsyncPar.getBatchExpParSer();
                        CommonParams commonParams = new CommonParams();
                        commonParams.setMapping(exportAsyncPar.getParamsMapping());
                        commonParams.setMonitor((AsyncTaskMonitor)monitor);
                        dataExportService.expExcelSync(new BatchExpPar(batchExpParSer), exportAsyncPar.getFilePath(), commonParams);
                        if (monitor.isCancel()) {
                            monitor.canceled(null, null);
                        }
                        break block5;
                    }
                    throw new IllegalArgumentException(" args not instanceof ExportAsyncPar");
                }
                throw new IllegalArgumentException(" REALTIME_TASK_PARAMS_KEY_ARGS is null");
            }
            catch (Exception e) {
                monitor.error(e.getMessage(), (Throwable)e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }
}

