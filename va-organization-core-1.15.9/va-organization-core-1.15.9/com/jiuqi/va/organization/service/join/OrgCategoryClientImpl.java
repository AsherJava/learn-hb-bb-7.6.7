/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.organization.service.join;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.OrgCategoryService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@Component(value="vaCoreOrgCategoryClientImpl")
public class OrgCategoryClientImpl
implements OrgCategoryClient {
    private OrgCategoryService orgCatService;

    public OrgCategoryService getOrgCategoryService() {
        if (this.orgCatService == null) {
            this.orgCatService = (OrgCategoryService)ApplicationContextRegister.getBean(OrgCategoryService.class);
        }
        return this.orgCatService;
    }

    public PageVO<OrgCategoryDO> list(OrgCategoryDO orgCatDTO) {
        return this.getOrgCategoryService().list(orgCatDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R add(OrgCategoryDO orgCategoryDO) {
        return this.getOrgCategoryService().add(orgCategoryDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R update(OrgCategoryDO orgCategoryDO) {
        return this.getOrgCategoryService().update(orgCategoryDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R remove(@RequestBody OrgCategoryDO orgCategoryDO) {
        return this.getOrgCategoryService().remove(orgCategoryDO);
    }

    public R syncCache(OrgCategoryDO orgCategoryDO) {
        return this.getOrgCategoryService().syncCache(orgCategoryDO);
    }
}

