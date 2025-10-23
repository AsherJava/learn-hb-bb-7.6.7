/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.logic.api.ICheckService
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryCol
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryCondition
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.logic.api.ICheckService;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.helper.CurrencyFilterCondition;
import com.jiuqi.nr.workflow2.events.helper.DimensionBuilderCondition;
import com.jiuqi.nr.workflow2.events.helper.UnitFilterCondition;
import com.jiuqi.nr.workflow2.events.monitor.ProcessReviewEventMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class CompleteReviewEventExecutor
extends AbstractActionEventExecutor {
    protected ICheckService checkService;
    protected EventDependentServiceHelper helper;
    private final AuditTypeDefineService auditTypeDefineService;
    protected static final String attr_key_auditCurrency = "auditCurrency";
    protected static final String attr_key_auditCurrency_type = "type";
    protected static final String attr_key_auditCurrency_currency = "currency";
    protected static final String attr_key_formulaScheme = "formulaScheme";
    protected static final String attr_key_errorHandle = "errorHandle";
    protected static final String attr_key_errorHandle_hint = "hint";
    protected static final String attr_key_errorHandle_warning = "warning";
    protected static final String attr_key_errorHandle_error = "error";

    public CompleteReviewEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
        this.checkService = helper.dataLogicServiceFactory.getCheckService();
        this.auditTypeDefineService = (AuditTypeDefineService)SpringBeanUtils.getBean(AuditTypeDefineService.class);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        if (actionArgs.getBoolean("FORCE_REPORT")) {
            operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5f00\u542f\u4e86\u5f3a\u5236\u4e0a\u62a5\uff0c\u4e0d\u6267\u884c\u5ba1\u6838\uff01\uff01"));
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        if (!this.isSuperiorCurrencyAndHasValue(envParam, businessKey)) {
            operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u4e0a\u7ea7\u672c\u4f4d\u5e01\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u8fdb\u884c\u5ba1\u6838\uff01\uff01"));
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        TaskDefine taskDefine = this.helper.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
        if (TaskGatherType.TASK_GATHER_AUTO == taskDefine.getTaskGatherType() && !this.isLeafNode((IProcessRunPara)envParam, businessKey)) {
            operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u81ea\u52a8\u6c47\u603b\u4efb\u52a1\uff0c\u975e\u53f6\u5b50\u8282\u70b9\u4e0d\u53c2\u4e0e\u5ba1\u6838\uff01\uff01"));
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        DimensionCollectionBuilder dimensionCollectionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKey, this.getDimensionBuilderCondition(envParam));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        List<String> checkRangeFormKeys = this.getCheckRangeFormKeys(envParam, businessKey);
        CheckParam checkParam = new CheckParam();
        checkParam.setMode(Mode.FORM);
        checkParam.setActionId(monitor.getAsyncTaskId());
        checkParam.setRangeKeys(checkRangeFormKeys);
        checkParam.setDimensionCollection(dimensionCollection);
        ProcessReviewEventMonitor checkMonitor = new ProcessReviewEventMonitor(monitor);
        List<String> checkFormulaSchemeKeys = this.getCheckFormulaSchemeKeys(envParam, this.eventJsonConfig);
        for (String formulaSchemeKey : checkFormulaSchemeKeys) {
            checkParam.setFormulaSchemeKey(formulaSchemeKey);
            this.checkService.allCheck(checkParam);
        }
        CheckResultQueryParam checkResultQueryParam = this.getCheckResultQueryParam(dimensionCollection, checkFormulaSchemeKeys, checkRangeFormKeys, this.eventJsonConfig);
        CheckResult checkResult = this.helper.checkResultService.queryAllCheckResult(checkResultQueryParam, monitor.getAsyncTaskId());
        WFMonitorCheckResult wfMonitorCheckResult = checkResult.getTotalCount() > 0 ? WFMonitorCheckResult.CHECK_UN_PASS : WFMonitorCheckResult.CHECK_PASS;
        operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(wfMonitorCheckResult, "check"));
        EventExecutionStatus finishedStatus = WFMonitorCheckResult.CHECK_PASS == wfMonitorCheckResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishedStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        block13: {
            HashMap errorUnitMapForms;
            WorkflowObjectType flowObjectType;
            IBusinessObjectCollection businessObjects;
            ProcessExecuteEnv envParam;
            block14: {
                CheckResultQueryParam checkResultQueryParam;
                block12: {
                    if (actionArgs.getBoolean("FORCE_REPORT")) {
                        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
                    }
                    if (businessKeyCollection.getBusinessObjects().size() == 0) {
                        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
                    }
                    envParam = this.getEnvParam(actionArgs);
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
                    businessObjects = businessKeyCollection.getBusinessObjects();
                    flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKeyCollection.getTask());
                    checkResultQueryParam = this.getCheckResultQueryParam(dimensionCollection, checkFormulaSchemeKeys, checkRangeFormKeys, this.eventJsonConfig);
                    checkResultQueryParam.setBatchId(monitor.getAsyncTaskId());
                    if (WorkflowObjectType.MAIN_DIMENSION != flowObjectType && WorkflowObjectType.MD_WITH_SFR != flowObjectType) break block12;
                    checkResultQueryParam.setGroupType(GroupType.unit);
                    CheckResultGroup checkResultGroup = this.helper.checkResultService.queryBatchCheckResultGroup(checkResultQueryParam);
                    List groupData = checkResultGroup.getGroupData();
                    List errorUnitKeyList = groupData.stream().map(CheckResultGroupData::getKey).collect(Collectors.toList());
                    for (IBusinessObject businessObject : businessObjects) {
                        if (errorUnitKeyList.contains(businessObject.getDimensions().getDWDimensionValue().getValue().toString())) {
                            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, "\u5ba1\u6838\u4e0d\u901a\u8fc7"));
                            continue;
                        }
                        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5ba1\u6838\u901a\u8fc7"));
                    }
                    break block13;
                }
                checkResultQueryParam.setGroupType(GroupType.UNIT_FORM);
                CheckResultGroup checkResultGroup = this.helper.checkResultService.queryBatchCheckResultGroup(checkResultQueryParam);
                List groupData = checkResultGroup.getGroupData();
                errorUnitMapForms = new HashMap();
                for (CheckResultGroupData checkResultGroupData : groupData) {
                    errorUnitMapForms.put(checkResultGroupData.getKey(), checkResultGroupData.getChildren().stream().map(CheckResultGroupData::getKey).collect(Collectors.toSet()));
                }
                if (WorkflowObjectType.FORM != flowObjectType) break block14;
                for (IBusinessObject businessObject : businessObjects) {
                    String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
                    IFormObject formObject = (IFormObject)businessObject;
                    if (errorUnitMapForms.containsKey(unitId) && ((Set)errorUnitMapForms.get(unitId)).contains(formObject.getFormKey())) {
                        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, "\u5ba1\u6838\u4e0d\u901a\u8fc7"));
                        continue;
                    }
                    operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5ba1\u6838\u901a\u8fc7"));
                }
                break block13;
            }
            if (WorkflowObjectType.FORM_GROUP != flowObjectType) break block13;
            HashMap groupToFormMap = new HashMap();
            List formGroupDefines = this.helper.runtimeParamHelper.listFormGroupByFormScheme(envParam.getTaskKey(), envParam.getPeriod());
            for (FormGroupDefine formGroupDefine : formGroupDefines) {
                List formDefines = this.helper.runTimeViewController.listFormByGroup(formGroupDefine.getKey(), formGroupDefine.getFormSchemeKey());
                groupToFormMap.put(formGroupDefine.getKey(), formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
            }
            for (IBusinessObject businessObject : businessObjects) {
                String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
                if (errorUnitMapForms.containsKey(unitId)) {
                    Set errorFormIds = (Set)errorUnitMapForms.get(unitId);
                    IFormGroupObject formGroupObject = (IFormGroupObject)businessObject;
                    Set groupFormIds = (Set)groupToFormMap.get(formGroupObject.getFormGroupKey());
                    if (groupFormIds.stream().anyMatch(errorFormIds::contains)) {
                        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, "\u5ba1\u6838\u4e0d\u901a\u8fc7"));
                        continue;
                    }
                    operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5ba1\u6838\u901a\u8fc7"));
                    continue;
                }
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5ba1\u6838\u901a\u8fc7"));
            }
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    protected boolean isSuperiorCurrencyAndHasValue(ProcessExecuteEnv envParam, IBusinessKey businessKey) throws Exception {
        CurrencyType auditCurrencyType = this.getAuditCurrencyType(this.eventJsonConfig);
        if (CurrencyType.SUPERIOR == auditCurrencyType) {
            FixedDimensionValue dwDimensionValue = businessKey.getBusinessObject().getDimensions().getDWDimensionValue();
            FixedDimensionValue periodDimensionValue = businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue(dwDimensionValue.getName(), dwDimensionValue.getValue());
            dimensionValueSet.setValue(periodDimensionValue.getName(), periodDimensionValue.getValue());
            IEntityTable entityTable = this.helper.entityQueryHelper.getIEntityTable(envParam.getTaskKey(), envParam.getPeriod(), dimensionValueSet);
            IEntityRow entityRow = entityTable.findByEntityKey(dwDimensionValue.getValue().toString());
            dimensionValueSet.setValue(dwDimensionValue.getName(), (Object)entityRow.getParentEntityKey());
            entityTable = this.helper.entityQueryHelper.getIEntityTable(envParam.getTaskKey(), envParam.getPeriod(), dimensionValueSet);
            IEntityRow parentRow = entityTable.findByEntityKey(entityRow.getParentEntityKey());
            if (parentRow == null) {
                return false;
            }
            String parentCurrency = parentRow.getAsString("CURRENCYID");
            return StringUtils.isNotEmpty((String)parentCurrency);
        }
        return true;
    }

    protected DimensionBuilderCondition getDimensionBuilderCondition(ProcessExecuteEnv envParam) {
        DimensionBuilderCondition builderCondition = new DimensionBuilderCondition();
        CurrencyType currencyType = this.getAuditCurrencyType(this.eventJsonConfig);
        List<String> customCurrencies = this.getAuditCurrencyCustomValues(this.eventJsonConfig);
        boolean isMdCurrencyReferEntity = this.helper.dimensionHelper.isMdCurrencyReferEntity(envParam.getTaskKey(), envParam.getPeriod());
        CurrencyFilterCondition currencyFilterCondition = new CurrencyFilterCondition();
        currencyFilterCondition.setCurrencyType(currencyType);
        currencyFilterCondition.setMdCurrencyReferEntity(isMdCurrencyReferEntity);
        currencyFilterCondition.setCustomCurrency(customCurrencies);
        builderCondition.setCurrencyFilterCondition(currencyFilterCondition);
        TaskDefine taskDefine = this.helper.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
        UnitFilterCondition unitFilterCondition = new UnitFilterCondition();
        unitFilterCondition.setFilterType(TaskGatherType.TASK_GATHER_AUTO == taskDefine.getTaskGatherType() ? UnitFilterCondition.FilterType.by_leaf_node : UnitFilterCondition.FilterType.by_self);
        builderCondition.setUnitFilterCondition(unitFilterCondition);
        return builderCondition;
    }

    protected List<String> getCheckRangeFormKeys(ProcessExecuteEnv envParam, IBusinessKey businessKey) {
        return this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKey);
    }

    protected List<String> getCheckRangeFormKeys(ProcessExecuteEnv envParam, IBusinessKeyCollection businessKeyCollection) {
        return this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKeyCollection);
    }

    protected CheckResultQueryParam getCheckResultQueryParam(DimensionCollection dimensionCollection, List<String> checkFormulaSchemeKeys, List<String> checkRangeFormKeys, JSONObject eventConfig) throws Exception {
        List<Integer> writeCkdAuditList = this.getWriteCkdAuditList(eventConfig);
        List<Integer> mustPassAuditList = this.getMustPassAuditList(eventConfig);
        QueryCondition queryCondition = CompleteReviewEventExecutor.getQueryCondition(writeCkdAuditList, mustPassAuditList);
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        checkResultQueryParam.setQueryByDim(true);
        checkResultQueryParam.setFormulaSchemeKeys(checkFormulaSchemeKeys);
        checkResultQueryParam.setMode(Mode.FORM);
        checkResultQueryParam.setRangeKeys(checkRangeFormKeys);
        checkResultQueryParam.setQueryCondition(queryCondition);
        return checkResultQueryParam;
    }

    protected CurrencyType getAuditCurrencyType(JSONObject eventConfig) {
        JSONObject auditCurrency;
        if (eventConfig.has(attr_key_auditCurrency) && (auditCurrency = eventConfig.getJSONObject(attr_key_auditCurrency)).has(attr_key_auditCurrency_type)) {
            return CurrencyType.valueOf(auditCurrency.getString(attr_key_auditCurrency_type));
        }
        return null;
    }

    protected List<String> getAuditCurrencyCustomValues(JSONObject eventConfig) {
        JSONObject auditCurrency;
        ArrayList<String> auditCurrencyCustomValues = new ArrayList<String>();
        if (eventConfig.has(attr_key_auditCurrency) && (auditCurrency = eventConfig.getJSONObject(attr_key_auditCurrency)).has(attr_key_auditCurrency_currency)) {
            JSONArray currencyJSONArray = auditCurrency.getJSONArray(attr_key_auditCurrency_currency);
            for (int i = 0; i < currencyJSONArray.length(); ++i) {
                auditCurrencyCustomValues.add(currencyJSONArray.getString(i));
            }
        }
        return auditCurrencyCustomValues;
    }

    protected List<String> getCheckFormulaSchemeKeys(ProcessExecuteEnv envParam, JSONObject eventConfig) {
        List formulaSchemeDefines = this.runtimeParamHelper.listAllFormulaSchemeByFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        ArrayList<String> checkFormulaSchemeKeys = new ArrayList<String>();
        if (formulaSchemeDefines != null && !formulaSchemeDefines.isEmpty()) {
            List formulaSchemes = formulaSchemeDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            if (eventConfig.has(attr_key_formulaScheme)) {
                JSONArray jsonArray = eventConfig.getJSONArray(attr_key_formulaScheme);
                for (int i = 0; i < jsonArray.length(); ++i) {
                    String formulaSchemeKey = jsonArray.getString(i);
                    if (!formulaSchemes.contains(formulaSchemeKey)) continue;
                    checkFormulaSchemeKeys.add(jsonArray.getString(i));
                }
            }
        }
        return checkFormulaSchemeKeys;
    }

    protected List<Integer> getWriteCkdAuditList(JSONObject eventConfig) {
        ArrayList<Integer> writeCkdAuditList;
        block8: {
            writeCkdAuditList = new ArrayList<Integer>();
            if (eventConfig.has(attr_key_errorHandle)) {
                List auditTypes;
                try {
                    auditTypes = this.auditTypeDefineService.queryAllAuditType();
                }
                catch (Exception exception) {
                    LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
                    throw new RuntimeException(exception);
                }
                try {
                    Map<String, String> errorTypeValueMap = eventConfig.getJSONArray(attr_key_errorHandle).toList().stream().map(e -> (HashMap)e).collect(Collectors.toMap(e -> (String)e.get("code"), e -> (String)e.get("value"), (v1, v2) -> v1));
                    for (AuditType auditType : auditTypes) {
                        ProcessCheckType checkType;
                        Integer auditTypeCode = auditType.getCode();
                        String value = errorTypeValueMap.get(String.valueOf(auditTypeCode));
                        if (value == null || !ProcessCheckType.DESC_REQUIRED.equals((Object)(checkType = ProcessCheckType.translate(value)))) continue;
                        writeCkdAuditList.add(auditTypeCode);
                    }
                }
                catch (JSONException e2) {
                    ProcessCheckType errorType;
                    ProcessCheckType warningType;
                    JSONObject checkTypeConfig = eventConfig.getJSONObject(attr_key_errorHandle);
                    ProcessCheckType hintType = ProcessCheckType.translate(checkTypeConfig.getString(attr_key_errorHandle_hint));
                    if (ProcessCheckType.DESC_REQUIRED.equals((Object)hintType)) {
                        writeCkdAuditList.add(1);
                    }
                    if (ProcessCheckType.DESC_REQUIRED.equals((Object)(warningType = ProcessCheckType.translate(checkTypeConfig.getString(attr_key_errorHandle_warning))))) {
                        writeCkdAuditList.add(2);
                    }
                    if (!ProcessCheckType.DESC_REQUIRED.equals((Object)(errorType = ProcessCheckType.translate(checkTypeConfig.getString(attr_key_errorHandle_error))))) break block8;
                    writeCkdAuditList.add(4);
                }
            }
        }
        return writeCkdAuditList;
    }

    protected List<Integer> getMustPassAuditList(JSONObject eventConfig) {
        ArrayList<Integer> mustPassAuditList;
        block8: {
            mustPassAuditList = new ArrayList<Integer>();
            if (eventConfig.has(attr_key_errorHandle)) {
                List auditTypes;
                try {
                    auditTypes = this.auditTypeDefineService.queryAllAuditType();
                }
                catch (Exception exception) {
                    LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
                    throw new RuntimeException(exception);
                }
                try {
                    Map<String, String> errorTypeValueMap = eventConfig.getJSONArray(attr_key_errorHandle).toList().stream().map(e -> (HashMap)e).collect(Collectors.toMap(e -> (String)e.get("code"), e -> (String)e.get("value"), (v1, v2) -> v1));
                    for (AuditType auditType : auditTypes) {
                        ProcessCheckType checkType;
                        Integer auditTypeCode = auditType.getCode();
                        String value = errorTypeValueMap.get(String.valueOf(auditTypeCode));
                        if (value == null || !ProcessCheckType.MUST_APPROVED.equals((Object)(checkType = ProcessCheckType.translate(value)))) continue;
                        mustPassAuditList.add(auditTypeCode);
                    }
                }
                catch (JSONException e2) {
                    ProcessCheckType errorType;
                    ProcessCheckType warningType;
                    JSONObject checkTypeConfig = eventConfig.getJSONObject(attr_key_errorHandle);
                    ProcessCheckType hintType = ProcessCheckType.translate(checkTypeConfig.getString(attr_key_errorHandle_hint));
                    if (ProcessCheckType.MUST_APPROVED.equals((Object)hintType)) {
                        mustPassAuditList.add(1);
                    }
                    if (ProcessCheckType.MUST_APPROVED.equals((Object)(warningType = ProcessCheckType.translate(checkTypeConfig.getString(attr_key_errorHandle_warning))))) {
                        mustPassAuditList.add(2);
                    }
                    if (!ProcessCheckType.MUST_APPROVED.equals((Object)(errorType = ProcessCheckType.translate(checkTypeConfig.getString(attr_key_errorHandle_error))))) break block8;
                    mustPassAuditList.add(4);
                }
            }
        }
        return mustPassAuditList;
    }

    private static QueryCondition getQueryCondition(List<Integer> writeCkdAuditList, List<Integer> mustPassAuditList) {
        DefaultQueryFilter queryCondition;
        int writeSize = writeCkdAuditList == null ? 0 : writeCkdAuditList.size();
        int passSize = mustPassAuditList == null ? 0 : mustPassAuditList.size();
        QueryConditionBuilder queryConditionBuilder = null;
        if (writeSize == 0) {
            if (passSize > 1) {
                queryConditionBuilder = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.IN, mustPassAuditList);
            } else if (passSize == 1) {
                queryConditionBuilder = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)mustPassAuditList.get(0));
            }
            queryCondition = queryConditionBuilder == null ? DefaultQueryFilter.NO_FILTER : queryConditionBuilder.build();
        } else {
            for (Integer writeCkdAuditCode : writeCkdAuditList) {
                QueryConditionBuilder subQuery = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)writeCkdAuditCode);
                subQuery.andSubQuery((QueryCondition)DefaultQueryFilter.DES_IS_NULL);
                if (queryConditionBuilder == null) {
                    queryConditionBuilder = new QueryConditionBuilder(subQuery.build());
                    continue;
                }
                queryConditionBuilder.orSubQuery(subQuery.build());
            }
            if (passSize > 1) {
                queryConditionBuilder.or(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.IN, mustPassAuditList);
            } else if (passSize == 1) {
                queryConditionBuilder.or(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)mustPassAuditList.get(0));
            }
            queryCondition = queryConditionBuilder.build();
        }
        return queryCondition;
    }

    protected static enum ProcessCheckType {
        IGNORE(0),
        DESC_REQUIRED(1),
        MUST_APPROVED(2);

        public final int value;

        private ProcessCheckType(int value) {
            this.value = value;
        }

        public static ProcessCheckType translate(int value) {
            for (ProcessCheckType type : ProcessCheckType.values()) {
                if (type.value != value) continue;
                return type;
            }
            return null;
        }

        public static ProcessCheckType translate(String intValue) {
            return ProcessCheckType.translate(Integer.parseInt(intValue));
        }
    }
}

