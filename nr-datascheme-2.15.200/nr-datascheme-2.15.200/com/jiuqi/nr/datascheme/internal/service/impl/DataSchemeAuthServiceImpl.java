/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataSchemeAuthServiceImpl
implements IDataSchemeAuthService {
    @Autowired
    public DefaultAuthQueryService defaultAuthQueryService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private SystemIdentityService systemIdentityService;

    private String getIdentityId() {
        return NpContextHolder.getContext().getIdentityId();
    }

    private boolean hasAuth(String privilegeId, String resourceId) {
        String identityId = this.getIdentityId();
        if (!StringUtils.hasLength(identityId)) {
            return false;
        }
        return this.defaultAuthQueryService.hasAuth(privilegeId, identityId, (Object)resourceId);
    }

    public boolean canReadGroup(String groupKey) {
        if ("00000000-0000-0000-0000-000000000000".equals(groupKey) || "00000000-0000-0000-0000-111111111111".equals(groupKey)) {
            return true;
        }
        return this.hasAuth("datascheme_auth_resource_read", DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId(groupKey));
    }

    public boolean canWriteGroup(String groupKey) {
        if ("00000000-0000-0000-0000-000000000000".equals(groupKey) || "00000000-0000-0000-0000-111111111111".equals(groupKey)) {
            return true;
        }
        return this.hasAuth("datascheme_auth_resource_write", DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId(groupKey));
    }

    public boolean canReadScheme(String schemeKey) {
        return this.hasAuth("datascheme_auth_resource_read", DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(schemeKey));
    }

    public boolean canWriteScheme(String schemeKey) {
        return this.hasAuth("datascheme_auth_resource_write", DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(schemeKey));
    }

    public boolean canReadScheme(DataScheme scheme) {
        return this.canReadScheme(scheme.getKey());
    }

    public void filterSchemes(Collection<? extends DataScheme> schemes) {
        if (CollectionUtils.isEmpty(schemes)) {
            return;
        }
        schemes.removeIf(s -> !this.canReadScheme((DataScheme)s));
    }

    public void grantAllPrivileges(DesignDataScheme dataScheme) {
        String identityId = this.getIdentityId();
        if (!StringUtils.hasText(identityId) || this.systemIdentityService.isSystemIdentity(identityId)) {
            return;
        }
        String resourceId = DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(dataScheme.getKey());
        String grantorId = "SYSTEM.ROOT";
        int privilegeType = PrivilegeType.OBJECT_INHERIT.getValue();
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, identityId).grant("datascheme_auth_resource_read", resourceId, privilegeType, authority).grant("datascheme_auth_resource_write", resourceId, privilegeType, authority).submit();
    }

    public void grantAllPrivileges(DesignDataGroup dataGroup) {
        if (DataGroupKind.SCHEME_GROUP != dataGroup.getDataGroupKind() && DataGroupKind.QUERY_SCHEME_GROUP != dataGroup.getDataGroupKind()) {
            return;
        }
        String identityId = this.getIdentityId();
        if (!StringUtils.hasText(identityId) || this.systemIdentityService.isSystemIdentity(identityId)) {
            return;
        }
        String resourceId = DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId(dataGroup.getKey());
        String grantorId = "SYSTEM.ROOT";
        int privilegeType = PrivilegeType.OBJECT_INHERIT.getValue();
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, identityId).grant("datascheme_auth_resource_read", resourceId, privilegeType, authority).grant("datascheme_auth_resource_write", resourceId, privilegeType, authority).submit();
    }

    public void revokeAll(DesignDataScheme dataScheme) {
        String resourceId = DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(dataScheme.getKey());
        this.authorizationService.revokeByResourceId(new String[]{resourceId});
    }

    public void revokeAll(DesignDataGroup dataGroup) {
        if (DataGroupKind.SCHEME_GROUP != dataGroup.getDataGroupKind() && DataGroupKind.QUERY_SCHEME_GROUP != dataGroup.getDataGroupKind()) {
            return;
        }
        String resourceId = DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId(dataGroup.getKey());
        this.authorizationService.revokeByResourceId(new String[]{resourceId});
    }

    public void revokeAllForSchemeGroup(List<String> dataGroupKeys) {
        if (CollectionUtils.isEmpty(dataGroupKeys)) {
            return;
        }
        String[] resourceIds = (String[])dataGroupKeys.stream().filter(StringUtils::hasText).map(DataSchemeAuthResourceType.DATA_SCHEME_GROUP::toResourceId).toArray(String[]::new);
        this.authorizationService.revokeByResourceId(resourceIds);
    }
}

