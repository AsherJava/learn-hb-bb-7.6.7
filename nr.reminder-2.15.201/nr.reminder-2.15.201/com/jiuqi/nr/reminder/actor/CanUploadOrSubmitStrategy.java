/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.authz2.service.IdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 *  com.jiuqi.nvwa.authority.user.vo.UserDTORes
 *  com.jiuqi.nvwa.authority.user.vo.UserQueryReq
 */
package com.jiuqi.nr.reminder.actor;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.authz2.service.IdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.reminder.actor.BaseActorStrategy;
import com.jiuqi.nr.reminder.untils.EntityQueryManager;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import com.jiuqi.nvwa.authority.user.vo.UserDTORes;
import com.jiuqi.nvwa.authority.user.vo.UserQueryReq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CanUploadOrSubmitStrategy
extends BaseActorStrategy {
    private static final Logger log = LoggerFactory.getLogger(CanUploadOrSubmitStrategy.class);
    private final EntityQueryManager entityQueryManager;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final RoleService roleService;
    @Autowired
    private NvwaUserService nvwaUserService;

    public CanUploadOrSubmitStrategy(IEntityAuthorityService entityAuthority, DefinitionAuthorityProvider definitionAuthorityProvider, EntityQueryManager entityQueryManager, EntityIdentityService entityIdentityService, IDataDefinitionRuntimeController drCtrl, IEntityViewRunTimeController eVRCtrl, IEntityViewRunTimeController entityViewRunTimeController, RoleService roleService, IdentityService identityService) {
        super(entityAuthority, definitionAuthorityProvider, entityIdentityService);
        this.entityQueryManager = entityQueryManager;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.roleService = roleService;
    }

    @Override
    public List<String> getActors(String entityView, String entityKey, String formSchemeKey, Date queryVersionStartDate, Date queryVersionEndDate, List<IEntityRow> allRows) {
        HashSet grantedCanSubmitOrUploadIdentityKeys = null;
        try {
            Set canUploadForFormSchemeIdentityKeys = this.definitionAuthorityProvider.getCanUploadIdentityKeys(formSchemeKey);
            Set canSubmitForFormSchemeIdentityKeys = this.definitionAuthorityProvider.getCanSubmitIdentityKeys(formSchemeKey);
            HashSet canSubmitOrUploadForFormSchemeIdentityKeys = new HashSet();
            canSubmitOrUploadForFormSchemeIdentityKeys.addAll(canUploadForFormSchemeIdentityKeys);
            canSubmitOrUploadForFormSchemeIdentityKeys.addAll(canSubmitForFormSchemeIdentityKeys);
            Set canUploadForEntityIdentityKeys = this.entityAuthority.getCanUploadIdentityKeys(entityView, entityKey, queryVersionStartDate, queryVersionEndDate);
            Set canSubmitForEntityIdentityKeys = this.entityAuthority.getCanSubmitIdentityKeys(entityView, entityKey, queryVersionStartDate, queryVersionEndDate);
            HashSet canSubmitOrUploadForEntityIdentityKeys = new HashSet();
            canSubmitOrUploadForEntityIdentityKeys.addAll(canUploadForEntityIdentityKeys);
            canSubmitOrUploadForEntityIdentityKeys.addAll(canSubmitForEntityIdentityKeys);
            HashSet canSubmitOrUploadIdentityKeys = canSubmitOrUploadForEntityIdentityKeys;
            canSubmitOrUploadIdentityKeys.retainAll(canSubmitOrUploadForFormSchemeIdentityKeys);
            HashSet<String> ids = new HashSet<String>();
            for (IEntityRow iEntityRow : allRows) {
                AbstractData orgCode = iEntityRow.getValue("orgcode");
                UserQueryReq userQueryReq = new UserQueryReq();
                userQueryReq.setOrgCode(orgCode.getAsString());
                List users = this.nvwaUserService.queryUser(userQueryReq);
                for (UserDTORes user : users) {
                    ids.add(user.getId());
                }
            }
            grantedCanSubmitOrUploadIdentityKeys = canSubmitOrUploadIdentityKeys;
            grantedCanSubmitOrUploadIdentityKeys.retainAll(ids);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<String>(grantedCanSubmitOrUploadIdentityKeys);
    }

    public List<String> getActors(String entityView, String entityKey, String formSchemeKey, Date queryVersionStartDate, Date queryVersionEndDate, List<String> roleIds, List<IEntityRow> allRows) {
        try {
            if (roleIds == null) {
                Collection ids = this.entityIdentityService.getGrantedIdentityKeys(entityView, entityKey);
                return ids.stream().filter(identityId -> {
                    Set rids = this.roleService.getIdByIdentity(identityId);
                    return rids.size() > 1 || rids.size() == 1 && !rids.contains("ffffffff-ffff-ffff-bbbb-ffffffffffff");
                }).collect(Collectors.toList());
            }
            HashSet<String> ids = new HashSet<String>();
            for (IEntityRow iEntityRow : allRows) {
                AbstractData orgCode = iEntityRow.getValue("orgcode");
                UserQueryReq userQueryReq = new UserQueryReq();
                userQueryReq.setOrgCode(orgCode.getAsString());
                List users = this.nvwaUserService.queryUser(userQueryReq);
                for (UserDTORes user : users) {
                    ids.add(user.getId());
                }
            }
            List res = roleIds.stream().map(arg_0 -> ((RoleService)this.roleService).getIdentityIdByRole(arg_0)).flatMap(Collection::stream).collect(Collectors.toList());
            res.retainAll(ids);
            return new ArrayList<String>(res);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<String>();
        }
    }

    public List<String> getActorsOnlyRoleIds(List<String> roleIds) {
        try {
            List res = roleIds.stream().map(arg_0 -> ((RoleService)this.roleService).getIdentityIdByRole(arg_0)).flatMap(Collection::stream).collect(Collectors.toList());
            return new ArrayList<String>(res);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<String>();
        }
    }
}

