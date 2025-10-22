/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  javax.annotation.Resource
 *  org.activiti.engine.delegate.DelegateTask
 *  org.activiti.engine.impl.persistence.entity.TaskEntityImpl
 */
package com.jiuqi.nr.bpm.de.dataflow.other.action;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
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
import com.jiuqi.nr.bpm.impl.activiti6.common.DelegateTaskWrapper;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.service.AbstractRuntimeService;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Resource;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetrieveAction
implements IOtherAction {
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private IActionAlias actionAlias;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IRunTimeViewController runtimenViewController;
    @Resource
    private WorkflowSettingService settingService;
    @Autowired
    private List<ProcessTaskBuilder> processTaskBuilders;
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private WorkflowSettingService workflowSettingService;

    @Override
    public boolean enabled(List<Task> tasks, BusinessKey businessKey) {
        List<Task> taskList;
        Optional<ProcessTaskBuilder> processTaskBuilder;
        Optional<ProcessEngine> processEngine;
        AbstractRuntimeService abstractRuntimeService;
        FormSchemeDefine formScheme = this.runtimenViewController.getFormScheme(businessKey.getFormSchemeKey());
        boolean isDefaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        String currTaskId = null;
        if (tasks != null && tasks.size() > 0) {
            currTaskId = tasks.get(0).getUserTaskId();
        } else if (isDefaultWorkflow && (abstractRuntimeService = (AbstractRuntimeService)(processEngine = this.getProcessEngine(businessKey.getFormSchemeKey())).map(ProcessEngine::getRunTimeService).orElse(null)) != null && (processTaskBuilder = this.getProcessTaskBuilderByType(abstractRuntimeService.processType)).isPresent() && (taskList = processTaskBuilder.get().queryTaskByBusinessKey(businessKey)) != null && taskList.size() > 0) {
            Optional<UserTask> userTask = abstractRuntimeService.deployService.getUserTask(null, taskList.get(0).getUserTaskId(), businessKey.getFormSchemeKey());
            currTaskId = userTask.map(UserTask::getId).orElse(null);
        }
        List<Object> uploadStates = new ArrayList();
        if (isDefaultWorkflow) {
            UploadRecordNew uploadState;
            boolean isDefaultShowButton;
            boolean bl = isDefaultShowButton = Objects.equals(currTaskId, "tsk_audit") && formScheme.getFlowsSetting().isAllowTakeBack() || Objects.equals(currTaskId, "tsk_upload") && formScheme.getFlowsSetting().isAllowTakeBackForSubmit();
            if (isDefaultShowButton && (uploadState = this.queryUploadStateService.queryLatestUploadAction(businessKey)) != null) {
                uploadStates.add(uploadState);
            }
        } else {
            uploadStates = this.queryUploadStateService.queryUploadHistoryStates(businessKey);
        }
        if (!uploadStates.isEmpty()) {
            boolean retrieve = isDefaultWorkflow ? Objects.equals(currTaskId, "tsk_audit") && formScheme.getFlowsSetting().isAllowTakeBack() || Objects.equals(currTaskId, "tsk_upload") && formScheme.getFlowsSetting().isAllowTakeBackForSubmit() : this.getBack(currTaskId, uploadStates, formScheme);
            boolean isActor = this.isActor(formScheme, businessKey, isDefaultWorkflow, uploadStates);
            return retrieve && isActor;
        }
        return false;
    }

    @Override
    public WorkflowAction otherAction(List<Task> tasks, BusinessKey businessKey) {
        Map<String, String> actionCodeAndActionName;
        WorkflowAction workflowAction = new WorkflowAction();
        String actionName = "\u53d6\u56de";
        String currTaskId = null;
        if (tasks != null && tasks.size() > 0) {
            currTaskId = tasks.get(0).getUserTaskId();
        }
        if ((actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(businessKey.getFormSchemeKey())) != null && actionCodeAndActionName.size() > 0) {
            for (Map.Entry<String, String> action : actionCodeAndActionName.entrySet()) {
                if (!"act_retrieve".equals(action.getKey())) continue;
                actionName = action.getValue();
            }
        }
        FormSchemeDefine formScheme = this.runtimenViewController.getFormScheme(businessKey.getFormSchemeKey());
        boolean isDefaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        ActionStateBean uploadState = this.queryUploadStateService.queryActionState(businessKey);
        if (uploadState != null) {
            if (isDefaultWorkflow) {
                boolean isDisplayRetrieveAction;
                boolean isSubmitRetrieve = UploadState.SUBMITED.toString().equals(uploadState.getCode());
                boolean isUploadRetrieve = UploadState.UPLOADED.toString().equals(uploadState.getCode());
                boolean bl = isDisplayRetrieveAction = isSubmitRetrieve || isUploadRetrieve;
                if (isDisplayRetrieveAction) {
                    workflowAction = new WorkflowAction();
                    workflowAction.setCode("act_retrieve");
                    workflowAction.setTitle(actionName);
                    workflowAction.setIcon("#icon-_GJHshangbao");
                    workflowAction.setActionParam(new ActionParam());
                }
            } else {
                boolean isDisplayRetrieveAction;
                boolean bl = isDisplayRetrieveAction = UploadState.SUBMITED.toString().equals(uploadState.getCode()) || UploadState.UPLOADED.toString().equals(uploadState.getCode()) || UploadState.CONFIRMED.toString().equals(uploadState.getCode()) || UploadState.RETURNED.toString().equals(uploadState.getCode()) || UploadState.REJECTED.toString().equals(uploadState.getCode());
                if (isDisplayRetrieveAction) {
                    workflowAction = new WorkflowAction();
                    workflowAction.setCode("act_retrieve");
                    workflowAction.setTitle(actionName);
                    workflowAction.setIcon("#icon-_GJHshangbao");
                    workflowAction.setActionParam(new ActionParam());
                }
            }
            return workflowAction;
        }
        return null;
    }

    public WorkflowAction returnAction(String formSchemeKey, DimensionValueSet dim, List<Task> tasks, BusinessKey businessKey, String formKey, String groupKey) {
        ActionStateBean queryUploadState;
        boolean isActor;
        boolean retrieve;
        FormSchemeDefine formScheme;
        boolean isDefaultWorkflow;
        Map<String, String> actionCodeAndActionName;
        WorkflowAction workflowAction = new WorkflowAction();
        String actionName = "\u53d6\u56de";
        String currTaskId = null;
        if (tasks.size() > 0) {
            currTaskId = tasks.get(0).getUserTaskId();
        }
        if ((actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(formSchemeKey)) != null && actionCodeAndActionName.size() > 0) {
            for (Map.Entry<String, String> action : actionCodeAndActionName.entrySet()) {
                if (!"act_retrieve".equals(action.getKey())) continue;
                actionName = action.getValue();
            }
        }
        if ((isDefaultWorkflow = this.workflow.isDefaultWorkflow((formScheme = this.runtimenViewController.getFormScheme(formSchemeKey)).getKey())) && !formScheme.getFlowsSetting().isAllowTakeBack()) {
            return workflowAction;
        }
        List<Object> uploadStates = new ArrayList();
        if (isDefaultWorkflow) {
            UploadRecordNew uploadState = this.queryUploadStateService.queryLatestUploadAction(formScheme.getKey(), dim, formKey, groupKey);
            if (uploadState != null) {
                uploadStates.add(uploadState);
            }
        } else {
            uploadStates = this.queryUploadStateService.queryUploadHistoryStates(formSchemeKey, dim, formKey, groupKey);
        }
        boolean bl = retrieve = isDefaultWorkflow ? true : this.getBack(currTaskId, uploadStates, formScheme);
        if (retrieve && (isActor = this.isActor(formScheme, businessKey, isDefaultWorkflow, uploadStates)) && (queryUploadState = this.queryUploadStateService.queryActionState(formSchemeKey, dim, formKey, groupKey)) != null) {
            if (isDefaultWorkflow) {
                if (UploadState.UPLOADED.toString().equals(queryUploadState.getCode()) || "act_upload".toString().equals(currTaskId)) {
                    workflowAction = new WorkflowAction();
                    workflowAction.setCode("act_retrieve");
                    workflowAction.setTitle(actionName);
                    workflowAction.setIcon("#icon-_GJHshangbao");
                    workflowAction.setActionParam(new ActionParam());
                }
            } else if (UploadState.SUBMITED.toString().equals(queryUploadState.getCode())) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_retrieve");
                workflowAction.setTitle(actionName);
                workflowAction.setIcon("#icon-_GJHshangbao");
                workflowAction.setActionParam(new ActionParam());
            } else if (UploadState.UPLOADED.toString().equals(queryUploadState.getCode())) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_retrieve");
                workflowAction.setTitle(actionName);
                workflowAction.setIcon("#icon-_GJHshangbao");
                workflowAction.setActionParam(new ActionParam());
            } else if (UploadState.CONFIRMED.toString().equals(queryUploadState.getCode())) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_retrieve");
                workflowAction.setTitle(actionName);
                workflowAction.setIcon("#icon-_GJHshangbao");
                workflowAction.setActionParam(new ActionParam());
            }
        }
        return workflowAction;
    }

    private boolean getBack(String currTaskId, List<UploadRecordNew> queryUploadStates, FormSchemeDefine formScheme) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(formScheme.getKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        boolean retrieve = false;
        WorkFlowNodeSet workFlowNode = null;
        if (queryUploadStates != null && queryUploadStates.size() > 0) {
            if ("act_retrieve".equals(queryUploadStates.get(0).getAction())) {
                String taskId = null;
                taskId = queryUploadStates.size() > 2 ? queryUploadStates.get(2).getTaskId() : queryUploadStates.get(1).getTaskId();
                workFlowNode = this.customWorkFolwService.getWorkFlowNodeSetByID(taskId, workFlowDefine.getLinkid());
            } else {
                workFlowNode = this.customWorkFolwService.getWorkFlowNodeSetByID(queryUploadStates.get(0).getTaskId(), workFlowDefine.getLinkid());
            }
        } else {
            workFlowNode = this.customWorkFolwService.getWorkFlowNodeSetByID(currTaskId, workFlowDefine.getLinkid());
        }
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
            if (userTaskId.equals("tsk_audit_after_confirm")) {
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
                taskId = this.workflow.queryRevertTaskId(queryUploadActionsNew, currentTasks.get(0), businessKey);
                if (instance.isPresent() && taskId != null) {
                    ProcessInstance processInstance = instance.get();
                    UserTask task = runtimeService.getRetrievableTask(processInstance.getId(), Actor.fromNpContext(), taskId);
                    TaskEntityImpl historyTask = new TaskEntityImpl();
                    historyTask.setId(UUID.randomUUID().toString());
                    historyTask.setName(task.getName());
                    historyTask.setProcessInstanceId(processInstance.getId());
                    historyTask.setProcessDefinitionId(processInstance.getProcessDefinitionId());
                    historyTask.setTaskDefinitionKey(task.getId());
                    if (task != null) {
                        isActor = runtimeService.isTaskActor(task, businessKey, new DelegateTaskWrapper((DelegateTask)historyTask));
                    }
                }
            }
        }
        return isActor;
    }

    public String queryReturnTaskId(String formSchemeKey) {
        return "";
    }

    private Optional<ProcessTaskBuilder> getProcessTaskBuilderByType(ProcessType processType) {
        return this.processTaskBuilders.stream().filter(e -> e.getProcessType() == processType).findFirst();
    }

    private Optional<ProcessEngine> getProcessEngine(String formSchemeKey) {
        boolean isDefaultFlow = this.isDefaultWorkflow(formSchemeKey);
        if (isDefaultFlow) {
            return this.processEngineProvider.getProcessEngine(ProcessType.DEFAULT);
        }
        return this.processEngineProvider.getProcessEngine();
    }

    public boolean isDefaultWorkflow(String formSchemeKey) {
        WorkflowStatus queryFlowType;
        boolean defaultWorkflow = false;
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(formSchemeKey);
        FlowsType flowsType = formSchemeDefine.getFlowsSetting().getFlowsType();
        if (FlowsType.DEFAULT.equals((Object)flowsType) && WorkflowStatus.DEFAULT.equals((Object)(queryFlowType = this.workflowSettingService.queryFlowType(formSchemeKey)))) {
            defaultWorkflow = true;
        }
        return defaultWorkflow;
    }
}

