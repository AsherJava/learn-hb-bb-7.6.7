/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.AuthzType
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 */
package com.jiuqi.gcreport.analysisreport.authority;

import com.jiuqi.gcreport.analysisreport.authority.AnalysisReportTemplateAuthorityProvider;
import com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AnalysisReportTemplateAuthorityProviderImpl
implements AnalysisReportTemplateAuthorityProvider {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private AuthorizationService privilegeManageService;

    @Override
    public boolean canRead(String resourceKey) {
        if (AnalysisReportTemplateConsts.ROOT_PARENT_ID.equals(resourceKey)) {
            return true;
        }
        Assert.notNull((Object)resourceKey, "parameter 'resourceKey' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        if (this.systemIdentityService.isSystemIdentity(identityId)) {
            return true;
        }
        ResourceItem analysisReportResource = new ResourceItem();
        analysisReportResource.setId(resourceKey);
        boolean hasAuth = this.privilegeService.hasAuth("analysisreport_template_resource_read", identityId, (Object)analysisReportResource);
        return hasAuth;
    }

    @Override
    public void grantAllPrivileges(String resourceKey) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        String grantorId = "SYSTEM.ROOT";
        this.privilegeManageService.grant("analysisreport_template_resource_read", granteeId, grantorId, resourceKey, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
    }
}

