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
package com.jiuqi.nr.query.auth.authz2;

import authority.DashBoardResource;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.query.auth.QueryModelAuthorityProvider;
import com.jiuqi.nr.query.auth.authz2.QueryModelResource;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class QueryModelAuthorityService
implements QueryModelAuthorityProvider {
    @Autowired
    SystemIdentityService systemIdentityService;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private AuthorizationService privilegeManageService;

    @Override
    public boolean canReadModal(String queryModalKey, String type) {
        Assert.notNull((Object)queryModalKey, "parameter 'queryModalKey' must not be null.");
        if (type.equals("QueryModel") || type.equals("queryModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "query_model_resource_read", type);
        }
        if (type.equals("DashBoard") || type.equals("dashBoardGroup")) {
            return this.canOperateDashboradResource(queryModalKey, "dashboard_model_resource_read", type);
        }
        if (type.equals("SimpleQueryModel") || type.equals("simpleQueryModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "simple_model_resource_read", type);
        }
        if (type.equals("CustomInputModel") || type.equals("CustomInputModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "custom_model_resource_read", type);
        }
        return false;
    }

    @Override
    public boolean canWriteModal(String queryModalKey, String type) {
        Assert.notNull((Object)queryModalKey, "parameter 'queryModalKey' must not be null.");
        if (type.equals("QueryModel") || type.equals("queryModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "query_model_resource_write", type);
        }
        if (type.equals("DashBoard") || type.equals("dashBoardGroup")) {
            return this.canOperateDashboradResource(queryModalKey, "dashboard_model_resource_write", type);
        }
        if (type.equals("SimpleQueryModel") || type.equals("simpleQueryModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "simple_model_resource_write", type);
        }
        if (type.equals("CustomInputModel") || type.equals("CustomInputModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "custom_model_resource_write", type);
        }
        return false;
    }

    @Override
    public boolean canDeleteModal(String queryModalKey, String type) {
        Assert.notNull((Object)queryModalKey, "parameter 'queryModalKey' must not be null.");
        if (type.equals("QueryModel") || type.equals("queryModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "query_model_resource_write", type);
        }
        if (type.equals("DashBoard") || type.equals("dashBoardGroup")) {
            return this.canOperateDashboradResource(queryModalKey, "dashboard_model_resource_write", type);
        }
        if (type.equals("SimpleQueryModel") || type.equals("simpleQueryModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "simple_model_resource_write", type);
        }
        if (type.equals("CustomInputModel") || type.equals("CustomInputModelGroup")) {
            return this.canOperateQueryModalCategoryResource(queryModalKey, "custom_model_resource_write", type);
        }
        return false;
    }

    @Override
    public boolean canOperateQueryModalCategoryResource(String resourceKey, String privilegeId, String type) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        QueryModelResource queryModelResource = new QueryModelResource();
        queryModelResource.setId(resourceKey);
        queryModelResource.setType(type);
        return this.privilegeService.hasAuth(privilegeId, identityId, (Object)queryModelResource);
    }

    public boolean canOperateDashboradResource(String resourceKey, String privilegeId, String type) {
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
    public void grantAllPrivileges(String resourceId, String type, QueryModelType modelType) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        String grantorId = "SYSTEM.ROOT";
        QueryModelResource queryModelResource = new QueryModelResource();
        queryModelResource.setId(resourceId);
        queryModelResource.setType(type);
        String privilegeIdWrite = null;
        String privilegeIdRead = null;
        switch (modelType) {
            case OWNER: {
                privilegeIdRead = "query_model_resource_read";
                privilegeIdWrite = "query_model_resource_write";
                break;
            }
            case DASHOWNER: {
                privilegeIdRead = "dashboard_model_resource_read";
                privilegeIdWrite = "dashboard_model_resource_write";
                break;
            }
            case SIMPLEOWER: {
                privilegeIdRead = "simple_model_resource_read";
                privilegeIdWrite = "simple_model_resource_write";
                break;
            }
            case CUSTOMINPUT: {
                privilegeIdRead = "custom_model_resource_read";
                privilegeIdWrite = "custom_model_resource_write";
                break;
            }
            default: {
                return;
            }
        }
        this.privilegeManageService.grant(privilegeIdRead, granteeId, grantorId, resourceId, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
        this.privilegeManageService.grant(privilegeIdWrite, granteeId, grantorId, resourceId, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
        this.privilegeManageService.grant(privilegeIdRead, granteeId, grantorId, resourceId, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.DELEGATE);
        this.privilegeManageService.grant(privilegeIdWrite, granteeId, grantorId, resourceId, PrivilegeType.OBJECT_INHERIT.getValue(), Authority.ALLOW, AuthzType.DELEGATE);
    }

    @Override
    public boolean canDelegateQueryModalResource(String resourceKey, String type) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        QueryModelResource queryModelResource = new QueryModelResource();
        queryModelResource.setId(resourceKey);
        queryModelResource.setType(type);
        return this.privilegeService.hasDelegateAuth("query_model_resource_read", identityId, (Object)queryModelResource);
    }
}

