/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
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
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError$TaskNotFoundErrorData
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
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
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.StepByStepRejectEventExecutor;
import com.jiuqi.nr.workflow2.events.response.StepByStepUploadItem;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.helper.IProcessAuthJudgeHelper;
import com.jiuqi.nr.workflow2.service.helper.ProcessAuthJudgeHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class StepByStepRejectAllParentExecutor
extends StepByStepRejectEventExecutor {
    protected IProcessAuthJudgeHelper authJudgeHelper;
    private final Map<String, Boolean> auditAuthMap = new HashMap<String, Boolean>();

    public StepByStepRejectAllParentExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig, helper);
        this.authJudgeHelper = (IProcessAuthJudgeHelper)SpringBeanUtils.getBean(ProcessAuthJudgeHelper.class);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        String[] parentsEntityKeyDataPath;
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        this.currFormSchemeDefine = this.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        IBusinessObject businessObject = businessKey.getBusinessObject();
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKey.getTask());
        String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
        IEntityDefine entityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKey.getTask());
        IPeriodEntity periodEntityDefine = this.helper.runtimeParamHelper.getPeriodEntityDefine(businessKey.getTask());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine, AuthorityType.None);
        ArrayList<StepByStepUploadItem> uploadCheckItems = new ArrayList<StepByStepUploadItem>();
        for (String parentEntityKey : parentsEntityKeyDataPath = entityTable.getParentsEntityKeyDataPath(unitId)) {
            IEntityRow parentEntityRow = entityTable.findByEntityKey(parentEntityKey);
            IBusinessObject parentBusinessObject = this.helper.eventExecuteDimensionBuilder.toBusinessObject(businessObject, parentEntityRow, flowObjectType);
            IProcessStatus processStatus = this.helper.processQueryService.queryInstanceState((IProcessRunPara)envParam, (IBusinessKey)new BusinessKey(envParam.getTaskKey(), parentBusinessObject));
            if (processStatus == null || IProcessStatus.DataReportStatus.UNREPORTED == processStatus.getDataReportStatus()) continue;
            if (processStatus.getDataReportStatus() == IProcessStatus.DataReportStatus.CONFIRMED) {
                uploadCheckItems.add(this.buildCheckItemInfo(parentEntityRow, "\u5c42\u5c42\u9000\u56de", "\u4e0a\u7ea7\u672a\u9000\u56de\uff0c\u672c\u7ea7\u4e0d\u53ef\u9000\u56de\uff01\uff01"));
                continue;
            }
            if (!this.hasUnitAuditAuth(entityDefine, periodEntityDefine, parentEntityRow.getEntityKeyData(), envParam.getPeriod())) {
                uploadCheckItems.add(this.buildCheckItemInfo(parentEntityRow, "\u5c42\u5c42\u9000\u56de", "\u6ca1\u6709\u4e0a\u7ea7\u7684\u5ba1\u6279\u6743\u9650\uff0c\u672c\u7ea7\u4e0d\u53ef\u9000\u56de\uff01\uff01"));
                continue;
            }
            operateResultSet.setOperateResult(parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
        }
        WFMonitorCheckResult checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
        Map<String, Object> checkResultDetail = this.getCheckResultDetail(envParam, businessKey);
        operateResultSet.setOperateResult(checkResultDetail);
        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
        EventExecutionStatus finishStatus = WFMonitorCheckResult.CHECK_PASS == checkResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK, CompleteDependentType.PARENT_EXECUTION_FIRST);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        this.currFormSchemeDefine = this.runtimeParamHelper.getFormScheme(envParam.getTaskKey(), envParam.getPeriod());
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKeyCollection.getTask());
        IEntityDefine entityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKeyCollection.getTask());
        IPeriodEntity periodEntityDefine = this.helper.runtimeParamHelper.getPeriodEntityDefine(businessKeyCollection.getTask());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine, AuthorityType.None);
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        ArrayList<IBusinessObject> removeObjects = new ArrayList<IBusinessObject>();
        for (IBusinessObject businessObject : businessObjects) {
            String[] parentsEntityKeyDataPath;
            String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            for (String parentEntityKey : parentsEntityKeyDataPath = entityTable.getParentsEntityKeyDataPath(unitId)) {
                TaskNotFoundError.TaskNotFoundErrorData taskNotFoundErrorData;
                IEntityRow parentEntityRow = entityTable.findByEntityKey(parentEntityKey);
                IBusinessObject parentBusinessObject = this.helper.eventExecuteDimensionBuilder.toBusinessObject(businessObject, parentEntityRow, flowObjectType);
                if (operateResultSet.containsBusinessObject(parentBusinessObject)) continue;
                IProcessStatus processStatus = this.helper.processQueryService.queryInstanceState((IProcessRunPara)envParam, (IBusinessKey)new BusinessKey(envParam.getTaskKey(), parentBusinessObject));
                if (processStatus == null) {
                    operateResultSet.setOtherOperateResult(parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.UN_CHECK, ErrorCode.INSTANCE_NOT_FOUND.name(), (Object)"\u6d41\u7a0b\u5b9e\u4f8b\u4e0d\u5b58\u5728\uff01"));
                    removeObjects.add(parentBusinessObject);
                    continue;
                }
                if (IProcessStatus.DataReportStatus.UNREPORTED == processStatus.getDataReportStatus()) {
                    taskNotFoundErrorData = new TaskNotFoundError.TaskNotFoundErrorData();
                    taskNotFoundErrorData.setCurrentStatus(processStatus.getCode());
                    operateResultSet.setOtherOperateResult(parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.UN_CHECK, ErrorCode.TASK_NOT_FOUND.name(), (Object)taskNotFoundErrorData));
                    removeObjects.add(parentBusinessObject);
                    continue;
                }
                if (IProcessStatus.DataReportStatus.CONFIRMED == processStatus.getDataReportStatus()) {
                    taskNotFoundErrorData = new TaskNotFoundError.TaskNotFoundErrorData();
                    taskNotFoundErrorData.setCurrentStatus(processStatus.getCode());
                    operateResultSet.setOtherOperateResult(parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, ErrorCode.TASK_NOT_FOUND.name(), (Object)taskNotFoundErrorData));
                    continue;
                }
                if (!this.hasUnitAuditAuth(entityDefine, periodEntityDefine, parentEntityRow.getEntityKeyData(), envParam.getPeriod())) {
                    operateResultSet.setOtherOperateResult(parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, ErrorCode.UNAUTHORIZED.name(), (Object)"\u5f53\u524d\u7528\u6237\u5bf9\u5355\u4f4d\u6ca1\u6709\u5ba1\u6279\u6743\u9650"));
                    continue;
                }
                operateResultSet.setOperateResult(parentBusinessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
            }
        }
        OperateBusinessKeyCollection operateBusinessKeyCollection = new OperateBusinessKeyCollection(businessKeyCollection.getTask(), operateResultSet.allCheckPassBusinessObjects());
        IEventFinishedResult eventFinishedResult = super.executionEvent(monitor, operateResultSet, actionArgs, (IBusinessKeyCollection)operateBusinessKeyCollection);
        operateResultSet.removeBusinessObjects(removeObjects);
        return eventFinishedResult;
    }

    protected boolean hasUnitAuditAuth(IEntityDefine entityDefine, IPeriodEntity periodEntityDefine, String entityKeyData, String period) {
        if (!this.auditAuthMap.containsKey(entityKeyData)) {
            boolean hasAuth = this.authJudgeHelper.hasUnitAuditOperation(entityDefine.getId(), entityKeyData, periodEntityDefine.getKey(), period);
            this.auditAuthMap.put(entityKeyData, hasAuth);
        }
        return this.auditAuthMap.get(entityKeyData);
    }
}

