/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.bpm.impl.upload.modeling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeActionSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.impl.Actor.SpecifiedAndGrantedUsersParameter;
import com.jiuqi.nr.bpm.impl.Actor.SpecifiedUsersParameter;
import com.jiuqi.nr.bpm.modeling.ProcessBuilder;
import com.jiuqi.nr.bpm.modeling.model.Action;
import com.jiuqi.nr.bpm.modeling.model.ConditionExpression;
import com.jiuqi.nr.bpm.modeling.model.EndEvent;
import com.jiuqi.nr.bpm.modeling.model.ExecutionListener;
import com.jiuqi.nr.bpm.modeling.model.ExtensionElements;
import com.jiuqi.nr.bpm.modeling.model.FlowableProcessElement;
import com.jiuqi.nr.bpm.modeling.model.FormEditable;
import com.jiuqi.nr.bpm.modeling.model.MultiInstanceLoop;
import com.jiuqi.nr.bpm.modeling.model.MultiInstanceLoopCompleteCondition;
import com.jiuqi.nr.bpm.modeling.model.Retrivable;
import com.jiuqi.nr.bpm.modeling.model.StartEvent;
import com.jiuqi.nr.bpm.modeling.model.TaskListener;
import com.jiuqi.nr.bpm.modeling.model.UserTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProcessBuilder
extends ProcessBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CustomProcessBuilder.class);
    private WorkFlowInfoObj desWorkFlowInfoObj;

    public CustomProcessBuilder(String processId, WorkFlowInfoObj desWorkFlowInfoObj) {
        super(processId);
        this.desWorkFlowInfoObj = desWorkFlowInfoObj;
    }

    @Override
    public ProcessBuilder build() {
        try {
            this.buildTaskAndFlow();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BpmException(e);
        }
        return this;
    }

    private void buildTaskAndFlow() throws Exception {
        WorkFlowDefine workFlowDefine = this.desWorkFlowInfoObj.getDefine();
        List<WorkFlowNodeSet> nodes = this.desWorkFlowInfoObj.getNodesets();
        List<WorkFlowLine> lines = this.desWorkFlowInfoObj.getLines();
        ArrayList<UserTask> userTasks = new ArrayList<UserTask>();
        ExecutionListener.ExecutionEndListener executionListener = new ExecutionListener.ExecutionEndListener();
        ExtensionElements extensionElements = new ExtensionElements();
        extensionElements.addExtension(executionListener);
        this.addExtensionElements(extensionElements);
        StartEvent startEvent = new StartEvent("start");
        startEvent.setName("start");
        this.addStart(startEvent);
        for (WorkFlowNodeSet workFlowNodeSet : nodes) {
            if (workFlowNodeSet.getId().contains("StartEvent") || workFlowNodeSet.getId().contains("EndEvent")) continue;
            UserTask task = this.buildTask(workFlowNodeSet);
            this.addUserTask(task);
            userTasks.add(task);
        }
        EndEvent endEvent = null;
        if (workFlowDefine.isCustom()) {
            endEvent = new EndEvent("end");
            endEvent.setName("end");
            this.addEnd(endEvent);
        }
        List startLine = lines.stream().filter(e -> e.getBeforeNodeID().contains("StartEvent")).collect(Collectors.toList());
        for (WorkFlowLine workFlowLine : lines) {
            FlowableProcessElement beforeNode = this.getTask(userTasks, workFlowLine.getBeforeNodeID());
            FlowableProcessElement afterNode = this.getTask(userTasks, workFlowLine.getAfterNodeID());
            if (workFlowLine.getAfterNodeID().contains("EndEvent") && endEvent != null) {
                afterNode = endEvent;
            }
            if (workFlowLine.getBeforeNodeID().contains("StartEvent")) {
                beforeNode = startEvent;
            }
            WorkFlowAction action = this.getActionCode(this.desWorkFlowInfoObj.getActions(), workFlowLine.getActionid());
            ConditionExpression.OnActionCondition onCondition = null;
            if (workFlowLine.getActionid() != null && action != null) {
                onCondition = new ConditionExpression.OnActionCondition(beforeNode, workFlowLine.getId(), action.getActionCode(), "true");
                this.addSequenceFlowConditional(beforeNode, afterNode, onCondition);
                continue;
            }
            if (startLine.size() > 1 && beforeNode == startEvent) {
                onCondition = new ConditionExpression.OnActionCondition(workFlowLine.getId(), "true");
                this.addSequenceFlowConditional(beforeNode, afterNode, onCondition);
                continue;
            }
            this.addSequenceFlow(beforeNode, afterNode);
        }
    }

    private void addSequenceFlowConditional(String id, FlowableProcessElement beforeNode, FlowableProcessElement afterNode, ConditionExpression.OnActionCondition onCondition) {
    }

    private WorkFlowAction getActionCode(List<WorkFlowAction> actions, String actionid) {
        for (WorkFlowAction action : actions) {
            if (!action.getId().equals(actionid)) continue;
            return action;
        }
        return null;
    }

    private UserTask getTask(List<UserTask> userTasks, String taskId) {
        for (UserTask userTask : userTasks) {
            if (!userTask.getId().equals(taskId)) continue;
            return userTask;
        }
        return null;
    }

    public UserTask buildTask(WorkFlowNodeSet node) throws Exception {
        UserTask userTask = new UserTask(node.getId());
        userTask.setName(node.getTitle());
        TaskListener.DefaultTaskCreateListener taskListenerExtension = new TaskListener.DefaultTaskCreateListener();
        userTask.addExtension(taskListenerExtension);
        String[] actorStrategyIds = node.getPartis();
        ArrayList actorStrategyIdList = new ArrayList();
        if (actorStrategyIds != null) {
            Collections.addAll(actorStrategyIdList, actorStrategyIds);
        }
        List<WorkFlowParticipant> participants = this.desWorkFlowInfoObj.getParticis();
        for (WorkFlowParticipant workFlowParticipant : participants) {
            ActorStrategyParameter param;
            if (!actorStrategyIdList.contains(workFlowParticipant.getId())) continue;
            com.jiuqi.nr.bpm.modeling.model.ActorStrategy actorStrategyExtension = new com.jiuqi.nr.bpm.modeling.model.ActorStrategy();
            Class<?> clazz = Class.forName(workFlowParticipant.getStrategyid());
            actorStrategyExtension.setType(clazz);
            String[] roleIds = workFlowParticipant.getRoleIds();
            String[] userIds = workFlowParticipant.getUserIds();
            ActorStrategy actorStrategyInstance = (ActorStrategy)clazz.newInstance();
            String parameterType = actorStrategyInstance.getParameterType().getSimpleName();
            HashSet<String> roleIdSet = new HashSet<String>();
            HashSet<String> userIdSet = new HashSet<String>();
            if (roleIds != null) {
                roleIdSet = new HashSet<String>(Arrays.asList(roleIds));
            }
            if (userIds != null) {
                userIdSet = new HashSet<String>(Arrays.asList(userIds));
            }
            if ((param = this.getActorStrategyParameterType(parameterType, roleIdSet, userIdSet)) != null) {
                actorStrategyExtension.setParameter(param);
            }
            userTask.addExtension(actorStrategyExtension);
        }
        String[] actionArr = node.getActions();
        ArrayList actionList = new ArrayList();
        if (actionArr != null) {
            Collections.addAll(actionList, actionArr);
        }
        List<WorkFlowAction> actions = this.desWorkFlowInfoObj.getActions();
        for (WorkFlowAction action : actions) {
            if (!actionList.contains(action.getId())) continue;
            Action actionObj = new Action(action.getActionCode());
            actionObj.setName(action.getActionTitle());
            ObjectMapper objectMapper = new ObjectMapper();
            WorkFlowNodeActionSet workFlowNodeActionSet = null;
            try {
                workFlowNodeActionSet = (WorkFlowNodeActionSet)objectMapper.readValue(action.getExset(), WorkFlowNodeActionSet.class);
            }
            catch (Exception e) {
                throw new BpmException(e);
            }
            actionObj.setNeedComment(workFlowNodeActionSet.isNeedOptDesc());
            userTask.addExtension(actionObj);
        }
        boolean signNode = node.isSignNode();
        if (signNode) {
            userTask.setCandidateGroups("assignee");
            MultiInstanceLoop multiInstanceLoop = new MultiInstanceLoop();
            multiInstanceLoop.setProperty("isSequential", "false");
            multiInstanceLoop.setProperty("activiti:collection", "assigneeList");
            multiInstanceLoop.setProperty("activiti:elementVariable", "assignee");
            MultiInstanceLoopCompleteCondition.DefaultMultiInstanceLoopExtension condition = new MultiInstanceLoopCompleteCondition.DefaultMultiInstanceLoopExtension();
            userTask.setMultiInstanceLoop(multiInstanceLoop);
            userTask.addMultiInstanceLoop(condition);
        }
        userTask.addExtension(new Retrivable(node.isGetback()));
        userTask.addExtension(new FormEditable(node.isWritable()));
        return userTask;
    }

    private ActorStrategyParameter getActorStrategyParameterType(String actorStrategy, Set<String> roleIdSet, Set<String> userIdSet) {
        switch (actorStrategy) {
            case "SpecifiedAndGrantedUsersParameter": {
                SpecifiedAndGrantedUsersParameter param = new SpecifiedAndGrantedUsersParameter();
                param.setRoleIdSet(roleIdSet);
                param.setUserIdSet(userIdSet);
                return param;
            }
            case "SpecifiedUsersParameter": {
                SpecifiedUsersParameter param1 = new SpecifiedUsersParameter();
                param1.setUserIdSet(userIdSet);
                return param1;
            }
            case "GrantedToEntityAndRoleParameter": {
                GrantedToEntityAndRoleParameter param2 = new GrantedToEntityAndRoleParameter();
                param2.setRoleIdSet(roleIdSet);
                return param2;
            }
        }
        return null;
    }
}

