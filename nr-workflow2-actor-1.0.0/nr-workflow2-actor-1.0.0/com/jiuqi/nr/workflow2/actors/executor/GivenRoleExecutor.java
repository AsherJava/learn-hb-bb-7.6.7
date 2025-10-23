/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.actors.executor;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.workflow2.actors.executor.ExecutorBase;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GivenRoleExecutor
extends ExecutorBase {
    private Set<String> roleIds;
    private RoleService roleService;

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean isActive() {
        return this.roleIds != null && !this.roleIds.isEmpty();
    }

    public boolean isMatch(IActor actor, RuntimeBusinessKey rtBusinessKey) {
        Set userGrantedRoleIds;
        if (actor.getIdentityId() == null) {
            return false;
        }
        return !this.isActive() || (userGrantedRoleIds = this.roleService.getIdByIdentity(actor.getIdentityId())) != null && !userGrantedRoleIds.isEmpty() && !this.roleIds.stream().noneMatch(o -> userGrantedRoleIds.contains(o));
    }

    public Set<String> getMatchUsers(RuntimeBusinessKey rtBusinessKey) {
        if (this.isActive()) {
            HashSet<String> usersGrantedToRole = new HashSet<String>();
            for (String roleId : this.roleIds) {
                usersGrantedToRole.addAll(this.roleService.getUserIdAndIdentityMappUserIdByRole(roleId));
            }
            return usersGrantedToRole;
        }
        return Collections.emptySet();
    }

    public IBusinessObjectSet getMatchBusinessKeys(IActor actor, RuntimeBusinessKeyCollection rtBusinessKeys) {
        if (this.isActive()) {
            Set userGrantedRoleIds = this.roleService.getIdByIdentity(actor.getIdentityId());
            if (userGrantedRoleIds == null || userGrantedRoleIds.isEmpty() || this.roleIds.stream().noneMatch(o -> userGrantedRoleIds.contains(o))) {
                return new BusinessObjectSet();
            }
            BusinessObjectSet canActObjectSet = new BusinessObjectSet();
            for (IBusinessObject businessObject : rtBusinessKeys.getBusinessKeys().getBusinessObjects()) {
                canActObjectSet.add((Object)businessObject);
            }
            return canActObjectSet;
        }
        return new BusinessObjectSet();
    }

    @Override
    boolean isDillwithBusinessKey() {
        return false;
    }
}

