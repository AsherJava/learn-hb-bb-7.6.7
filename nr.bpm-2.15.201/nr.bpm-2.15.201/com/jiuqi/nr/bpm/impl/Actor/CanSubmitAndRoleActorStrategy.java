/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.IActorStrategyCountSign;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyBase;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.impl.common.EntityQueryVersion;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.common.NrProcessAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CanSubmitAndRoleActorStrategy
extends ActorStrategyBase<GrantedToEntityAndRoleParameter>
implements IActorStrategyCountSign {
    private static final Logger logger = LoggerFactory.getLogger(CanSubmitAndRoleActorStrategy.class);
    @Autowired
    private RoleService roleInfoService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private NrProcessAuthorityProvider nrProcessAuthorityProvider;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;

    @Override
    public String getTitle() {
        return "\u6709\u9001\u5ba1\u6743\u9650\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u4eba";
    }

    @Override
    public String getDescription() {
        return "\u6709\u9001\u5ba1\u6743\u9650\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237\u53ef\u4ee5\u770b\u5230\u5355\u4f4d\u3001\u5f53\u524d\u6d41\u7a0b\u8282\u70b9\u7684\u52a8\u4f5c\u5e76\u6267\u884c\u3002";
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, Task task) {
        Set<String> identitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, task);
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
        EntityViewDefine entityViewDefine = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityViewDefine == null) {
            return false;
        }
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(t.getName());
        if (StringUtils.isEmpty((String)entityKeyData)) {
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
        if (!this.entityAuthorityService.isEnableAuthority(entityViewDefine.getEntityId())) {
            return true;
        }
        boolean canSubmitEntity = false;
        try {
            FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
            String dateTime = formScheme.getDateTime();
            EntityQueryVersion queryPeriod = EntityQueryVersion.parseFromPeriod(businessKey.getPeriod(), businessKey.getFormSchemeKey(), dateTime);
            canSubmitEntity = this.entityAuthorityService.canSubmitEntity(entityViewDefine.getEntityId(), entityKeyData, queryPeriod.getQueryVersionStartDate(), queryPeriod.getQueryVersionDate());
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        return isGrantRole && isCanExecute != false && canSubmitEntity;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, UserTask userTask) {
        Set<String> identitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, userTask);
        Set<String> validIdentityIds = this.actors(businessKey, strategyParameter);
        validIdentityIds.retainAll(identitys);
        return validIdentityIds;
    }

    private Set<String> actors(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter) {
        HashSet<String> actorIds = new HashSet<String>();
        TableModelDefine tableModel = this.nrParameterUtils.getFirstEntityTable(businessKey);
        if (tableModel == null) {
            return actorIds;
        }
        EntityViewDefine entityViewDefine = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityViewDefine == null) {
            return actorIds;
        }
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(tableModel.getName());
        if (StringUtils.isEmpty((String)entityKeyData)) {
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
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        String dateTime = formScheme.getDateTime();
        EntityQueryVersion queryPeriod = EntityQueryVersion.parseFromPeriod(businessKey.getPeriod(), businessKey.getFormSchemeKey(), dateTime);
        try {
            Set canAuditIdentityKeys = this.entityAuthorityService.getCanSubmitIdentityKeys(entityViewDefine.getEntityId(), entityKeyData, queryPeriod.getQueryVersionStartDate(), queryPeriod.getQueryVersionDate());
            actorIds.retainAll(canAuditIdentityKeys);
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        return actorIds;
    }

    @Override
    public Class<? extends GrantedToEntityAndRoleParameter> getParameterType() {
        return GrantedToEntityAndRoleParameter.class;
    }

    @Override
    public Set<String> getCountSignGroupNum(BusinessKeyInfo businessKey, GrantedToEntityAndRoleParameter strategyParameter, UserTask task) {
        HashSet<String> actorIds = new HashSet<String>();
        TableModelDefine tableModel = this.nrParameterUtils.getFirstEntityTable(businessKey);
        if (tableModel == null) {
            return actorIds;
        }
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(tableModel.getName());
        if (StringUtils.isEmpty((String)entityKeyData)) {
            return actorIds;
        }
        EntityViewDefine entityViewDefine = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityViewDefine == null) {
            return actorIds;
        }
        Set<String> givenRoleIds = strategyParameter.getRoleIdSet();
        if (givenRoleIds.isEmpty()) {
            return actorIds;
        }
        Set<String> givenRoleIdsConvertToLow = ActorUtils.setToLow(givenRoleIds);
        actorIds.addAll(givenRoleIdsConvertToLow);
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        String dateTime = formScheme.getDateTime();
        EntityQueryVersion queryPeriod = EntityQueryVersion.parseFromPeriod(businessKey.getPeriod(), businessKey.getFormSchemeKey(), dateTime);
        try {
            Set canAuditIdentityKeys = this.entityAuthorityService.getCanAuditIdentityKeys(entityViewDefine.getEntityId(), entityKeyData, queryPeriod.getQueryVersionStartDate(), queryPeriod.getQueryVersionDate());
            Set<String> identitys = this.nrProcessAuthorityProvider.getCanExecuteCurrentTaskIdentityKeys(businessKey, task);
            if (canAuditIdentityKeys != null && canAuditIdentityKeys.size() > 0) {
                if (identitys != null && identitys.size() > 0) {
                    canAuditIdentityKeys.retainAll(identitys);
                }
                HashSet roleList = new HashSet();
                Map roleMap = this.roleInfoService.getByIdentity(new ArrayList(canAuditIdentityKeys));
                for (Map.Entry map : roleMap.entrySet()) {
                    List roles = (List)map.getValue();
                    Set roleIds = roles.stream().map(e -> e.getId()).collect(Collectors.toSet());
                    roleList.addAll(roleIds);
                }
                actorIds.retainAll(roleList);
            }
        }
        catch (UnauthorizedEntityException e2) {
            logger.error(e2.getMessage(), e2);
        }
        return actorIds;
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
        EntityViewDefine entityViewDefine = this.nrParameterUtils.getFirstEntityView(businessKey);
        if (entityViewDefine == null) {
            return false;
        }
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(t.getName());
        if (StringUtils.isEmpty((String)entityKeyData)) {
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
        if (!this.entityAuthorityService.isEnableAuthority(entityViewDefine.getEntityId())) {
            return true;
        }
        boolean canSubmitEntity = false;
        try {
            FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
            String dateTime = formScheme.getDateTime();
            EntityQueryVersion queryPeriod = EntityQueryVersion.parseFromPeriod(businessKey.getPeriod(), businessKey.getFormSchemeKey(), dateTime);
            canSubmitEntity = this.entityAuthorityService.canSubmitEntity(entityViewDefine.getEntityId(), entityKeyData, queryPeriod.getQueryVersionStartDate(), queryPeriod.getQueryVersionDate());
        }
        catch (UnauthorizedEntityException e) {
            logger.error(e.getMessage(), e);
        }
        return isGrantRole && isCanExecute != false && canSubmitEntity;
    }
}

