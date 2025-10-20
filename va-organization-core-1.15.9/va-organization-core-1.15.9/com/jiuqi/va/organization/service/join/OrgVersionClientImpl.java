/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.organization.service.join;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component(value="vaCoreOrgVersionClientImpl")
public class OrgVersionClientImpl
implements OrgVersionClient {
    private OrgVersionService orgVersionService;

    public OrgVersionService getOrgVersionService() {
        if (this.orgVersionService == null) {
            this.orgVersionService = (OrgVersionService)ApplicationContextRegister.getBean(OrgVersionService.class);
        }
        return this.orgVersionService;
    }

    public OrgVersionDO get(OrgVersionDTO param) {
        return this.getOrgVersionService().get(param);
    }

    public PageVO<OrgVersionDO> list(OrgVersionDTO param) {
        return this.getOrgVersionService().list(param);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R add(OrgVersionDO orgVersionDO) {
        return this.getOrgVersionService().add(orgVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R update(OrgVersionDO orgVersionDO) {
        return this.getOrgVersionService().update(orgVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R split(OrgVersionDO orgVersionDO) {
        return this.getOrgVersionService().split(orgVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R remove(OrgVersionDO orgVersionDO) {
        return this.getOrgVersionService().remove(orgVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R changeStatus(List<OrgVersionDO> dataList) {
        return this.getOrgVersionService().changeStatus(dataList);
    }

    public R syncCache(OrgVersionDO orgVersionDO) {
        return this.getOrgVersionService().syncCache(orgVersionDO);
    }
}

