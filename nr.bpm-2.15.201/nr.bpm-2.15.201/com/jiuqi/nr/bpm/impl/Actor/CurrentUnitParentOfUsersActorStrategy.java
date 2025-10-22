/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyBase;
import com.jiuqi.nr.bpm.impl.Actor.EntityIdentityServiceExtends;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.common.NrProcessAuthorityProvider;
import com.jiuqi.nr.bpm.service.BpmIEntityUpgrader;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentUnitParentOfUsersActorStrategy
extends ActorStrategyBase<ActorStrategyParameter.Useless> {
    @Autowired
    private EntityIdentityServiceExtends entityLinkService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private NrProcessAuthorityProvider nrProcessAuthorityProvider;
    @Autowired
    private BpmIEntityUpgrader bpmIEntityUpgrader;

    @Override
    public String getTitle() {
        return "\u5f53\u524d\u5355\u4f4d\u7684\u76f4\u63a5\u4e0a\u7ea7\u7528\u6237";
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, ActorStrategyParameter.Useless strategyParameter, Task task) {
        Set<String> identitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, task);
        Set<String> actorIds = this.actors(businessKey);
        actorIds.retainAll(identitys);
        return actorIds;
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, ActorStrategyParameter.Useless strategyParameter, UserTask userTask) {
        Set<String> identitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, userTask);
        Set<String> actorIds = this.actors(businessKey);
        actorIds.retainAll(identitys);
        return actorIds;
    }

    private Set<String> actors(BusinessKeyInfo businessKey) {
        HashSet<String> actorIds = new HashSet<String>();
        TableModelDefine t = this.nrParameterUtils.getFirstEntityTable(businessKey);
        if (t == null) {
            return actorIds;
        }
        EntityViewDefine entityView = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityView == null) {
            return actorIds;
        }
        String entityKey = businessKey.getMasterEntityInfo().getMasterEntityKey(t.getName());
        if (StringUtils.isEmpty((String)entityKey)) {
            return actorIds;
        }
        String parentsEntityKey = this.bpmIEntityUpgrader.queryParentEntityKey(entityKey, entityView, businessKey);
        if (parentsEntityKey != null) {
            actorIds.addAll(this.entityLinkService.getGrantedIdentityKeysWithEntity(parentsEntityKey));
        }
        return actorIds;
    }

    @Override
    public boolean isUserMatch(BusinessKeyInfo businessKey, ActorStrategyParameter.Useless strategyParameter, Actor actor, Task task) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        EntityViewDefine entityView = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityView == null) {
            return false;
        }
        TableModelDefine t = this.nrParameterUtils.getFirstEntityTable(businessKey);
        if (t == null) {
            return false;
        }
        String entityKey = businessKey.getMasterEntityInfo().getMasterEntityKey(t.getName());
        if (StringUtils.isEmpty((String)entityKey)) {
            return false;
        }
        String parentsEntityKey = this.bpmIEntityUpgrader.queryParentEntityKey(entityKey, entityView, businessKey);
        Boolean isGrantEntity = false;
        if (parentsEntityKey != null) {
            isGrantEntity = this.entityLinkService.isGrantedWithEntity(businessKey.getFormSchemeKey(), entityView, parentsEntityKey, actor.getIdentityId(), businessKey.getPeriod());
        }
        Boolean isCanExecute = this.nrProcessAuthorityProvider.isCanExecuteCurrentTaskIdentity(businessKey, task);
        return (isGrantEntity != false || isCanExecute != false) && (isGrantEntity == false || isCanExecute != false);
    }

    @Override
    public Class<? extends ActorStrategyParameter.Useless> getParameterType() {
        return ActorStrategyParameter.Useless.class;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public String getDescription() {
        return "\u5c5e\u4e8e\u5f53\u524d\u5355\u4f4d\u7684\u76f4\u63a5\u4e0a\u7ea7\u5355\u4f4d\u7684\u7528\u6237\u53ef\u4ee5\u770b\u5230\u5f53\u524d\u5355\u4f4d\u3001\u5f53\u524d\u6d41\u7a0b\u8282\u70b9\u7684\u52a8\u4f5c\u5e76\u6267\u884c\u3002";
    }
}

