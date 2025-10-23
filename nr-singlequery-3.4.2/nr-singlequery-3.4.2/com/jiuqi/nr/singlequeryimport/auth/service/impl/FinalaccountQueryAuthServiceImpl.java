/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 */
package com.jiuqi.nr.singlequeryimport.auth.service.impl;

import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.service.IFinalaccountQueryAuthService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FinalaccountQueryAuthServiceImpl
implements IFinalaccountQueryAuthService {
    private static final Logger logger = LoggerFactory.getLogger(FinalaccountQueryAuthServiceImpl.class);
    @Autowired
    public DefaultAuthQueryService defaultAuthQueryService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private QueryModeleDao queryModeleDao;
    @Autowired
    public AuthorizationService d_authorizationService;

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

    private boolean hasAuth(String privilegeId, String identityId, String resourceId) {
        if (!StringUtils.hasLength(identityId)) {
            return false;
        }
        return this.defaultAuthQueryService.hasAuth(privilegeId, identityId, (Object)resourceId);
    }

    private boolean hasSameSuperiorAuth(String privilegeId, String resourceId) {
        String identityId = this.getIdentityId();
        if (!StringUtils.hasLength(identityId)) {
            return false;
        }
        Optional selfQuery = this.d_authorizationService.query(privilegeId, identityId, resourceId);
        return !selfQuery.isPresent();
    }

    public boolean hasSameSuperiorAuth(String privilegeId, String identityId, String resourceId) {
        if (!StringUtils.hasLength(identityId)) {
            return false;
        }
        Optional selfQuery = this.d_authorizationService.query(privilegeId, identityId, resourceId);
        return !selfQuery.isPresent();
    }

    @Override
    public boolean canReadGroup(String groupKey) {
        return this.hasAuth("finalaccountquery_auth_resource_read", FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(groupKey));
    }

    @Override
    public boolean canWriteGroup(String groupKey) {
        if ("00000000-0000-0000-0000-000000000000".equals(groupKey)) {
            return true;
        }
        return this.hasAuth("finalaccountquery_auth_resource_write", FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(groupKey));
    }

    @Override
    public boolean canAuthorizeGroup(String groupKey) {
        return this.hasAuth("finalaccountquery_auth_resource_accredit", FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(groupKey));
    }

    @Override
    public boolean canReadModel(String modelNodeId) {
        boolean result = this.hasAuth("finalaccountquery_auth_resource_read", FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(modelNodeId));
        return result;
    }

    @Override
    public boolean canWriteModel(String modelNodeId) {
        boolean result = this.hasAuth("finalaccountquery_auth_resource_write", FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(modelNodeId));
        return result;
    }

    @Override
    public boolean canAuthorizeModel(String modelNodeId) {
        boolean result = this.hasAuth("finalaccountquery_auth_resource_accredit", FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(modelNodeId));
        return result;
    }

    @Override
    public boolean canReadGroupByIdentityId(String groupKey, String identityId) {
        return this.hasAuth("finalaccountquery_auth_resource_read", identityId, FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(groupKey));
    }

    @Override
    public boolean canWriteGroupByIdentityId(String groupKey, String identityId) {
        if ("00000000-0000-0000-0000-000000000000".equals(groupKey)) {
            return true;
        }
        return this.hasAuth("finalaccountquery_auth_resource_write", identityId, FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(groupKey));
    }

    @Override
    public boolean canAuthorizeGroupByIdentityId(String groupKey, String identityId) {
        return this.hasAuth("finalaccountquery_auth_resource_accredit", identityId, FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(groupKey));
    }

    @Override
    public boolean canReadModelByIdentityId(String modelNodeId, String identityId) {
        boolean result = this.hasAuth("finalaccountquery_auth_resource_read", identityId, FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(modelNodeId));
        return result;
    }

    @Override
    public boolean canWriteModelByIdentityId(String modelNodeId, String identityId) {
        boolean result = this.hasAuth("finalaccountquery_auth_resource_write", identityId, FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(modelNodeId));
        return result;
    }

    @Override
    public boolean canAuthorizeModelByIdentityId(String modelNodeId, String identityId) {
        boolean result = this.hasAuth("finalaccountquery_auth_resource_accredit", identityId, FinalaccountQueryAuthResourceType.FQ_MODEL_NODE.toResourceId(modelNodeId));
        return result;
    }

    @Override
    public boolean canReadGroupWithChildModels(String formSchemeKey, String groupKey) {
        if (this.canReadGroup(formSchemeKey + "#" + groupKey)) {
            return true;
        }
        Set<String> modelIds = this.queryModeleDao.getModalKeyByGroupKey(formSchemeKey, groupKey);
        for (String modelId : modelIds) {
            if (!this.canReadModel(modelId)) continue;
            return true;
        }
        return false;
    }

    private boolean getGroupHasAuth(String privilegeId, String modelNodeId) {
        try {
            QueryModel modalNodeInfo = this.queryModeleDao.getData(modelNodeId);
            if (modalNodeInfo != null) {
                return this.hasAuth(privilegeId, FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(modalNodeInfo.getFormschemeKey() + "#" + modalNodeInfo.getGroup()));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    private boolean getGroupHasAuth(String privilegeId, String identityId, String modelNodeId) {
        try {
            QueryModel modalNodeInfo = this.queryModeleDao.getData(modelNodeId);
            if (modalNodeInfo != null) {
                return this.hasAuth(privilegeId, identityId, FinalaccountQueryAuthResourceType.FQ_GROUP.toResourceId(modalNodeInfo.getFormschemeKey() + "#" + modalNodeInfo.getGroup()));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }
}

