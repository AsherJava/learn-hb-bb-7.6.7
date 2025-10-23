/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 */
package com.jiuqi.nr.sbdata.carry.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.sbdata.carry.bean.FormCarryResult;
import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownDTO;
import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownRestParam;
import com.jiuqi.nr.sbdata.carry.exception.TzCarryDownException;
import com.jiuqi.nr.sbdata.carry.job.TzCarrySubRealTimeJob;
import com.jiuqi.nr.sbdata.carry.util.TzCarryUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_TZ_CARRYDOWN", groupTitle="\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c", subject="\u62a5\u8868")
public class TzCarryDownRealTimeJob
extends AbstractRealTimeJob {
    private static final Logger logger = LoggerFactory.getLogger(TzCarryDownRealTimeJob.class);

    public void execute(JobContext jobContext) throws JobExecutionException {
        RuntimeViewController runtimeViewController = (RuntimeViewController)BeanUtil.getBean(RuntimeViewController.class);
        DimCollectionBuildUtil dimCollectionBuildUtil = (DimCollectionBuildUtil)BeanUtil.getBean(DimCollectionBuildUtil.class);
        AsyncTaskManager asyncTaskManager = (AsyncTaskManager)BeanUtil.getBean(AsyncTaskManager.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_TZ_CARRYDOWN", jobContext);
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            List<String> formKeys;
            TzCarryDownRestParam param = (TzCarryDownRestParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            String paramFormKeys = param.getFormKeys();
            if (paramFormKeys == null) {
                throw new TzCarryDownException("\u8bf7\u9009\u62e9\u8868\u5355\uff01");
            }
            boolean isAll = paramFormKeys.isEmpty();
            if (isAll) {
                List formDefines = runtimeViewController.queryAllFormDefinesByFormScheme(param.getSourceFormSchemeKey());
                formKeys = formDefines.stream().filter(a -> a.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            } else {
                formKeys = Arrays.asList(paramFormKeys.split(";"));
            }
            asyncTaskMonitor.progressAndMessage(0.01, "\u5f00\u59cb\u53f0\u8d26\u7ed3\u8f6c");
            logger.info("---\u5f00\u59cb\u53f0\u8d26\u7ed3\u8f6c---");
            DimensionCollection sourceMasterKey = dimCollectionBuildUtil.buildDimensionCollection(param.getSourceDimensionSet(), param.getSourceFormSchemeKey());
            Map<String, DimensionValue> destDim = this.getDestDim(param.getSourceDimensionSet(), param.getDestPeriod());
            DimensionCollection destMasterKey = dimCollectionBuildUtil.buildDimensionCollection(destDim, param.getDestFormSchemeKey());
            HashSet<String> allAsyncTasks = new HashSet<String>();
            HashSet<String> executingTasks = new HashSet<String>();
            HashSet<String> executedTasks = new HashSet<String>();
            int parallelNumber = param.getParallelNumbers();
            asyncTaskMonitor.progressAndMessage(0.1, null);
            for (String formKey : formKeys) {
                TzCarryDownDTO subParam = new TzCarryDownDTO(param);
                subParam.setFormKey(formKey);
                subParam.setSourceMasterKey(sourceMasterKey);
                subParam.setDestMasterKey(destMasterKey);
                TzCarrySubRealTimeJob subJob = new TzCarrySubRealTimeJob();
                TzCarryUtils.publishSubJob(jobContext, subJob, parallelNumber, allAsyncTasks, executingTasks, executedTasks, asyncTaskManager, subParam, (AsyncTaskMonitor)asyncTaskMonitor, formKeys.size());
            }
            TzCarryUtils.updateProgressAndLog(jobContext, asyncTaskManager, (AsyncTaskMonitor)asyncTaskMonitor, allAsyncTasks, executedTasks, formKeys.size());
            asyncTaskMonitor.progressAndMessage(0.9, null);
            List<FormCarryResult> allResults = TzCarryUtils.getAllResultAndLog(jobContext, asyncTaskManager, allAsyncTasks);
            String detail = JsonUtil.objectToJson(allResults);
            boolean containFail = TzCarryUtils.getContainFail(allResults);
            if (containFail) {
                asyncTaskMonitor.error("\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c\u5b8c\u6210\uff0c\u5b58\u5728\u7ed3\u8f6c\u5931\u8d25\u8868\u5355", null, detail);
                logger.info("---\u53f0\u8d26\u7ed3\u8f6c\u5b8c\u6210\uff0c\u5b58\u5728\u7ed3\u8f6c\u5931\u8d25\u8868\u5355---");
            } else {
                asyncTaskMonitor.finish("\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c\u5b8c\u6210", (Object)detail);
                logger.info("---\u53f0\u8d26\u7ed3\u8f6c\u5b8c\u6210---");
            }
        }
    }

    private Map<String, DimensionValue> getDestDim(Map<String, DimensionValue> sourceDimensionSet, String destPeriod) {
        HashMap<String, DimensionValue> result = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> entry : sourceDimensionSet.entrySet()) {
            DimensionValue value = entry.getValue();
            String dimName = value.getName();
            if ("DATATIME".equals(dimName)) {
                DimensionValue dimValue = new DimensionValue(value);
                dimValue.setValue(destPeriod);
                result.put(entry.getKey(), dimValue);
                continue;
            }
            result.put(entry.getKey(), value);
        }
        return result;
    }
}

