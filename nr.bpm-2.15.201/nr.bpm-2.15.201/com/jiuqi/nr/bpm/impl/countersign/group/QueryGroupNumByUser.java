/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 */
package com.jiuqi.nr.bpm.impl.countersign.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
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

public class QueryGroupNumByUser
implements IQueryGroupCount {
    private final Logger logger = LoggerFactory.getLogger(QueryGroupNumByUser.class);
    private List<ActorStrategy> actorStrategy;
    private CustomWorkFolwService customWorkFolwService;
    private SystemIdentityService systemIdentityService;
    private WorkflowSettingService settingService;

    public QueryGroupNumByUser(List<ActorStrategy> actorStrategy, CustomWorkFolwService customWorkFolwService, SystemIdentityService systemIdentityService, WorkflowSettingService settingService) {
        this.actorStrategy = actorStrategy;
        this.customWorkFolwService = customWorkFolwService;
        this.systemIdentityService = systemIdentityService;
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
                        for (ActorStrategy ai : this.actorStrategy) {
                            if (!ai.getType().equals(strategyid)) continue;
                            Set<String> actor = ai.getActors((BusinessKeyInfo)businessKey, receiverParam, task);
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
        this.removeSystemUser(actors);
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

    private Set<String> removeSystemUser(Set<String> users) {
        HashSet<String> temp = new HashSet<String>();
        Set allSystemIdentities = this.systemIdentityService.getAllSystemIdentities();
        if (allSystemIdentities != null && users != null) {
            for (String userKey : users) {
                if (allSystemIdentities.contains(userKey)) continue;
                temp.add(userKey);
            }
        }
        return temp;
    }

    @Override
    public boolean isGroupActor(UserTask userTask, BusinessKey businessKey, Actor actor, ActorStrategyProvider actorStrategyProvider, Task task, String roleKey) {
        return false;
    }
}

