/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.zbquery.authority;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class ZBQueryAuthorityService {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private AuthorizationService authorizationService;

    public boolean canReadGroup(String id) {
        if ("00000000-0000-0000-0000-000000000000".equals(id)) {
            return true;
        }
        return this.hasAuth("ZBQueryResource_read", "ZBQUERYRESOURCE_G_" + id);
    }

    public boolean canWriteGroup(String id) {
        if ("00000000-0000-0000-0000-000000000000".equals(id)) {
            return true;
        }
        return this.hasAuth("ZBQueryResource_edit", "ZBQUERYRESOURCE_G_" + id);
    }

    public boolean canReadInfo(String id) {
        return this.hasAuth("ZBQueryResource_read", "ZBQUERYRESOURCE_I_" + id);
    }

    public boolean canWriteInfo(String id) {
        return this.hasAuth("ZBQueryResource_edit", "ZBQUERYRESOURCE_I_" + id);
    }

    public void grantGroup(String id) {
        this.grantAllPrivileges(id, true);
    }

    public void grantInfo(String id) {
        this.grantAllPrivileges(id, false);
    }

    private void grantAllPrivileges(String id, boolean group) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemIdentity(granteeId)) {
            return;
        }
        String resourceId = (group ? "ZBQUERYRESOURCE_G_" : "ZBQUERYRESOURCE_I_") + id;
        String grantorId = "SYSTEM.ROOT";
        int privilegeType = PrivilegeType.OBJECT_INHERIT.getValue();
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, granteeId).grant("ZBQueryResource_read", resourceId, privilegeType, authority).grant("ZBQueryResource_edit", resourceId, privilegeType, authority).grant("22222222-2222-2222-2222-222222222222", resourceId, privilegeType, authority).submit();
    }

    private boolean hasAuth(String privilegeId, String resource) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        return this.privilegeService.hasAuth(privilegeId, identityId, (Object)resource);
    }
}

