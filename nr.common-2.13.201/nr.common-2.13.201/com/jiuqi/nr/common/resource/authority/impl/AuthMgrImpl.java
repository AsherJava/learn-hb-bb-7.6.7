/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.common.resource.authority.impl;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.resource.authority.IAuthMgr;
import com.jiuqi.nr.common.resource.authority.RoleAuthority;
import com.jiuqi.nr.common.resource.authority.RoleCheckType;
import com.jiuqi.nr.common.resource.exception.AuthErrorEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthMgrImpl
implements IAuthMgr {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleAuthority roleAuthority;

    @Override
    public void checkPermissionUserIds(List<String> userIds, RoleCheckType type) throws JQException {
        TreeSet set = new TreeSet();
        for (String userId : userIds) {
            Set roleIdByIdentity = this.roleService.getIdByIdentity(userId);
            set.addAll(roleIdByIdentity);
            if (!RoleCheckType.ONE.equals((Object)type)) continue;
            this.hasAuthRolesIsOne(new ArrayList<String>(roleIdByIdentity));
        }
        set.remove("ffffffff-ffff-ffff-bbbb-ffffffffffff");
        if (RoleCheckType.ALL.equals((Object)type)) {
            this.hasAuthRolesIsAll(new ArrayList<String>(set));
        }
    }

    private void hasAuthRolesIsAll(List<String> roleIds) throws JQException {
        if (roleIds != null) {
            for (String roleId : roleIds) {
                if (this.roleAuthority.canMgrMember(roleId)) continue;
                throw new JQException((ErrorEnum)AuthErrorEnum.AUTH_ROLE_005);
            }
        }
    }

    private void hasAuthRolesIsOne(List<String> roleIds) throws JQException {
        if (roleIds != null && !roleIds.isEmpty()) {
            for (String roleId : roleIds) {
                if (!this.roleAuthority.canMgrMember(roleId)) continue;
                return;
            }
            throw new JQException((ErrorEnum)AuthErrorEnum.AUTH_ROLE_005);
        }
    }
}

