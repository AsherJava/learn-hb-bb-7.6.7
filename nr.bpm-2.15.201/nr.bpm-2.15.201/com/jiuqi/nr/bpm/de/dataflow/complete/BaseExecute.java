/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.message.TodoCompleteEvent
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationService
 */
package com.jiuqi.nr.bpm.de.dataflow.complete;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.message.TodoCompleteEvent;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BaseExecute {
    private static final Logger logger = LoggerFactory.getLogger(BaseExecute.class);
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    protected IWorkflow workflow;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IQueryUploadStateService queryUploadStateServiceImpl;
    @Autowired
    DeEntityHelper deEntityHelper;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private ISendMessage sendMessage;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    protected TodoManipulationService todoManipulationService;
    @Value(value="${jiuqi.nr.todo.version:2.0}")
    protected String todoVersion;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private RoleService roleService;

    public CompleteMsg isFillTime(String fromSchemeKey, DimensionValueSet dimValueSet) {
        CompleteMsg completeMsg = new CompleteMsg();
        MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(fromSchemeKey, dimValueSet);
        if (compareSetTime.isDisabled()) {
            completeMsg.setSucceed(true);
            completeMsg.setMsg("\u4e0d\u5728\u586b\u62a5\u65f6\u95f4\u8303\u56f4\u5185");
            return completeMsg;
        }
        return completeMsg;
    }

    public DimensionValueSet buildDimensionValueSet(FormSchemeDefine formSchemeDefine, DimensionValueSet dimvalueSet) {
        return this.dimensionUtil.fliterDimensionValueSet(dimvalueSet, formSchemeDefine);
    }

    public Optional<ProcessEngine> getProcessEngine(String formSchemeKey) {
        return this.workflow.getProcessEngine(formSchemeKey);
    }

    public boolean isDefaultWorkflow(String formSchemeKey) {
        return this.workflow.isDefaultWorkflow(formSchemeKey);
    }

    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        return this.commonUtil.getFormScheme(formSchemeKey);
    }

    public String getMinName(String formSchemeKey) {
        return this.dimensionUtil.getDwMainDimName(formSchemeKey);
    }

    public String queryRevertTaskId(List<UploadRecordNew> queryUploadActionsNew, Task currentTask, BusinessKey businessKey) {
        return this.workflow.queryRevertTaskId(queryUploadActionsNew, currentTask, businessKey);
    }

    public String getForceKey() {
        return this.nrParameterUtils.getForceMapKey();
    }

    public List<UploadRecordNew> queryUploadActionsNew(BusinessKey businessKey) {
        return this.queryUploadStateServiceImpl.queryUploadHistoryStates(businessKey);
    }

    public ActionStateBean queryUploadState(DimensionValueSet dimSet, String formKey, FormSchemeDefine formScheme) {
        return this.queryUploadStateServiceImpl.queryActionState(formScheme.getKey(), dimSet, formKey);
    }

    public ActionStateBean queryUploadState(DimensionValueSet dimSet, String formKey, String groupKey, FormSchemeDefine formScheme) {
        return this.queryUploadStateServiceImpl.queryActionState(formScheme.getKey(), dimSet, formKey, groupKey);
    }

    public List<UploadStateNew> queryUploadStates(String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> formGroupKeys) {
        return this.queryUploadStateServiceImpl.queryUploadStates(formSchemeKey, dimensionValueSet, formKeys, formGroupKeys);
    }

    public String getParentId(DimensionValueSet dimVaueSet, String formSchemeKey) {
        String minName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        String period = dimVaueSet.getValue("DATATIME").toString();
        return this.deEntityHelper.getParentId(dimVaueSet.getValue(minName).toString(), formSchemeKey, period);
    }

    public String getFormKey(BusinessKey businessKey) {
        WorkFlowType startType = this.workflow.queryStartType(businessKey.getFormSchemeKey());
        return startType == WorkFlowType.FORM || startType == WorkFlowType.GROUP ? businessKey.getFormKey() : null;
    }

    public CompleteMsg errorMsg(CompleteMsg completeMsg, String msg) {
        completeMsg.setSucceed(false);
        completeMsg.setMsg(msg);
        return completeMsg;
    }

    public CompleteMsg succMsg(CompleteMsg completeMsg, String msg) {
        completeMsg.setSucceed(true);
        completeMsg.setMsg(msg);
        return completeMsg;
    }

    public String getPrevent(boolean isWorkFlow, List<UploadRecordNew> queryUploadActionsNew, FormSchemeDefine formSchemeDefine, String currentTaskId, String targetTaskId) {
        String preEvent = null;
        if (isWorkFlow) {
            if (!queryUploadActionsNew.isEmpty()) {
                List<WorkFlowLine> workflowLines;
                String curTaskId = currentTaskId;
                for (int i = 0; i < queryUploadActionsNew.size(); ++i) {
                    if (curTaskId.equals(queryUploadActionsNew.get(i).getTaskId())) continue;
                    if (i + 1 >= queryUploadActionsNew.size() || !"act_retrieve".equals(preEvent = queryUploadActionsNew.get(i + 1).getAction()) || i + 2 >= queryUploadActionsNew.size()) break;
                    preEvent = queryUploadActionsNew.get(i + 2).getAction();
                    break;
                }
                if ((workflowLines = this.commonUtil.getWorkflowLines(targetTaskId, formSchemeDefine)).size() > 0 && preEvent != null) {
                    for (WorkFlowLine workFlowLine : workflowLines) {
                        if (!preEvent.equals("cus_reject") && !preEvent.equals("cus_return")) {
                            if (workFlowLine.getActionid() != null) continue;
                            preEvent = null;
                        }
                        break;
                    }
                }
            }
        } else {
            preEvent = formSchemeDefine.getFlowsSetting().isUnitSubmitForCensorship() ? (currentTaskId.equals("tsk_upload") ? "start" : "act_submit") : "start";
        }
        return preEvent;
    }

    private boolean isSubmitRetrieve(List<UploadRecordNew> queryUploadActionsNew) {
        UploadRecordNew latestHistoryState;
        UploadRecordNew targetHistoryState = queryUploadActionsNew.size() > 1 ? ((latestHistoryState = queryUploadActionsNew.get(0)).getAction().equals("act_retrieve") ? queryUploadActionsNew.get(1) : latestHistoryState) : queryUploadActionsNew.get(0);
        String action = targetHistoryState.getAction();
        String taskId = targetHistoryState.getTaskId();
        return action.equals("act_submit") && taskId.equals("tsk_submit") || action.equals("act_upload") && taskId.equals("tsk_upload");
    }

    public void sendTodoMessage(Task task, BusinessKey businessKey, String actionCode, String content, boolean sendEmail) {
        if (SendMessageTaskConfig.canSendMessage()) {
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            this.sendMessage.send(task, businessKey, actionCode, content, sendEmail, fullname);
        }
    }

    public void sendTodoMessage(Task task, BusinessKey businessKey, String actionCode, String content, boolean sendEmail, int canUploadUnitSize, Set<String> formOrGroupKeys) {
        if (SendMessageTaskConfig.canSendMessage()) {
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            this.sendMessage.send(task, businessKey, actionCode, content, sendEmail, canUploadUnitSize, formOrGroupKeys, fullname);
        }
    }

    public void sendRetrieveMessage(Task task, Optional<UserTask> userTask, BusinessKey businessKey, DimensionValueSet dim, String formSchemeKey, Task currentTask) {
        if (SendMessageTaskConfig.canSendMessage()) {
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            this.sendMessage.sendRetrieveMessage(task, userTask, businessKey, dim, formSchemeKey, currentTask, fullname);
        }
    }

    public void clearMessage(String formSchemeKey, DimensionValueSet dims, String taskId, Set<String> formKeyOrGroupKeys, String actionCode, String taskCode) {
        if (SendMessageTaskConfig.canSendMessage()) {
            if (formKeyOrGroupKeys != null && formKeyOrGroupKeys.size() > 0) {
                formKeyOrGroupKeys.forEach(formKey -> this.clearMessage1(formSchemeKey, dims, taskId, (String)formKey, actionCode, taskCode));
            } else {
                this.clearMessage1(formSchemeKey, dims, taskId, null, actionCode, taskCode);
            }
        }
    }

    public void clearMessage1(String formSchemeKey, DimensionValueSet dims, String taskId, String formKeyOrGroupKey, String actionCode, String taskCode) {
        if (SendMessageTaskConfig.canSendMessage()) {
            WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
            if (WorkFlowType.FORM == startType) {
                this.clearMessage(null, formSchemeKey, dims, taskId, formKeyOrGroupKey, null, actionCode, taskCode);
            } else if (WorkFlowType.GROUP == startType) {
                this.clearMessage(null, formSchemeKey, dims, taskId, null, formKeyOrGroupKey, actionCode, taskCode);
            } else if (WorkFlowType.ENTITY == startType) {
                this.clearMessage(null, formSchemeKey, dims, taskId, null, null, actionCode, taskCode);
            }
        }
    }

    public void clearMessage(String msgid, String formSchemeKey, DimensionValueSet dims, String taskId, String formKey, String groupKey, String actionCode, String taskCode) {
        block28: {
            if (SendMessageTaskConfig.canSendMessage()) {
                String roleOrUserKey2 = "";
                boolean mulitiInstanceTask = false;
                boolean signNodeByRoleType = false;
                boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
                if (!defaultWorkflow) {
                    String identityId = NpContextHolder.getContext().getIdentityId();
                    Set idByIdentity = this.roleService.getIdByIdentity(identityId);
                    if (idByIdentity != null && idByIdentity.size() > 0) {
                        for (String roleOrUserKey2 : idByIdentity) {
                        }
                    }
                    mulitiInstanceTask = this.nrParameterUtils.isMulitiInstanceTask(taskCode, formSchemeKey);
                    signNodeByRoleType = this.nrParameterUtils.isSignNodeByRoleType(taskCode, formSchemeKey);
                }
                if (this.todoVersion.equals("1.0")) {
                    try {
                        WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
                        String period = dims.getValue("DATATIME").toString();
                        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
                        String unitObject = dims.getValue(mainDimName).toString();
                        if (unitObject instanceof ArrayList) {
                            List unitIds = (List)dims.getValue(mainDimName);
                            for (String unit : unitIds) {
                                if (msgid == null) {
                                    if (WorkFlowType.ENTITY.equals((Object)startType)) {
                                        msgid = formSchemeKey + ";" + period + ";" + unit + ";" + taskCode;
                                    } else {
                                        String formOrGroupKey = startType == WorkFlowType.FORM ? formKey : (startType == WorkFlowType.GROUP ? groupKey : null);
                                        msgid = formSchemeKey + ";" + period + ";" + unit + ";" + formOrGroupKey + ";" + taskCode;
                                    }
                                }
                                TodoCompleteEvent todoCompleteEvent = new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode);
                                todoCompleteEvent.setSign(mulitiInstanceTask);
                                todoCompleteEvent.setSignType(signNodeByRoleType ? "role" : "user");
                                todoCompleteEvent.setSignTag(roleOrUserKey2);
                                this.applicationContext.publishEvent(todoCompleteEvent);
                            }
                            break block28;
                        }
                        String unitId = dims.getValue(mainDimName).toString();
                        if (msgid == null) {
                            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                                msgid = formSchemeKey + ";" + period + ";" + unitId + ";" + taskCode;
                            } else {
                                String formOrGroupKey = startType == WorkFlowType.FORM ? formKey : (startType == WorkFlowType.GROUP ? groupKey : null);
                                msgid = formSchemeKey + ";" + period + ";" + unitId + ";" + formOrGroupKey + ";" + taskCode;
                            }
                        }
                        TodoCompleteEvent todoCompleteEvent = new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode);
                        todoCompleteEvent.setSign(mulitiInstanceTask);
                        todoCompleteEvent.setSignType(signNodeByRoleType ? "role" : "user");
                        todoCompleteEvent.setSignTag(roleOrUserKey2);
                        this.applicationContext.publishEvent(todoCompleteEvent);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                } else if (this.todoVersion.equals("2.0")) {
                    FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
                    TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
                    try {
                        WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
                        String period = dims.getValue("DATATIME").toString();
                        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
                        String unitObject = dims.getValue(mainDimName).toString();
                        Object adjust = dims.getValue("ADJUST");
                        String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), dims);
                        if (unitObject instanceof ArrayList) {
                            List unitIds = (List)dims.getValue(mainDimName);
                            for (String unit : unitIds) {
                                msgid = this.workflow.getMessageId(formSchemeKey, period, unit, adjust == null ? null : adjust.toString(), formKey, groupKey, startType, taskCode, corporateValue);
                                if (adjust != null) {
                                    this.clearMessage(msgid, formSchemeKey, period, unit, adjust.toString(), formKey, groupKey, startType, taskId, taskCode, corporateValue, mulitiInstanceTask, actionCode);
                                } else {
                                    this.clearMessage(msgid, formSchemeKey, period, unit, null, formKey, groupKey, startType, taskId, taskCode, corporateValue, mulitiInstanceTask, actionCode);
                                }
                                if (flowsSetting.isApplyReturn() && taskCode.equals("tsk_audit")) {
                                    this.clearApplyReturnTodo(msgid);
                                }
                                TodoCompleteEvent todoCompleteEvent = new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode);
                                todoCompleteEvent.setSign(mulitiInstanceTask);
                                todoCompleteEvent.setSignType(signNodeByRoleType ? "role" : "user");
                                todoCompleteEvent.setSignTag(roleOrUserKey2);
                                this.applicationContext.publishEvent(todoCompleteEvent);
                            }
                        } else {
                            String unitId = dims.getValue(mainDimName).toString();
                            msgid = this.workflow.getMessageId(formSchemeKey, period, unitId, adjust == null ? null : adjust.toString(), formKey, groupKey, startType, taskCode, corporateValue);
                            if (adjust != null) {
                                this.clearMessage(msgid, formSchemeKey, period, unitId, adjust.toString(), formKey, groupKey, startType, taskId, taskCode, corporateValue, mulitiInstanceTask, actionCode);
                            } else {
                                this.clearMessage(msgid, formSchemeKey, period, unitId, null, formKey, groupKey, startType, taskId, taskCode, corporateValue, mulitiInstanceTask, actionCode);
                            }
                            if (flowsSetting.isApplyReturn() && taskCode.equals("tsk_audit")) {
                                this.clearApplyReturnTodo(msgid);
                            }
                            TodoCompleteEvent todoCompleteEvent = new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode);
                            todoCompleteEvent.setSign(mulitiInstanceTask);
                            todoCompleteEvent.setSignType(signNodeByRoleType ? "role" : "user");
                            todoCompleteEvent.setSignTag(roleOrUserKey2);
                            this.applicationContext.publishEvent(todoCompleteEvent);
                        }
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    public void clearReturnMessage(String msgid, String formSchemeKey, DimensionValueSet dims, String taskId, String formKey, String groupKey, String actionCode, String taskCode) {
        if (SendMessageTaskConfig.canSendReturnMessage()) {
            try {
                WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
                String period = dims.getValue("DATATIME").toString();
                String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
                String unitObject = dims.getValue(mainDimName).toString();
                Object adjust = dims.getValue("ADJUST");
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
                String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), dims);
                if (unitObject instanceof ArrayList) {
                    List unitIds = (List)dims.getValue(mainDimName);
                    for (String unit : unitIds) {
                        if (adjust != null) {
                            this.clearMessage(msgid, formSchemeKey, period, unit, adjust.toString(), formKey, groupKey, startType, taskId, taskCode, corporateValue, false, actionCode);
                            continue;
                        }
                        this.clearMessage(msgid, formSchemeKey, period, unit, null, formKey, groupKey, startType, taskId, taskCode, corporateValue, false, actionCode);
                    }
                } else {
                    String unitId = dims.getValue(mainDimName).toString();
                    if (adjust != null) {
                        this.clearMessage(msgid, formSchemeKey, period, unitId, adjust.toString(), formKey, groupKey, startType, taskId, taskCode, corporateValue, false, actionCode);
                    } else {
                        this.clearMessage(msgid, formSchemeKey, period, unitId, null, formKey, groupKey, startType, taskId, taskCode, corporateValue, false, actionCode);
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void completeMsgVersion1_0(String msgid, String actionCode) {
        if (SendMessageTaskConfig.canSendMessage()) {
            try {
                this.applicationContext.publishEvent(new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void completeMsg(String msgid, String actionCode) {
        if (SendMessageTaskConfig.canSendMessage()) {
            if (this.todoVersion.equals("1.0")) {
                try {
                    this.applicationContext.publishEvent(new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode));
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            } else if (this.todoVersion.equals("2.0")) {
                this.applicationContext.publishEvent(new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode));
                this.clearMessage(msgid);
            }
        }
    }

    public void completeReturnMsg(String msgid, String actionCode) {
        if (SendMessageTaskConfig.canSendReturnMessage()) {
            try {
                this.clearMessage(msgid);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void clearMessage(String msgId) {
        this.clearMessage(msgId, null, null, null, null, null, null, null, null, null, null, false, null);
    }

    private void clearMessage(String msgid, String formSchemeKey, String period, String unitId, String adjust, String formKey, String groupKey, WorkFlowType workflowType, String taskId, String taskCode, String corporateValue, boolean mulitiInstanceTask, String actionCode) {
        if (msgid == null) {
            msgid = this.workflow.getMessageId(formSchemeKey, period, unitId, adjust, formKey, groupKey, workflowType, taskCode, corporateValue);
        }
        if (mulitiInstanceTask) {
            if (actionCode != null && ("act_return".equals(actionCode) || "cus_return".equals(actionCode) || "act_reject".equals(actionCode) || "cus_reject".equals(actionCode))) {
                this.todoManipulationService.batchClearTodo(Collections.singletonList(ProcessBuilderUtils.produceUUIDKey(msgid)));
            } else {
                this.todoManipulationService.deleteTodoMessageByCurrentUser(ProcessBuilderUtils.produceUUIDKey(msgid));
            }
        } else {
            this.todoManipulationService.batchClearTodo(Collections.singletonList(ProcessBuilderUtils.produceUUIDKey(msgid)));
        }
    }

    private void clearApplyReturnTodo(String msgId) {
        if (SendMessageTaskConfig.canSendReturnMessage()) {
            TodoConsumeInfoImpl todoConsumeInfoImpl = new TodoConsumeInfoImpl();
            todoConsumeInfoImpl.setBusinessKey(msgId);
            todoConsumeInfoImpl.setWorkflowInstance(ProcessBuilderUtils.produceUUIDKey(msgId));
            todoConsumeInfoImpl.setWorkflowNodeTask(TodoNodeType.REQUEST_REJECT.title);
            this.todoManipulationService.consumeTodo((TodoConsumeInfo)todoConsumeInfoImpl);
        }
    }
}

