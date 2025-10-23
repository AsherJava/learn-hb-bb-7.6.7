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
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 */
package com.jiuqi.nr.multcheck2.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.service.IMCExecuteEntryMulti;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(subject="\u62a5\u8868", group="ASYNCTASK_DATAENTRY_MULTCHECK_BATCH", cancellable=true, rollback=true, groupTitle="\u6570\u636e\u5f55\u5165\u6279\u91cf\u7efc\u5408\u5ba1\u6838")
public class MCDataEntryBatchRealTimeTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MCDataEntryBatchRealTimeTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String errorResult = "\u7efc\u5408\u5ba1\u6838\u51fa\u73b0\u5f02\u5e38\uff0c\u70b9\u51fb\u67e5\u8be2\u5f02\u5e38\u8be6\u60c5";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_DATAENTRY_MULTCHECK_BATCH", jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                MCRunVO vo = (MCRunVO)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                IMCExecuteEntryMulti executeService = (IMCExecuteEntryMulti)BeanUtil.getBean(IMCExecuteEntryMulti.class);
                DsContextImpl newContext = (DsContextImpl)DsContextHolder.createEmptyContext();
                newContext.setEntityId(vo.getOrg());
                DsContextHolder.setDsContext((DsContext)newContext);
                MCExecuteResult mcExecuteResult = executeService.multiExecute(jobContext, (AsyncTaskMonitor)asyncTaskMonitor, vo);
                if (mcExecuteResult.getError() != null) {
                    asyncTaskMonitor.error(errorResult, (Throwable)new Exception(mcExecuteResult.getError().getMessage()), SerializeUtil.serializeToJson(mcExecuteResult));
                } else if (asyncTaskMonitor.isCancel()) {
                    logger.warn("\u5f55\u5165\u6279\u91cf\u7efc\u5408\u5ba1\u6838\u5df2\u53d6\u6d88\uff1a" + mcExecuteResult.getCancelMsg());
                    asyncTaskMonitor.canceled("\u7efc\u5408\u5ba1\u6838\u5df2\u53d6\u6d88", (Object)"");
                } else {
                    asyncTaskMonitor.progressAndMessage(1.0, "\u6267\u884c\u5b8c\u6210");
                    asyncTaskMonitor.finish("task_success_info", (Object)mcExecuteResult);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            MCExecuteResult mcExecuteResult = new MCExecuteResult();
            mcExecuteResult.setError(MultcheckSchemeError.RUN_ASYNC);
            mcExecuteResult.setErrorMsg(e.getMessage());
            try {
                asyncTaskMonitor.error(errorResult, (Throwable)e, SerializeUtil.serializeToJson(mcExecuteResult));
            }
            catch (Exception ex) {
                asyncTaskMonitor.error(errorResult, (Throwable)e, ex.getMessage());
            }
        }
    }
}

