/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.access.WorklfowAccessService
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.common.WorkflowState
 *  com.jiuqi.nr.data.access.exception.AccessException
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.FormBatchAccessCache
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataAccessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneDim
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 */
package com.jiuqi.nr.workflow2.converter.workflow.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.access.WorklfowAccessService;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.common.WorkflowState;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class WorkflowUnitAccessConverter
extends WorklfowAccessService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public int getOrder() {
        return 8;
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            return false;
        }
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        boolean workflowEnable = this.workflowSettingsService.queryTaskWorkflowEnable(formSchemeDefine.getTaskKey());
        if (!workflowEnable) {
            return false;
        }
        this.applyStateRename(taskKey);
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        return workflowObjectType.equals((Object)WorkflowObjectType.MAIN_DIMENSION);
    }

    public List<String> getCodeList() {
        return new ArrayList<String>(noAccessReasonMap.keySet());
    }

    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        return this.canAccess;
    }

    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        return this.canAccess;
    }

    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        return this.write(formSchemeKey, masterKey.toDimensionValueSet(), formKey);
    }

    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKeys, "masterKeys is must not be null!");
        return (masterKey, formKey) -> this.canAccess;
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) throws AccessException {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = WorkflowUnitAccessConverter.getEntityCaliber(formSchemeDefine);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKeys);
        List dimensionSetList = DimensionValueSetUtil.toDimensionValueSetList((DimensionCollection)masterKeys);
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        Object unitInfoObject = dimensionValueSet.getValue(dimensionName);
        List<Object> rangeUnitKeys = new ArrayList();
        if (unitInfoObject instanceof List) {
            rangeUnitKeys = (List)unitInfoObject;
        } else if (unitInfoObject instanceof String) {
            rangeUnitKeys = Collections.singletonList(unitInfoObject.toString());
        }
        String[] rangePeriods = dimensionValueSet.getValue("DATATIME").toString().split(";");
        HashMap<String, DimensionValueSet> filterMap = new HashMap<String, DimensionValueSet>();
        for (DimensionValueSet dimensionSet : dimensionSetList) {
            String unitCode = dimensionSet.getValue(dimensionName).toString();
            String period = dimensionSet.getValue("DATATIME").toString();
            filterMap.put(unitCode + period, dimensionSet);
        }
        FormBatchAccessCache accessCache = new FormBatchAccessCache(this.name(), formSchemeKey);
        for (String period : rangePeriods) {
            HashSet<ProcessRangeDims> rangeDims = new HashSet<ProcessRangeDims>();
            ProcessRangeDims unitRangeDim = new ProcessRangeDims();
            unitRangeDim.setDimensionName(dimensionName);
            unitRangeDim.setDimensionKey(entityId);
            unitRangeDim.setRangeType(EProcessRangeDimType.RANGE);
            unitRangeDim.setRangeDims(rangeUnitKeys);
            rangeDims.add(unitRangeDim);
            ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
            batchRunPara.setTaskKey(formSchemeDefine.getTaskKey());
            batchRunPara.setPeriod(period);
            batchRunPara.setReportDimensions(rangeDims);
            IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
            IBizObjectOperateResult operateResult = this.processQueryService.queryUnitState((IProcessRunPara)batchRunPara, businessKeyCollection);
            Iterable businessObjects = operateResult.getBusinessObjects();
            HashMap cacheMap = new HashMap();
            for (IBusinessObject businessObject : businessObjects) {
                String dimUnitCode = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
                String dimPeriod = businessObject.getDimensions().getPeriodDimensionValue().getValue().toString();
                IOperateResult result = operateResult.getResult((Object)businessObject);
                IProcessStatus processStatus = (IProcessStatus)result.getResult();
                if (processStatus == null || processStatus.getDataAccessStatus().equals((Object)IProcessStatus.DataAccessStatus.WRITEABLE)) continue;
                WorkflowState workflowState = WorkflowUnitAccessConverter.transferWorkflowState(processStatus.getCode());
                String accCode = (String)this.accessCodeCompute.apply(String.valueOf(workflowState.getValue()));
                HashMap<String, String> valueMap = new HashMap<String, String>();
                if (this.getCodeList().contains(accCode) && formKeys != null) {
                    for (String formKey : formKeys) {
                        valueMap.put(formKey, accCode);
                    }
                }
                DimensionValueSet dim = (DimensionValueSet)filterMap.get(dimUnitCode + dimPeriod);
                cacheMap.put(dim, valueMap);
            }
            accessCache.setCacheMap(cacheMap);
        }
        return accessCache;
    }

    public String name() {
        return "upload-unit";
    }

    public IAccessMessage getAccessMessage() {
        return code -> (String)this.noAccessReason.apply(code);
    }

    private AccessCode write(String formSchemeKey, DimensionValueSet masterKey, String formKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = WorkflowUnitAccessConverter.getEntityCaliber(formSchemeDefine);
        DimensionValueSet dimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(masterKey, formSchemeDefine);
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        String unitKey = dimensionValueSet.getValue(dimensionName).toString();
        String period = dimensionValueSet.getValue("DATATIME").toString();
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dimensionName);
        unitOneDim.setDimensionKey(entityId);
        unitOneDim.setDimensionValue(unitKey);
        oneDims.add(unitOneDim);
        ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
        oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        oneRunPara.setPeriod(period);
        oneRunPara.setReportDimensions(oneDims);
        IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneRunPara);
        IProcessStatus processStatus = this.processQueryService.queryUnitState((IProcessRunPara)oneRunPara, businessKey);
        if (processStatus == null || processStatus.getDataAccessStatus().equals((Object)IProcessStatus.DataAccessStatus.WRITEABLE)) {
            return new AccessCode(this.name());
        }
        WorkflowState workflowState = WorkflowUnitAccessConverter.transferWorkflowState(processStatus.getCode());
        String accCode = (String)this.accessCodeCompute.apply(String.valueOf(workflowState.getValue()));
        if (this.getCodeList().contains(accCode)) {
            return new AccessCode(this.name(), accCode);
        }
        return new AccessCode(this.name());
    }

    public static WorkflowState transferWorkflowState(String statusCode) {
        switch (statusCode) {
            case "submited": {
                return WorkflowState.SUBMITED;
            }
            case "reported": {
                return WorkflowState.UPLOADED;
            }
            case "backed": {
                return WorkflowState.RETURNED;
            }
            case "rejected": {
                return WorkflowState.REJECTED;
            }
            case "confirmed": {
                return WorkflowState.CONFIRMED;
            }
        }
        return WorkflowState.ORIGINAL_UPLOAD;
    }

    public static String getEntityCaliber(FormSchemeDefine formSchemeDefine) {
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        if (entityCaliber != null && !entityCaliber.isEmpty()) {
            return entityCaliber;
        }
        return formSchemeDefine.getDw();
    }

    public void applyStateRename(String taskKey) {
        Map filterMap = this.processMetaDataService.queryAllStatus(taskKey).stream().collect(Collectors.toMap(IProcessStatus::getCode, Function.identity(), (v1, v2) -> v1));
        IProcessStatus processStatus_submitted = (IProcessStatus)filterMap.get("submited");
        IProcessStatus processStatus_reported = (IProcessStatus)filterMap.get("reported");
        IProcessStatus processStatus_confirmed = (IProcessStatus)filterMap.get("confirmed");
        if (processStatus_submitted != null) {
            noAccessReasonMap.put("NO_AC_" + WorkflowState.SUBMITED.getValue(), "\u6570\u636e" + processStatus_submitted.getAlias() + "\u4e0d\u53ef\u7f16\u8f91");
        }
        if (processStatus_reported != null) {
            noAccessReasonMap.put("NO_AC_" + WorkflowState.UPLOADED.getValue(), "\u6570\u636e" + processStatus_reported.getAlias() + "\u4e0d\u53ef\u7f16\u8f91");
        }
        if (processStatus_confirmed != null) {
            noAccessReasonMap.put("NO_AC_" + WorkflowState.CONFIRMED.getValue(), "\u6570\u636e" + processStatus_confirmed.getAlias() + "\u4e0d\u53ef\u7f16\u8f91");
        }
    }
}

