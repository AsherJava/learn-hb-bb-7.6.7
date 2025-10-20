/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource.service;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nr.common.resource.NrResource;
import com.jiuqi.nr.common.resource.NrResourceCategory;
import com.jiuqi.nr.common.resource.bean.AuthzAction;
import com.jiuqi.nr.common.resource.service.ResourceService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceServiceImpl
implements ResourceService,
InitializingBean {
    @Autowired(required=false)
    private List<NrResourceCategory> nrResourceCategories;
    private final HashMap<String, NrResourceCategory> resourceCategoryMap = new HashMap();

    @Override
    public NrResourceCategory getResourceCategory(String resCategoryId) {
        return this.resourceCategoryMap.get(resCategoryId);
    }

    @Override
    public List<NrResource> getRootResources(String resourceCategoryId, String granteeId, Object param) {
        return this.resourceCategoryMap.get(resourceCategoryId).getRoot(granteeId, param);
    }

    @Override
    public List<NrResource> getRootResources(String resourceCategoryId, String granteeId, int privilegeType) {
        return this.resourceCategoryMap.get(resourceCategoryId).getRoot(granteeId, privilegeType);
    }

    @Override
    public List<NrResource> getChildResources(String resourceCategoryId, String resourceGroupId, String granteeId) {
        return this.resourceCategoryMap.get(resourceCategoryId).getChild(resourceGroupId, granteeId);
    }

    @Override
    public List<NrResource> getChildResources(String resourceCategoryId, String resourceGroupId, String granteeId, Object param) {
        return this.resourceCategoryMap.get(resourceCategoryId).getChild(resourceGroupId, granteeId, param);
    }

    @Override
    public List<Authority> linkage(String resourceCategoryId, AuthzAction authzAction) {
        return this.resourceCategoryMap.get(resourceCategoryId).linkage(authzAction);
    }

    @Override
    public List<NrResource> getResourcesAuthority(String resourceCategoryId, List<NrResource> rs, String granteeId, Object param) {
        return this.resourceCategoryMap.get(resourceCategoryId).getNrResourcesAuthority(rs, granteeId, param);
    }

    @Override
    public List<NrResource> getResourcesAuthorityById(String resourceCategoryId, List<String> rs, String granteeId, Boolean isDuty) {
        NrResourceCategory nrResourceCategory = this.resourceCategoryMap.get(resourceCategoryId);
        if (nrResourceCategory == null) {
            return Collections.emptyList();
        }
        return nrResourceCategory.getNrResourcesAuthorityById(rs, granteeId, isDuty == null ? false : isDuty);
    }

    @Override
    public void afterPropertiesSet() {
        if (this.nrResourceCategories == null) {
            return;
        }
        for (NrResourceCategory resourceCategorie : this.nrResourceCategories) {
            this.resourceCategoryMap.put(resourceCategorie.getId(), resourceCategorie);
        }
    }
}

