/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.helper.IProcessAuthJudgeHelper
 *  com.jiuqi.nr.workflow2.service.helper.ProcessAuthJudgeHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.response.StepByStepUploadItem;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.helper.IProcessAuthJudgeHelper;
import com.jiuqi.nr.workflow2.service.helper.ProcessAuthJudgeHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class StepByStepRejectEventExecutor
extends AbstractActionEventExecutor {
    protected IProcessAuthJudgeHelper authJudgeHelper;
    protected EventDependentServiceHelper helper;
    protected FormSchemeDefine currFormSchemeDefine;

    public StepByStepRejectEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
        this.authJudgeHelper = (IProcessAuthJudgeHelper)SpringBeanUtils.getBean(ProcessAuthJudgeHelper.class);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        this.currFormSchemeDefine = this.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        IBusinessObject businessObject = businessKey.getBusinessObject();
        WorkflowObjectType flowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(businessKey.getTask());
        IEntityDefine entityDefine = this.runtimeParamHelper.getProcessEntityDefinition(businessKey.getTask());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine, AuthorityType.None);
        operateResultSet.setLevel(businessObject, this.getBusinessObjectLevel(businessObject, entityTable));
        List<StepByStepUploadItem> uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)envParam, operateResultSet, flowObjectType, businessObject, entityTable);
        WFMonitorCheckResult checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
        Map<String, Object> checkResultDetail = this.getCheckResultDetail(envParam, businessKey);
        operateResultSet.setOperateResult(checkResultDetail);
        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
        EventExecutionStatus finishStatus = WFMonitorCheckResult.CHECK_PASS == checkResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK, CompleteDependentType.PARENT_EXECUTION_FIRST);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        this.currFormSchemeDefine = this.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        IEntityDefine entityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKeyCollection.getTask());
        IPeriodEntity periodEntityDefine = this.helper.runtimeParamHelper.getPeriodEntityDefine(businessKeyCollection.getTask());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine, AuthorityType.None);
        WorkflowObjectType flowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(businessKeyCollection.getTask());
        this.executionEvent(envParam, businessKeyCollection, entityTable, flowObjectType, operateResultSet, monitor, entityDefine, periodEntityDefine);
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK, CompleteDependentType.PARENT_EXECUTION_FIRST);
    }

    protected void executionEvent(ProcessExecuteEnv envParam, IBusinessKeyCollection businessKeyCollection, IEntityTable entityTable, WorkflowObjectType flowObjectType, IOperateResultSet operateResultSet, IProcessAsyncMonitor monitor, IEntityDefine entityDefine, IPeriodEntity periodEntityDefine) throws Exception {
        this.updateLevelAndParentRowIndex(businessKeyCollection, entityTable, operateResultSet, flowObjectType);
        Map level2RowIndexes = operateResultSet.getLevel2RowIndexes();
        List<Integer> sortLevelByAsc = this.getSortLevelList(level2RowIndexes.keySet());
        for (Integer level : sortLevelByAsc) {
            Set rowIndexes = (Set)level2RowIndexes.get(level);
            for (Integer rowIndex : rowIndexes) {
                IBusinessObject businessObject = operateResultSet.findBusinessObject(rowIndex);
                if (!operateResultSet.hasCheckPassed(businessObject)) continue;
                List<StepByStepUploadItem> uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)envParam, operateResultSet, flowObjectType, businessObject, entityTable);
                WFMonitorCheckResult checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
            }
        }
    }

    protected void updateLevelAndParentRowIndex(IBusinessKeyCollection businessKeyCollection, IEntityTable entityTable, IOperateResultSet operateResultSet, WorkflowObjectType flowObjectType) {
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            operateResultSet.setLevel(businessObject, this.getBusinessObjectLevel(businessObject, entityTable));
            IEntityRow entityRow = entityTable.findByEntityKey(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
            IEntityRow parentRow = entityTable.findByEntityKey(entityRow.getParentEntityKey());
            if (parentRow == null) continue;
            IBusinessObject parentBusinessObject = this.helper.eventExecuteDimensionBuilder.toBusinessObject(businessObject, parentRow, flowObjectType);
            operateResultSet.setParentRowIndex(businessObject, operateResultSet.findBusinessObjectIndex(parentBusinessObject));
        }
    }

    protected List<StepByStepUploadItem> getBusinessObjectCheckResult(IProcessRunPara runEnvPara, IOperateResultSet operateResultSet, WorkflowObjectType flowObjectType, IBusinessObject businessObject, IEntityTable entityTable) throws Exception {
        ArrayList<StepByStepUploadItem> checkErrorItems = new ArrayList<StepByStepUploadItem>();
        IEntityRow entityRow = entityTable.findByEntityKey(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
        IEntityRow parentEntityRow = entityTable.findByEntityKey(entityRow.getParentEntityKey());
        IBusinessObject parentBusinessObject = this.getParentBusinessObject(runEnvPara, businessObject, parentEntityRow, flowObjectType);
        if (parentBusinessObject == null) {
            return checkErrorItems;
        }
        BusinessKey parentBusinessKey = new BusinessKey(runEnvPara.getTaskKey(), parentBusinessObject);
        IProcessStatus parentStatus = this.helper.processQueryService.queryInstanceState(runEnvPara, (IBusinessKey)parentBusinessKey);
        if (parentStatus == null) {
            return checkErrorItems;
        }
        if (operateResultSet.containsBusinessObject(parentBusinessObject)) {
            if (!operateResultSet.hasCheckPassed(parentBusinessObject)) {
                if (IProcessStatus.DataReportStatus.UNREPORTED == parentStatus.getDataReportStatus()) {
                    return checkErrorItems;
                }
                checkErrorItems.add(this.buildCheckItemInfo(parentEntityRow, "", "\u4e0a\u7ea7\u672a\u9000\u56de\uff0c\u672c\u7ea7\u4e0d\u53ef\u9000\u56de\uff01\uff01"));
            }
            return checkErrorItems;
        }
        if (IProcessStatus.DataReportStatus.REPORTED == parentStatus.getDataReportStatus() || IProcessStatus.DataReportStatus.CONFIRMED == parentStatus.getDataReportStatus()) {
            checkErrorItems.add(this.buildCheckItemInfo(parentEntityRow, parentStatus.getAlias(), "\u4e0a\u7ea7\u672a\u9000\u56de\uff0c\u672c\u7ea7\u4e0d\u53ef\u9000\u56de\uff01\uff01"));
            return checkErrorItems;
        }
        return checkErrorItems;
    }

    protected Map<String, Object> getCheckResultDetail(ProcessExecuteEnv runEnvPara, IBusinessKey businessKey) {
        IProcessInstance instance = this.helper.processQueryService.queryInstances((IProcessRunPara)runEnvPara, businessKey);
        IUserAction userAction = this.helper.processMetaDataService.queryAction(runEnvPara.getTaskKey(), instance.getCurrentUserTask(), runEnvPara.getActionCode());
        HashMap<String, Object> checkResultDetail = new HashMap<String, Object>();
        checkResultDetail.put("userActionAlias", userAction.getAlias());
        return checkResultDetail;
    }

    protected List<Integer> getSortLevelList(Set<Integer> levels) {
        ArrayList<Integer> sortLevelByDesc = new ArrayList<Integer>(levels);
        sortLevelByDesc.sort(Comparator.naturalOrder());
        return sortLevelByDesc;
    }

    protected StepByStepUploadItem buildCheckItemInfo(IEntityRow entityRow, String workflowState, String detailMessage) {
        StepByStepUploadItem checkItemInfo = new StepByStepUploadItem();
        checkItemInfo.setUnitId(entityRow.getEntityKeyData());
        checkItemInfo.setUnitCode(entityRow.getCode());
        checkItemInfo.setUnitTitle(entityRow.getTitle());
        checkItemInfo.setWorkflowState(workflowState);
        checkItemInfo.setDetailMessage(detailMessage);
        return checkItemInfo;
    }

    protected IBusinessObject getParentBusinessObject(IProcessRunPara runEnvPara, IBusinessObject businessObject, IEntityRow parentEntityRow, WorkflowObjectType flowObjectType) throws Exception {
        if (parentEntityRow == null) {
            return null;
        }
        IBusinessObject parentBusinessObject = this.helper.eventExecuteDimensionBuilder.toBusinessObject(businessObject, parentEntityRow, flowObjectType);
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            IFormGroupObject formGroupObject = (IFormGroupObject)parentBusinessObject;
            ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(formGroupObject.getDimensions());
            ArrayList<String> groupKeys = new ArrayList<String>();
            groupKeys.add(formGroupObject.getFormGroupKey());
            IDimensionObjectMapping dimensionObjectMapping = this.helper.processDimensionsBuilder.processDimToGroupConditionMap(this.currFormSchemeDefine, (DimensionCollection)dimensionCollection, groupKeys);
            if (dimensionObjectMapping.getObject(formGroupObject.getDimensions()).contains(formGroupObject.getFormGroupKey())) {
                return parentBusinessObject;
            }
            return null;
        }
        if (WorkflowObjectType.FORM == flowObjectType) {
            IFormObject formObject = (IFormObject)parentBusinessObject;
            ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(formObject.getDimensions());
            ArrayList<String> formKeys = new ArrayList<String>();
            formKeys.add(formObject.getFormKey());
            IDimensionObjectMapping dimensionObjectMapping = this.helper.processDimensionsBuilder.processDimToFormConditionMap(this.currFormSchemeDefine, (DimensionCollection)dimensionCollection, formKeys);
            if (dimensionObjectMapping.getObject(formObject.getDimensions()).contains(formObject.getFormKey())) {
                return parentBusinessObject;
            }
            return null;
        }
        return parentBusinessObject;
    }
}

