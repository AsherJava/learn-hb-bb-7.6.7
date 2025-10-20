/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetch.impl.asynctask.GcFetchAsyncTaskExecutor
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.inputdata.util.InputDataConver
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.efdc.asynctask.EFDCAsyncTaskExecutor
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetch.impl.asynctask.GcFetchAsyncTaskExecutor;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskExecutor;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyTaskPoolEnum;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.efdc.asynctask.EFDCAsyncTaskExecutor;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="dataPick")
public class MergeTaskDataPickExecutorImpl
implements MergeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MergeTaskDataPickExecutorImpl.class);
    private static final String TASKCODE = TaskTypeEnum.DATAPICK.getCode();
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public String getTaskType() {
        return TASKCODE;
    }

    @Override
    public MergeTaskEO createTask(GcActionParamsVO param) {
        MergeTaskEO mergeTaskEO = OneKeyMergeUtils.buildMergeTask(param);
        GcOrgCacheVO currentOrg = OrgUtils.getOrgByCode(param.getPeriodStr(), param.getOrgType(), param.getOrgId());
        if (currentOrg.getOrgKind().equals((Object)GcOrgKindEnum.DIFFERENCE)) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u5f53\u524d\u5355\u4f4d\u4e3a\u5dee\u989d\u5355\u4f4d\u3002");
            return mergeTaskEO;
        }
        String curCurrency = StringUtils.toViewString((Object)currentOrg.getTypeFieldValue("CURRENCYID"));
        Map<String, DimensionValue> dimensionValueMap = this.onekeyMergeService.buildDimensionMap(param.getTaskId(), curCurrency, param.getPeriodStr(), param.getOrgType(), param.getOrgId(), param.getSelectAdjustCode());
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDimWithCache(param.getSchemeId(), param.getOrgId(), InputDataConver.getDimFieldValueMap(dimensionValueMap, (String)param.getTaskId()));
        if (formulaSchemeConfigDTO == null || StringUtils.isEmpty((String)formulaSchemeConfigDTO.getFetchSchemeId())) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u53d6\u6570\u65b9\u6848\u4e0d\u5b58\u5728\u3002");
            return mergeTaskEO;
        }
        return mergeTaskEO;
    }

    @Override
    public Object buildAsyncTaskParam(GcActionParamsVO param, String orgId) {
        GcOrgCacheVO currentOrg = OrgUtils.getOrgByCode(param.getPeriodStr(), param.getOrgType(), orgId);
        EfdcInfo efdcInfo = new EfdcInfo();
        efdcInfo.setTaskKey(param.getTaskId());
        efdcInfo.setFormSchemeKey(param.getSchemeId());
        String curCurrency = StringUtils.toViewString((Object)currentOrg.getTypeFieldValue("CURRENCYID"));
        Map<String, DimensionValue> dimensionValueMap = this.onekeyMergeService.buildDimensionMap(param.getTaskId(), curCurrency, param.getPeriodStr(), param.getOrgType(), orgId, param.getSelectAdjustCode());
        efdcInfo.setDimensionSet(dimensionValueMap);
        efdcInfo.setVariableMap(new HashMap());
        return efdcInfo;
    }

    @Override
    public String publishTask(GcActionParamsVO param, Object asyncTaskParam, OneKeyTaskPoolEnum taskPoolEnum) {
        GcOrgCacheVO currentOrg = OrgUtils.getOrgByCode(param.getPeriodStr(), param.getOrgType(), param.getOrgId());
        String curCurrency = StringUtils.toViewString((Object)currentOrg.getTypeFieldValue("CURRENCYID"));
        Map<String, DimensionValue> dimensionValueMap = this.onekeyMergeService.buildDimensionMap(param.getTaskId(), curCurrency, param.getPeriodStr(), param.getOrgType(), param.getOrgId(), param.getSelectAdjustCode());
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDimWithCache(param.getSchemeId(), param.getOrgId(), InputDataConver.getDimFieldValueMap(dimensionValueMap, (String)param.getTaskId()));
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(param.getTaskId());
        npRealTimeTaskInfo.setFormSchemeKey(param.getSchemeId());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)asyncTaskParam));
        if (formulaSchemeConfigDTO.getFetchSchemeId().length() == 16) {
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new GcFetchAsyncTaskExecutor());
            return this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        }
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new EFDCAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
    }
}

