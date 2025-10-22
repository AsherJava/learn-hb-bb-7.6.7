/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserAction;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleRejectFormActions {
    private static final Logger logger = LoggerFactory.getLogger(SingleRejectFormActions.class);
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Autowired
    private ActionMethod actionMethod;
    @Autowired
    private IWorkflow workflow;

    public List<WorkflowAction> getRejectFormActions(DeployService deployService, RunTimeService runtimeService, String businessKey, WorkFlowType startType, DimensionValueSet dimensionValueSet, FormSchemeDefine formScheme, Task currTask, String formKey) {
        ArrayList<WorkflowAction> worlflowActionList = new ArrayList<WorkflowAction>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        if (!defaultWorkflow) {
            return worlflowActionList;
        }
        if (!formScheme.getFlowsSetting().getDesignFlowSettingDefine().isAllowFormBack() || !startType.equals((Object)WorkFlowType.ENTITY)) {
            return worlflowActionList;
        }
        boolean isReject = this.singleFormRejectService.isRejectOrReturnForm(dimensionValueSet, formScheme.getKey(), formKey, "single_form_reject");
        boolean isSubmit = formScheme.getFlowsSetting().getDesignFlowSettingDefine().isUnitSubmitForCensorship();
        boolean isConfirm = formScheme.getFlowsSetting().getDesignFlowSettingDefine().isDataConfirm();
        String currTaskId = currTask.getUserTaskId();
        Optional<UserTask> currUserTask = deployService.getUserTask(currTask.getProcessDefinitionId(), currTask.getUserTaskId(), formScheme.getKey());
        List userAction = currUserTask.map(usertask -> usertask.getActions()).orElse(null);
        HashSet<WorkflowAction> workFlowActions = new HashSet<WorkflowAction>();
        Map<String, WorkflowAction> actionMap = new CommonAction().getActionMap();
        boolean isActor = runtimeService.isTaskActor(currUserTask.get(), BusinessKeyFormatter.parsingFromString(businessKey), currTask);
        if (!isReject && isActor) {
            for (UserAction action : userAction) {
                if (!action.getId().equals("act_reject") && !action.getId().equals("act_return")) continue;
                workFlowActions.add(actionMap.get("single_form_reject"));
            }
        }
        ActionParam actionParam = this.actionMethod.getWlrkflowParam(formScheme.getKey(), "act_upload", dimensionValueSet);
        WorkflowAction uplaodAction = actionMap.get("single_form_upload");
        uplaodAction.setActionParam(actionParam);
        boolean isLastAuditNode = currTaskId.equals("tsk_audit_after_confirm");
        boolean isUploadActor = this.isTargetTaskActor(runtimeService, currTask, "tsk_upload", businessKey);
        if (isSubmit && isConfirm) {
            if (isReject) {
                if (isLastAuditNode) {
                    if (isUploadActor) {
                        workFlowActions.add(uplaodAction);
                    }
                    if (this.isTargetTaskActor(runtimeService, currTask, "tsk_audit", businessKey)) {
                        workFlowActions.add(uplaodAction);
                    }
                }
                if (currTaskId.equals("tsk_audit") && isUploadActor) {
                    workFlowActions.add(uplaodAction);
                }
                if (this.isTargetTaskActor(runtimeService, currTask, "tsk_submit", businessKey)) {
                    workFlowActions.add(uplaodAction);
                }
            }
        } else if (isConfirm) {
            if (isReject) {
                if (isLastAuditNode) {
                    if (isUploadActor) {
                        workFlowActions.add(uplaodAction);
                    }
                    if (this.isTargetTaskActor(runtimeService, currTask, "tsk_audit", businessKey)) {
                        workFlowActions.add(uplaodAction);
                    }
                }
                if (currTaskId.equals("tsk_audit") && isUploadActor) {
                    workFlowActions.add(uplaodAction);
                }
            }
        } else if (isSubmit) {
            if (isReject) {
                if (currTaskId.equals("tsk_audit")) {
                    if (isUploadActor) {
                        workFlowActions.add(uplaodAction);
                    }
                    if (this.isTargetTaskActor(runtimeService, currTask, "tsk_submit", businessKey)) {
                        workFlowActions.add(uplaodAction);
                    }
                }
                if (currTaskId.equals("tsk_upload") && this.isTargetTaskActor(runtimeService, currTask, "tsk_submit", businessKey)) {
                    workFlowActions.add(uplaodAction);
                }
            }
        } else if (isReject && currTaskId.equals("tsk_audit") && isUploadActor) {
            workFlowActions.add(uplaodAction);
        }
        worlflowActionList.addAll(workFlowActions.stream().collect(Collectors.toList()));
        return worlflowActionList;
    }

    public boolean isTargetTaskActor(RunTimeService runTimeService, Task currTask, String targetTaskId, String businessKey) {
        Optional<UserTask> targetTask = runTimeService.getTargetTaskById(currTask, targetTaskId, BusinessKeyFormatter.parsingFromString(businessKey));
        return runTimeService.isTaskActor(targetTask.get(), BusinessKeyFormatter.parsingFromString(businessKey), currTask);
    }

    public boolean reportReadOnly(FormSchemeDefine formScheme, DimensionValueSet dimSet, String formKey) {
        try {
            if (formScheme.getFlowsSetting().getDesignFlowSettingDefine().isAllowFormBack()) {
                return this.singleFormRejectService.isCanWrite(dimSet, formScheme.getKey(), formKey);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public WorkflowDataInfo singleRejectAction(FormSchemeDefine formScheme, BusinessKey businessKey, DimensionValueSet dims, String formKey, RunTimeService runtimeService, DeployService deployService, WorkFlowType workflowType) {
        boolean allowFormBack;
        WorkflowDataInfo workflowDataInfo = null;
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        if (defaultWorkflow && (allowFormBack = formScheme.getFlowsSetting().isAllowFormBack())) {
            List<Task> queryTasks = this.workflow.queryTasks(formScheme.getKey(), businessKey);
            for (Task task : queryTasks) {
                workflowDataInfo = new WorkflowDataInfo();
                List<WorkflowAction> otherAction = this.getRejectFormActions(deployService, runtimeService, BusinessKeyFormatter.formatToString(businessKey), workflowType, dims, formScheme, task, formKey);
                workflowDataInfo.setTaskId(task.getId());
                workflowDataInfo.setTaskCode(task.getUserTaskId());
                workflowDataInfo.setWorkFlowType(workflowType);
                workflowDataInfo.setActions(otherAction);
            }
        }
        return workflowDataInfo;
    }

    public Set<String> queryFormKeysByAction(DimensionValueSet dimensionSet, String formSchemeKey, String actionCode) {
        return this.singleFormRejectService.getFormKeysByAction(dimensionSet, formSchemeKey, actionCode);
    }

    private class CommonAction {
        private Map<String, WorkflowAction> actionMap = new HashMap<String, WorkflowAction>();

        public Map<String, WorkflowAction> getActionMap() {
            return this.actionMap;
        }

        public CommonAction() {
            WorkflowAction workflowAction = null;
            ActionParam actionParam = null;
            workflowAction = new WorkflowAction();
            workflowAction.setCode("single_form_reject");
            workflowAction.setTitle("\u6309\u8868\u9a73\u56de");
            workflowAction.setIcon(SingleRejectFormActions.this.actionMethod.getIcon("act_reject"));
            actionParam = new ActionParam();
            actionParam.setBatchOpt(true);
            workflowAction.setActionParam(actionParam);
            this.actionMap.put("single_form_reject", workflowAction);
            workflowAction = new WorkflowAction();
            workflowAction.setCode("single_form_return");
            workflowAction.setTitle("\u5355\u8868\u9000\u5ba1");
            workflowAction.setIcon(SingleRejectFormActions.this.actionMethod.getIcon("act_return"));
            actionParam = new ActionParam();
            actionParam.setBatchOpt(true);
            workflowAction.setActionParam(actionParam);
            this.actionMap.put("single_form_return", workflowAction);
            workflowAction = new WorkflowAction();
            workflowAction.setCode("single_form_upload");
            workflowAction.setTitle("\u91cd\u65b0\u63d0\u4ea4");
            workflowAction.setIcon(SingleRejectFormActions.this.actionMethod.getIcon("act_upload"));
            actionParam = new ActionParam();
            actionParam.setBatchOpt(true);
            workflowAction.setActionParam(actionParam);
            this.actionMap.put("single_form_upload", workflowAction);
            workflowAction = new WorkflowAction();
            workflowAction.setCode("single_form_submit");
            workflowAction.setTitle("\u91cd\u65b0\u9001\u5ba1");
            workflowAction.setIcon(SingleRejectFormActions.this.actionMethod.getIcon("act_submit"));
            actionParam = new ActionParam();
            actionParam.setBatchOpt(true);
            workflowAction.setActionParam(actionParam);
            this.actionMap.put("single_form_submit", workflowAction);
        }
    }
}

