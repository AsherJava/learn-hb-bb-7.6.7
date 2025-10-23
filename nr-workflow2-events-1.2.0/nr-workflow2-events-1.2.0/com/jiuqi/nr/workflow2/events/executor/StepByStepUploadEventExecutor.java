/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.helper.StepBusinessObjectCollection;
import com.jiuqi.nr.workflow2.events.response.StepByStepUploadItem;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class StepByStepUploadEventExecutor
extends AbstractActionEventExecutor {
    protected EventDependentServiceHelper helper;
    protected static final String attr_key_upload_strategy = "StepbystepUploadStrategy";

    public StepByStepUploadEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        UploadLayerByLayerStrategy strategy = this.getStrategy(this.eventJsonConfig);
        IBusinessObject businessObject = businessKey.getBusinessObject();
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKey.getTask());
        IEntityDefine entityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKey.getTask());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine);
        operateResultSet.setLevel(businessObject, this.getBusinessObjectLevel(businessObject, entityTable));
        List<StepByStepUploadItem> uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)envParam, operateResultSet, flowObjectType, businessObject, entityTable, strategy);
        WFMonitorCheckResult checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
        Map<String, Object> checkResultDetail = this.getCheckResultDetail(envParam, businessKey, strategy);
        operateResultSet.setOperateResult(checkResultDetail);
        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
        EventExecutionStatus finishStatus = WFMonitorCheckResult.CHECK_PASS == checkResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        UploadLayerByLayerStrategy strategy = this.getStrategy(this.eventJsonConfig);
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        WorkflowObjectType flowObjectType = this.helper.workflowSettingsService.queryTaskWorkflowObjectType(businessKeyCollection.getTask());
        IEntityDefine entityDefine = this.helper.runtimeParamHelper.getProcessEntityDefinition(businessKeyCollection.getTask());
        IEntityTable entityTable = this.getEntityTableFullBuilder((IProcessRunPara)envParam, entityDefine, AuthorityType.Read);
        for (IBusinessObject businessObject : businessObjects) {
            operateResultSet.setLevel(businessObject, this.getBusinessObjectLevel(businessObject, entityTable));
            IEntityRow entityRow = entityTable.findByEntityKey(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
            IEntityRow parentRow = entityTable.findByEntityKey(entityRow.getParentEntityKey());
            if (parentRow == null) continue;
            IBusinessObject parentBusinessObject = this.helper.eventExecuteDimensionBuilder.toBusinessObject(businessObject, parentRow, flowObjectType);
            operateResultSet.setParentRowIndex(businessObject, operateResultSet.findBusinessObjectIndex(parentBusinessObject));
        }
        Map level2RowIndexes = operateResultSet.getLevel2RowIndexes();
        List<Integer> sortLevelByDesc = this.getSortLevelList(level2RowIndexes.keySet());
        for (Integer level : sortLevelByDesc) {
            Set rowIndexes = (Set)level2RowIndexes.get(level);
            for (Integer rowIndex : rowIndexes) {
                IBusinessObject businessObject = operateResultSet.findBusinessObject(rowIndex);
                List<StepByStepUploadItem> uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)envParam, operateResultSet, flowObjectType, businessObject, entityTable, strategy);
                WFMonitorCheckResult checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
            }
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    protected List<StepByStepUploadItem> getBusinessObjectCheckResult(IProcessRunPara runEnvPara, IOperateResultSet operateResultSet, WorkflowObjectType flowObjectType, IBusinessObject businessObject, IEntityTable entityTable, UploadLayerByLayerStrategy strategy) throws Exception {
        ArrayList<StepByStepUploadItem> checkErrorItems = new ArrayList<StepByStepUploadItem>();
        StepBusinessObjectCollection optChildBusinessObjects = this.helper.eventExecuteDimensionBuilder.getCanOptChildBusinessObjectCollection(runEnvPara, businessObject, flowObjectType, entityTable);
        Iterator iterator = optChildBusinessObjects.iterator();
        while (iterator.hasNext()) {
            IBusinessObject childBusinessObj = (IBusinessObject)iterator.next();
            WFMonitorCheckResult checkStatus = operateResultSet.getCheckStatus(childBusinessObj);
            if (WFMonitorCheckResult.CHECK_UN_PASS == checkStatus) {
                checkErrorItems.add(this.buildCheckItemInfo(optChildBusinessObjects.getMapEntityRow(childBusinessObj), "\u672a\u4e0a\u62a5", "\u4e0b\u7ea7\u672a\u4e0a\u62a5\uff0c\u672c\u7ea7\u4e0d\u80fd\u4e0a\u62a5"));
                optChildBusinessObjects.putBusinessObjectCheckState(childBusinessObj, checkStatus);
                continue;
            }
            if (UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_CONFIRMED == strategy) {
                optChildBusinessObjects.putBusinessObjectCheckState(childBusinessObj, WFMonitorCheckResult.UN_CHECK);
                continue;
            }
            optChildBusinessObjects.putBusinessObjectCheckState(childBusinessObj, checkStatus);
        }
        IBusinessObjectCollection unCheckedBusinessObjects = optChildBusinessObjects.getUnCheckBusinessKeyCollection();
        if (unCheckedBusinessObjects.size() != 0) {
            IBizObjectOperateResult operateResult = this.helper.processQueryService.queryInstanceState(runEnvPara, (IBusinessKeyCollection)new BusinessKeyCollection(runEnvPara.getTaskKey(), unCheckedBusinessObjects));
            Iterable iterable = operateResult.getBusinessObjects();
            for (IBusinessObject busObj : iterable) {
                IOperateResult result = operateResult.getResult((Object)busObj);
                if (!result.isSuccessful() || result.getResult() == null) {
                    ErrorCode errorCode = result.getErrorCode();
                    if (ErrorCode.INSTANCE_NOT_FOUND == errorCode) continue;
                    checkErrorItems.add(this.buildCheckItemInfo(optChildBusinessObjects.getMapEntityRow(busObj), "\u72b6\u6001\u9519\u8bef", "\u4e0b\u7ea7\u72b6\u6001\u9519\u8bef\uff0c\u672c\u7ea7\u4e0d\u80fd\u4e0a\u62a5"));
                    continue;
                }
                IProcessStatus.DataReportStatus reportStatus = ((IProcessStatus)result.getResult()).getDataReportStatus();
                if (IProcessStatus.DataReportStatus.UNREPORTED == reportStatus) {
                    checkErrorItems.add(this.buildCheckItemInfo(optChildBusinessObjects.getMapEntityRow(busObj), "\u672a\u4e0a\u62a5", "\u4e0b\u7ea7\u672a\u4e0a\u62a5\uff0c\u672c\u7ea7\u4e0d\u80fd\u4e0a\u62a5"));
                    continue;
                }
                if (UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_CONFIRMED != strategy || IProcessStatus.DataReportStatus.CONFIRMED == reportStatus) continue;
                checkErrorItems.add(this.buildCheckItemInfo(optChildBusinessObjects.getMapEntityRow(busObj), "\u672a\u4e0a\u62a5", "\u4e0b\u7ea7\u5df2\u786e\u8ba4\uff0c\u4e0a\u7ea7\u53ef\u4e0a\u62a5"));
            }
        }
        return checkErrorItems;
    }

    protected List<Integer> getSortLevelList(Set<Integer> levels) {
        ArrayList<Integer> sortLevelByDesc = new ArrayList<Integer>(levels);
        sortLevelByDesc.sort(Comparator.reverseOrder());
        return sortLevelByDesc;
    }

    protected Map<String, Object> getCheckResultDetail(ProcessExecuteEnv runEnvPara, IBusinessKey businessKey, UploadLayerByLayerStrategy strategy) {
        HashMap<String, Object> checkResultDetail = new HashMap<String, Object>();
        if (UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_CONFIRMED == strategy) {
            IUserAction userAction = this.helper.processMetaDataService.queryAction(runEnvPara.getTaskKey(), "tsk_audit", "act_confirm");
            checkResultDetail.put("userActionAlias", userAction != null ? userAction.getAlias() : "\u786e\u8ba4");
        } else {
            IProcessInstance instance = this.helper.processQueryService.queryInstances((IProcessRunPara)runEnvPara, businessKey);
            IUserAction userAction = this.helper.processMetaDataService.queryAction(runEnvPara.getTaskKey(), instance.getCurrentUserTask(), runEnvPara.getActionCode());
            checkResultDetail.put("userActionAlias", userAction != null ? userAction.getAlias() : "\u4e0a\u62a5");
        }
        checkResultDetail.put(attr_key_upload_strategy, strategy);
        return checkResultDetail;
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

    protected UploadLayerByLayerStrategy getStrategy(JSONObject eventJsonConfig) {
        if (eventJsonConfig.has(attr_key_upload_strategy)) {
            String name = eventJsonConfig.getString(attr_key_upload_strategy);
            if (UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_UPLOADED.name().equals(name)) {
                return UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_UPLOADED;
            }
            if (UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_CONFIRMED.name().equals(name)) {
                return UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_CONFIRMED;
            }
        }
        return UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_UPLOADED;
    }
}

