/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.IActorStrategyCountSign;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyBase;
import com.jiuqi.nr.bpm.impl.Actor.EntityIdentityServiceExtends;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.common.NrProcessAuthorityProvider;
import com.jiuqi.nr.bpm.service.BpmIEntityUpgrader;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParentUnitsAndSpecifiedUsers
extends ActorStrategyBase<GrantedToEntityAndRoleParameter>
implements IActorStrategyCountSign {
    @Autowired
    private EntityIdentityServiceExtends entityLinkService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    RoleService roleService;
    @Autowired
    NrProcessAuthorityProvider nrProcessAuthorityProvider;
    @Autowired
    private BpmIEntityUpgrader bpmIEntityUpgrader;

    @Override
    public String getTitle() {
        return "\u5f53\u524d\u5355\u4f4d\u7684\u4e0a\u7ea7\u5355\u4f4d\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237";
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, Task task) {
        Set<String> canExeIdentitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, task);
        Set<String> actors = this.actors(businessKey, strategyParameter);
        actors.retainAll(canExeIdentitys);
        return actors;
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, UserTask userTask) {
        Set<String> canExeIdentitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, userTask);
        Set<String> actors = this.actors(businessKey, strategyParameter);
        actors.retainAll(canExeIdentitys);
        return actors;
    }

    private Set<String> actors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter) {
        Set<String> roles = strategyParameter.getRoleIdSet();
        HashSet<String> identities = new HashSet<String>();
        for (String roleId : roles) {
            identities.addAll(this.roleService.getUserIdAndIdentityMappUserIdByRole(roleId));
        }
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
        String[] parentsEntityKey = this.bpmIEntityUpgrader.queryParentEntityKeys(entityKey, entityView, businessKey);
        if (parentsEntityKey != null && parentsEntityKey.length > 0) {
            for (String parentKey : parentsEntityKey) {
                actorIds.addAll(this.entityLinkService.getGrantedIdentityKeysWithEntity(parentKey));
            }
        }
        identities.retainAll(actorIds);
        return identities;
    }

    @Override
    public boolean isUserMatch(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, Actor actor, Task task) {
        boolean isRetainAll;
        if (actor.getIdentityId() == null) {
            return false;
        }
        List roles = this.roleService.getByIdentity(actor.getIdentityId());
        HashSet<String> roleSet = new HashSet<String>();
        for (Role role : roles) {
            roleSet.add(role.getId());
        }
        roleSet.retainAll(strategyParameter.getRoleIdSet());
        boolean bl = strategyParameter == null ? false : (isRetainAll = roleSet.size() > 0);
        if (!isRetainAll) {
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
        String[] parentsEntityKey = this.bpmIEntityUpgrader.queryParentEntityKeys(entityKey, entityView, businessKey);
        Boolean isCanExecute = this.nrProcessAuthorityProvider.isCanExecuteCurrentTaskIdentity(businessKey, task);
        int i = 0;
        while (parentsEntityKey != null && i < parentsEntityKey.length) {
            Boolean isGrant = this.entityLinkService.isGrantedWithEntity(businessKey.getFormSchemeKey(), entityView, parentsEntityKey[i++], actor.getIdentityId(), businessKey.getPeriod());
            if (!isGrant.booleanValue() || !isCanExecute.booleanValue()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public Class<? extends GrantedToEntityAndRoleParameter> getParameterType() {
        return GrantedToEntityAndRoleParameter.class;
    }

    @Override
    public String getDescription() {
        return "\u5c5e\u4e8e\u5f53\u524d\u5355\u4f4d\u7684\u4e0a\u7ea7\u5355\u4f4d\uff08\u76f4\u63a5\u548c\u95f4\u63a5\u4e0a\u7ea7\uff09\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237\u53ef\u4ee5\u770b\u5230\u5f53\u524d\u5355\u4f4d\u3001\u5f53\u524d\u6d41\u7a0b\u8282\u70b9\u7684\u52a8\u4f5c\u5e76\u6267\u884c\u3002\u9700\u8981\u6307\u5b9a\u89d2\u8272\u3002";
    }

    @Override
    public Set<String> getCountSignGroupNum(BusinessKeyInfo businessKeyInfo, GrantedToEntityAndRoleParameter strategyParameter, UserTask task) {
        HashSet<String> actorIds = new HashSet<String>();
        Set<String> roles = strategyParameter.getRoleIdSet();
        if (roles.isEmpty()) {
            return actorIds;
        }
        return roles;
    }

    @Override
    public boolean isGroupActor(BusinessKeyInfo businessKey, Actor actor, Task task, String roleKey) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        List roles = this.roleService.getByIdentity(actor.getIdentityId());
        HashSet<String> roleSet = new HashSet<String>();
        for (Role role : roles) {
            roleSet.add(role.getId());
        }
        HashSet<String> roleTemps = new HashSet<String>();
        roleTemps.add(roleKey);
        roleSet.retainAll(roleTemps);
        if (roleSet.size() <= 0) {
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
        String[] parentsEntityKey = this.bpmIEntityUpgrader.queryParentEntityKeys(entityKey, entityView, businessKey);
        Boolean isCanExecute = this.nrProcessAuthorityProvider.isCanExecuteCurrentTaskIdentity(businessKey, task);
        int i = 0;
        while (parentsEntityKey != null && i < parentsEntityKey.length) {
            Boolean isGrant = this.entityLinkService.isGrantedWithEntity(businessKey.getFormSchemeKey(), entityView, parentsEntityKey[i++], actor.getIdentityId(), businessKey.getPeriod());
            if (!isGrant.booleanValue() || !isCanExecute.booleanValue()) continue;
            return true;
        }
        return false;
    }
}

