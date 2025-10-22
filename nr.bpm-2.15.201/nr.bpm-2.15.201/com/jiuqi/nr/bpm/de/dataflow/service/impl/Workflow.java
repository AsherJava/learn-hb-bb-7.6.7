/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ProcessAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.ProcessTaskNode;
import com.jiuqi.nr.bpm.de.dataflow.bean.StatisticalStateData;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.complete.AutoComplete;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeIconAndColor;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Workflow
implements IWorkflow {
    private static final Logger logger = LoggerFactory.getLogger(Workflow.class);
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private AutoComplete autoCompelete;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private ActionAndStateUtil actionAndStateUtil;
    @Autowired
    private ActionMethod actionMethod;
    @Autowired
    private TreeIconAndColor treeIconAndColor;

    @Override
    public WorkFlowType queryStartType(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
            if (formScheme == null) {
                return null;
            }
            WorkFlowType wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
            return wordFlowType;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean bindProcess(String formSchemeKey, DimensionValueSet dim, String formKey, String groupKey) {
        String formOrGroupKey;
        String unitId;
        String period = (String)dim.getValue("DATATIME");
        WorkflowStatus queryFlowType = this.workflowSettingService.queryFlowType(formSchemeKey, period, unitId = (String)dim.getValue(this.dimensionUtil.getDwMainDimName(formSchemeKey)), formOrGroupKey = this.getFormOrGroupKey(formSchemeKey, formKey, groupKey));
        return !WorkflowStatus.NOSTARTUP.equals((Object)queryFlowType);
    }

    @Override
    public boolean bindProcess(String formSchemeKey, DimensionValueSet dim, String formKeyOrGroupKey) {
        String unitId;
        String period = (String)dim.getValue("DATATIME");
        WorkflowStatus queryFlowType = this.workflowSettingService.queryFlowType(formSchemeKey, period, unitId = (String)dim.getValue(this.dimensionUtil.getDwMainDimName(formSchemeKey)), formKeyOrGroupKey);
        return !WorkflowStatus.NOSTARTUP.equals((Object)queryFlowType);
    }

    @Override
    public String getFormOrGroupKey(String formSchemeKey, String formKey, String groupKey) {
        WorkFlowType startType = this.queryStartType(formSchemeKey);
        String formOrGroupKey = startType == WorkFlowType.FORM ? formKey : (startType == WorkFlowType.GROUP ? groupKey : this.nrParameterUtils.getDefaultFormId(formSchemeKey));
        return formOrGroupKey;
    }

    @Override
    public List<String> getFormOrGroupKey(String formSchemeKey, List<String> formKeys, List<String> groupKeys) {
        ArrayList<String> formOrGroupKeys = new ArrayList<String>();
        WorkFlowType startType = this.queryStartType(formSchemeKey);
        if (WorkFlowType.FORM.equals((Object)startType)) {
            formOrGroupKeys.addAll(formKeys);
        } else if (WorkFlowType.GROUP.equals((Object)startType)) {
            formOrGroupKeys.addAll(groupKeys);
        } else {
            String defaultFormId = this.nrParameterUtils.getDefaultFormId(formSchemeKey);
            if (defaultFormId != null) {
                formOrGroupKeys.add(defaultFormId);
            }
        }
        return formOrGroupKeys;
    }

    @Override
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

    @Override
    public Optional<ProcessEngine> getProcessEngine(String formSchemeKey) {
        boolean isDefaultFlow = this.isDefaultWorkflow(formSchemeKey);
        if (isDefaultFlow) {
            return this.processEngineProvider.getProcessEngine(ProcessType.DEFAULT);
        }
        return this.processEngineProvider.getProcessEngine();
    }

    @Override
    public List<Task> queryTasks(String formSchemeKey, String formKey, String groupKey, DimensionValueSet filterDims, BusinessKey businessKey, boolean isStart) {
        List<Task> tasks = new ArrayList<Task>();
        boolean isDefaultFlow = this.isDefaultWorkflow(formSchemeKey);
        boolean bindProcess = this.bindProcess(formSchemeKey, filterDims, formKey, groupKey);
        Optional<ProcessEngine> processEngine = this.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Actor candicateActor = Actor.fromNpContext();
        if (isDefaultFlow) {
            if (bindProcess) {
                tasks = runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, isStart);
            }
        } else {
            Optional<ProcessInstance> instance = runtimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false, null);
            if (!Optional.empty().equals(instance)) {
                tasks = runtimeService.queryTaskByProcessInstance(instance.get().getId(), candicateActor);
            }
        }
        return tasks;
    }

    @Override
    public String queryTaskId(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String groupKey) {
        String taskId = null;
        List<Object> tasks = new ArrayList();
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(formSchemeKey);
        boolean isDefaultFlow = this.isDefaultWorkflow(formSchemeKey);
        DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formSchemeDefine);
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, filterDims, formKey, groupKey);
        boolean bindProcess = this.bindProcess(formSchemeKey, filterDims, formKey, groupKey);
        Optional<ProcessEngine> processEngine = this.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Actor candicateActor = Actor.fromNpContext();
        if (isDefaultFlow) {
            if (bindProcess) {
                tasks = runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, true);
            }
        } else {
            Optional<ProcessInstance> instance = runtimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false, null);
            if (!Optional.empty().equals(instance)) {
                tasks = runtimeService.queryTaskByProcessInstance(instance.get().getId(), candicateActor);
            }
        }
        for (Task task : tasks) {
            taskId = task.getId();
        }
        return taskId;
    }

    @Override
    public String queryRevertTaskId(List<UploadRecordNew> uploadRecords, Task currentTask, BusinessKey businessKey) {
        String targetTaskId = this.autoCompelete.taskId(currentTask, businessKey);
        ArrayList<String> filterTaskIds = new ArrayList<String>();
        List taskIds = uploadRecords.stream().map(e -> e.getAction()).collect(Collectors.toList());
        if (taskIds != null && taskIds.size() > 0) {
            String firstId = (String)taskIds.stream().findFirst().get();
            if (!"act_retrieve".equals(firstId)) {
                for (UploadRecordNew his : uploadRecords) {
                    if (!firstId.equals(his.getAction())) break;
                    if (his.getTaskId().equals(currentTask.getUserTaskId())) continue;
                    filterTaskIds.add(his.getTaskId());
                }
            } else {
                for (UploadRecordNew his : uploadRecords) {
                    if (his.getAction().equals("act_retrieve") || his.getTaskId().equals(currentTask.getUserTaskId())) continue;
                    filterTaskIds.add(his.getTaskId());
                    break;
                }
            }
            if (filterTaskIds.contains(targetTaskId)) {
                return targetTaskId;
            }
        }
        return null;
    }

    @Override
    public List<Task> queryTasks(String formSchemeKey, BusinessKey businessKey) {
        ArrayList<Task> tasks = new ArrayList();
        Optional<ProcessEngine> processEngine = this.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        tasks = runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
        return tasks;
    }

    @Override
    public List<Task> queryTasks(String formSchemeKey, String formKey, String groupKey, DimensionValueSet filterDims, BusinessKey businessKey, boolean isStart, IConditionCache conditionCache) {
        List<Task> tasks = new ArrayList<Task>();
        boolean isDefaultFlow = this.isDefaultWorkflow(formSchemeKey);
        boolean bindProcess = this.bindProcess(formSchemeKey, filterDims, formKey, groupKey);
        Optional<ProcessEngine> processEngine = this.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Actor candicateActor = Actor.fromNpContext();
        if (isDefaultFlow) {
            if (bindProcess) {
                tasks = runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, isStart, conditionCache);
            }
        } else {
            Optional<ProcessInstance> instance = runtimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false, null);
            if (!Optional.empty().equals(instance)) {
                tasks = runtimeService.queryTaskByProcessInstance(instance.get().getId(), candicateActor);
            }
        }
        return tasks;
    }

    @Override
    public boolean isTwoTree() {
        if (StringUtils.isNotEmpty((String)DsContextHolder.getDsContext().getContextEntityId())) {
            if (this.systemIdentityService.isAdmin()) {
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTaskCode(String formSchemeKey, String actionCode) {
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            if ("act_submit".equals(actionCode)) {
                return "tsk_upload";
            }
            if ("act_upload".equals(actionCode)) {
                return "tsk_audit";
            }
            if ("act_confirm".equals(actionCode)) {
                return "tsk_audit_after_confirm";
            }
        } else {
            WorkflowSettingDefine workflowSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            if (workflowSetting != null) {
                String workflowId = workflowSetting.getWorkflowId();
                WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowId, 1);
                List<WorkFlowAction> workflowActions = this.customWorkFolwService.getWorkflowActionsByLinkid(workFlowDefine.getLinkid());
                for (WorkFlowAction workFlowAction : workflowActions) {
                    String action = workFlowAction.getActionCode();
                    if (!action.equals(actionCode)) continue;
                    return workFlowAction.getNodeid();
                }
            }
        }
        return null;
    }

    @Override
    public String getSubmitActionCode(String formSchemeKey) {
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            return "act_submit".toString();
        }
        return "cus_submit".toString();
    }

    @Override
    public String getUploadActionCode(String formSchemeKey) {
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            return "act_upload".toString();
        }
        return "cus_upload".toString();
    }

    @Override
    public String getConfirmActionCode(String formSchemeKey) {
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            return "act_confirm".toString();
        }
        return "cus_confirm".toString();
    }

    @Override
    public String getReturnActionCode(String formSchemeKey) {
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            return "act_return".toString();
        }
        return "cus_return".toString();
    }

    @Override
    public String getRejectActionCode(String formSchemeKey) {
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            return "act_reject".toString();
        }
        return "cus_reject".toString();
    }

    @Override
    public List<ProcessTaskNode> getProcessTaskNodes(FormSchemeDefine formSchemeDefine) {
        ArrayList<ProcessTaskNode> processNodes = new ArrayList<ProcessTaskNode>();
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeDefine.getKey());
        if (defaultWorkflow) {
            TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
            boolean unitSubmitForCensorship = flowsSetting.isUnitSubmitForCensorship();
            boolean isReturnExplain = flowsSetting.isReturnExplain();
            boolean backDescriptionNeedWrite = flowsSetting.isBackDescriptionNeedWrite();
            boolean submitExplain = flowsSetting.isSubmitExplain();
            String defaultNodeNameConfig = flowsSetting.getDefaultNodeNameConfig();
            JSONObject jsonObject = this.isJSONStr(defaultNodeNameConfig) ? new JSONObject(defaultNodeNameConfig) : new JSONObject();
            JSONObject submitNodeJSON = new JSONObject();
            JSONObject uploadNodeJSON = new JSONObject();
            JSONObject auditNodeJSON = new JSONObject();
            if (!jsonObject.isEmpty()) {
                submitNodeJSON = jsonObject.isNull("tsk_submit") ? new JSONObject() : jsonObject.getJSONObject("tsk_submit");
                uploadNodeJSON = jsonObject.isNull("tsk_upload") ? new JSONObject() : jsonObject.getJSONObject("tsk_upload");
                JSONObject jSONObject = auditNodeJSON = jsonObject.isNull("tsk_audit") ? new JSONObject() : jsonObject.getJSONObject("tsk_audit");
            }
            if (unitSubmitForCensorship) {
                ProcessTaskNode submitNode = new ProcessTaskNode();
                submitNode.setCode("tsk_submit");
                submitNode.setTitle(submitNodeJSON.isNull("rename") ? "\u9001\u5ba1" : submitNodeJSON.getString("rename"));
                submitNode.setOpenCommitDes(isReturnExplain || backDescriptionNeedWrite);
                processNodes.add(submitNode);
            }
            ProcessTaskNode uploadNode = new ProcessTaskNode();
            uploadNode.setCode("tsk_upload");
            uploadNode.setTitle(uploadNodeJSON.isNull("rename") ? "\u4e0a\u62a5" : uploadNodeJSON.getString("rename"));
            if (unitSubmitForCensorship) {
                uploadNode.setOpenCommitDes(false);
            } else {
                uploadNode.setOpenCommitDes(backDescriptionNeedWrite);
            }
            processNodes.add(uploadNode);
            ProcessTaskNode auditNode = new ProcessTaskNode();
            auditNode.setCode("tsk_audit");
            auditNode.setTitle(auditNodeJSON.isNull("rename") ? "\u5ba1\u6279" : auditNodeJSON.getString("rename"));
            auditNode.setOpenCommitDes(submitExplain);
            processNodes.add(auditNode);
        } else {
            WorkflowSettingDefine settingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeDefine.getKey());
            WorkFlowDefine workflowDefine = this.customWorkFolwService.getWorkFlowDefineByID(settingDefine.getWorkflowId(), 1);
            List workFlowNodeSets = this.customWorkFolwService.getWorkFlowNodeSets(workflowDefine.getLinkid()).stream().sorted(Comparator.comparing(WorkFlowNodeSet::getCode)).collect(Collectors.toList());
            for (WorkFlowNodeSet node : workFlowNodeSets) {
                if (node.getId().contains("StartEvent")) continue;
                List<WorkFlowLine> workflowLinesByPreTask = this.customWorkFolwService.getWorkflowLineByEndNode(node.getId(), workflowDefine.getLinkid());
                ArrayList<Boolean> commitDesList = new ArrayList<Boolean>();
                if (workflowLinesByPreTask != null && workflowLinesByPreTask.size() > 0) {
                    for (WorkFlowLine line : workflowLinesByPreTask) {
                        WorkFlowAction workflowAction;
                        String exset;
                        String actionid = line.getActionid();
                        if (actionid == null || (exset = (workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid, workflowDefine.getLinkid())).getExset()) == null) continue;
                        ObjectMapper objMapper = new ObjectMapper();
                        try {
                            Map object = (Map)objMapper.readValue(exset, (TypeReference)new TypeReference<Map<String, Object>>(){});
                            if (object == null || !object.containsKey("needOptDesc")) continue;
                            Boolean needOptDesc = Workflow.convertBoolean(object.get("needOptDesc"));
                            commitDesList.add(needOptDesc);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                ProcessTaskNode taskNode = new ProcessTaskNode();
                taskNode.setCode(node.getId());
                taskNode.setTitle(node.getTitle());
                if (commitDesList.contains(true)) {
                    taskNode.setOpenCommitDes(true);
                }
                processNodes.add(taskNode);
            }
        }
        return processNodes;
    }

    private static Boolean convertBoolean(Object obj) {
        String str;
        Boolean valueOf = false;
        if (obj != null && null != (str = obj.toString()) && !"".equals(str)) {
            valueOf = Boolean.valueOf(str);
            return valueOf;
        }
        return valueOf;
    }

    @Override
    public List<ProcessAction> getProcessAction(FormSchemeDefine formSchemeDefine, String period, String nodeCode) {
        ArrayList<ProcessAction> processActions = new ArrayList<ProcessAction>();
        ProcessAction processAction = null;
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeDefine.getKey());
        if (defaultWorkflow) {
            TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
            boolean unitSubmitForCensorship = flowsSetting.isUnitSubmitForCensorship();
            boolean dataConfirm = flowsSetting.isDataConfirm();
            if (unitSubmitForCensorship) {
                if ("tsk_submit".equals(nodeCode)) {
                    processAction = new ProcessAction();
                    processAction.setActionCode("start");
                    processAction.setStateCode("start");
                    processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "start", nodeCode));
                    processActions.add(processAction);
                    processAction = new ProcessAction();
                    processAction.setActionCode("act_return");
                    processAction.setStateCode(UploadState.RETURNED.toString());
                    processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_return", nodeCode));
                    processActions.add(processAction);
                    processAction = new ProcessAction();
                    processAction.setActionCode("act_reject");
                    processAction.setStateCode(UploadState.REJECTED.toString());
                    processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_reject", nodeCode));
                    processActions.add(processAction);
                }
                if ("tsk_upload".equals(nodeCode)) {
                    processAction = new ProcessAction();
                    processAction.setActionCode("act_submit");
                    processAction.setStateCode(UploadState.SUBMITED.toString());
                    processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_submit", nodeCode));
                    processActions.add(processAction);
                }
            } else if ("tsk_upload".equals(nodeCode)) {
                processAction = new ProcessAction();
                processAction.setActionCode("start");
                processAction.setStateCode("start");
                processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "start", nodeCode));
                processActions.add(processAction);
                processAction = new ProcessAction();
                processAction.setActionCode("act_reject");
                processAction.setStateCode(UploadState.REJECTED.toString());
                processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_reject", nodeCode));
                processActions.add(processAction);
            }
            if ("tsk_audit".equals(nodeCode)) {
                processAction = new ProcessAction();
                processAction.setActionCode("act_upload");
                processAction.setStateCode(UploadState.UPLOADED.toString());
                processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_upload", nodeCode));
                processActions.add(processAction);
            }
            if (dataConfirm && "tsk_audit_after_confirm".equals(nodeCode)) {
                processAction = new ProcessAction();
                processAction.setActionCode("act_confirm");
                processAction.setStateCode(UploadState.CONFIRMED.toString());
                processAction.setStateTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_confirm", nodeCode));
                processActions.add(processAction);
            }
        } else {
            WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeDefine.getKey());
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            List<WorkFlowLine> workflowLines = this.customWorkFolwService.getWorkflowLineByEndNode(nodeCode, workFlowDefine.getLinkid());
            if (workflowLines != null && workflowLines.size() > 0) {
                for (WorkFlowLine workFlowLine : workflowLines) {
                    String beforeNodeID = workFlowLine.getBeforeNodeID();
                    String actionid = workFlowLine.getActionid();
                    if (beforeNodeID.contains("StartEvent")) {
                        List<WorkFlowAction> workFlowNodeAction = this.customWorkFolwService.getRunWorkFlowNodeAction(nodeCode, workFlowDefine.getLinkid());
                        if (workFlowNodeAction == null || workFlowNodeAction.size() <= 0) continue;
                        for (WorkFlowAction workFlowAction : workFlowNodeAction) {
                            processAction = new ProcessAction();
                            String stateTitle = "\u672a" + workFlowAction.getActionTitle();
                            processAction.setActionCode("start");
                            processAction.setStateCode(workFlowAction.getStateCode());
                            processAction.setStateTitle(stateTitle);
                            processActions.add(processAction);
                        }
                        continue;
                    }
                    WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid, workFlowDefine.getLinkid());
                    if (workflowAction == null) continue;
                    processAction = new ProcessAction();
                    String stateTitle = "\u5df2" + workflowAction.getActionTitle();
                    processAction.setActionCode(workflowAction.getActionCode());
                    processAction.setStateCode(workflowAction.getStateCode());
                    processAction.setStateTitle(stateTitle);
                    processActions.add(processAction);
                }
            }
        }
        return new ArrayList<ProcessAction>(processActions.stream().collect(Collectors.toMap(action -> (action.getActionCode() + action.getStateTitle()).trim(), Function.identity(), (v1, v2) -> v1)).values());
    }

    @Override
    public String getMessageId(String formSchemeKey, String period, String unitId, String adjust, String formKey, String groupKey, WorkFlowType startType, String taskCode, String corporateValue) {
        String msgId = null;
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            msgId = StringUtils.isNotEmpty((String)adjust) ? (corporateValue != null ? formSchemeKey + ";" + period + ";" + unitId + ";" + adjust + ";" + taskCode + ";" + corporateValue : formSchemeKey + ";" + period + ";" + unitId + ";" + adjust + ";" + taskCode) : (corporateValue != null ? formSchemeKey + ";" + period + ";" + unitId + ";" + taskCode + ";" + corporateValue : formSchemeKey + ";" + period + ";" + unitId + ";" + taskCode);
        } else if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
            String formOrGroupKey;
            String string = startType == WorkFlowType.FORM ? formKey : (formOrGroupKey = startType == WorkFlowType.GROUP ? groupKey : "");
            msgId = StringUtils.isNotEmpty((String)adjust) ? (corporateValue != null ? formSchemeKey + ";" + period + ";" + unitId + ";" + formOrGroupKey + ";" + adjust + ";" + taskCode + ";" + corporateValue : formSchemeKey + ";" + period + ";" + unitId + ";" + formOrGroupKey + ";" + adjust + ";" + taskCode) : (corporateValue != null ? formSchemeKey + ";" + period + ";" + unitId + ";" + formOrGroupKey + ";" + taskCode + ";" + corporateValue : formSchemeKey + ";" + period + ";" + unitId + ";" + formOrGroupKey + ";" + taskCode);
        }
        return msgId;
    }

    @Override
    public List<WorkflowAction> getExecuteActions(String taskId, String period, String nodeCode) {
        ArrayList<WorkflowAction> actions = new ArrayList<WorkflowAction>();
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(taskId, period);
        String formSchemeKey = formSchemeDefine.getKey();
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeDefine.getKey());
        WorkflowAction workflowAction = null;
        if (defaultWorkflow) {
            ActionParam wlrkflowParam;
            String actionName;
            ActionParam uploadParam;
            TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
            boolean unitSubmitForCensorship = flowsSetting.isUnitSubmitForCensorship();
            boolean dataConfirm = flowsSetting.isDataConfirm();
            if ("tsk_submit".equals(nodeCode)) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_submit");
                String submitName = this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, "act_submit");
                workflowAction.setTitle(submitName);
                uploadParam = this.actionMethod.getWlrkflowParam(formSchemeKey, "act_submit");
                workflowAction.setActionParam(uploadParam);
                actions.add(workflowAction);
            }
            if ("tsk_upload".equals(nodeCode)) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_upload");
                String uploadName = this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, "act_upload");
                workflowAction.setTitle(uploadName);
                uploadParam = this.actionMethod.getWlrkflowParam(formSchemeKey, "act_upload");
                workflowAction.setActionParam(uploadParam);
                actions.add(workflowAction);
                if (unitSubmitForCensorship) {
                    workflowAction = new WorkflowAction();
                    workflowAction.setCode("act_return");
                    actionName = this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, "act_return");
                    workflowAction.setTitle(actionName);
                    ActionParam wlrkflowParam2 = this.actionMethod.getWlrkflowParam(formSchemeKey, "act_return");
                    workflowAction.setActionParam(wlrkflowParam2);
                    actions.add(workflowAction);
                }
            }
            if ("tsk_audit".equals(nodeCode)) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_reject");
                String actionName2 = this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, "act_reject");
                workflowAction.setTitle(actionName2);
                wlrkflowParam = this.actionMethod.getWlrkflowParam(formSchemeKey, "act_reject");
                workflowAction.setActionParam(wlrkflowParam);
                actions.add(workflowAction);
                if (dataConfirm) {
                    workflowAction = new WorkflowAction();
                    workflowAction.setCode("act_confirm");
                    String confirmName = this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, "act_confirm");
                    workflowAction.setTitle(confirmName);
                    ActionParam confirmParam = this.actionMethod.getWlrkflowParam(formSchemeKey, "act_confirm");
                    workflowAction.setActionParam(confirmParam);
                    actions.add(workflowAction);
                }
            }
            if ("tsk_audit_after_confirm".equals(nodeCode)) {
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_reject");
                String actionNameByActionCode = this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, "act_reject");
                workflowAction.setTitle(actionNameByActionCode);
                wlrkflowParam = this.actionMethod.getWlrkflowParam(formSchemeKey, "act_reject");
                workflowAction.setActionParam(wlrkflowParam);
                actions.add(workflowAction);
                workflowAction = new WorkflowAction();
                workflowAction.setCode("act_cancel_confirm");
                actionName = this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, "act_cancel_confirm");
                workflowAction.setTitle(actionName);
                ActionParam cancelConfirmParam = this.actionMethod.getWlrkflowParam(formSchemeKey, "act_cancel_confirm");
                workflowAction.setActionParam(cancelConfirmParam);
                actions.add(workflowAction);
            }
        } else {
            WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeDefine.getKey());
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            WorkFlowNodeSet workFlowNodeSetByID = this.customWorkFolwService.getWorkFlowNodeSetByID(nodeCode, workFlowDefine.getLinkid());
            String[] actions2 = workFlowNodeSetByID.getActions();
            if (actions2 != null && actions2.length > 0) {
                for (String action : actions2) {
                    workflowAction = new WorkflowAction();
                    WorkFlowAction waction = this.customWorkFolwService.getWorkflowActionById(action, workFlowDefine.getLinkid());
                    if (waction == null) continue;
                    String actionCode = waction.getActionCode();
                    String actionTitle = waction.getActionTitle();
                    ActionParam customWlrkflowParam = this.actionMethod.getCustomWlrkflowParam(formSchemeKey, waction, nodeCode);
                    workflowAction.setCode(actionCode);
                    workflowAction.setTitle(actionTitle);
                    workflowAction.setActionParam(customWlrkflowParam);
                    actions.add(workflowAction);
                }
            }
        }
        return actions;
    }

    public FormSchemeDefine getFormSchemeDefine(String taskId, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine;
        try {
            schemePeriodLinkDefine = this.runtimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskId);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        return schemePeriodLinkDefine == null ? null : this.runtimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
    }

    private boolean isJSONStr(String str) {
        boolean result = false;
        if (StringUtils.isNotEmpty((String)str) && (str = str.trim()).startsWith("{") && str.endsWith("}")) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean hasStatisticalNode(String formSchemeKey) {
        List<WorkFlowNodeSet> workFlowNodeSets;
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine workflowSettingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowSettingDefine != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowSettingDefine.getWorkflowId(), 1)) != null && workFlowDefine.getLinkid() != null && (workFlowNodeSets = this.customWorkFolwService.getWorkFlowNodeSets(workFlowDefine.getLinkid())) != null && workFlowNodeSets.size() > 0) {
            for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
                boolean statisticalNode = workFlowNodeSet.isStatisticalNode();
                if (!statisticalNode) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, List<String>> getStatisticalStates(String formSchemeKey) {
        HashMap<String, String> stateToColor = new HashMap<String, String>();
        Map<String, List<String>> statisticalStates = this.getStatisticalStates(formSchemeKey, stateToColor);
        return statisticalStates;
    }

    @Override
    public Map<String, List<String>> getStatisticalStates(String formSchemeKey, Map<String, String> stateToColor) {
        LinkedHashMap<String, List<String>> actionMap = new LinkedHashMap<String, List<String>>();
        HashSet<String> nodeCodes = new HashSet<String>();
        LinkedHashMap newActionMap = new LinkedHashMap();
        Map<String, String> colorMap = this.treeIconAndColor.getColorMap();
        WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowDefine != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefine.getWorkflowId(), 1);
            String linkId = workFlowDefine.getLinkid();
            if (workFlowDefine != null) {
                List<WorkFlowNodeSet> workFlowNodeSets = this.customWorkFolwService.getWorkFlowNodeSets(linkId);
                WorkFlowNodeSet startNode = workFlowNodeSets.stream().filter(e -> e.getId().startsWith("Start")).findAny().get();
                this.queryNextNode(actionMap, startNode, linkId, nodeCodes, colorMap, stateToColor);
                Map<String, List<String>> listMap = this.nodeSort(workFlowNodeSets, linkId);
                for (Map.Entry<String, List<String>> entry : listMap.entrySet()) {
                    List<String> value = entry.getValue();
                    for (String stateTitle : value) {
                        if (newActionMap.containsKey(stateTitle)) continue;
                        newActionMap.put(stateTitle, actionMap.get(stateTitle));
                    }
                }
            }
        }
        return actionMap;
    }

    private Map<String, List<String>> nodeSort(List<WorkFlowNodeSet> workFlowNodeSets, String linkId) {
        LinkedHashMap nodeToActionMap = new LinkedHashMap();
        for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
            if (workFlowNodeSet.getId().startsWith("End")) continue;
            if (workFlowNodeSet.getId().startsWith("Start")) {
                List<WorkFlowLine> workflowLinesByPreTask = this.customWorkFolwService.getWorkflowLinesByPreTask(workFlowNodeSet.getId(), linkId);
                for (WorkFlowLine workFlowLine : workflowLinesByPreTask) {
                    WorkFlowNodeSet afterNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(workFlowLine.getAfterNodeID(), linkId);
                    String[] actions1 = afterNodeSet.getActions();
                    if (actions1 == null || actions1.length <= 0) continue;
                    for (String action : actions1) {
                        WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(action, linkId);
                        String stateTitle = "\u672a" + workflowAction.getActionTitle();
                        ArrayList<String> actionList = new ArrayList<String>();
                        actionList.add(stateTitle);
                        nodeToActionMap.put("0", actionList);
                    }
                }
                continue;
            }
            String[] actions = workFlowNodeSet.getActions();
            for (String action : actions) {
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(action, linkId);
                String stateName = this.getStateName(workflowAction);
                ArrayList<String> actionList = (ArrayList<String>)nodeToActionMap.get(workFlowNodeSet.getCode());
                if (actionList == null) {
                    actionList = new ArrayList<String>();
                    actionList.add(stateName);
                    nodeToActionMap.put(workFlowNodeSet.getCode(), actionList);
                    continue;
                }
                if (actionList.contains(stateName)) continue;
                actionList.add(stateName);
            }
        }
        TreeMap<String, List<String>> nodeMap = new TreeMap<String, List<String>>(nodeToActionMap);
        return nodeMap;
    }

    private void queryNextNode(Map<String, List<String>> actionMap, WorkFlowNodeSet beforeWorkflowNode, String linkId, Set<String> nodeCodes, Map<String, String> colorMap, Map<String, String> stateToColor) {
        try {
            String preNodeId = beforeWorkflowNode.getId();
            List<WorkFlowLine> workflowLinesByPreTask = this.customWorkFolwService.getWorkflowLinesByPreTask(preNodeId, linkId);
            for (WorkFlowLine workFlowLine : workflowLinesByPreTask) {
                WorkFlowNodeSet afterNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(workFlowLine.getAfterNodeID(), linkId);
                if (afterNodeSet.getId().startsWith("End")) continue;
                ArrayList<String> statisticalStates = new ArrayList<String>();
                String actionid = workFlowLine.getActionid();
                if (actionid != null) {
                    WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid, linkId);
                    if (workflowAction != null) {
                        String stateCode = workflowAction.getStateCode();
                        String customCode = preNodeId + "@" + stateCode;
                        StatisticalStateData statisticalStateData = this.calculateState(workflowAction, colorMap, preNodeId);
                        List<String> customCodes = actionMap.get(statisticalStateData.getTitle());
                        if (customCodes == null) {
                            customCodes = new ArrayList<String>();
                            customCodes.add(customCode);
                            actionMap.put(statisticalStateData.getTitle(), customCodes);
                        } else {
                            customCodes.add(customCode);
                        }
                        statisticalStates.add(statisticalStateData.getTitle());
                        stateToColor.put(statisticalStateData.getTitle(), statisticalStateData.getColor());
                    }
                } else {
                    String customCode = afterNodeSet.getId() + "@" + "start";
                    String[] actions1 = afterNodeSet.getActions();
                    if (actions1 != null && actions1.length > 0) {
                        for (String action : actions1) {
                            WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(action, linkId);
                            StatisticalStateData statisticalStateData = this.calculateStartState(workflowAction, colorMap, customCode);
                            List<String> customCodes = actionMap.get(statisticalStateData.getTitle());
                            if (customCodes == null) {
                                customCodes = new ArrayList<String>();
                                customCodes.add(customCode);
                                actionMap.put(statisticalStateData.getTitle(), customCodes);
                            } else {
                                customCodes.add(customCode);
                            }
                            statisticalStates.add(statisticalStateData.getTitle());
                            stateToColor.put(statisticalStateData.getTitle(), statisticalStateData.getColor());
                        }
                    }
                }
                if (nodeCodes.contains(afterNodeSet.getId())) continue;
                nodeCodes.add(afterNodeSet.getId());
                this.queryNextNode(actionMap, afterNodeSet, linkId, nodeCodes, colorMap, stateToColor);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private StatisticalStateData calculateStartState(WorkFlowAction workFlowAction, Map<String, String> colorMap, String nodeCode) {
        String customStateName = null;
        StatisticalStateData statisticalStateData = this.getStatisticalStateName(nodeCode, workFlowAction, colorMap);
        if (statisticalStateData.getTitle() == null) {
            customStateName = "cus_submit".equals(workFlowAction.getActionCode()) ? "\u672a" + workFlowAction.getActionTitle() : workFlowAction.getActionTitle();
            statisticalStateData.setTitle(customStateName);
        }
        return statisticalStateData;
    }

    private StatisticalStateData calculateState(WorkFlowAction workFlowAction, Map<String, String> colorMap, String nodeCode) {
        String customStateName = workFlowAction.getStateName();
        StatisticalStateData statisticalStateData = this.getStatisticalStateName(nodeCode, workFlowAction, colorMap);
        if (statisticalStateData.getTitle() == null) {
            statisticalStateData.setTitle(customStateName);
        }
        return statisticalStateData;
    }

    private StatisticalStateData getStatisticalStateName(String nodeCode, WorkFlowAction workFlowAction, Map<String, String> colorMap) {
        String statisticalStateName = null;
        String statisticalStateColor = null;
        StatisticalStateData statisticalStateData = new StatisticalStateData();
        try {
            String exset = workFlowAction.getExset();
            if (exset != null) {
                Object object;
                ObjectMapper objMapper = new ObjectMapper();
                Map workflowExset = (Map)objMapper.readValue(exset, (TypeReference)new TypeReference<Map<String, Object>>(){});
                if (workflowExset.containsKey("statisticalStateName") && (object = workflowExset.get("statisticalStateName")) != null) {
                    statisticalStateName = (String)object;
                }
                if (workflowExset.containsKey("stateColor")) {
                    object = workflowExset.get("stateColor");
                    if (object != null && (statisticalStateColor = (String)object) == null) {
                        statisticalStateColor = colorMap.get(workFlowAction.getStateCode());
                    }
                } else {
                    statisticalStateColor = colorMap.get(workFlowAction.getStateCode());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        statisticalStateData.setKey(nodeCode + "@" + workFlowAction.getStateCode());
        statisticalStateData.setColor(statisticalStateColor);
        statisticalStateData.setTitle(statisticalStateName);
        return statisticalStateData;
    }

    private String getStateName(WorkFlowAction workFlowAction) {
        String statisticalStateName = null;
        try {
            String exset = workFlowAction.getExset();
            if (exset != null) {
                ObjectMapper objMapper = new ObjectMapper();
                Map workflowExset = (Map)objMapper.readValue(exset, (TypeReference)new TypeReference<Map<String, Object>>(){});
                if (workflowExset.containsKey("statisticalStateName")) {
                    Object object = workflowExset.get("statisticalStateName");
                    if (object != null) {
                        statisticalStateName = (String)object;
                    }
                } else {
                    statisticalStateName = workFlowAction.getStateName();
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return statisticalStateName;
    }
}

