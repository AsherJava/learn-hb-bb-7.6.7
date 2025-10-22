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
package com.jiuqi.nr.dataresource.authority;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.dataresource.authority.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataResourceAuthorityService {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private AuthorizationService authorizationService;

    public boolean canRead(String dataSourceId, int nodetype) {
        if ("00000000-0000-0000-0000-000000000000".equals(dataSourceId)) {
            return true;
        }
        String sourceId = Util.getResourceIdByType(dataSourceId, nodetype);
        return this.hasAuth("DataResource_read", sourceId);
    }

    public boolean canWrite(String dataSourceId, int nodetype) {
        if ("00000000-0000-0000-0000-000000000000".equals(dataSourceId)) {
            return true;
        }
        String sourceId = Util.getResourceIdByType(dataSourceId, nodetype);
        return this.hasAuth("DataResource_write", sourceId);
    }

    private boolean hasAuth(String privilegeId, String resource) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        return this.privilegeService.hasAuth(privilegeId, identityId, (Object)resource);
    }

    public void grantWrite(String dataSourceId, int nodetype) {
        String sourceId = Util.getResourceIdByType(dataSourceId, nodetype);
        this.grantAllPrivileges(sourceId);
    }

    private void grantAllPrivileges(String sourceId) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemIdentity(granteeId)) {
            return;
        }
        String grantorId = "SYSTEM.ROOT";
        int privilegeType = PrivilegeType.OBJECT_INHERIT.getValue();
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, granteeId).grant("DataResource_read", sourceId, privilegeType, authority).grant("DataResource_write", sourceId, privilegeType, authority).submit();
    }
}

