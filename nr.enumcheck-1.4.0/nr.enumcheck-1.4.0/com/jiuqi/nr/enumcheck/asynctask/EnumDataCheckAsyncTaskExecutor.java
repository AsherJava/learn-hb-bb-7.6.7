/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.integritycheck.asynctask.CanceledInfo
 */
package com.jiuqi.nr.enumcheck.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.enumcheck.common.EnumCheckResInfo;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckInfo;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckParam;
import com.jiuqi.nr.enumcheck.service.IEnumCheckService;
import com.jiuqi.nr.integritycheck.asynctask.CanceledInfo;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_ENUMCHECK", groupTitle="\u679a\u4e3e\u5b57\u5178\u68c0\u67e5")
public class EnumDataCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EnumDataCheckAsyncTaskExecutor.class);
    public static final String ASYNCTASK_ENUMDATACHECK = "ASYNCTASK_ENUMCHECK";

    public void execute(JobContext jobContext) {
        IEnumCheckService enumCheckService = (IEnumCheckService)BeanUtil.getBean(IEnumCheckService.class);
        DimCollectionBuildUtil dimCollectionBuildUtil = (DimCollectionBuildUtil)BeanUtil.getBean(DimCollectionBuildUtil.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, ASYNCTASK_ENUMDATACHECK, jobContext);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                EnumDataCheckInfo enumDataCheckInfo = (EnumDataCheckInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                EnumDataCheckParam enumDataCheckParam = new EnumDataCheckParam();
                enumDataCheckParam.setBatchId(taskId);
                enumDataCheckParam.setTaskKey(enumDataCheckInfo.getContext().getTaskKey());
                enumDataCheckParam.setFormSchemeKey(enumDataCheckInfo.getContext().getFormSchemeKey());
                Map dimensionSet = enumDataCheckInfo.getContext().getDimensionSet();
                DimensionCollection dimensionCollection = dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, enumDataCheckInfo.getContext().getFormSchemeKey());
                enumDataCheckParam.setDims(dimensionCollection);
                if (StringUtils.isNotEmpty((String)enumDataCheckInfo.getEnumNames())) {
                    String[] enumNameArry = enumDataCheckInfo.getEnumNames().split(";");
                    enumDataCheckParam.setEnumNames(Arrays.asList(enumNameArry));
                }
                enumDataCheckParam.setIgnoreBlank(enumDataCheckInfo.isIgnoreBlank());
                enumDataCheckParam.setFilterFormula(enumDataCheckInfo.getFilterFormula());
                enumDataCheckParam.setVariableMap(enumDataCheckInfo.getVariableMap());
                EnumCheckResInfo enumCheckResInfo = enumCheckService.enumDataCheck(enumDataCheckParam, (AsyncTaskMonitor)asyncTaskMonitor);
                if (null == enumCheckResInfo) {
                    asyncTaskMonitor.error("\u7ef4\u5ea6\u4e3a\u7a7a", null);
                    return;
                }
                if (asyncTaskMonitor.isCancel()) {
                    AsyncTaskManager asyncTaskManager = (AsyncTaskManager)BeanUtil.getBean(AsyncTaskManager.class);
                    Object asyncDetial = asyncTaskManager.queryDetail(asyncTaskMonitor.getTaskId());
                    if (null == asyncDetial) {
                        CanceledInfo canceledInfo = new CanceledInfo();
                        canceledInfo.setFormNum(enumCheckResInfo.getFormCount());
                        asyncTaskMonitor.canceled("task_cancel_info", (Object)canceledInfo);
                    }
                    return;
                }
                asyncTaskMonitor.finish("task_success_info", (Object)enumCheckResInfo);
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error("task_error_info", (Throwable)e);
            logger.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return ASYNCTASK_ENUMDATACHECK;
    }
}

