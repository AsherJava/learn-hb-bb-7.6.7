/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.IActorStrategyCountSign;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyBase;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.impl.Actor.EntityIdentityServiceExtends;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.common.NrProcessAuthorityProvider;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrantedToEntityAndRoleActorStrategy
extends ActorStrategyBase<GrantedToEntityAndRoleParameter>
implements IActorStrategyCountSign {
    @Autowired
    private RoleService roleInfoService;
    @Autowired
    private EntityIdentityServiceExtends entityLinkService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private NrProcessAuthorityProvider nrProcessAuthorityProvider;

    @Override
    public String getTitle() {
        return "\u5f53\u524d\u5355\u4f4d\u4e0b\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237";
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, Task task) {
        Set<String> identitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, task);
        Set<String> validIdentityIds = this.actors(businessKey, strategyParameter);
        validIdentityIds.retainAll(identitys);
        return validIdentityIds;
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, UserTask userTask) {
        Set<String> identitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, userTask);
        Set<String> validIdentityIds = this.actors(businessKey, strategyParameter);
        validIdentityIds.retainAll(identitys);
        return validIdentityIds;
    }

    @Override
    public boolean isUserMatch(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, Actor actor, Task task) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        TableModelDefine t = this.nrParameterUtils.getFirstEntityTable(businessKey);
        if (t == null) {
            return false;
        }
        EntityViewDefine entityView = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityView == null) {
            return false;
        }
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(t.getName());
        if (StringUtils.isEmpty((String)entityKeyData)) {
            return false;
        }
        if (!this.entityLinkService.isGrantedWithEntity(businessKey.getFormSchemeKey(), entityView, entityKeyData.toString(), actor.getIdentityId(), businessKey.getPeriod()).booleanValue()) {
            return false;
        }
        Set<String> givenRoleIds = strategyParameter.getRoleIdSet();
        if (givenRoleIds.isEmpty()) {
            return true;
        }
        Set<String> givenRoleIdsConvertToLow = ActorUtils.setToLow(givenRoleIds);
        Set identityLinkedRoleIds = this.roleInfoService.getIdByIdentity(actor.getIdentityId());
        Collection<String> identityLinkedRoleIdsToLow = ActorUtils.listToLow(identityLinkedRoleIds);
        boolean isGrantRole = identityLinkedRoleIdsToLow.stream().anyMatch(o -> givenRoleIdsConvertToLow.contains(o));
        Boolean isCanExecute = this.nrProcessAuthorityProvider.isCanExecuteCurrentTaskIdentity(businessKey, task);
        return isGrantRole && isCanExecute != false;
    }

    @Override
    public Class<? extends GrantedToEntityAndRoleParameter> getParameterType() {
        return GrantedToEntityAndRoleParameter.class;
    }

    private Set<String> actors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter) {
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
        Set<String> givenRoleIds = strategyParameter.getRoleIdSet();
        if (givenRoleIds.isEmpty()) {
            return actorIds;
        }
        Set<String> givenRoleIdsConvertToLow = ActorUtils.setToLow(givenRoleIds);
        for (String roleId : givenRoleIdsConvertToLow) {
            actorIds.addAll(this.roleInfoService.getUserIdAndIdentityMappUserIdByRole(roleId));
        }
        Collection<String> grantedIdentityKeysWithEntity = this.entityLinkService.getGrantedIdentityKeysWithEntity(entityKey.toString());
        actorIds.retainAll(grantedIdentityKeysWithEntity);
        return actorIds;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public String getDescription() {
        return "\u5c5e\u4e8e\u5f53\u524d\u5355\u4f4d\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237\u53ef\u4ee5\u770b\u5230\u5f53\u524d\u5355\u4f4d\u3001\u5f53\u524d\u6d41\u7a0b\u8282\u70b9\u7684\u52a8\u4f5c\u5e76\u6267\u884c\u3002\u9700\u8981\u6307\u5b9a\u89d2\u8272\u3002";
    }

    @Override
    public Set<String> getCountSignGroupNum(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, UserTask task) {
        HashSet<String> actorIds = new HashSet<String>();
        Set<String> givenRoleIds = strategyParameter.getRoleIdSet();
        if (givenRoleIds.isEmpty()) {
            return actorIds;
        }
        Set<String> givenRoleIdsConvertToLow = ActorUtils.setToLow(givenRoleIds);
        return givenRoleIdsConvertToLow;
    }

    @Override
    public boolean isGroupActor(BusinessKeyInfo businessKey, Actor actor, Task task, String roleKey) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        TableModelDefine t = this.nrParameterUtils.getFirstEntityTable(businessKey);
        if (t == null) {
            return false;
        }
        EntityViewDefine entityView = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityView == null) {
            return false;
        }
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(t.getName());
        if (StringUtils.isEmpty((String)entityKeyData)) {
            return false;
        }
        if (!this.entityLinkService.isGrantedWithEntity(businessKey.getFormSchemeKey(), entityView, entityKeyData.toString(), actor.getIdentityId(), businessKey.getPeriod()).booleanValue()) {
            return false;
        }
        HashSet<String> roleTemps = new HashSet<String>();
        roleTemps.add(roleKey);
        if (roleTemps.isEmpty()) {
            return true;
        }
        Set<String> givenRoleIdsConvertToLow = ActorUtils.setToLow(roleTemps);
        Set identityLinkedRoleIds = this.roleInfoService.getIdByIdentity(actor.getIdentityId());
        Collection<String> identityLinkedRoleIdsToLow = ActorUtils.listToLow(identityLinkedRoleIds);
        boolean isGrantRole = identityLinkedRoleIdsToLow.stream().anyMatch(o -> givenRoleIdsConvertToLow.contains(o));
        Boolean isCanExecute = this.nrProcessAuthorityProvider.isCanExecuteCurrentTaskIdentity(businessKey, task);
        return isGrantRole && isCanExecute != false;
    }
}

