/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
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
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
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
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.StepByStepRejectEventExecutor;
import com.jiuqi.nr.workflow2.events.response.StepByStepUploadItem;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.List;
import org.json.JSONObject;

public class StepByStepRejectForceControlExecutor
extends StepByStepRejectEventExecutor {
    protected EventDependentServiceHelper helper;

    public StepByStepRejectForceControlExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig, helper);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        IBusinessObject businessObject = businessKey.getBusinessObject();
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKey.getTask());
        String taskKey = businessKey.getTask();
        String period = businessObject.getDimensions().getPeriodDimensionValue().getValue().toString();
        IEntityDefine mainEntityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKey.getTask());
        IPeriodEntity periodEntityDefine = this.helper.runtimeParamHelper.getPeriodEntityDefine(businessKey.getTask());
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        this.currFormSchemeDefine = this.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, mainEntityDefine);
        operateResultSet.setLevel(businessObject, this.getBusinessObjectLevel(businessObject, entityTable));
        List<StepByStepUploadItem> uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)envParam, operateResultSet, flowObjectType, businessObject, entityTable);
        WFMonitorCheckResult checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
        if (WFMonitorCheckResult.CHECK_UN_PASS == checkResult) {
            return new EventFinishedResult(EventExecutionStatus.STOP, EventExecutionAffect.IMPACT_REPORTING_CHECK);
        }
        List entityDefines = this.helper.runtimeParamHelper.listAllProcessEntityDefine(taskKey, false);
        for (IEntityDefine entityDefine : entityDefines) {
            if (entityDefine.getCode().equals(mainEntityDefine.getCode())) continue;
            entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine);
            operateResultSet.setLevel(businessObject, this.getBusinessObjectLevel(businessObject, entityTable));
            uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)envParam, operateResultSet, flowObjectType, businessObject, entityTable);
            checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
            if (WFMonitorCheckResult.CHECK_UN_PASS != checkResult) continue;
            return new EventFinishedResult(EventExecutionStatus.STOP, EventExecutionAffect.IMPACT_REPORTING_CHECK);
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        this.currFormSchemeDefine = this.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        IEntityDefine mainEntityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKeyCollection.getTask());
        IPeriodEntity periodEntityDefine = this.helper.runtimeParamHelper.getPeriodEntityDefine(businessKeyCollection.getTask());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, mainEntityDefine);
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKeyCollection.getTask());
        this.executionEvent(envParam, businessKeyCollection, entityTable, flowObjectType, operateResultSet, monitor, mainEntityDefine, periodEntityDefine);
        List entityDefines = this.helper.runtimeParamHelper.listAllProcessEntityDefine(envParam.getTaskKey(), false);
        for (IEntityDefine entityDefine : entityDefines) {
            if (entityDefine.getCode().equals(mainEntityDefine.getCode())) continue;
            entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine);
            this.executionEvent(envParam, businessKeyCollection, entityTable, flowObjectType, operateResultSet, monitor, mainEntityDefine, periodEntityDefine);
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }
}

