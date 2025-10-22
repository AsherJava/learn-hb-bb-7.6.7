/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.authz2.Authz2Constants
 */
package com.jiuqi.nr.bpm.impl.countersign.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.authz2.Authz2Constants;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.Actor.IActorStrategyCountSign;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.impl.countersign.group.IQueryGroupCount;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class QueryGroupNumByRole
implements IQueryGroupCount {
    private final Logger logger = LoggerFactory.getLogger(QueryGroupNumByRole.class);
    private List<IActorStrategyCountSign> actorStrategyCountSigns;
    private CustomWorkFolwService customWorkFolwService;
    private WorkflowSettingService settingService;

    public QueryGroupNumByRole(List<IActorStrategyCountSign> actorStrategyCountSigns, CustomWorkFolwService customWorkFolwService, WorkflowSettingService settingService) {
        this.actorStrategyCountSigns = actorStrategyCountSigns;
        this.customWorkFolwService = customWorkFolwService;
        this.settingService = settingService;
    }

    @Override
    public Set<String> getActors(WorkFlowNodeSet workFlowNodeSet, UserTask task, BusinessKey businessKey) {
        HashSet<String> actors = new HashSet<String>();
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        List<WorkFlowParticipant> workFlowParticipants = this.getParticPants(workFlowNodeSet, workFlowDefine);
        if (!CollectionUtils.isEmpty(workFlowParticipants)) {
            for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                String[] roleIds = workFlowParticipant.getRoleIds();
                String[] userIds = workFlowParticipant.getUserIds();
                String strategyid = workFlowParticipant.getStrategyid();
                if (strategyid == null) continue;
                HashMap<String, String[]> map = new HashMap<String, String[]>();
                map.put("roleIds", roleIds);
                map.put("userIds", userIds);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String param = objectMapper.writeValueAsString(map);
                    try {
                        ActorStrategyParameter receiverParam = ActionMethod.getReceiverParam(strategyid, param);
                        for (IActorStrategyCountSign ai : this.actorStrategyCountSigns) {
                            if (!ai.getActorStrategyType().equals(strategyid) || !(receiverParam instanceof GrantedToEntityAndRoleParameter)) continue;
                            GrantedToEntityAndRoleParameter parameter = (GrantedToEntityAndRoleParameter)receiverParam;
                            Set<String> actor = ai.getCountSignGroupNum(businessKey, parameter, task);
                            actors.addAll(actor);
                        }
                    }
                    catch (Exception e) {
                        this.logger.error("\u67e5\u8be2\u53c2\u4e0e\u8005\u7b56\u7565\u5931\u8d25" + e.getMessage(), e);
                    }
                }
                catch (JsonProcessingException e1) {
                    this.logger.error(e1.getMessage(), e1);
                }
            }
        }
        this.removeRoleId(actors);
        return actors;
    }

    private List<WorkFlowParticipant> getParticPants(WorkFlowNodeSet workFlowNodeSet, WorkFlowDefine workFlowDefine) {
        String[] partis;
        ArrayList<WorkFlowParticipant> participants = new ArrayList<WorkFlowParticipant>();
        if (workFlowNodeSet != null && (partis = workFlowNodeSet.getPartis()) != null && partis.length > 0) {
            List<WorkFlowParticipant> workFlowParticipants = this.customWorkFolwService.getWorkFlowParticipantsByID(partis, workFlowDefine.getLinkid());
            participants.addAll(workFlowParticipants);
        }
        return participants;
    }

    private Set<String> removeRoleId(Set<String> roleIds) {
        HashSet<String> temp = new HashSet<String>();
        List allsystemroleids = Authz2Constants.allSystemRoleIds;
        for (String roleId : roleIds) {
            if (allsystemroleids.contains(roleId)) continue;
            temp.add(roleId);
        }
        return temp;
    }

    @Override
    public boolean isGroupActor(UserTask userTask, BusinessKey businessKey, Actor actor, ActorStrategyProvider actorStrategyProvider, Task task, String roleKey) {
        for (ActorStrategyInstance actorStrategyInstance : userTask.getActorStrategies()) {
            ActorStrategy<?> actorStrategy = actorStrategyProvider.getActorStrategyByType(actorStrategyInstance.getType());
            String type = actorStrategy.getType();
            for (IActorStrategyCountSign ai : this.actorStrategyCountSigns) {
                if (!ai.getActorStrategyType().equals(type)) continue;
                return ai.isGroupActor(businessKey, actor, task, roleKey);
            }
        }
        return false;
    }
}

