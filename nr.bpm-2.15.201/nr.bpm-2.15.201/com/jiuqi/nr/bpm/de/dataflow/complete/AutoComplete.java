/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.complete;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.common.ConcurrentTaskContext;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.NodeParam;
import com.jiuqi.nr.bpm.de.dataflow.common.ForceTaskContext;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.CounterParamBuilder;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AutoComplete {
    private static final Logger logger = LoggerFactory.getLogger(AutoComplete.class);
    public static final String ORDINARYNODE = "ordinary";
    public static final String SIGNNODE = "signnode";
    public static final String SPECIALNODE = "specialnode";
    @Autowired
    ActionMethod actionMethod;
    @Autowired
    List<ActorStrategy> actorStrategy;
    @Autowired
    CustomWorkFolwService customWorkFolwService;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IQueryUploadStateService queryUploadStateServiceImpl;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    CounterParamBuilder counterParamBuilder;
    @Resource
    private WorkflowSettingService settingService;

    public Map<Boolean, Task> autoCompelete(RunTimeService runTimeService, DeployService deployService, BusinessKey businessKey, String fromSchemeKey, String actionid, String comment, boolean isForceUpload, Map<String, Object> variables, TaskContext taskContext) {
        LinkedHashMap<Boolean, Task> status = new LinkedHashMap<Boolean, Task>();
        boolean compelete = false;
        Actor candicateActor = Actor.fromNpContext();
        try {
            List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor);
            if (tasks.size() > 0) {
                List<Task> tempTasks = tasks;
                if (taskContext == null) {
                    taskContext = new ConcurrentTaskContext();
                }
                taskContext.put(this.nrParameterUtils.getForceMapKey(), isForceUpload);
                taskContext.put(this.nrParameterUtils.getExecuteOrder(), 0);
                runTimeService.completeProcessTask(businessKey, tasks.get(0).getId(), candicateActor.getIdentityId(), actionid, comment, taskContext, variables);
                compelete = true;
                status.put(compelete, tasks.get(0));
                this.jumpToNextNode(tempTasks.get(0), runTimeService, businessKey, actionid, comment, isForceUpload, variables, null, status);
            }
        }
        catch (UserActionException e2) {
            throw new UserActionException(e2.getMessage(), e2.getMessage());
        }
        catch (Exception e) {
            throw new BpmException(e);
        }
        return status;
    }

    public LinkedHashMap<Boolean, Task> batchAutoCompelete(RunTimeService runTimeService, DeployService deployService, BusinessKey businessKey, String fromSchemeKey, BusinessKeySet businessKeySet, String actionid, String comment, boolean isForceUpload, Map<String, Object> variables, TaskContext taskContext) {
        return this.batchAutoCompelete(runTimeService, deployService, businessKey, fromSchemeKey, businessKeySet, actionid, comment, isForceUpload, variables, null, taskContext);
    }

    public LinkedHashMap<Boolean, Task> batchAutoCompelete(RunTimeService runTimeService, DeployService deployService, BusinessKey businessKey, String fromSchemeKey, BusinessKeySet businessKeySet, String actionid, String comment, boolean isForceUpload, Map<String, Object> variables, IConditionCache conditionCache, TaskContext taskContext) {
        LinkedHashMap<Boolean, Task> status = new LinkedHashMap<Boolean, Task>();
        boolean compelete = false;
        Actor candicateActor = Actor.fromNpContext();
        try {
            List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), true);
            if (tasks.size() > 0) {
                List<Task> tempTasks = tasks;
                if (taskContext == null) {
                    taskContext = new ConcurrentTaskContext();
                }
                taskContext.put(this.nrParameterUtils.getForceMapKey(), isForceUpload);
                if (conditionCache != null) {
                    taskContext.put(this.nrParameterUtils.getIConditionCache(), conditionCache);
                }
                taskContext.put(this.nrParameterUtils.getExecuteOrder(), 0);
                runTimeService.batchCompleteProcessTasks(businessKeySet, candicateActor, actionid, tasks.get(0).getId(), comment, taskContext, variables);
                businessKeySet.getMasterEntitySet().reset();
                compelete = true;
                status.put(compelete, tasks.get(0));
                Object returnTypeString = taskContext.get("returnType");
                variables.put("returnType", returnTypeString == null ? "" : returnTypeString.toString());
                this.batchJumpToNextNode(tempTasks.get(0), runTimeService, businessKey, actionid, comment, isForceUpload, variables, null, status, businessKeySet);
            }
        }
        catch (UserActionException e2) {
            throw new UserActionException(e2.getMessage(), e2.getMessage());
        }
        catch (Exception e) {
            throw new BpmException(e);
        }
        return status;
    }

    private void jumpToNextNode(Task task, RunTimeService runTimeService, BusinessKey businessKey, String actionid, String comment, boolean isForceUpload, Map<String, Object> variables, IConditionCache conditionCache, LinkedHashMap<Boolean, Task> status) {
        boolean compelete = false;
        Actor candicateActor = Actor.fromNpContext();
        List<NodeParam> nodeParams = this.nodeParams(task, businessKey, actionid);
        for (int i = 0; i < nodeParams.size(); ++i) {
            List<Task> tasks;
            String nodeCode = nodeParams.get(i).getNodeCode();
            String nodeType = nodeParams.get(i).getNodeType();
            ForceTaskContext forceTaskContext = new ForceTaskContext();
            forceTaskContext.put(this.nrParameterUtils.getForceMapKey(), isForceUpload);
            if (conditionCache != null) {
                forceTaskContext.put(this.nrParameterUtils.getIConditionCache(), conditionCache);
            }
            if (SIGNNODE.equals(nodeType)) {
                if (i == 0) {
                    forceTaskContext.put(this.nrParameterUtils.getExecuteOrder(), i + 1);
                    tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, false);
                    if (tasks == null || tasks.size() <= 0) continue;
                    runTimeService.completeProcessTask(businessKey, tasks.get(0).getId(), candicateActor.getIdentityId(), actionid, comment, forceTaskContext, variables);
                    compelete = true;
                    status.put(compelete, tasks.get(0));
                    continue;
                }
                if (!this.signNodeCanExecute(businessKey, nodeCode)) continue;
                forceTaskContext.put(this.nrParameterUtils.getExecuteOrder(), i + 1);
                tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, false);
                if (tasks == null || tasks.size() <= 0) continue;
                runTimeService.completeProcessTask(businessKey, tasks.get(0).getId(), candicateActor.getIdentityId(), actionid, comment, forceTaskContext, variables);
                compelete = true;
                status.put(compelete, tasks.get(0));
                continue;
            }
            forceTaskContext.put(this.nrParameterUtils.getExecuteOrder(), i + 1);
            tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, false);
            if (tasks == null || tasks.size() <= 0) continue;
            runTimeService.completeProcessTask(businessKey, tasks.get(0).getId(), candicateActor.getIdentityId(), actionid, comment, forceTaskContext, variables);
            compelete = true;
            status.put(compelete, tasks.get(0));
        }
    }

    private void batchJumpToNextNode(Task task, RunTimeService runTimeService, BusinessKey businessKey, String actionid, String comment, boolean isForceUpload, Map<String, Object> variables, IConditionCache conditionCache, LinkedHashMap<Boolean, Task> status, BusinessKeySet businessKeySet) {
        boolean compelete = false;
        Actor candicateActor = Actor.fromNpContext();
        List<NodeParam> nodeParams = this.nodeParams(task, businessKey, actionid);
        for (int i = 0; i < nodeParams.size(); ++i) {
            List<Task> tasks;
            String nodeCode = nodeParams.get(i).getNodeCode();
            String nodeType = nodeParams.get(i).getNodeType();
            ForceTaskContext forceTaskContext = new ForceTaskContext();
            forceTaskContext.put(this.nrParameterUtils.getForceMapKey(), isForceUpload);
            forceTaskContext.put("returnType", variables.get("returnType"));
            if (conditionCache != null) {
                forceTaskContext.put(this.nrParameterUtils.getIConditionCache(), conditionCache);
            }
            if (SIGNNODE.equals(nodeType)) {
                if (i == 0) {
                    forceTaskContext.put(this.nrParameterUtils.getExecuteOrder(), i + 1);
                    tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, false);
                    if (tasks == null || tasks.size() <= 0) continue;
                    runTimeService.batchCompleteProcessTasks(businessKeySet, candicateActor, actionid, tasks.get(0).getId(), comment, forceTaskContext, variables);
                    businessKeySet.getMasterEntitySet().reset();
                    compelete = true;
                    status.put(compelete, tasks.get(0));
                    continue;
                }
                if (!this.signNodeCanExecute(businessKey, nodeCode)) continue;
                forceTaskContext.put(this.nrParameterUtils.getExecuteOrder(), i + 1);
                tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, false);
                if (tasks == null || tasks.size() <= 0) continue;
                runTimeService.batchCompleteProcessTasks(businessKeySet, candicateActor, actionid, tasks.get(0).getId(), comment, forceTaskContext, variables);
                businessKeySet.getMasterEntitySet().reset();
                compelete = true;
                status.put(compelete, tasks.get(0));
                continue;
            }
            forceTaskContext.put(this.nrParameterUtils.getExecuteOrder(), i + 1);
            tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), candicateActor, false);
            if (tasks == null || tasks.size() <= 0) continue;
            runTimeService.batchCompleteProcessTasks(businessKeySet, candicateActor, actionid, tasks.get(0).getId(), comment, forceTaskContext, variables);
            businessKeySet.getMasterEntitySet().reset();
            compelete = true;
            status.put(compelete, tasks.get(0));
        }
    }

    public List<NodeParam> nodeParams(Task task, BusinessKey businessKey, String actionId) {
        String nodeCode = task.getUserTaskId();
        ArrayList<NodeParam> nodeParams = new ArrayList<NodeParam>();
        this.nodeList(task, nodeCode, businessKey, actionId, nodeParams);
        return nodeParams;
    }

    private void nodeList(Task task, String nodeId, BusinessKey businessKey, String actionId, List<NodeParam> nodeParams) {
        try {
            WorkFlowDefine workFlowDefine;
            List<WorkFlowLine> workflowLines;
            WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
            if (refSetting != null && (workflowLines = this.customWorkFolwService.getWorkflowLinesByPreTask(nodeId, (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1)).getLinkid())).size() > 0) {
                for (WorkFlowLine workFlowLine : workflowLines) {
                    Set nodeCodes;
                    String actionid2 = workFlowLine.getActionid();
                    WorkFlowAction workflowActionById = this.customWorkFolwService.getWorkflowActionById(actionid2, workFlowDefine.getLinkid());
                    if (workFlowLine.getAfterNodeID() == null || workFlowLine.getAfterNodeID().contains("End") || !workflowActionById.getActionCode().equals(actionId) || (nodeCodes = nodeParams.stream().map(e -> e.getNodeCode()).collect(Collectors.toSet())).contains(workFlowLine.getAfterNodeID())) continue;
                    WorkFlowNodeSet nestNode = this.customWorkFolwService.getWorkFlowNodeSetByID(workFlowLine.getAfterNodeID(), workFlowDefine.getLinkid());
                    boolean signNode = nestNode.isSignNode();
                    boolean specialTag = nestNode.isSpecialTag();
                    boolean nodeJump = nestNode.isNodeJump();
                    if (!nodeJump) continue;
                    if (signNode) {
                        this.signNode(task, businessKey, nodeParams, nestNode, actionId);
                        continue;
                    }
                    if (specialTag) {
                        this.specialNode(task, businessKey, nodeParams, workFlowLine.getAfterNodeID(), actionId);
                        continue;
                    }
                    this.ordinaryNode(task, businessKey, nodeParams, nodeId, nestNode, workFlowLine, actionId, workFlowDefine);
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void specialNode(Task task, BusinessKey businessKey, List<NodeParam> nodeParams, String nodeId, String actionId) {
        List<UploadRecordNew> uploadActionsNew = this.queryUploadStateServiceImpl.queryUploadHistoryStates(businessKey);
        if (uploadActionsNew != null && uploadActionsNew.size() > 0) {
            for (UploadRecordNew uploadAction : uploadActionsNew) {
                if (uploadAction == null || !nodeId.equals(uploadAction.getTaskId())) continue;
                String action = uploadAction.getAction();
                if (!"act_upload".equals(action) && !"cus_upload".equals(action) && !"act_submit".equals(action) && !"cus_submit".equals(action) && !"act_confirm".equals(action) && !"cus_confirm".equals(action)) break;
                NodeParam nodeParam = new NodeParam();
                nodeParam.setNodeCode(nodeId);
                nodeParam.setNodeType(SPECIALNODE);
                nodeParams.add(nodeParam);
                this.nodeList(task, nodeId, businessKey, actionId, nodeParams);
                break;
            }
        }
    }

    private void signNode(Task task, BusinessKey businessKey, List<NodeParam> nodeParams, WorkFlowNodeSet nextNode, String actionId) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        NodeParam nodeParam = new NodeParam();
        nodeParam.setNodeCode(nextNode.getId());
        nodeParam.setNodeType(SIGNNODE);
        String[] actions = nextNode.getActions();
        if (actions != null && actions.length > 0) {
            for (String action : actions) {
                WorkFlowAction workflowActionById = this.customWorkFolwService.getWorkflowActionById(action, workFlowDefine.getLinkid());
                if (workflowActionById == null || !workflowActionById.getActionCode().equals(actionId)) continue;
                nodeParams.add(nodeParam);
                this.nodeList(task, nextNode.getId(), businessKey, actionId, nodeParams);
            }
        }
    }

    private void ordinaryNode(Task task, BusinessKey businessKey, List<NodeParam> nodeParams, String nodeId, WorkFlowNodeSet nestNode, WorkFlowLine workFlowLine, String actionId, WorkFlowDefine workFlowDefine) {
        Actor candicateActor = Actor.fromNpContext();
        HashSet<String> actors = new HashSet<String>();
        actors.add(candicateActor.getIdentityId());
        HashSet<String> actorsTemp = actors;
        String actionid2 = workFlowLine.getActionid();
        WorkFlowAction workflowActionById = this.customWorkFolwService.getWorkflowActionById(actionid2, workFlowDefine.getLinkid());
        Set<Object> actorId = new HashSet();
        if (nestNode != null) {
            String[] actions = nestNode.getActions();
            String[] partis = nestNode.getPartis();
            for (String action : actions) {
                actorsTemp = actors;
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(action, workFlowDefine.getLinkid());
                if (workflowAction == null || !actionId.equals(workflowAction.getActionCode())) continue;
                actorId = this.actors(task, businessKey, partis);
                actorsTemp.retainAll(actorId);
                if (!actionId.equals(workflowActionById.getActionCode()) || actorsTemp.isEmpty() || !actionId.equals(workflowAction.getActionCode())) continue;
                NodeParam nodeParam = new NodeParam();
                nodeParam.setNodeCode(workFlowLine.getAfterNodeID());
                nodeParam.setNodeType(ORDINARYNODE);
                nodeParams.add(nodeParam);
                this.nodeList(task, workFlowLine.getAfterNodeID(), businessKey, actionId, nodeParams);
            }
        }
    }

    private Set<String> actors(Task task, BusinessKey businessKey, String[] partis) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        HashSet<String> actorId = new HashSet<String>();
        try {
            List<WorkFlowParticipant> workFlowParticipants = this.customWorkFolwService.getWorkFlowParticipantsByID(partis, workFlowDefine.getLinkid());
            if (workFlowParticipants != null) {
                for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                    String convertParam = this.convertParam(workFlowParticipant);
                    String strategyid = workFlowParticipant.getStrategyid();
                    ActorStrategyParameter receiverParam = ActionMethod.getReceiverParam(strategyid, convertParam);
                    for (ActorStrategy ai : this.actorStrategy) {
                        if (!ai.getType().equals(strategyid)) continue;
                        Set<String> actor = ai.getActors((BusinessKeyInfo)businessKey, ai.serializeParameter(receiverParam), task);
                        actorId.addAll(actor);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actorId;
    }

    public String taskId(Task task, BusinessKey businessKey) {
        String nodeCode = task.getUserTaskId();
        Actor candicateActor = Actor.fromNpContext();
        HashSet<String> actors = new HashSet<String>();
        actors.add(candicateActor.getIdentityId());
        LinkedHashSet<String> nodeLists = new LinkedHashSet<String>();
        this.beginNodeList(task, nodeCode, businessKey, actors, nodeLists);
        ArrayList<String> collect = null;
        if (!CollectionUtils.isEmpty(nodeLists)) {
            collect = new ArrayList<String>(nodeLists);
            Collections.reverse(collect);
        }
        if (collect != null && collect.size() > 0) {
            return (String)collect.get(0);
        }
        return nodeCode;
    }

    private Set<String> beginNodeList(Task task, String nodeId, BusinessKey businessKey, Set<String> actors, LinkedHashSet<String> nodeLists) {
        try {
            WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(businessKey.getFormSchemeKey());
            DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
            Actor candicateActor = Actor.fromNpContext();
            ArrayList<String> beginNodeID = new ArrayList<String>();
            List<WorkFlowLine> line = this.line(nodeId, beginNodeID, workFlowDefine);
            String actionCode = this.actionCode(nodeId, beginNodeID, line, workFlowDefine);
            this.beginNodeId(nodeId, actionCode, beginNodeID, line, workFlowDefine);
            for (String nodeid : beginNodeID) {
                HashSet<String> actorId = new HashSet<String>();
                WorkFlowNodeSet workFlowNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(nodeid, workFlowDefine.getLinkid());
                if (workFlowNodeSet != null && !workFlowNodeSet.getId().contains("Start") && !workFlowNodeSet.getId().contains("End")) {
                    String[] actions = workFlowNodeSet.getActions();
                    String[] partis = workFlowNodeSet.getPartis();
                    for (String action : actions) {
                        WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(action, workFlowDefine.getLinkid());
                        if (workflowAction == null || !actionCode.equals(workflowAction.getActionCode())) continue;
                        Optional<UserTask> userTask = deployService.getUserTask(task.getProcessDefinitionId(), nodeid, businessKey.getFormSchemeKey());
                        List<WorkFlowParticipant> workFlowParticipants = this.customWorkFolwService.getWorkFlowParticipantsByID(partis, workFlowDefine.getLinkid());
                        if (workFlowParticipants == null) continue;
                        for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                            String convertParam = this.convertParam(workFlowParticipant);
                            String strategyid = workFlowParticipant.getStrategyid();
                            ActorStrategyParameter receiverParam = ActionMethod.getReceiverParam(strategyid, convertParam);
                            for (ActorStrategy ai : this.actorStrategy) {
                                if (!ai.getType().equals(strategyid)) continue;
                                Set<String> actor = ai.getActors((BusinessKeyInfo)businessKey, receiverParam, userTask.get());
                                actorId.addAll(actor);
                            }
                        }
                    }
                }
                if (actorId.contains(candicateActor.getUserId())) {
                    nodeLists.add(nodeid);
                    continue;
                }
                return nodeLists;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return nodeLists;
    }

    public List<WorkFlowLine> line(String nodeId, List<String> beginNodeID, WorkFlowDefine workFlowDefine) {
        ArrayList<WorkFlowLine> temp = new ArrayList<WorkFlowLine>();
        WorkFlowNodeSet workFlowNodeSetByID = this.customWorkFolwService.getWorkFlowNodeSetByID(nodeId, workFlowDefine.getLinkid());
        List<WorkFlowLine> workflowLines = this.customWorkFolwService.getWorkflowLinesByLinkid(workFlowNodeSetByID.getLinkid());
        for (WorkFlowLine workline : workflowLines) {
            WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(workline.getActionid(), workFlowDefine.getLinkid());
            if (workline.getActionid() != null && ("act_reject".equals(workflowAction.getActionCode()) || "act_return".equals(workflowAction.getActionCode()) || "cus_reject".equals(workflowAction.getActionCode()) || "cus_return".equals(workflowAction.getActionCode()))) continue;
            temp.add(workline);
        }
        return temp;
    }

    public String actionCode(String nodeId, List<String> beginNodeID, List<WorkFlowLine> workflowLines, WorkFlowDefine workFlowDefine) {
        for (WorkFlowLine workline : workflowLines) {
            String actionid;
            WorkFlowAction workflowAction;
            if (!nodeId.equals(workline.getAfterNodeID()) || beginNodeID.contains(workline.getBeforeNodeID()) || (workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid = workline.getActionid(), workFlowDefine.getLinkid())) == null) continue;
            return workflowAction.getActionCode();
        }
        return null;
    }

    public void beginNodeId(String nodeId, String actionCode, List<String> beginNodeID, List<WorkFlowLine> workflowLines, WorkFlowDefine workFlowDefine) {
        for (WorkFlowLine workline : workflowLines) {
            if (!nodeId.equals(workline.getAfterNodeID()) || beginNodeID.contains(workline.getBeforeNodeID())) continue;
            String actionid = workline.getActionid();
            WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid, workFlowDefine.getLinkid());
            if (workline.getBeforeNodeID().contains("Start") || !workflowAction.getActionCode().equals(actionCode)) continue;
            beginNodeID.add(workline.getBeforeNodeID());
            this.beginNodeId(workline.getBeforeNodeID(), actionCode, beginNodeID, workflowLines, workFlowDefine);
        }
    }

    public static List<Integer> receiveUnionList(List<Integer> firstArrayList, List<Integer> secondArrayList) {
        ArrayList<Integer> resultList = new ArrayList();
        TreeSet<Integer> firstSet = new TreeSet<Integer>(firstArrayList);
        if (secondArrayList != null) {
            for (Integer id : secondArrayList) {
                firstSet.add(id);
            }
        }
        resultList = new ArrayList<Integer>(firstSet);
        return resultList;
    }

    private boolean signNodeCanExecute(BusinessKey businessKey, String nodeId) {
        Map<String, Set<String>> userAndRoleMap;
        Set<String> actors;
        boolean compareDataVersion = this.counterParamBuilder.compareDataVersion(businessKey);
        WorkFlowType workflowType = this.commonUtil.workflowType(businessKey.getFormSchemeKey());
        List<String> formKeys = this.commonUtil.getFormKeyBySchemeKey(businessKey.getFormSchemeKey());
        boolean upload = this.counterParamBuilder.upload(businessKey, formKeys, nodeId, actors = this.counterParamBuilder.nodeActors(businessKey, nodeId), userAndRoleMap = this.counterParamBuilder.userReleateRole(actors), workflowType);
        return upload && !compareDataVersion;
    }

    private String convertParam(WorkFlowParticipant workFlowParticipant) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        map.put("roleIds", workFlowParticipant.getRoleIds());
        map.put("userIds", workFlowParticipant.getUserIds());
        try {
            return mapper.writeValueAsString(map);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

