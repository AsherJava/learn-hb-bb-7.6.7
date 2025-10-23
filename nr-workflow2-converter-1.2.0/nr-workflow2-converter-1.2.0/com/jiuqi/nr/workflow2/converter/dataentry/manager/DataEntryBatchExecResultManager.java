/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo
 *  com.jiuqi.nr.dataentry.bean.MultCheckLabel
 *  com.jiuqi.nr.dataentry.bean.MultCheckReturnResult
 *  com.jiuqi.nr.dataentry.internal.service.BatchWorkflowServiceImpl
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.Info
 *  com.jiuqi.nr.jtable.params.output.LevelUploadInfo
 *  com.jiuqi.nr.jtable.params.output.LevelUploadObj
 *  com.jiuqi.nr.jtable.params.output.MultCheckResult
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.params.output.UploadBeforeCheck
 *  com.jiuqi.nr.jtable.params.output.UploadBeforeNodeCheck
 *  com.jiuqi.nr.jtable.params.output.UploadReturnInfo
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatusTemplate
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError$TaskNotFoundErrorData
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.events.enumeration.ReviewType
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn
 *  com.jiuqi.nr.workflow2.service.helper.ProcessRuntimeParamHelper
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.dataentry.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo;
import com.jiuqi.nr.dataentry.bean.MultCheckLabel;
import com.jiuqi.nr.dataentry.bean.MultCheckReturnResult;
import com.jiuqi.nr.dataentry.internal.service.BatchWorkflowServiceImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.Info;
import com.jiuqi.nr.jtable.params.output.LevelUploadInfo;
import com.jiuqi.nr.jtable.params.output.LevelUploadObj;
import com.jiuqi.nr.jtable.params.output.MultCheckResult;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.UploadBeforeCheck;
import com.jiuqi.nr.jtable.params.output.UploadBeforeNodeCheck;
import com.jiuqi.nr.jtable.params.output.UploadReturnInfo;
import com.jiuqi.nr.workflow2.converter.dataentry.manager.dto.BatchExecManagerDTO;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatusTemplate;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.events.enumeration.ReviewType;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import com.jiuqi.nr.workflow2.service.helper.ProcessRuntimeParamHelper;
import com.jiuqi.util.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class DataEntryBatchExecResultManager
extends EventOperateResult {
    private final ProcessRuntimeParamHelper processRuntimeParamHelper;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final IEntityDataService entityDataService;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final IRunTimeViewController runTimeViewController;
    private final Environment environment;
    private final AuditTypeDefineService auditTypeDefineService;
    private final AsyncTaskMonitor asyncTaskMonitor;
    private final BatchExecManagerDTO execManagerDTO;
    private IUserAction userAction;
    private boolean isErrorOccurred;
    private Object outputDetail;

    public DataEntryBatchExecResultManager(IBusinessObjectCollection businessObjectCollection, AsyncTaskMonitor asyncTaskMonitor, BatchExecManagerDTO execManagerDTO) {
        super(businessObjectCollection);
        this.asyncTaskMonitor = asyncTaskMonitor;
        this.execManagerDTO = execManagerDTO;
        this.processRuntimeParamHelper = (ProcessRuntimeParamHelper)SpringBeanUtils.getBean(ProcessRuntimeParamHelper.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        this.entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
        this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        this.environment = (Environment)SpringBeanUtils.getBean(Environment.class);
        this.auditTypeDefineService = (AuditTypeDefineService)SpringBeanUtils.getBean(AuditTypeDefineService.class);
        this.isErrorOccurred = false;
        this.outputDetail = null;
        this.userAction = execManagerDTO.getUserAction();
    }

    public void setUserAction(IUserAction userAction) {
        this.userAction = userAction;
    }

    public Object toOutputDetail() {
        if (this.outputDetail == null) {
            this.outputDetail = this.buildDetail(this.userAction);
        }
        return this.outputDetail;
    }

    public String toResultMessage() {
        if (this.outputDetail == null) {
            this.outputDetail = this.buildDetail(this.userAction);
        }
        WorkflowObjectType workflowObjectType = this.execManagerDTO.getWorkflowObjectType();
        String actionCode = this.userAction.getCode();
        if (this.isErrorOccurred) {
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                switch (actionCode) {
                    case "act_submit": {
                        return "submit_report_fail";
                    }
                    case "act_return": {
                        return "return_report_fail";
                    }
                    case "act_upload": {
                        return "upload_report_fail";
                    }
                    case "act_reject": {
                        return "reject_report_fail";
                    }
                    case "act_confirm": {
                        return "confrim_report_fail";
                    }
                    case "act_cancel_confirm": {
                        return "cancel_confrim_report_fail";
                    }
                    case "act_retrieve": {
                        return "retrieve_report_info";
                    }
                    case "act_apply_reject": {
                        return "apply_return_report_info";
                    }
                }
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                switch (actionCode) {
                    case "act_submit": {
                        return "submit_group_fail";
                    }
                    case "act_return": {
                        return "return_group_fail";
                    }
                    case "act_upload": {
                        return "upload_group_fail";
                    }
                    case "act_reject": {
                        return "reject_group_fail";
                    }
                    case "act_confirm": {
                        return "confrim_group_fail";
                    }
                    case "act_cancel_confirm": {
                        return "cancel_confrim_group_fail";
                    }
                    case "act_retrieve": {
                        return "retrieve_group_info";
                    }
                    case "act_apply_reject": {
                        return "apply_return_group_info";
                    }
                }
            } else {
                switch (actionCode) {
                    case "act_submit": {
                        return "submit_unit_fail";
                    }
                    case "act_return": {
                        return "return_unit_fail";
                    }
                    case "act_upload": {
                        return "upload_unit_fail";
                    }
                    case "act_reject": {
                        return "reject_unit_fail";
                    }
                    case "act_confirm": {
                        return "confrim_unit_fail";
                    }
                    case "act_cancel_confirm": {
                        return "cancel_confrim_unit_fail";
                    }
                    case "act_retrieve": {
                        return "retrieve_unit_info";
                    }
                    case "act_apply_reject": {
                        return "apply_return_unit_info";
                    }
                }
            }
        } else {
            switch (actionCode) {
                case "act_submit": {
                    return "submit_success_info";
                }
                case "act_return": {
                    return "return_success_info";
                }
                case "act_upload": {
                    return "upload_success_info";
                }
                case "act_reject": {
                    return "reject_success_info";
                }
                case "act_confirm": {
                    return "confirm_success_info";
                }
                case "act_cancel_confirm": {
                    return "cancel_confirm_success_info";
                }
                case "act_retrieve": {
                    return "retrieve_success_info";
                }
                case "act_apply_reject": {
                    return "apply_return_success_info";
                }
            }
        }
        return null;
    }

    public AsyncJobResult toAsyncJobResult() {
        if (this.outputDetail == null) {
            this.outputDetail = this.buildDetail(this.userAction);
        }
        return this.isErrorOccurred ? AsyncJobResult.FAILURE : AsyncJobResult.SUCCESS;
    }

    private Object buildDetail(IUserAction userAction) {
        String result;
        ObjectMapper mapper = new ObjectMapper();
        WorkflowObjectType workflowObjectType = this.execManagerDTO.getWorkflowObjectType();
        BatchUploadRetrunInfo batchUploadReturnInfo = new BatchUploadRetrunInfo();
        this.execManagerDTO.getLogInfo().setBatchUploadRetrunInfo(batchUploadReturnInfo);
        UploadReturnInfo uploadReturnInfo = new UploadReturnInfo();
        UploadBeforeNodeCheck uploadBeforeNodeCheck = new UploadBeforeNodeCheck();
        ArrayList<LevelUploadInfo> levelUploadInfos = new ArrayList<LevelUploadInfo>();
        LevelUploadObj levelUploadObj = new LevelUploadObj();
        LinkedHashMap<String, List<String>> unitWithFormOrFormGroupMap = new LinkedHashMap<String, List<String>>();
        LinkedHashMap<String, List<String>> unitWithUnPassFormOrFormGroupMap = new LinkedHashMap<String, List<String>>();
        LinkedHashMap<String, List<String>> unitWithOtherErrorFormOrFormGroupMap = new LinkedHashMap<String, List<String>>();
        LinkedHashMap<Long, String> otherErrorMessageMap = new LinkedHashMap<Long, String>();
        LinkedHashMap<Long, Object> otherErrorDetailMap = new LinkedHashMap<Long, Object>();
        List businessObjectCollection = this.getAllBusinessObject();
        for (IBusinessObject businessObject : businessObjectCollection) {
            String unitCode = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            Integer index = (Integer)this.businessObjectIndexes.get(businessObject);
            if (index == null) continue;
            boolean isPass = true;
            boolean isOtherError = false;
            for (Map.Entry entry : this.operateResultSetMap.entrySet()) {
                IEventOperateColumn eventOperateColumn = (IEventOperateColumn)entry.getKey();
                String eventId = eventOperateColumn.getColumnName();
                EventExecutionAffect affectStatus = eventOperateColumn.getAffectStatus();
                IOperateResultSet operateResultSet = (IOperateResultSet)entry.getValue();
                IEventOperateInfo operateResult = operateResultSet.getOperateResult(businessObject);
                if (operateResult == null) continue;
                WFMonitorCheckResult checkResult = operateResult.getCheckResult();
                if (eventId.equals("default-operate-column") && checkResult.equals((Object)WFMonitorCheckResult.CHECK_UN_PASS)) {
                    long key2;
                    isOtherError = true;
                    if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                        FormObject formObject = (FormObject)businessObject;
                        key2 = ((String)unitCode + formObject.getFormKey()).hashCode();
                    } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                        FormGroupObject formGroupObject = (FormGroupObject)businessObject;
                        key2 = ((String)unitCode + formGroupObject.getFormGroupKey()).hashCode();
                    } else {
                        key2 = unitCode.hashCode();
                    }
                    otherErrorMessageMap.put(key2, operateResult.getCheckResultMessage());
                    otherErrorDetailMap.put(key2, operateResult.getCheckResultDetail());
                } else if (affectStatus.equals((Object)EventExecutionAffect.IMPACT_REPORTING_CHECK) && checkResult.equals((Object)WFMonitorCheckResult.CHECK_UN_PASS)) {
                    isPass = false;
                }
                if (isPass && !isOtherError) continue;
                break;
            }
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                FormObject formObject = (FormObject)businessObject;
                unitWithFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList()).add(formObject.getFormKey());
                if (!isPass) {
                    unitWithUnPassFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList()).add(formObject.getFormKey());
                }
                if (!isOtherError) continue;
                unitWithOtherErrorFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList()).add(formObject.getFormKey());
                continue;
            }
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                FormGroupObject formGroupObject = (FormGroupObject)businessObject;
                unitWithFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList()).add(formGroupObject.getFormGroupKey());
                if (!isPass) {
                    unitWithUnPassFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList()).add(formGroupObject.getFormGroupKey());
                }
                if (!isOtherError) continue;
                unitWithOtherErrorFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList()).add(formGroupObject.getFormGroupKey());
                continue;
            }
            unitWithFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList());
            if (!isPass) {
                unitWithUnPassFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList());
            }
            if (!isOtherError) continue;
            unitWithOtherErrorFormOrFormGroupMap.computeIfAbsent(unitCode, key -> new ArrayList());
        }
        int allUnitNum = unitWithFormOrFormGroupMap.size();
        int formOrFormGroupWithAuthorityNum = 0;
        for (List formOrFormGroupList : unitWithFormOrFormGroupMap.values()) {
            formOrFormGroupWithAuthorityNum += formOrFormGroupList.size();
        }
        int allFormNum = workflowObjectType.equals((Object)WorkflowObjectType.FORM) ? formOrFormGroupWithAuthorityNum : 0;
        int allFormGroupNum = workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP) ? formOrFormGroupWithAuthorityNum : 0;
        int unPassUnitNum = unitWithUnPassFormOrFormGroupMap.size();
        int unPassFormOrFormGroupNum = 0;
        for (List unPassFormOrFormGroup : unitWithUnPassFormOrFormGroupMap.values()) {
            unPassFormOrFormGroupNum += unPassFormOrFormGroup.size();
        }
        int unPassFormNum = workflowObjectType.equals((Object)WorkflowObjectType.FORM) ? unPassFormOrFormGroupNum : 0;
        int unPassFormGroupNum = workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP) ? unPassFormOrFormGroupNum : 0;
        int otherErrorUnitNum = unitWithOtherErrorFormOrFormGroupMap.size();
        int otherErrorFormOrFormGroupNum = 0;
        for (List otherErrorFormOrFormGroup : unitWithOtherErrorFormOrFormGroupMap.values()) {
            otherErrorFormOrFormGroupNum += otherErrorFormOrFormGroup.size();
        }
        int otherErrorFormNum = workflowObjectType.equals((Object)WorkflowObjectType.FORM) ? otherErrorFormOrFormGroupNum : 0;
        int otherErrorFormGroupNum = workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP) ? otherErrorFormOrFormGroupNum : 0;
        uploadReturnInfo.setAllEntityNums(allUnitNum);
        uploadReturnInfo.setAllFormNums(allFormNum);
        uploadReturnInfo.setAllFormGroupNums(allFormGroupNum);
        uploadReturnInfo.setErrorEntityNum(unPassUnitNum);
        uploadReturnInfo.setErrorFormNums(unPassFormNum);
        uploadReturnInfo.setErrorFromGroupNums(unPassFormGroupNum);
        uploadReturnInfo.setMessage(this.buildOtherErrorReturnInfoEntityList(unitWithOtherErrorFormOrFormGroupMap, otherErrorMessageMap, otherErrorDetailMap, workflowObjectType));
        uploadReturnInfo.setOtherErrorNum(otherErrorUnitNum);
        uploadReturnInfo.setOtherErrorFromNum(otherErrorFormNum);
        uploadReturnInfo.setOtherErrorFromGroupNum(otherErrorFormGroupNum);
        Map<String, List<String>> successUnitWithFormOrFormGroupMap = this.getSuccessUnitWithFormOrFormGroupMap(unitWithFormOrFormGroupMap, unitWithUnPassFormOrFormGroupMap, unitWithOtherErrorFormOrFormGroupMap);
        uploadReturnInfo.setSuccessEntity(this.buildReturnInfoEntityList(successUnitWithFormOrFormGroupMap, workflowObjectType));
        uploadReturnInfo.setSuccessEntityNum(allUnitNum - unPassUnitNum - otherErrorUnitNum);
        uploadReturnInfo.setSuccessFormNums(allFormNum - unPassFormNum - otherErrorFormNum);
        uploadReturnInfo.setSuccessFromGroupNums(allFormGroupNum - unPassFormGroupNum - otherErrorFormGroupNum);
        batchUploadReturnInfo.setUploadReturnInfo(uploadReturnInfo);
        for (IEventOperateColumn eventOperateColumn : this.operateResultSetMap.keySet()) {
            String unitCode;
            IEventOperateInfo operateResult;
            Object businessObject2;
            IOperateResultSet operateResultSet;
            EventExecutionAffect affectStatus = eventOperateColumn.getAffectStatus();
            if (affectStatus.equals((Object)EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK)) continue;
            String eventId = eventOperateColumn.getColumnName();
            if (eventId.equals("complete-review-event")) {
                JSONObject reviewEventParam = new JSONObject(eventOperateColumn.getColumnSettings());
                ReviewType reviewType = ReviewType.valueOf((String)reviewEventParam.getString("reviewType"));
                if (reviewType.equals((Object)ReviewType.COMPREHENSIVE_REVIEW)) {
                    String finalAccountsAuditDetail;
                    String mulCheckVersion = this.environment.getProperty("jiuqi.nr.workflow.mul-check.version", String.class);
                    if (mulCheckVersion == null || mulCheckVersion.equals("2.0")) {
                        MultCheckReturnResult multCheckReturnResult;
                        String mcUploadResult = (String)this.operateMergeResult.get(eventOperateColumn);
                        try {
                            multCheckReturnResult = (MultCheckReturnResult)mapper.readValue(mcUploadResult, MultCheckReturnResult.class);
                        }
                        catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        String errorMsg = multCheckReturnResult.getErrorMsg();
                        if (errorMsg != null) {
                            return errorMsg;
                        }
                        List failedList = multCheckReturnResult.getFailedList();
                        if (!failedList.isEmpty()) {
                            this.isErrorOccurred = true;
                        }
                        MultCheckResult multCheckResult = new MultCheckResult();
                        List failTitles = failedList.stream().map(MultCheckLabel::getTitle).collect(Collectors.toList());
                        multCheckResult.setErrorNum(failTitles.size());
                        multCheckResult.setTitle(failTitles);
                        multCheckResult.setResultStr(mcUploadResult);
                        batchUploadReturnInfo.setMultCheckResult(multCheckResult);
                        continue;
                    }
                    if (!mulCheckVersion.equals("1.0")) continue;
                    try {
                        finalAccountsAuditDetail = mapper.writeValueAsString(this.operateMergeResult.get(eventOperateColumn));
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    batchUploadReturnInfo.setFinalaccountsAudit(finalAccountsAuditDetail);
                    continue;
                }
                if (!reviewType.equals((Object)ReviewType.FORMULA_REVIEW)) continue;
                batchUploadReturnInfo.setUploadBeforeCheck(this.getUploadBeforeCheck(eventOperateColumn, workflowObjectType));
                continue;
            }
            if (eventId.equals("check-unit-node-event")) {
                operateResultSet = this.getOperateResultSet(eventOperateColumn);
                LinkedHashMap<String, List> nodeCheckUnPassMap = new LinkedHashMap<String, List>();
                LinkedHashMap<String, List> unPassFormMap = new LinkedHashMap<String, List>();
                for (Object businessObject2 : businessObjectCollection) {
                    operateResult = operateResultSet.getOperateResult((IBusinessObject)businessObject2);
                    if (this.isSkip(operateResult)) continue;
                    this.isErrorOccurred = true;
                    unitCode = businessObject2.getDimensions().getDWDimensionValue().getValue().toString();
                    if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                        nodeCheckUnPassMap.computeIfAbsent(unitCode, key -> new ArrayList());
                    } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                        FormGroupObject formGroupObject = (FormGroupObject)businessObject2;
                        nodeCheckUnPassMap.computeIfAbsent(unitCode, key -> new ArrayList()).add(formGroupObject.getFormGroupKey());
                    } else {
                        nodeCheckUnPassMap.computeIfAbsent(unitCode, key -> new ArrayList());
                    }
                    NodeCheckResult nodeCheckResult = (NodeCheckResult)operateResult.getCheckResultDetail();
                    Set resultItemInfos = nodeCheckResult.getResultItemInfos();
                    for (NodeCheckResultItemInfo resultItemInfo : resultItemInfos) {
                        Set unPassFormKeys = resultItemInfo.getNodeCheckResultItems().stream().map(item -> item.getNodeCheckFieldMessage().getFormKey()).collect(Collectors.toSet());
                        if (unPassFormKeys.isEmpty()) continue;
                        unPassFormMap.computeIfAbsent(unitCode, key -> new ArrayList()).addAll(unPassFormKeys);
                    }
                }
                uploadBeforeNodeCheck.setAsyncTaskId(this.operateMergeResult.get(eventOperateColumn).toString());
                uploadBeforeNodeCheck.setUnPassEntityNum(nodeCheckUnPassMap.size());
                uploadBeforeNodeCheck.setUnPassEntity(this.buildNodeCheckUnPassEntity(nodeCheckUnPassMap.keySet()));
                int NodeCheckUnPassFormGroupNum = 0;
                businessObject2 = nodeCheckUnPassMap.values().iterator();
                while (businessObject2.hasNext()) {
                    List unPassFormGroups = (List)businessObject2.next();
                    NodeCheckUnPassFormGroupNum += unPassFormGroups.size();
                }
                int NodeCheckUnPassFormNum = 0;
                for (List unPassForms : unPassFormMap.values()) {
                    NodeCheckUnPassFormNum += unPassForms.size();
                }
                uploadBeforeNodeCheck.setUnPassFormNum(NodeCheckUnPassFormNum);
                uploadBeforeNodeCheck.setUnPassForms(unPassFormMap);
                uploadBeforeNodeCheck.setUnPassFormGroupNum(NodeCheckUnPassFormGroupNum);
                uploadBeforeNodeCheck.setUnPassFormGroup(nodeCheckUnPassMap);
                batchUploadReturnInfo.setUploadBeforeNodeCheck(uploadBeforeNodeCheck);
                continue;
            }
            if (!eventId.contains("step-by-step-upload-event") && !eventId.contains("step-by-step-reject-event") && !eventId.contains("step-by-step-applyreject-event") && !eventId.contains("step-by-step-retrievereject-event") && !eventId.contains("step-by-step-cancel-confirm-event")) continue;
            operateResultSet = this.getOperateResultSet(eventOperateColumn);
            LinkedHashMap<String, List<String>> unPassMap = new LinkedHashMap<String, List<String>>();
            LinkedHashMap<Long, List<String>> unPassEntityInfoMap = new LinkedHashMap<Long, List<String>>();
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM) || workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                for (Object businessObject2 : businessObjectCollection) {
                    long key3;
                    operateResult = operateResultSet.getOperateResult((IBusinessObject)businessObject2);
                    if (this.isSkip(operateResult)) continue;
                    this.isErrorOccurred = true;
                    unitCode = businessObject2.getDimensions().getDWDimensionValue().getValue().toString();
                    List unPassEntityItem = (List)operateResult.getCheckResultDetail();
                    if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                        FormObject formObject = (FormObject)businessObject2;
                        key3 = (unitCode + formObject.getFormKey()).hashCode();
                        unPassMap.computeIfAbsent(unitCode, mapKey -> new ArrayList()).add(formObject.getFormKey());
                    } else {
                        FormGroupObject formGroupObject = (FormGroupObject)businessObject2;
                        key3 = (unitCode + formGroupObject.getFormGroupKey()).hashCode();
                        unPassMap.computeIfAbsent(unitCode, mapKey -> new ArrayList()).add(formGroupObject.getFormGroupKey());
                    }
                    List value = unPassEntityItem.stream().map(item -> item.getUnitCode() + " | " + item.getUnitTitle()).collect(Collectors.toList());
                    unPassEntityInfoMap.put(key3, value);
                }
                int total = 0;
                businessObject2 = unPassMap.values().iterator();
                while (businessObject2.hasNext()) {
                    List formOrFormGroupList = (List)businessObject2.next();
                    total += formOrFormGroupList.size();
                }
                levelUploadObj.setTotal(total);
                levelUploadObj.setAction(userAction.getAlias());
                levelUploadObj.setNoOperateGroupOrFormMap(this.buildNotOperateFormOrFormGroupMap(unPassMap, unPassEntityInfoMap, workflowObjectType));
                batchUploadReturnInfo.setLevelUploadObj(levelUploadObj);
            } else {
                for (Object businessObject2 : businessObjectCollection) {
                    operateResult = operateResultSet.getOperateResult((IBusinessObject)businessObject2);
                    if (this.isSkip(operateResult)) continue;
                    this.isErrorOccurred = true;
                    unitCode = businessObject2.getDimensions().getDWDimensionValue().getValue().toString();
                    FormSchemeDefine formSchemeDefine = this.execManagerDTO.getFormSchemeDefine();
                    String period = this.execManagerDTO.getPeriod();
                    IEntityDefine entityDefine = this.processRuntimeParamHelper.getProcessEntityDefinition(this.execManagerDTO.getFormSchemeDefine().getTaskKey());
                    String entityCaliber = entityDefine.getId();
                    IEntityTable entityTable = this.getEntityTable(StringUtils.isEmpty((String)entityCaliber) ? formSchemeDefine.getDw() : entityCaliber, period, formSchemeDefine.getDateTime());
                    IEntityRow targetEntityRow = entityTable.findByEntityKey(unitCode);
                    List unPassEntityInfo = (List)operateResult.getCheckResultDetail();
                    LevelUploadInfo levelUploadInfo = new LevelUploadInfo();
                    levelUploadInfo.setTotal(unPassEntityInfo.size());
                    levelUploadInfo.setAction(userAction.getAlias());
                    levelUploadInfo.setParId(targetEntityRow.getEntityKeyData());
                    levelUploadInfo.setParCode(targetEntityRow.getCode());
                    levelUploadInfo.setParName(targetEntityRow.getTitle());
                    levelUploadInfo.setUnitInfo(unPassEntityInfo.stream().map(row -> {
                        Info info = new Info();
                        info.setId(row.getUnitId());
                        info.setCode(row.getUnitCode());
                        info.setName(row.getUnitTitle());
                        return info;
                    }).collect(Collectors.toList()));
                    levelUploadInfos.add(levelUploadInfo);
                }
            }
            batchUploadReturnInfo.setLevelUploadInfo(levelUploadInfos);
        }
        try {
            result = mapper.writeValueAsString((Object)batchUploadReturnInfo);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private UploadBeforeCheck getUploadBeforeCheck(IEventOperateColumn eventOperateColumn, WorkflowObjectType workflowObjectType) {
        ArrayList<Integer> needCommentErrorStatus;
        ArrayList<Integer> canNotIgnoreErrorStatus;
        JSONObject reviewEventParam;
        UploadBeforeCheck uploadBeforeCheck;
        block19: {
            List auditTypes;
            uploadBeforeCheck = new UploadBeforeCheck();
            reviewEventParam = new JSONObject(eventOperateColumn.getColumnSettings());
            canNotIgnoreErrorStatus = new ArrayList<Integer>();
            needCommentErrorStatus = new ArrayList<Integer>();
            try {
                auditTypes = this.auditTypeDefineService.queryAllAuditType();
            }
            catch (Exception exception) {
                LoggerFactory.getLogger(((Object)((Object)this)).getClass()).error(exception.getMessage(), exception);
                throw new RuntimeException(exception);
            }
            try {
                Map<String, String> errorTypeValueMap = reviewEventParam.getJSONArray("errorHandle").toList().stream().map(e -> (HashMap)e).collect(Collectors.toMap(e -> (String)e.get("code"), e -> (String)e.get("value"), (v1, v2) -> v1));
                for (AuditType auditType : auditTypes) {
                    Integer auditTypeCode = auditType.getCode();
                    String value = errorTypeValueMap.get(String.valueOf(auditTypeCode));
                    if (value == null) {
                        canNotIgnoreErrorStatus.add(auditTypeCode);
                        continue;
                    }
                    if (value.equals("1")) {
                        canNotIgnoreErrorStatus.add(auditTypeCode);
                        needCommentErrorStatus.add(auditTypeCode);
                        continue;
                    }
                    if (!value.equals("2")) continue;
                    canNotIgnoreErrorStatus.add(auditTypeCode);
                }
            }
            catch (JSONException e2) {
                JSONObject errorHandle = reviewEventParam.getJSONObject("errorHandle");
                String hint = errorHandle.getString("hint");
                if (hint.equals("1")) {
                    canNotIgnoreErrorStatus.add(1);
                    needCommentErrorStatus.add(1);
                } else if (hint.equals("2")) {
                    canNotIgnoreErrorStatus.add(1);
                }
                String warning = errorHandle.getString("warning");
                if (warning.equals("1")) {
                    canNotIgnoreErrorStatus.add(2);
                    needCommentErrorStatus.add(2);
                }
                if (!warning.equals("2")) break block19;
                canNotIgnoreErrorStatus.add(2);
            }
        }
        LinkedHashMap<String, String> resultEntity = new LinkedHashMap<String, String>();
        LinkedHashMap<String, List<String>> resultForm = new LinkedHashMap<String, List<String>>();
        LinkedHashMap<String, List<String>> resultFormGroup = new LinkedHashMap<String, List<String>>();
        LinkedHashMap<String, ArrayList<Integer>> canNotIgnore = new LinkedHashMap<String, ArrayList<Integer>>();
        LinkedHashMap<String, ArrayList<Integer>> needComment = new LinkedHashMap<String, ArrayList<Integer>>();
        List businessObjectCollection = this.getAllBusinessObject();
        for (IBusinessObject businessObject : businessObjectCollection) {
            WFMonitorCheckResult checkResult;
            IOperateResultSet operateResultSet = this.getOperateResultSet(eventOperateColumn);
            IEventOperateInfo operateResult = operateResultSet.getOperateResult(businessObject);
            if (operateResult == null || (checkResult = operateResult.getCheckResult()) == null || !checkResult.equals((Object)WFMonitorCheckResult.CHECK_UN_PASS)) continue;
            this.isErrorOccurred = true;
            IEntityDefine entityDefine = this.processRuntimeParamHelper.getProcessEntityDefinition(this.execManagerDTO.getFormSchemeDefine().getTaskKey());
            DimensionCombination dimensions = businessObject.getDimensions();
            String unitCode = dimensions.getDWDimensionValue().getValue().toString();
            IEntityRow targetEntityRow = this.getTargetEntityRow(this.execManagerDTO.getFormSchemeDefine(), entityDefine.getId(), this.execManagerDTO.getPeriod(), unitCode);
            resultEntity.put(unitCode, targetEntityRow.getTitle());
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                FormObject formObject = (FormObject)businessObject;
                resultForm.computeIfAbsent(unitCode, key -> new ArrayList()).add(formObject.getFormKey());
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                FormGroupObject formGroupObject = (FormGroupObject)businessObject;
                resultFormGroup.computeIfAbsent(unitCode, key -> new ArrayList()).add(formGroupObject.getFormGroupKey());
            }
            canNotIgnore.put(unitCode, canNotIgnoreErrorStatus);
            needComment.put(unitCode, needCommentErrorStatus);
        }
        uploadBeforeCheck.setAsyncTaskId(this.asyncTaskMonitor.getTaskId());
        uploadBeforeCheck.setResultEntity(resultEntity);
        uploadBeforeCheck.setResultForms(resultForm);
        uploadBeforeCheck.setResultFormGroup(resultFormGroup);
        uploadBeforeCheck.setCanIgnoreErrorStatus(canNotIgnore);
        uploadBeforeCheck.setNeedCommentErrorStatus(needComment);
        uploadBeforeCheck.setUnPassEntityNum(resultEntity.size());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            boolean isReviewAllBeforeUpload = reviewEventParam.getBoolean("isReviewAllBeforeUpload");
            uploadBeforeCheck.setNeedCheckForms(isReviewAllBeforeUpload ? new ArrayList() : this.execManagerDTO.getFormKeys());
            uploadBeforeCheck.setUnPassFormsNum(this.getUnPassFormOrFormGroupNum(resultForm));
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ArrayList allFormKeys = new ArrayList();
            for (String formGroupKey : this.execManagerDTO.getFormGroupKeys()) {
                List formKeys = this.runTimeViewController.listFormByGroup(formGroupKey, this.execManagerDTO.getFormSchemeDefine().getKey()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                allFormKeys.addAll(formKeys);
            }
            uploadBeforeCheck.setNeedCheckForms(allFormKeys);
            uploadBeforeCheck.setUnPassFormGroupNum(this.getUnPassFormOrFormGroupNum(resultFormGroup));
        }
        return uploadBeforeCheck;
    }

    private IEntityRow getTargetEntityRow(FormSchemeDefine formSchemeDefine, String entityCaliber, String period, String unitCode) {
        return this.getEntityTable(StringUtils.isEmpty((String)entityCaliber) ? formSchemeDefine.getDw() : entityCaliber, period, formSchemeDefine.getDateTime()).findByEntityKey(unitCode);
    }

    public IEntityTable getEntityTable(String entityId, String period, String periodView) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.markLeaf();
        entityQuery.lazyQuery();
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getUnPassFormOrFormGroupNum(Map<String, List<String>> result) {
        int num = 0;
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            num += entry.getValue().size();
        }
        return num;
    }

    private Map<String, List<String>> getSuccessUnitWithFormOrFormGroupMap(Map<String, List<String>> unitWithFormOrFormGroupMap, Map<String, List<String>> unitWithUnPassFormOrFormGroupMap, Map<String, List<String>> unitWithOtherErrorFormOrFormGroupMap) {
        List successFormOrFormGroupList;
        List<String> formOrFormGroupList;
        String unitCode;
        LinkedHashMap<String, List<String>> successUnitWithFormOrFormGroupMap = new LinkedHashMap<String, List<String>>(unitWithFormOrFormGroupMap);
        for (Map.Entry<String, List<String>> entry : unitWithUnPassFormOrFormGroupMap.entrySet()) {
            unitCode = entry.getKey();
            formOrFormGroupList = entry.getValue();
            successFormOrFormGroupList = (List)successUnitWithFormOrFormGroupMap.get(unitCode);
            if (successFormOrFormGroupList == null) continue;
            successFormOrFormGroupList.removeAll(formOrFormGroupList);
            if (!successFormOrFormGroupList.isEmpty()) continue;
            successUnitWithFormOrFormGroupMap.remove(unitCode);
        }
        for (Map.Entry<String, List<String>> entry : unitWithOtherErrorFormOrFormGroupMap.entrySet()) {
            unitCode = entry.getKey();
            formOrFormGroupList = entry.getValue();
            successFormOrFormGroupList = (List)successUnitWithFormOrFormGroupMap.get(unitCode);
            if (successFormOrFormGroupList == null) continue;
            successFormOrFormGroupList.removeAll(formOrFormGroupList);
            if (!successFormOrFormGroupList.isEmpty()) continue;
            successUnitWithFormOrFormGroupMap.remove(unitCode);
        }
        return successUnitWithFormOrFormGroupMap;
    }

    private List<ReturnInfo> buildReturnInfoEntityList(Map<String, List<String>> unitWithFormOrFormGroup, WorkflowObjectType workflowObjectType) {
        FormSchemeDefine formSchemeDefine = this.execManagerDTO.getFormSchemeDefine();
        String period = this.execManagerDTO.getPeriod();
        IEntityDefine entityDefine = this.processRuntimeParamHelper.getProcessEntityDefinition(this.execManagerDTO.getFormSchemeDefine().getTaskKey());
        String entityCaliber = entityDefine.getId();
        IEntityTable entityTable = this.getEntityTable(StringUtils.isEmpty((String)entityCaliber) ? formSchemeDefine.getDw() : entityCaliber, period, formSchemeDefine.getDateTime());
        ArrayList<ReturnInfo> returnInfos = new ArrayList<ReturnInfo>();
        for (Map.Entry<String, List<String>> entry : unitWithFormOrFormGroup.entrySet()) {
            String unitCode = entry.getKey();
            IEntityRow targetEntityRow = entityTable.findByEntityKey(unitCode);
            EntityData entityData = new EntityData();
            entityData.setId(targetEntityRow.getEntityKeyData());
            entityData.setRowCaption(targetEntityRow.getTitle());
            entityData.setCode(targetEntityRow.getCode());
            entityData.setTitle(targetEntityRow.getTitle());
            entityData.setOrder(((BigDecimal)targetEntityRow.getEntityOrder()).doubleValue());
            entityData.setLeaf(targetEntityRow.isLeaf());
            entityData.setChildrenCount(entityTable.getDirectChildCount(targetEntityRow.getEntityKeyData()));
            entityData.setParentId(targetEntityRow.getParentEntityKey());
            String[] parentsPath = entityTable.getParentsEntityKeyDataPath(targetEntityRow.getParentEntityKey());
            entityData.setParents(parentsPath == null || parentsPath.length == 0 ? new ArrayList() : Arrays.asList(parentsPath));
            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setEntity(entityData);
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                List<String> formKeys = entry.getValue();
                ArrayList<FormDefine> formDefines = new ArrayList<FormDefine>();
                for (String formKey : formKeys) {
                    FormDefine formDefine = this.runTimeViewController.getForm(formKey, formSchemeDefine.getKey());
                    formDefines.add(formDefine);
                }
                returnInfo.setFormDefine(formDefines);
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                List<String> formGroupKeys = entry.getValue();
                ArrayList<FormGroupDefine> formGroupDefines = new ArrayList<FormGroupDefine>();
                for (String formGroupKey : formGroupKeys) {
                    FormGroupDefine formGroupDefine = this.runTimeViewController.getFormGroup(formGroupKey, formSchemeDefine.getKey());
                    formGroupDefines.add(formGroupDefine);
                }
                returnInfo.setFormGroupDefine(formGroupDefines);
            }
            returnInfos.add(returnInfo);
        }
        return returnInfos;
    }

    private List<ReturnInfo> buildOtherErrorReturnInfoEntityList(Map<String, List<String>> unitWithOtherErrorFormOrFormGroup, Map<Long, String> otherErrorMessageMap, Map<Long, Object> otherErrorDetailMap, WorkflowObjectType workflowObjectType) {
        List<ReturnInfo> returnInfos = this.buildReturnInfoEntityList(unitWithOtherErrorFormOrFormGroup, workflowObjectType);
        boolean isChinese = BatchWorkflowServiceImpl.isChinese();
        for (ReturnInfo returnInfo : returnInfos) {
            TaskNotFoundError.TaskNotFoundErrorData taskNotFoundErrorData;
            String errorCode;
            String message;
            this.isErrorOccurred = true;
            String unitCode = returnInfo.getEntity().getCode();
            ArrayList<String> stateMessage = new ArrayList<String>();
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                List formDefines = returnInfo.getFormDefine();
                for (FormDefine formDefine : formDefines) {
                    message = "\u672a\u89e3\u6790\u7684\u5f02\u5e38\u4fe1\u606f";
                    long key = (unitCode + formDefine.getKey()).hashCode();
                    errorCode = otherErrorMessageMap.get(key);
                    if (errorCode.equals(ErrorCode.TASK_NOT_FOUND.toString())) {
                        taskNotFoundErrorData = (TaskNotFoundError.TaskNotFoundErrorData)otherErrorDetailMap.get(key);
                        String curFormGroupStatus = Objects.requireNonNull(ProcessStatusTemplate.get((String)taskNotFoundErrorData.getCurrentStatus())).getTitle();
                        message = isChinese ? formDefine.getTitle() + ", \u72b6\u6001\u4e3a" + curFormGroupStatus + ", \u4e0d\u80fd\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : formDefine.getTitle() + ", In the state of " + curFormGroupStatus + ", The " + this.getActionEnglish(this.userAction) + " operation isn't be performed";
                    } else if (errorCode.equals(ErrorCode.INSTANCE_NOT_FOUND.toString())) {
                        message = isChinese ? formDefine.getTitle() + "\u672a\u542f\u52a8\u6d41\u7a0b\uff0c\u65e0\u6cd5\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : formDefine.getTitle() + "Process not initiated, can't execute " + this.getActionEnglish(this.userAction) + " operation";
                    } else if (errorCode.equals(ErrorCode.UNAUTHORIZED.toString())) {
                        message = isChinese ? formDefine.getTitle() + " \u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : formDefine.getTitle() + " The current user does not have permission to perform " + this.getActionEnglish(this.userAction) + " operation";
                    } else if (errorCode.equals(ErrorCode.CONCURRENT_OPERATION.toString())) {
                        message = isChinese ? formDefine.getTitle() + " \u6b63\u5728\u5904\u7406\u4e2d\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5" : formDefine.getTitle() + " The process is currently being processed, please try again later";
                    }
                    stateMessage.add(message);
                }
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                List formGroupDefines = returnInfo.getFormGroupDefine();
                for (FormGroupDefine formGroupDefine : formGroupDefines) {
                    message = "\u672a\u89e3\u6790\u7684\u5f02\u5e38\u4fe1\u606f";
                    long key = (unitCode + formGroupDefine.getKey()).hashCode();
                    errorCode = otherErrorMessageMap.get(key);
                    if (errorCode.equals(ErrorCode.TASK_NOT_FOUND.toString())) {
                        taskNotFoundErrorData = (TaskNotFoundError.TaskNotFoundErrorData)otherErrorDetailMap.get(key);
                        String curFormStatus = Objects.requireNonNull(ProcessStatusTemplate.get((String)taskNotFoundErrorData.getCurrentStatus())).getTitle();
                        message = isChinese ? formGroupDefine.getTitle() + ", \u72b6\u6001\u4e3a" + curFormStatus + ", \u4e0d\u80fd\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : formGroupDefine.getTitle() + ", In the state of " + curFormStatus + ", The " + this.getActionEnglish(this.userAction) + " operation isn't be performed";
                    } else if (errorCode.equals(ErrorCode.INSTANCE_NOT_FOUND.toString())) {
                        message = isChinese ? formGroupDefine.getTitle() + "\u672a\u542f\u52a8\u6d41\u7a0b\uff0c\u65e0\u6cd5\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : formGroupDefine.getTitle() + "Process not initiated, can't execute " + this.getActionEnglish(this.userAction) + " operation";
                    } else if (errorCode.equals(ErrorCode.UNAUTHORIZED.toString())) {
                        message = isChinese ? formGroupDefine.getTitle() + " \u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : formGroupDefine.getTitle() + " The current user does not have permission to perform " + this.getActionEnglish(this.userAction) + " operation";
                    } else if (errorCode.equals(ErrorCode.CONCURRENT_OPERATION.toString())) {
                        message = isChinese ? formGroupDefine.getTitle() + " \u6b63\u5728\u5904\u7406\u4e2d\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5" : formGroupDefine.getTitle() + " The process is currently being processed, please try again later";
                    }
                    stateMessage.add(message);
                }
            } else {
                String message2 = "\u672a\u89e3\u6790\u7684\u5f02\u5e38\u4fe1\u606f";
                long key = unitCode.hashCode();
                String errorCode2 = otherErrorMessageMap.get(key);
                if (errorCode2.equals(ErrorCode.TASK_NOT_FOUND.toString())) {
                    TaskNotFoundError.TaskNotFoundErrorData taskNotFoundErrorData2 = (TaskNotFoundError.TaskNotFoundErrorData)otherErrorDetailMap.get(key);
                    String curUnitStatus = Objects.requireNonNull(ProcessStatusTemplate.get((String)taskNotFoundErrorData2.getCurrentStatus())).getTitle();
                    message2 = isChinese ? ", \u72b6\u6001\u4e3a" + curUnitStatus + ", \u4e0d\u80fd\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : ", In the state of " + curUnitStatus + ", The " + this.getActionEnglish(this.userAction) + " operation isn't be performed";
                } else if (errorCode2.equals(ErrorCode.INSTANCE_NOT_FOUND.toString())) {
                    message2 = isChinese ? "\u672a\u542f\u52a8\u6d41\u7a0b\uff0c\u65e0\u6cd5\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : ", Process not initiated, can't execute " + this.getActionEnglish(this.userAction) + " operation";
                } else if (errorCode2.equals(ErrorCode.UNAUTHORIZED.toString())) {
                    message2 = isChinese ? " \u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u6267\u884c" + this.userAction.getAlias() + "\u64cd\u4f5c" : " The current user does not have permission to perform " + this.getActionEnglish(this.userAction) + " operation";
                } else if (errorCode2.equals(ErrorCode.CONCURRENT_OPERATION.toString())) {
                    message2 = isChinese ? " \u6b63\u5728\u5904\u7406\u4e2d\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5" : " The process is currently being processed, please try again later";
                }
                stateMessage.add(message2);
            }
            returnInfo.setMessage((String)stateMessage.get(0));
            returnInfo.setStateMessage(stateMessage);
        }
        return returnInfos;
    }

    private String getActionEnglish(IUserAction userAction) {
        String actionCode = userAction.getCode();
        UserActionTemplate userActionTemplate = UserActionTemplate.get((String)actionCode);
        if (userActionTemplate.equals(UserActionTemplate.SUBMIT)) {
            return "submission";
        }
        if (userActionTemplate.equals(UserActionTemplate.BACK)) {
            return "return approval";
        }
        if (userActionTemplate.equals(UserActionTemplate.REPORT)) {
            return "report";
        }
        if (userActionTemplate.equals(UserActionTemplate.REJECT)) {
            return "return";
        }
        if (userActionTemplate.equals(UserActionTemplate.CONFIRM)) {
            return "confirmation";
        }
        if (userActionTemplate.equals(UserActionTemplate.CANCEL_CONFIRM)) {
            return "cancel confirmation";
        }
        if (userActionTemplate.equals(UserActionTemplate.RETRIEVE)) {
            return "retrieve";
        }
        if (userActionTemplate.equals(UserActionTemplate.APPLY_FOR_REJECT)) {
            return "apply for return";
        }
        return null;
    }

    private Map<String, String> buildNodeCheckUnPassEntity(Set<String> unitCodeSet) {
        FormSchemeDefine formSchemeDefine = this.execManagerDTO.getFormSchemeDefine();
        String period = this.execManagerDTO.getPeriod();
        IEntityDefine entityDefine = this.processRuntimeParamHelper.getProcessEntityDefinition(this.execManagerDTO.getFormSchemeDefine().getTaskKey());
        String entityCaliber = entityDefine.getId();
        IEntityTable entityTable = this.getEntityTable(StringUtils.isEmpty((String)entityCaliber) ? formSchemeDefine.getDw() : entityCaliber, period, formSchemeDefine.getDateTime());
        LinkedHashMap<String, String> unPassEntityMap = new LinkedHashMap<String, String>();
        for (String unitCode : unitCodeSet) {
            IEntityRow targetEntityRow = entityTable.findByEntityKey(unitCode);
            unPassEntityMap.put(unitCode, targetEntityRow.getTitle());
        }
        return unPassEntityMap;
    }

    private Map<String, Map<String, List<String>>> buildNotOperateFormOrFormGroupMap(Map<String, List<String>> unPassMap, Map<Long, List<String>> unPassEntityInfoMap, WorkflowObjectType workflowObjectType) {
        FormSchemeDefine formSchemeDefine = this.execManagerDTO.getFormSchemeDefine();
        String period = this.execManagerDTO.getPeriod();
        IEntityDefine entityDefine = this.processRuntimeParamHelper.getProcessEntityDefinition(this.execManagerDTO.getFormSchemeDefine().getTaskKey());
        String entityCaliber = entityDefine.getId();
        IEntityTable entityTable = this.getEntityTable(StringUtils.isEmpty((String)entityCaliber) ? formSchemeDefine.getDw() : entityCaliber, period, formSchemeDefine.getDateTime());
        LinkedHashMap<String, Map<String, List<String>>> result = new LinkedHashMap<String, Map<String, List<String>>>();
        for (Map.Entry<String, List<String>> entry : unPassMap.entrySet()) {
            long key;
            String unitCode = entry.getKey();
            List<String> unPassFormOrFormGroupKey = entry.getValue();
            IEntityRow targetEntityRow = entityTable.findByEntityKey(unitCode);
            LinkedHashMap<String, List<String>> unPassEntityInfoUnderForm = new LinkedHashMap<String, List<String>>();
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                for (String formKey : unPassFormOrFormGroupKey) {
                    FormDefine formDefine = this.runTimeViewController.getForm(formKey, formSchemeDefine.getKey());
                    key = (unitCode + formDefine.getKey()).hashCode();
                    unPassEntityInfoUnderForm.put(formDefine.getFormCode() + " | " + formDefine.getTitle(), unPassEntityInfoMap.get(key));
                }
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                for (String formGroupKey : unPassFormOrFormGroupKey) {
                    FormGroupDefine formGroupDefine = this.runTimeViewController.getFormGroup(formGroupKey, formSchemeDefine.getKey());
                    key = (unitCode + formGroupDefine.getKey()).hashCode();
                    unPassEntityInfoUnderForm.put(formGroupDefine.getCode() + " | " + formGroupDefine.getTitle(), unPassEntityInfoMap.get(key));
                }
            }
            result.put(targetEntityRow.getCode() + " | " + targetEntityRow.getTitle(), unPassEntityInfoUnderForm);
        }
        return result;
    }

    private boolean isSkip(IEventOperateInfo operateResult) {
        return operateResult == null || WFMonitorCheckResult.CHECK_PASS.equals((Object)operateResult.getCheckResult()) || WFMonitorCheckResult.UN_CHECK.equals((Object)operateResult.getCheckResult());
    }
}

