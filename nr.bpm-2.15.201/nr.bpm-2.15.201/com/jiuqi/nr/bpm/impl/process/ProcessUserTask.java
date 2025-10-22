/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.common.UserAction;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.impl.Actor.CanAuditActorStrategy;
import com.jiuqi.nr.bpm.impl.Actor.CanSubmitActorStrategy;
import com.jiuqi.nr.bpm.impl.Actor.CanUploadActorStrategy;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.impl.Actor.JsonUtils;
import com.jiuqi.nr.bpm.impl.Actor.SpecifiedAndGrantedUsersParameter;
import com.jiuqi.nr.bpm.impl.Actor.SpecifiedUsersParameter;
import com.jiuqi.nr.bpm.impl.process.ProcessActorStrategy;
import com.jiuqi.nr.bpm.impl.process.ProcessUserAction;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class ProcessUserTask
implements UserTask {
    private String userTaskId;
    private String name;
    private List<ProcessAction> processAction;
    private WorkFlowNodeSet nodeSet;
    private List<WorkFlowParticipant> participants;

    public ProcessUserTask(String userTaskId, String name, List<ProcessAction> processAction) {
        this.userTaskId = userTaskId;
        this.name = name;
        this.processAction = processAction;
    }

    public ProcessUserTask(WorkFlowNodeSet nodeSet, List<WorkFlowParticipant> participants, List<ProcessAction> processAction) {
        this.userTaskId = nodeSet.getId();
        this.name = nodeSet.getTitle();
        this.nodeSet = nodeSet;
        this.processAction = processAction;
        this.participants = participants;
    }

    @Override
    public String getId() {
        return this.userTaskId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTodoTemplate() {
        return null;
    }

    @Override
    public boolean isFormEditable() {
        return false;
    }

    @Override
    public boolean isRetrivable() {
        return this.nodeSet != null ? this.nodeSet.isGetback() : false;
    }

    @Override
    public List<UserAction> getActions() {
        ArrayList<UserAction> userActions = new ArrayList<UserAction>();
        this.processAction.forEach(e -> userActions.add(new ProcessUserAction(e.getAction(), e.getName(), false)));
        return userActions;
    }

    @Override
    public List<ActorStrategyInstance> getActorStrategies() {
        ArrayList<ActorStrategyInstance> actorStrategyInstances = new ArrayList<ActorStrategyInstance>();
        if (CollectionUtils.isEmpty(this.processAction)) {
            return actorStrategyInstances;
        }
        if (this.nodeSet != null) {
            this.activitiStrategy(actorStrategyInstances);
        } else {
            this.defaultProcessStratey(actorStrategyInstances);
        }
        return actorStrategyInstances;
    }

    private void activitiStrategy(List<ActorStrategyInstance> actorStrategyInstances) {
        for (WorkFlowParticipant particp : this.participants) {
            String strategyId = particp.getStrategyid();
            ProcessActorStrategy actorStrategyExtension = new ProcessActorStrategy();
            try {
                Class<?> clazz = Class.forName(strategyId);
                Set<String> rolesSet = Collections.emptySet();
                Set<String> usersSet = Collections.emptySet();
                String[] roles = particp.getRoleIds();
                String[] users = particp.getUserIds();
                if (roles != null && roles.length > 0) {
                    rolesSet = new HashSet<String>(Arrays.asList(roles));
                }
                if (users != null && users.length > 0) {
                    usersSet = new HashSet<String>(Arrays.asList(users));
                }
                ActorStrategy actorStrategyInstance = (ActorStrategy)clazz.newInstance();
                ActorStrategyParameter param = this.getActorStrategyParameterType(actorStrategyInstance.getParameterType().getSimpleName(), rolesSet, usersSet);
                actorStrategyExtension.setType(strategyId);
                actorStrategyExtension.setParameterJson(JsonUtils.toString(param));
                actorStrategyInstances.add(actorStrategyExtension);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void defaultProcessStratey(List<ActorStrategyInstance> actorStrategyInstances) {
        for (ProcessAction action : this.processAction) {
            ProcessActorStrategy actorStrategyExtension = new ProcessActorStrategy();
            String actionId = action.getAction();
            if (actionId.equals("act_upload") || actionId.equals("act_return")) {
                actorStrategyExtension.setType(CanUploadActorStrategy.class.getName());
            } else if (actionId.equals("act_submit")) {
                actorStrategyExtension.setType(CanSubmitActorStrategy.class.getName());
            } else {
                actorStrategyExtension.setType(CanAuditActorStrategy.class.getName());
            }
            actorStrategyInstances.add(actorStrategyExtension);
        }
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

    @Override
    public boolean isNeedNotice() {
        return false;
    }

    @Override
    public Set<String> getNoticeChannles() {
        return null;
    }
}

