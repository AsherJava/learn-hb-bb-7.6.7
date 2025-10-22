/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.RoleGroup
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.RoleGroupService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.common.resource.authority;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.RoleGroup;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.RoleGroupService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleAuthority {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleGroupService roleGroupService;
    @Autowired
    private SystemIdentityService systemIdentityService;

    public boolean canReadRole(Role role) {
        if ("ffffffff-ffff-ffff-bbbb-ffffffffffff".equals(role.getId()) || "ffffffff-ffff-ffff-aaaa-ffffffffffff".equals(role.getId())) {
            return false;
        }
        return this.hasAuth("role_privilege_read", role);
    }

    public boolean canReadRole(String roleId) {
        if ("ffffffff-ffff-ffff-bbbb-ffffffffffff".equals(roleId) || "ffffffff-ffff-ffff-aaaa-ffffffffffff".equals(roleId)) {
            return false;
        }
        Optional roleFound = this.roleService.get(roleId);
        if (!roleFound.isPresent()) {
            return false;
        }
        return this.canReadRole((Role)roleFound.get());
    }

    public boolean canWriteRole(Role role) {
        return true;
    }

    public boolean canWriteRole(String roleId) {
        return true;
    }

    public boolean canMgrMember(Role role) {
        return true;
    }

    public boolean canMgrMember(String roleId) {
        return true;
    }

    public boolean canMgrAuth(Role role) {
        return true;
    }

    public boolean canMgrAuth(String roleId) {
        return true;
    }

    public boolean canReadRoleGroup(RoleGroup roleGroup) {
        return this.hasAuth("role_privilege_read", roleGroup);
    }

    public boolean canReadRoleGroup(String roleGroupId) {
        Optional roleGroupFound = this.roleGroupService.get(roleGroupId);
        if (!roleGroupFound.isPresent()) {
            return false;
        }
        return this.canReadRoleGroup((RoleGroup)roleGroupFound.get());
    }

    public boolean canWriteRoleGroup(RoleGroup roleGroup) {
        return true;
    }

    public boolean canWriteRoleGroup(String roleGroupId) {
        return true;
    }

    public boolean canMgrMemberGroup(RoleGroup roleGroup) {
        return true;
    }

    public boolean canMgrMemberGroup(String roleGroupId) {
        return true;
    }

    public boolean canMgrAuthGroup(RoleGroup roleGroup) {
        return true;
    }

    public boolean canMgrAuthGroup(String roleGroupId) {
        return true;
    }

    private boolean hasAuth(String privilegeId, Object resource) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        return this.privilegeService.hasDelegateAuth(privilegeId, identityId, resource);
    }

    public void grantAllPrivileges(String resourceId) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemIdentity(granteeId)) {
            return;
        }
        String grantorId = "SYSTEM.ROOT";
        int privilegeType = PrivilegeType.OBJECT_INHERIT.getValue();
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, granteeId).grant("role_privilege_read", resourceId, privilegeType, authority).submit();
    }
}

