/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.integritycheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.integritycheck.asynctask.CanceledInfo;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckInfo;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckParam;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo;
import com.jiuqi.nr.integritycheck.service.ICRClearService;
import com.jiuqi.nr.integritycheck.service.IIntegrityCheckService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_TABLE_INTEGRITYCHECK", groupTitle="\u8868\u5b8c\u6574\u6027\u68c0\u67e5")
public class IntegrityCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityCheckAsyncTaskExecutor.class);
    private static final String REALTIME_TASK_PARAMSKEY_ARGS = "NR_ARGS";
    public static final String ASYNCTASK_INTEGRITYCHECK = "ASYNCTASK_TABLE_INTEGRITYCHECK";

    public void execute(JobContext jobContext) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        DimCollectionBuildUtil dimCollectionBuildUtil = (DimCollectionBuildUtil)BeanUtil.getBean(DimCollectionBuildUtil.class);
        IIntegrityCheckService integrityCheckService = (IIntegrityCheckService)BeanUtil.getBean(IIntegrityCheckService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, ASYNCTASK_INTEGRITYCHECK, jobContext);
        try {
            if (params != null && params.containsKey(REALTIME_TASK_PARAMSKEY_ARGS)) {
                IntegrityCheckInfo integrityCheckInfo = (IntegrityCheckInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get(REALTIME_TASK_PARAMSKEY_ARGS))));
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(integrityCheckInfo.getTaskKey());
                DimensionCollection dimensionCollection = dimCollectionBuildUtil.buildDimensionCollection(integrityCheckInfo.getDimensionSet(), integrityCheckInfo.getFormSchemeKey());
                IntegrityCheckParam param = new IntegrityCheckParam();
                param.setBatchId(taskId);
                param.setTaskKey(integrityCheckInfo.getTaskKey());
                param.setFormSchemeKey(integrityCheckInfo.getFormSchemeKey());
                param.setDims(dimensionCollection);
                param.setFormKeys(integrityCheckInfo.getFormKeys());
                IntegrityCheckResInfo integrityCheckResInfo = integrityCheckService.integrityCheck(param, (AsyncTaskMonitor)asyncTaskMonitor);
                if (null == integrityCheckResInfo) {
                    asyncTaskMonitor.error("\u7ef4\u5ea6\u4e3a\u7a7a", null);
                    return;
                }
                if (asyncTaskMonitor.isCancel()) {
                    ICRClearService icrClearService = (ICRClearService)BeanUtil.getBean(ICRClearService.class);
                    icrClearService.clearResult(taskDefine.getDataScheme(), taskId);
                    AsyncTaskManager asyncTaskManager = (AsyncTaskManager)BeanUtil.getBean(AsyncTaskManager.class);
                    Object asyncDetial = asyncTaskManager.queryDetail(asyncTaskMonitor.getTaskId());
                    if (null == asyncDetial) {
                        CanceledInfo canceledInfo = new CanceledInfo();
                        canceledInfo.setFormNum(integrityCheckResInfo.getFormKeys().size());
                        asyncTaskMonitor.canceled("task_cancel_info", (Object)canceledInfo);
                    }
                    return;
                }
                asyncTaskMonitor.finish("task_success_info", (Object)integrityCheckResInfo);
            } else {
                String missingParamError = "\u7f3a\u5c11\u5fc5\u8981\u7684\u4efb\u52a1\u53c2\u6570-REALTIME_TASK_PARAMSKEY_ARGS";
                logger.error(missingParamError);
                asyncTaskMonitor.error("task_error_info", (Throwable)new Exception(missingParamError));
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error("task_error_info", (Throwable)e);
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u51fa\u9519", e);
        }
    }

    public String getTaskPoolType() {
        return ASYNCTASK_INTEGRITYCHECK;
    }
}

