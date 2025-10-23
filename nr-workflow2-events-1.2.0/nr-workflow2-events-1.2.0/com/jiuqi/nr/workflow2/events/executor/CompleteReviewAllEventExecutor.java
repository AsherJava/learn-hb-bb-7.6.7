/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import com.jiuqi.nr.workflow2.events.executor.CompleteReviewEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.monitor.ProcessReviewEventMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class CompleteReviewAllEventExecutor
extends CompleteReviewEventExecutor {
    private boolean checkAllFormOrGroup = false;
    protected static final String CHECK_ALL = "check-all-form-or-groups";
    protected static final String CHECK_PART = "check-part-form-or-groups";

    public CompleteReviewAllEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig, helper);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        if (actionArgs.getBoolean("FORCE_REPORT")) {
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKeyCollection.getTask());
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            return this.groupCheckExecution(monitor, operateResultSet, actionArgs, businessKeyCollection);
        }
        if (WorkflowObjectType.FORM == flowObjectType) {
            return this.formCheckExecution(monitor, operateResultSet, actionArgs, businessKeyCollection);
        }
        return super.executionEvent(monitor, operateResultSet, actionArgs, businessKeyCollection);
    }

    public IEventFinishedResult groupCheckExecution(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection executionCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(executionCollection.getTask());
        List<String> executionGroupKeys = this.helper.eventExecuteDimensionBuilder.getProcessRangeGroupKeys(executionCollection);
        DimensionCollection dimensionCollection = executionCollection.getBusinessObjects().getDimensions();
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        List formGroupDefines = this.helper.runtimeParamHelper.listFormGroupByFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        List groupKeys = formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        IDimensionObjectMapping dimensionObjectMapping = this.helper.processDimensionsBuilder.processDimToGroupDefinesMap(formScheme, dimensionCollection, groupKeys);
        BusinessKeyCollection queryCollection = new BusinessKeyCollection(envParam.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormGroupObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
        Map<String, IBusinessKeyCollection> businessKeyCollectionMap = this.splitExecutionCollection(envParam, (IBusinessKeyCollection)queryCollection, executionCollection, executionGroupKeys, dimensionObjectMapping, flowObjectType);
        super.executionEvent(monitor, operateResultSet, actionArgs, businessKeyCollectionMap.get(CHECK_PART));
        this.checkAllFormOrGroup = true;
        this.executionAllCheck(monitor, operateResultSet, actionArgs, businessKeyCollectionMap.get(CHECK_ALL));
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult formCheckExecution(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection executionCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(executionCollection.getTask());
        List<String> executionFormKeys = this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(executionCollection);
        DimensionCollection dimensionCollection = executionCollection.getBusinessObjects().getDimensions();
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        List formDefines = this.helper.runtimeParamHelper.listFormByFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        List formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        IDimensionObjectMapping dimensionObjectMapping = this.helper.processDimensionsBuilder.processDimToFormDefinesMap(formScheme, dimensionCollection, formKeys);
        BusinessKeyCollection queryCollection = new BusinessKeyCollection(envParam.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
        Map<String, IBusinessKeyCollection> businessKeyCollectionMap = this.splitExecutionCollection(envParam, (IBusinessKeyCollection)queryCollection, executionCollection, executionFormKeys, dimensionObjectMapping, flowObjectType);
        super.executionEvent(monitor, operateResultSet, actionArgs, businessKeyCollectionMap.get(CHECK_PART));
        this.checkAllFormOrGroup = true;
        this.executionAllCheck(monitor, operateResultSet, actionArgs, businessKeyCollectionMap.get(CHECK_ALL));
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionAllCheck(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        if (actionArgs.getBoolean("FORCE_REPORT")) {
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        if (businessKeyCollection.getBusinessObjects().size() == 0) {
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        DimensionCollectionBuilder dimensionCollectionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKeyCollection, this.getDimensionBuilderCondition(envParam));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        List<String> checkRangeFormKeys = this.getCheckRangeFormKeys(envParam, businessKeyCollection);
        CheckParam checkParam = new CheckParam();
        checkParam.setMode(Mode.FORM);
        checkParam.setActionId(monitor.getAsyncTaskId());
        checkParam.setRangeKeys(checkRangeFormKeys);
        checkParam.setDimensionCollection(dimensionCollection);
        ProcessReviewEventMonitor checkMonitor = new ProcessReviewEventMonitor(monitor);
        List<String> checkFormulaSchemeKeys = this.getCheckFormulaSchemeKeys(envParam, this.eventJsonConfig);
        for (String formulaSchemeKey : checkFormulaSchemeKeys) {
            checkParam.setFormulaSchemeKey(formulaSchemeKey);
            this.checkService.batchCheck(checkParam);
        }
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        CheckResultQueryParam checkResultQueryParam = this.getCheckResultQueryParam(dimensionCollection, checkFormulaSchemeKeys, checkRangeFormKeys, this.eventJsonConfig);
        checkResultQueryParam.setBatchId(monitor.getAsyncTaskId());
        checkResultQueryParam.setGroupType(GroupType.UNIT_FORM);
        CheckResultGroup checkResultGroup = this.helper.checkResultService.queryBatchCheckResultGroup(checkResultQueryParam);
        List groupData = checkResultGroup.getGroupData();
        HashMap errorUnitMapForms = new HashMap();
        for (CheckResultGroupData checkResultGroupData : groupData) {
            errorUnitMapForms.put(checkResultGroupData.getKey(), checkResultGroupData.getChildren().stream().map(CheckResultGroupData::getKey).collect(Collectors.toSet()));
        }
        for (IBusinessObject businessObject : businessObjects) {
            String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            if (errorUnitMapForms.containsKey(unitId)) {
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, "\u5ba1\u6838\u4e0d\u901a\u8fc7"));
                continue;
            }
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5ba1\u6838\u901a\u8fc7"));
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    protected Map<String, IBusinessKeyCollection> splitExecutionCollection(ProcessExecuteEnv envParam, IBusinessKeyCollection queryCollection, IBusinessKeyCollection executionCollection, List<String> executionFormOrGroupKeys, IDimensionObjectMapping dimensionObjectMapping, WorkflowObjectType flowObjectType) {
        HashMap<DimensionCombination, Set> combinationSetMap = new HashMap<DimensionCombination, Set>();
        IBizObjectOperateResult operateResult = this.helper.processQueryService.queryInstanceState((IProcessRunPara)envParam, queryCollection);
        Iterable stateObjects = operateResult.getBusinessObjects();
        for (IBusinessObject stateObject : stateObjects) {
            IFormGroupObject formGroupObject;
            IOperateResult result = operateResult.getResult((Object)stateObject);
            Set set = combinationSetMap.computeIfAbsent(stateObject.getDimensions(), k -> new HashSet());
            if (!result.isSuccessful() || ((IProcessStatus)result.getResult()).getDataReportStatus() != IProcessStatus.DataReportStatus.REPORTED) continue;
            if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
                formGroupObject = (IFormGroupObject)stateObject;
                set.add(formGroupObject.getFormGroupKey());
                continue;
            }
            if (WorkflowObjectType.FORM != flowObjectType) continue;
            formGroupObject = (IFormObject)stateObject;
            set.add(formGroupObject.getFormKey());
        }
        HashMap combinationBooleanMap = new HashMap();
        int operateCount = executionFormOrGroupKeys.size();
        for (Map.Entry entry : combinationSetMap.entrySet()) {
            int uploadCount = ((Set)entry.getValue()).size();
            int totalCount = dimensionObjectMapping.getObject((DimensionCombination)entry.getKey()).size();
            combinationBooleanMap.put(entry.getKey(), uploadCount + operateCount >= totalCount);
        }
        ArrayList<IBusinessObject> checkAllObjects = new ArrayList<IBusinessObject>();
        ArrayList<IBusinessObject> arrayList = new ArrayList<IBusinessObject>();
        IBusinessObjectCollection businessObjects = executionCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            Boolean isCheckAllGroup = (Boolean)combinationBooleanMap.get(businessObject.getDimensions());
            if (isCheckAllGroup != null && isCheckAllGroup.booleanValue()) {
                checkAllObjects.add(businessObject);
                continue;
            }
            arrayList.add(businessObject);
        }
        HashMap<String, IBusinessKeyCollection> executionCollectionMap = new HashMap<String, IBusinessKeyCollection>();
        executionCollectionMap.put(CHECK_ALL, (IBusinessKeyCollection)new OperateBusinessKeyCollection(envParam.getTaskKey(), checkAllObjects));
        executionCollectionMap.put(CHECK_PART, (IBusinessKeyCollection)new OperateBusinessKeyCollection(envParam.getTaskKey(), arrayList));
        return executionCollectionMap;
    }

    @Override
    protected List<String> getCheckRangeFormKeys(ProcessExecuteEnv envParam, IBusinessKey businessKey) {
        List<String> checkRangeFormKeys = super.getCheckRangeFormKeys(envParam, businessKey);
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKey.getTask());
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
            ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(businessKey.getBusinessObject().getDimensions());
            List formGroupDefines = this.helper.runtimeParamHelper.listFormGroupByFormScheme(envParam.getTaskKey(), envParam.getPeriod());
            List groupKeys = formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            IDimensionObjectMapping dimensionObjectMapping = this.helper.processDimensionsBuilder.processDimToGroupDefinesMap(formScheme, (DimensionCollection)dimensionCollection, groupKeys);
            BusinessKeyCollection businessKeyCollection = new BusinessKeyCollection(envParam.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormGroupObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
            if (this.isLastReport(envParam, (IBusinessKeyCollection)businessKeyCollection)) {
                return new ArrayList<String>();
            }
        } else if (WorkflowObjectType.FORM == flowObjectType) {
            FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
            ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(businessKey.getBusinessObject().getDimensions());
            List formDefines = this.helper.runtimeParamHelper.listFormByFormScheme(envParam.getTaskKey(), envParam.getPeriod());
            List formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            IDimensionObjectMapping dimensionObjectMapping = this.helper.processDimensionsBuilder.processDimToFormDefinesMap(formScheme, (DimensionCollection)dimensionCollection, formKeys);
            BusinessKeyCollection businessKeyCollection = new BusinessKeyCollection(envParam.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
            if (this.isLastReport(envParam, (IBusinessKeyCollection)businessKeyCollection)) {
                return new ArrayList<String>();
            }
        }
        return checkRangeFormKeys;
    }

    @Override
    protected List<String> getCheckRangeFormKeys(ProcessExecuteEnv envParam, IBusinessKeyCollection businessKeyCollection) {
        if (this.checkAllFormOrGroup) {
            return new ArrayList<String>();
        }
        return super.getCheckRangeFormKeys(envParam, businessKeyCollection);
    }

    protected boolean isLastReport(ProcessExecuteEnv envParam, IBusinessKeyCollection businessKeyCollection) {
        IBizObjectOperateResult operateResult = this.helper.processQueryService.queryInstanceState((IProcessRunPara)envParam, businessKeyCollection);
        Iterable businessObjects = operateResult.getBusinessObjects();
        int uploadGroupCount = 0;
        int totalGroupCount = 0;
        for (IBusinessObject businessObject : businessObjects) {
            IOperateResult result = operateResult.getResult((Object)businessObject);
            if (result.isSuccessful() && ((IProcessStatus)result.getResult()).getDataReportStatus() == IProcessStatus.DataReportStatus.REPORTED) {
                ++uploadGroupCount;
            }
            ++totalGroupCount;
        }
        return uploadGroupCount + 1 == totalGroupCount;
    }
}

