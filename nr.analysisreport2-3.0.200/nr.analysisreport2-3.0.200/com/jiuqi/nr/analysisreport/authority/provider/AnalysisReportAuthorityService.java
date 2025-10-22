/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.AuthzType
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.service.SystemUserService
 */
package com.jiuqi.nr.analysisreport.authority.provider;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.analysisreport.authority.AnalysisReportAuthorityProvider;
import com.jiuqi.nr.analysisreport.authority.bean.AnalysisReportResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AnalysisReportAuthorityService
implements AnalysisReportAuthorityProvider {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private AuthorizationService privilegeManageService;

    @Override
    public boolean canReadModal(String resourceKey, String type) {
        Assert.notNull((Object)resourceKey, "parameter 'resourceKey' must not be null.");
        return this.canOperateQueryModalCategoryResource(resourceKey, "analysisreport_resource_read", type);
    }

    @Override
    public boolean canCreateModal(String resourceKey, String type) {
        Assert.notNull((Object)resourceKey, "parameter 'resourceKey' must not be null.");
        return this.canOperateQueryModalCategoryResource(resourceKey, "analysisreport_resource_create", type);
    }

    @Override
    public boolean canWriteModal(String resourceKey, String type) {
        Assert.notNull((Object)resourceKey, "parameter 'resourceKey' must not be null.");
        return this.canOperateQueryModalCategoryResource(resourceKey, "analysisreport_resource_write", type);
    }

    @Override
    public boolean canDeleteModal(String resourceKey, String type) {
        Assert.notNull((Object)resourceKey, "parameter 'resourceKey' must not be null.");
        return this.canOperateQueryModalCategoryResource(resourceKey, "analysisreport_resource_delete", type);
    }

    @Override
    public boolean canOperateQueryModalCategoryResource(String resourceKey, String privilegeId, String type) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        AnalysisReportResource analysisReportResource = new AnalysisReportResource();
        analysisReportResource.setId(resourceKey);
        analysisReportResource.setType(type);
        return this.privilegeService.hasAuth(privilegeId, identityId, (Object)analysisReportResource);
    }

    @Override
    public void grantAllPrivileges(String resourceKey) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        String grantorId = "SYSTEM.ROOT";
        this.privilegeManageService.grant("analysisreport_resource_read", granteeId, grantorId, resourceKey, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
        this.privilegeManageService.grant("analysisreport_resource_create", granteeId, grantorId, resourceKey, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
        this.privilegeManageService.grant("analysisreport_resource_write", granteeId, grantorId, resourceKey, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
    }
}

