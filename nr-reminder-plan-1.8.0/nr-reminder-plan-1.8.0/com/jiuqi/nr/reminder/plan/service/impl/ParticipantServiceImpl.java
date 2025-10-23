/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.extend.IDutySystemOptionsProvider
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 */
package com.jiuqi.nr.reminder.plan.service.impl;

import com.jiuqi.np.authz2.extend.IDutySystemOptionsProvider;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.service.ParticipantService;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ParticipantServiceImpl
implements ParticipantService {
    private static final Logger log = LoggerFactory.getLogger(ParticipantServiceImpl.class);
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private IEntityAuthorityService iEntityAuthorityService;
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    private DefaultAuthQueryService defaultAuthQueryService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired(required=false)
    private IDutySystemOptionsProvider dutySystemOptionsProvider;

    @Override
    public Set<String> collectUserId(CbPlanDTO cbPlanDTO, String unitId, String careKey, Map<String, Set<String>> canCache) {
        boolean enableDuty;
        Set canUnitSubmit;
        Set canUnitUp;
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(cbPlanDTO.getFormSchemeId());
        String dw = formScheme.getDw();
        List<UserDTO> users = this.getUserByOrg(unitId);
        ArrayList<String> orgUser = new ArrayList<String>();
        for (UserDTO userDTO : users) {
            cbPlanDTO.getUserMap().put(userDTO.getId(), (User)userDTO);
            if (!userDTO.isEnabled()) continue;
            orgUser.add(userDTO.getId());
        }
        if (orgUser.isEmpty()) {
            return new HashSet<String>(0);
        }
        try {
            canUnitUp = this.iEntityAuthorityService.getCanUploadIdentityKeys(cbPlanDTO.getDw(), unitId, cbPlanDTO.getCurrPeriodDate()[0], cbPlanDTO.getCurrPeriodDate()[1]);
            canUnitSubmit = this.iEntityAuthorityService.getCanSubmitIdentityKeys(cbPlanDTO.getDw(), unitId, cbPlanDTO.getCurrPeriodDate()[0], cbPlanDTO.getCurrPeriodDate()[1]);
        }
        catch (Exception e) {
            log.warn("\u67e5\u627e\u5bf9\u5355\u4f4d\u6709\u4e0a\u62a5\u6743\u9650\u3001\u9001\u5ba1\u6743\u9650\u7684\u8eab\u4efd\u5931\u8d25", e);
            return new HashSet<String>(0);
        }
        HashSet<String> canUnit = new HashSet<String>();
        canUnit.addAll(canUnitUp);
        canUnit.addAll(canUnitSubmit);
        if (canUnit.isEmpty()) {
            return canUnit;
        }
        canUnit.retainAll(orgUser);
        if (canUnit.isEmpty()) {
            return canUnit;
        }
        boolean bl = enableDuty = this.dutySystemOptionsProvider != null && this.dutySystemOptionsProvider.isEnableDuty();
        if (!enableDuty) {
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.ENTITY) {
                Set<String> canUser = canCache.get(careKey);
                if (canUser == null) {
                    canUser = new HashSet<String>();
                    Set canUp = this.definitionAuthorityProvider.getCanUploadIdentityKeys(careKey);
                    Set canSubmit = this.definitionAuthorityProvider.getCanSubmitIdentityKeys(careKey);
                    canUser.addAll(canUp);
                    canUser.addAll(canSubmit);
                    canCache.put(careKey, canUser);
                }
                canUnit.retainAll(canUser);
                return canUnit;
            }
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
                Set<String> canUser = canCache.get(careKey);
                if (canUser == null) {
                    canUser = new HashSet<String>();
                    Set canUp = this.definitionAuthorityProvider.getCanFormGroupUploadIdentityKeys(careKey);
                    Set canSubmit = this.definitionAuthorityProvider.getFormGroupCanSubmitIdentityKeys(careKey);
                    canUser.addAll(canUp);
                    canUser.addAll(canSubmit);
                    canCache.put(careKey, canUser);
                }
                canUnit.retainAll(canUser);
                return canUnit;
            }
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
                Set<String> canUser = canCache.get(careKey);
                if (canUser == null) {
                    canUser = new HashSet<String>();
                    Set canUp = this.definitionAuthorityProvider.getCanFormUploadIdentityKeys(careKey);
                    Set canSubmit = this.definitionAuthorityProvider.getFormCanSubmitIdentityKeys(careKey);
                    canUser.addAll(canUp);
                    canUser.addAll(canSubmit);
                    canCache.put(careKey, canUser);
                }
                canUnit.retainAll(canUser);
                return canUnit;
            }
        } else {
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.ENTITY) {
                HashSet canUser = new HashSet();
                Set canUp = this.definitionAuthorityProvider.getCanUploadIdentityKeys(careKey, unitId, dw);
                Set canSubmit = this.definitionAuthorityProvider.getCanSubmitIdentityKeys(careKey, unitId, dw);
                canUser.addAll(canUp);
                canUser.addAll(canSubmit);
                canUnit.retainAll(canUser);
                return canUnit;
            }
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
                HashSet canUser = new HashSet();
                Set canUp = this.definitionAuthorityProvider.getFormGroupCanUploadIdentityKeys(careKey, unitId, dw);
                Set canSubmit = this.definitionAuthorityProvider.getFormGroupCanSubmitIdentityKeys(careKey, unitId, dw);
                canUser.addAll(canUp);
                canUser.addAll(canSubmit);
                canUnit.retainAll(canUser);
                return canUnit;
            }
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
                HashSet canUser = new HashSet();
                Set canUp = this.definitionAuthorityProvider.getFormCanUploadIdentityKeys(careKey, unitId, dw);
                Set canSubmit = this.definitionAuthorityProvider.getFormCanSubmitIdentityKeys(careKey, unitId, dw);
                canUser.addAll(canUp);
                canUser.addAll(canSubmit);
                canUnit.retainAll(canUser);
                return canUnit;
            }
        }
        return new HashSet<String>();
    }

    @Override
    public Set<String> collectUserId(CbPlanDTO cbPlanDTO, List<String> roleIds, String unitId, String careKey, Map<String, Set<String>> canCache) {
        String identityId;
        boolean enableDuty;
        List<UserDTO> byOrgCode = this.getUserByOrg(unitId);
        String dw = cbPlanDTO.getDw();
        HashSet<String> orgUser = new HashSet<String>();
        for (UserDTO userDTO : byOrgCode) {
            cbPlanDTO.getUserMap().put(userDTO.getId(), (User)userDTO);
            if (!userDTO.isEnabled()) continue;
            orgUser.add(userDTO.getId());
        }
        if (orgUser.isEmpty()) {
            return orgUser;
        }
        if (cbPlanDTO.getWorkFlowType() == null) {
            return null;
        }
        Set careIds = null;
        boolean bl = enableDuty = this.dutySystemOptionsProvider != null && this.dutySystemOptionsProvider.isEnableDuty();
        if (!enableDuty) {
            careIds = canCache.get(careKey);
            if (careIds == null) {
                switch (cbPlanDTO.getWorkFlowType()) {
                    case ENTITY: {
                        careIds = this.definitionAuthorityProvider.getCanReadIdentityKeys(careKey);
                        break;
                    }
                    case GROUP: {
                        careIds = this.definitionAuthorityProvider.getFormGroupCanReadIdentityKeys(careKey);
                        break;
                    }
                    case FORM: {
                        careIds = this.definitionAuthorityProvider.getFormCanReadIdentityKeys(careKey);
                    }
                }
                canCache.put(careKey, careIds);
            }
        } else {
            switch (cbPlanDTO.getWorkFlowType()) {
                case ENTITY: {
                    careIds = this.definitionAuthorityProvider.getCanReadIdentityKeys(careKey, unitId, dw);
                    break;
                }
                case GROUP: {
                    careIds = this.definitionAuthorityProvider.getFormGroupCanReadIdentityKeys(careKey, unitId, dw);
                    break;
                }
                case FORM: {
                    careIds = this.definitionAuthorityProvider.getFormCanReadIdentityKeys(careKey, unitId, dw);
                }
            }
        }
        if (careIds.isEmpty()) {
            return careIds;
        }
        orgUser.retainAll(careIds);
        HashSet<String> res = new HashSet<String>();
        if (roleIds == null && (identityId = NpContextHolder.getContext().getIdentityId()) != null && !this.systemIdentityService.isSystemIdentity(identityId)) {
            HashSet hasAuthResource = canCache.get(identityId);
            if (hasAuthResource == null) {
                hasAuthResource = this.defaultAuthQueryService.getHasAuthResource("role_privilege_read", identityId);
                hasAuthResource = new HashSet(hasAuthResource);
                hasAuthResource.remove("ffffffff-ffff-ffff-bbbb-ffffffffffff");
                canCache.put(identityId, hasAuthResource);
            }
            if (hasAuthResource.isEmpty()) {
                return Collections.emptySet();
            }
            roleIds = new ArrayList<String>(hasAuthResource);
        }
        if (roleIds == null) {
            for (String careId : orgUser) {
                Set roleIdSet = this.roleService.getIdByIdentity(careId);
                if (roleIdSet.isEmpty() || roleIdSet.size() == 1 && roleIdSet.contains("ffffffff-ffff-ffff-bbbb-ffffffffffff")) continue;
                res.add(careId);
            }
        } else {
            block12: for (String careId : orgUser) {
                Set roleIdSet = this.roleService.getIdByIdentity(careId);
                for (String roleId : roleIds) {
                    if (!roleIdSet.contains(roleId)) continue;
                    res.add(careId);
                    continue block12;
                }
            }
        }
        return res;
    }

    public List<UserDTO> getUserByOrg(String orgCode) {
        List userDTOS;
        Collection grantedIdentity;
        ArrayList<UserDTO> users = new ArrayList<UserDTO>();
        List byOrgCode = this.nvwaUserClient.getByOrgCode(orgCode);
        if (!CollectionUtils.isEmpty(byOrgCode)) {
            users.addAll(byOrgCode);
        }
        if (!CollectionUtils.isEmpty(grantedIdentity = this.orgIdentityService.getGrantedIdentity(orgCode)) && !CollectionUtils.isEmpty(userDTOS = this.nvwaUserClient.get(new ArrayList(grantedIdentity)))) {
            users.addAll(userDTOS);
        }
        return users;
    }
}

