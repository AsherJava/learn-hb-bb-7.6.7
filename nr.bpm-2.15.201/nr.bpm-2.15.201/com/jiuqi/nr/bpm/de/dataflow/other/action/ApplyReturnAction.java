/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.other.action;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.de.dataflow.service.IOtherAction;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplyReturnAction
implements IOtherAction {
    private static final Integer TYPE = 1;
    @Autowired
    private IQueryUploadStateService queryUploadStateServiceImpl;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IActionAlias actionAlias;
    @Autowired
    private IWorkflow workflow;
    @Resource
    private WorkflowSettingService settingService;

    @Override
    public boolean enabled(List<Task> tasks, BusinessKey businessKey) {
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        String currTaskId = null;
        if (tasks != null && tasks.size() > 0) {
            currTaskId = tasks.get(0).getUserTaskId();
        }
        boolean isDefaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        List<Object> uploadStates = new ArrayList();
        if (isDefaultWorkflow) {
            UploadRecordNew uploadState;
            if (formScheme.getFlowsSetting().isApplyReturn() && (uploadState = this.queryUploadStateServiceImpl.queryLatestUploadAction(businessKey)) != null) {
                uploadStates.add(uploadState);
            }
        } else {
            uploadStates = this.queryUploadStateServiceImpl.queryUploadHistoryStates(businessKey);
        }
        if (uploadStates.size() > 0) {
            boolean retrieve;
            boolean bl = retrieve = isDefaultWorkflow ? formScheme.getFlowsSetting().isApplyReturn() : this.getBack(currTaskId, uploadStates, formScheme);
            if (!retrieve) {
                return false;
            }
            boolean isActor = this.isActor(formScheme, businessKey, isDefaultWorkflow, uploadStates);
            return retrieve && isActor;
        }
        return false;
    }

    @Override
    public WorkflowAction otherAction(List<Task> tasks, BusinessKey businessKey) {
        Map<String, String> actionCodeAndActionName;
        WorkflowAction workflowAction = new WorkflowAction();
        String actionName = "\u7533\u8bf7\u9000\u56de";
        String currTaskId = null;
        if (tasks != null && tasks.size() > 0) {
            currTaskId = tasks.get(0).getUserTaskId();
        }
        if ((actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(businessKey.getFormSchemeKey())) != null && actionCodeAndActionName.size() > 0) {
            for (Map.Entry<String, String> action : actionCodeAndActionName.entrySet()) {
                if (!"act_apply_return".equals(action.getKey())) continue;
                actionName = action.getValue();
            }
        }
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        boolean isDefaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        ActionParam actionParam = null;
        ActionStateBean queryUploadState = this.queryUploadStateServiceImpl.queryActionState(businessKey);
        if (queryUploadState != null) {
            if (isDefaultWorkflow && (UploadState.UPLOADED.toString().equals(queryUploadState.getCode()) || UploadState.CONFIRMED.toString().equals(queryUploadState.getCode()) || "act_upload".toString().equals(currTaskId))) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_apply_return");
                workflowAction.setTitle(actionName);
                workflowAction.setIcon("#icon-_GJHshangbao");
                actionParam = new ActionParam();
                actionParam.setNeedOptDesc(true);
                workflowAction.setActionParam(actionParam);
            }
            return workflowAction;
        }
        return null;
    }

    public WorkflowAction applyReturnAction(String formSchemeKey, DimensionValueSet dim, List<Task> tasks, BusinessKey businessKey, String formKey, String groupKey) {
        ActionStateBean queryUploadState;
        boolean isActor;
        boolean retrieve;
        FormSchemeDefine formScheme;
        boolean isDefaultWorkflow;
        Map<String, String> actionCodeAndActionName;
        WorkflowAction workflowAction = new WorkflowAction();
        String actionName = "\u7533\u8bf7\u9000\u56de";
        String currTaskId = null;
        if (tasks.size() > 0) {
            currTaskId = tasks.get(0).getUserTaskId();
        }
        if ((actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(formSchemeKey)) != null && actionCodeAndActionName.size() > 0) {
            for (Map.Entry<String, String> action : actionCodeAndActionName.entrySet()) {
                if (!"act_apply_return".equals(action.getKey())) continue;
                actionName = action.getValue();
            }
        }
        if ((isDefaultWorkflow = this.workflow.isDefaultWorkflow((formScheme = this.runtimeViewController.getFormScheme(formSchemeKey)).getKey())) && formScheme.getFlowsSetting().isApplyReturn()) {
            return workflowAction;
        }
        List<Object> uploadStates = new ArrayList();
        if (isDefaultWorkflow) {
            UploadRecordNew uploadState = this.queryUploadStateServiceImpl.queryLatestUploadAction(formScheme.getKey(), dim, formKey, groupKey);
            if (uploadState != null) {
                uploadStates.add(uploadState);
            }
        } else {
            uploadStates = this.queryUploadStateServiceImpl.queryUploadHistoryStates(formScheme.getKey(), dim, formKey, groupKey);
        }
        boolean bl = retrieve = isDefaultWorkflow ? true : this.getBack(currTaskId, uploadStates, formScheme);
        if (retrieve && (isActor = this.isActor(formScheme, businessKey, isDefaultWorkflow, uploadStates)) && (queryUploadState = this.queryUploadStateServiceImpl.queryActionState(formSchemeKey, dim, formKey, groupKey)) != null) {
            if (isDefaultWorkflow) {
                if (UploadState.UPLOADED.toString().equals(queryUploadState.getCode()) || "act_upload".toString().equals(currTaskId)) {
                    workflowAction = new WorkflowAction();
                    workflowAction.setCode("act_apply_return");
                    workflowAction.setTitle(actionName);
                    workflowAction.setIcon("#icon-_GJHshangbao");
                    workflowAction.setActionParam(new ActionParam());
                }
            } else if (UploadState.SUBMITED.toString().equals(queryUploadState.getCode())) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_apply_return");
                workflowAction.setTitle(actionName);
                workflowAction.setIcon("#icon-_GJHshangbao");
                workflowAction.setActionParam(new ActionParam());
            } else if (UploadState.UPLOADED.toString().equals(queryUploadState.getCode())) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_apply_return");
                workflowAction.setTitle(actionName);
                workflowAction.setIcon("#icon-_GJHshangbao");
                workflowAction.setActionParam(new ActionParam());
            } else if (UploadState.CONFIRMED.toString().equals(queryUploadState.getCode())) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_apply_return");
                workflowAction.setTitle(actionName);
                workflowAction.setIcon("#icon-_GJHshangbao");
                workflowAction.setActionParam(new ActionParam());
            }
        }
        return workflowAction;
    }

    public void optResult(int type) {
        if (TYPE == type) {
            // empty if block
        }
    }

    private boolean getBack(String currTaskId, List<UploadRecordNew> queryUploadStates, FormSchemeDefine formScheme) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(formScheme.getKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        boolean retrieve = false;
        WorkFlowNodeSet workFlowNode = null;
        workFlowNode = queryUploadStates != null && queryUploadStates.size() > 0 ? this.customWorkFolwService.getWorkFlowNodeSetByID(queryUploadStates.get(0).getTaskId(), workFlowDefine.getLinkid()) : this.customWorkFolwService.getWorkFlowNodeSetByID(currTaskId, workFlowDefine.getLinkid());
        if (workFlowNode != null) {
            retrieve = workFlowNode.isGetback();
        }
        return retrieve;
    }

    public boolean isActor(FormSchemeDefine formScheme, BusinessKey businessKey, boolean defaultProcess, List<UploadRecordNew> queryUploadActionsNew) {
        String taskId = null;
        boolean isActor = false;
        Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formScheme.getKey());
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        List<Task> currentTasks = runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
        if (defaultProcess) {
            if (queryUploadActionsNew == null || queryUploadActionsNew.isEmpty()) {
                return false;
            }
            String userTaskId = queryUploadActionsNew.get(0).getTaskId();
            if (userTaskId.equals("tsk_audit_after_confirm") || userTaskId.equals("tsk_audit")) {
                userTaskId = "tsk_upload";
            }
            Optional<UserTask> targetTask = deployService.getUserTask(null, userTaskId, businessKey.getFormSchemeKey());
            taskId = targetTask.get().getId();
            if (targetTask.isPresent()) {
                isActor = runtimeService.isTaskActor(targetTask.get(), businessKey, currentTasks.get(0));
            }
        } else {
            Optional<ProcessInstance> instance = runtimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            if (currentTasks != null && currentTasks.size() > 0) {
                UserTask task;
                taskId = this.workflow.queryRevertTaskId(queryUploadActionsNew, currentTasks.get(0), businessKey);
                if (instance.isPresent() && taskId != null && (task = runtimeService.getRetrievableTask(instance.get().getId(), Actor.fromNpContext(), taskId)) != null) {
                    isActor = runtimeService.isTaskActor(task, businessKey, currentTasks.get(0));
                }
            }
        }
        return isActor;
    }

    public String queryReturnTaskId(String formSchemeKey) {
        return "";
    }
}

