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
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableContextService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.monitor.BatchCheckAnalysisFormProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_BATCHCHECKANALYSISFORM", groupTitle="\u6279\u91cf\u5ba1\u6838\u5206\u6790\u8868", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class BatchCheckAnalysisFormAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckAnalysisFormAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHCHECKANALYSISFORM.getName(), jobContext);
        IBatchCheckService batchCheckService = (IBatchCheckService)BeanUtil.getBean(IBatchCheckService.class);
        IJtableContextService jtableContextService = (IJtableContextService)BeanUtil.getBean(IJtableContextService.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        IBatchDataSumService batchDataSumService = (IBatchDataSumService)BeanUtil.getBean(IBatchDataSumService.class);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                BatchCheckInfo batchCheckInfo = (BatchCheckInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                JtableContext queryJtableContext = new JtableContext(batchCheckInfo.getContext());
                List entityList = jtableParamService.getEntityList(batchCheckInfo.getContext().getFormSchemeKey());
                EntityViewData unitEntity = null;
                for (EntityViewData entity : entityList) {
                    if (!entity.isMasterEntity()) continue;
                    unitEntity = entity;
                }
                ((DimensionValue)queryJtableContext.getDimensionSet().get(unitEntity.getDimensionName())).setValue("");
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setEntityViewKey(unitEntity.getKey());
                entityQueryInfo.setAllChildren(true);
                entityQueryInfo.setContext(queryJtableContext);
                EntityReturnInfo entityData = jtableEntityService.queryEntityData(entityQueryInfo);
                List allEntityKey = DimensionValueSetUtil.getAllEntityKey((EntityReturnInfo)entityData, (boolean)false);
                BatchDataSumInfo batchDataSumInfo = new BatchDataSumInfo();
                batchDataSumInfo.setContext(batchCheckInfo.getContext());
                batchDataSumInfo.setIgnoreAuth(true);
                batchDataSumInfo.setRecursive(true);
                batchDataSumInfo.setDifference(false);
                BatchCheckAnalysisFormProgressMonitor formProgressMonitor = new BatchCheckAnalysisFormProgressMonitor((AsyncTaskMonitor)asyncTaskMonitor, 0.5, 0.0);
                batchDataSumService.batchDataSumForm(batchDataSumInfo, formProgressMonitor, 1.0f);
                StringBuffer stringBuffer = new StringBuffer();
                for (String entityKey : allEntityKey) {
                    stringBuffer.append(entityKey);
                    stringBuffer.append(";");
                }
                ((DimensionValue)batchCheckInfo.getContext().getDimensionSet().get(unitEntity.getDimensionName())).setValue(stringBuffer.toString());
                batchCheckService.batchCheckForm(batchCheckInfo, (AsyncTaskMonitor)asyncTaskMonitor);
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
        return AsynctaskPoolType.ASYNCTASK_BATCHCHECKANALYSISFORM.getName();
    }
}

