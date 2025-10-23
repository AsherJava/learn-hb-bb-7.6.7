/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.access.WorklfowAccessService
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
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
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataAccessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneDim
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.workflow.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.access.WorklfowAccessService;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
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
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.converter.workflow.access.WorkflowUnitAccessConverter;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
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
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class WorkflowFormAccessConverter
extends WorklfowAccessService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private WorkflowUnitAccessConverter workflowUnitAccessConverter;
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
        this.workflowUnitAccessConverter.applyStateRename(taskKey);
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        return !workflowObjectType.equals((Object)WorkflowObjectType.MAIN_DIMENSION) && !workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR);
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
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
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
        HashMap<DimensionValueSet, Map> cacheMap = new HashMap<DimensionValueSet, Map>();
        for (String period : rangePeriods) {
            HashSet<ProcessRangeDims> rangeDims = new HashSet<ProcessRangeDims>();
            ProcessRangeDims unitRangeDim = new ProcessRangeDims();
            unitRangeDim.setDimensionName(dimensionName);
            unitRangeDim.setDimensionKey(entityId);
            unitRangeDim.setRangeType(EProcessRangeDimType.RANGE);
            unitRangeDim.setRangeDims(rangeUnitKeys);
            rangeDims.add(unitRangeDim);
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                ProcessRangeDims formRangeDim = new ProcessRangeDims();
                formRangeDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
                formRangeDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
                formRangeDim.setRangeType(EProcessRangeDimType.RANGE);
                formRangeDim.setRangeDims(formKeys);
                rangeDims.add(formRangeDim);
                ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
                batchRunPara.setTaskKey(formSchemeDefine.getTaskKey());
                batchRunPara.setPeriod(period);
                batchRunPara.setReportDimensions(rangeDims);
                JSONObject envVariable = new JSONObject();
                envVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)new FormRejectExecuteParam(formKeys)));
                batchRunPara.setEnvVariables(envVariable);
                IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
                IBizObjectOperateResult operateResult = this.processQueryService.queryUnitState((IProcessRunPara)batchRunPara, businessKeyCollection);
                Iterable businessObjects = operateResult.getBusinessObjects();
                for (IBusinessObject businessObject : businessObjects) {
                    String dimUnitCode = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
                    String dimPeriod = businessObject.getDimensions().getPeriodDimensionValue().getValue().toString();
                    FormObject formObject = (FormObject)businessObject;
                    IOperateResult result = operateResult.getResult((Object)businessObject);
                    IProcessStatus processStatus = (IProcessStatus)result.getResult();
                    if (processStatus == null || processStatus.getDataAccessStatus().equals((Object)IProcessStatus.DataAccessStatus.WRITEABLE)) continue;
                    WorkflowState workflowState = WorkflowUnitAccessConverter.transferWorkflowState(processStatus.getCode());
                    String accCode = (String)this.accessCodeCompute.apply(String.valueOf(workflowState.getValue()));
                    if (!this.getCodeList().contains(accCode)) continue;
                    DimensionValueSet dim = (DimensionValueSet)filterMap.get(dimUnitCode + dimPeriod);
                    Map valueMap = cacheMap.computeIfAbsent(dim, k -> new HashMap());
                    valueMap.put(formObject.getFormKey(), accCode);
                }
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                List<String> formGroupKeys = formKeys;
                ArrayList<String> tempFormGroupKeys = new ArrayList<String>();
                for (String formKey : formKeys) {
                    List formGroupDefines = this.runTimeViewController.listFormGroupByForm(formKey, formSchemeKey);
                    if (formGroupDefines == null || formGroupDefines.isEmpty()) continue;
                    tempFormGroupKeys.add(((FormGroupDefine)formGroupDefines.stream().findFirst().get()).getKey());
                }
                if (!tempFormGroupKeys.isEmpty()) {
                    formGroupKeys = tempFormGroupKeys;
                }
                ProcessRangeDims formGroupRangeDim = new ProcessRangeDims();
                formGroupRangeDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
                formGroupRangeDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
                formGroupRangeDim.setRangeType(EProcessRangeDimType.RANGE);
                formGroupRangeDim.setRangeDims(formGroupKeys);
                rangeDims.add(formGroupRangeDim);
                ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
                batchRunPara.setTaskKey(formSchemeDefine.getTaskKey());
                batchRunPara.setPeriod(period);
                batchRunPara.setReportDimensions(rangeDims);
                JSONObject envVariable = new JSONObject();
                envVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)new FormRejectExecuteParam(formKeys)));
                batchRunPara.setEnvVariables(envVariable);
                IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
                IBizObjectOperateResult operateResult = this.processQueryService.queryUnitState((IProcessRunPara)batchRunPara, businessKeyCollection);
                Iterable businessObjects = operateResult.getBusinessObjects();
                for (IBusinessObject businessObject : businessObjects) {
                    String dimUnitCode = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
                    String dimPeriod = businessObject.getDimensions().getPeriodDimensionValue().getValue().toString();
                    FormGroupObject formGroupObject = (FormGroupObject)businessObject;
                    IOperateResult result = operateResult.getResult((Object)businessObject);
                    IProcessStatus processStatus = (IProcessStatus)result.getResult();
                    if (processStatus == null || processStatus.getDataAccessStatus().equals((Object)IProcessStatus.DataAccessStatus.WRITEABLE)) continue;
                    WorkflowState workflowState = WorkflowUnitAccessConverter.transferWorkflowState(processStatus.getCode());
                    String accCode = (String)this.accessCodeCompute.apply(String.valueOf(workflowState.getValue()));
                    if (!this.getCodeList().contains(accCode)) continue;
                    List formKeysBelongToGroup = this.runTimeViewController.listFormByGroup(formGroupObject.getFormGroupKey(), formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                    ArrayList<String> tempFormKeys = new ArrayList<String>(formKeys);
                    tempFormKeys.retainAll(formKeysBelongToGroup);
                    DimensionValueSet dim = (DimensionValueSet)filterMap.get(dimUnitCode + dimPeriod);
                    Map valueMap = cacheMap.computeIfAbsent(dim, k -> new HashMap());
                    for (String formKey : tempFormKeys) {
                        valueMap.put(formKey, accCode);
                    }
                }
            }
            accessCache.setCacheMap(cacheMap);
        }
        return accessCache;
    }

    public String name() {
        return "upload-form";
    }

    public IAccessMessage getAccessMessage() {
        return code -> (String)this.noAccessReason.apply(code);
    }

    private AccessCode write(String formSchemeKey, DimensionValueSet masterKey, String formKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String entityId = WorkflowUnitAccessConverter.getEntityCaliber(formSchemeDefine);
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(masterKey, formSchemeDefine);
        String unitKey = dimensionValueSet.getValue(dimensionName).toString();
        String period = dimensionValueSet.getValue("DATATIME").toString();
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dimensionName);
        unitOneDim.setDimensionKey(entityId);
        unitOneDim.setDimensionValue(unitKey);
        oneDims.add(unitOneDim);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessOneDim formOneDim = new ProcessOneDim();
            formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionValue(formKey);
            oneDims.add(formOneDim);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            String formGroupKey = formKey;
            List formGroupDefines = this.runTimeViewController.listFormGroupByForm(formKey, formSchemeKey);
            if (formGroupDefines != null && !formGroupDefines.isEmpty()) {
                formGroupKey = ((FormGroupDefine)formGroupDefines.stream().findFirst().get()).getKey();
            }
            ProcessOneDim formGroupOneDim = new ProcessOneDim();
            formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionValue(formGroupKey);
            oneDims.add(formGroupOneDim);
        }
        ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
        oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        oneRunPara.setPeriod(period);
        oneRunPara.setReportDimensions(oneDims);
        JSONObject envVariable = new JSONObject();
        envVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)new FormRejectExecuteParam(formKey)));
        oneRunPara.setEnvVariables(envVariable);
        IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneRunPara);
        IProcessStatus processStatus = this.processQueryService.queryInstanceState((IProcessRunPara)oneRunPara, businessKey);
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
}

