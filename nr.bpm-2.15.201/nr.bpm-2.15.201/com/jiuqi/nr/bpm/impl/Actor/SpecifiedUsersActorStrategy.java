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
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyBase;
import com.jiuqi.nr.bpm.impl.Actor.EntityIdentityServiceExtends;
import com.jiuqi.nr.bpm.impl.Actor.SpecifiedAndGrantedUsersParameter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.common.NrProcessAuthorityProvider;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpecifiedUsersActorStrategy
extends ActorStrategyBase<SpecifiedAndGrantedUsersParameter> {
    @Autowired
    private RoleService roleService;
    @Autowired
    private NrProcessAuthorityProvider nrProcessAuthorityProvider;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private EntityIdentityServiceExtends entityLinkService;

    @Override
    public String getTitle() {
        return "\u6307\u5b9a\u7684\u7528\u6237\u6216\u89d2\u8272";
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo processInstanceBusinessKey, SpecifiedAndGrantedUsersParameter strategyParameter, Task task) {
        Set<String> canExeIdentitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(processInstanceBusinessKey, task);
        Set<String> userSet = this.actors(processInstanceBusinessKey, strategyParameter);
        userSet.retainAll(canExeIdentitys);
        return userSet;
    }

    @Override
    public boolean isUserMatch(BusinessKeyInfo businessKey, SpecifiedAndGrantedUsersParameter strategyParameter, Actor actor, Task task) {
        boolean isMatchUser;
        this.nrParameterUtils.getFirstEntityView(businessKey);
        TableModelDefine t = this.nrParameterUtils.getFirstEntityTable(businessKey);
        if (t == null) {
            return false;
        }
        EntityViewDefine entityView = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityView == null) {
            return false;
        }
        String entityKey = businessKey.getMasterEntityInfo().getMasterEntityKey(t.getName());
        Boolean isGrantEntity = this.entityLinkService.isGrantedWithEntity(businessKey.getFormSchemeKey(), entityView, entityKey, actor.getIdentityId(), businessKey.getPeriod());
        if (!isGrantEntity.booleanValue()) {
            return false;
        }
        List roles = this.roleService.getByIdentity(actor.getIdentityId());
        HashSet<String> roleSet = new HashSet<String>();
        for (Role role : roles) {
            roleSet.add(role.getId());
        }
        roleSet.retainAll(strategyParameter.getRoleIdSet());
        Boolean isCanExecute = this.nrProcessAuthorityProvider.isCanExecuteCurrentTaskIdentity(businessKey, task);
        boolean bl = strategyParameter == null ? false : (isMatchUser = strategyParameter.getUserIdSet().contains(actor.getUserId()) && isCanExecute != false);
        boolean isMatchRole = strategyParameter == null ? false : roleSet.size() > 0 && isCanExecute != false;
        return isMatchRole || isMatchUser;
    }

    @Override
    public Class<? extends SpecifiedAndGrantedUsersParameter> getParameterType() {
        return SpecifiedAndGrantedUsersParameter.class;
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, SpecifiedAndGrantedUsersParameter strategyParameter, UserTask userTask) {
        Set<String> canExeIdentitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, userTask);
        Set<String> userSet = this.actors(businessKey, strategyParameter);
        userSet.retainAll(canExeIdentitys);
        return userSet;
    }

    private Set<String> actors(BusinessKeyInfo businessKey, SpecifiedAndGrantedUsersParameter strategyParameter) {
        Set<String> userIds;
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
        HashSet<String> userSet = new HashSet<String>();
        if (strategyParameter == null) {
            return Collections.emptySet();
        }
        Set<String> roleIds = strategyParameter.getRoleIdSet();
        if (!roleIds.isEmpty()) {
            for (String roleId : roleIds) {
                userSet.addAll(this.roleService.getUserIdAndIdentityMappUserIdByRole(roleId));
            }
        }
        if (!(userIds = strategyParameter.getUserIdSet()).isEmpty()) {
            userSet.addAll(userIds);
        }
        return userSet;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public String getDescription() {
        return "\u6307\u5b9a\u7684\u7528\u6237\u6216\u89d2\u8272\u53ef\u4ee5\u770b\u5230\u6709\u8bbf\u95ee\u6743\u9650\u7684\u5355\u4f4d\u3001\u5f53\u524d\u6d41\u7a0b\u8282\u70b9\u7684\u52a8\u4f5c\u5e76\u6267\u884c\u3002\u9700\u8981\u6307\u5b9a\u7528\u6237\u6216\u89d2\u8272\u3002";
    }
}

