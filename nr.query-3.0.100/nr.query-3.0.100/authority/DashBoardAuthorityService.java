/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.AuthzType
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.service.SystemUserService
 */
package authority;

import authority.DashBoardAuthorityProvider;
import authority.DashBoardResource;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class DashBoardAuthorityService
implements DashBoardAuthorityProvider {
    @Autowired
    SystemIdentityService systemIdentityService;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private AuthorizationService privilegeManageService;

    @Override
    public boolean canReadModal(String dashBoardKey, String type) {
        Assert.notNull((Object)dashBoardKey, "parameter 'dashBoardKey' must not be null.");
        return this.canOperateDashBoardCategoryResource(dashBoardKey, "dashboard_model_resource_write", type);
    }

    @Override
    public boolean canWriteModal(String dashBoardKey, String type) {
        Assert.notNull((Object)dashBoardKey, "parameter 'dashBoardKey' must not be null.");
        return this.canOperateDashBoardCategoryResource(dashBoardKey, "dashboard_model_resource_write", type);
    }

    @Override
    public boolean canDeleteModal(String dashBoardKey, String type) {
        Assert.notNull((Object)dashBoardKey, "parameter 'dashBoardKey' must not be null.");
        return this.canOperateDashBoardCategoryResource(dashBoardKey, "dashboard_model_resource_write", type);
    }

    @Override
    public boolean canOperateDashBoardCategoryResource(String resourceKey, String privilegeId, String type) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        DashBoardResource dashBoardResource = new DashBoardResource();
        dashBoardResource.setId(resourceKey);
        dashBoardResource.setType(type);
        return this.privilegeService.hasAuth(privilegeId, identityId, (Object)dashBoardResource);
    }

    @Override
    public void grantAllPrivileges(String resourceId, String type) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        String grantorId = "SYSTEM.ROOT";
        DashBoardResource queryModelResource = new DashBoardResource();
        queryModelResource.setId(resourceId);
        queryModelResource.setType(type);
        this.privilegeManageService.grant("dashboard_model_resource_read", granteeId, grantorId, resourceId, PrivilegeType.OBJECT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
        this.privilegeManageService.grant("dashboard_model_resource_write", granteeId, grantorId, resourceId, PrivilegeType.OBJECT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
    }
}

