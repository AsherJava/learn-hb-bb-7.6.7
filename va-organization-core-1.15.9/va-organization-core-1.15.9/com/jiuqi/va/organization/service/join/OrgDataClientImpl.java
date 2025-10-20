/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.organization.service.join;

import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.OrgConstants;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@Component(value="vaCoreOrgDataClientImpl")
public class OrgDataClientImpl
implements OrgDataClient {
    private OrgDataService orgDataService;

    public OrgDataService getOrgDataService() {
        if (this.orgDataService == null) {
            this.orgDataService = (OrgDataService)ApplicationContextRegister.getBean(OrgDataService.class);
        }
        return this.orgDataService;
    }

    public OrgDO get(OrgDTO orgDTO) {
        return this.getOrgDataService().get(orgDTO);
    }

    public PageVO<OrgDO> list(OrgDTO orgDTO) {
        Map context;
        OrgContext.removeContext((String)orgDTO.getCategoryname());
        PageVO<OrgDO> page = this.getOrgDataService().list(orgDTO);
        if (page != null && page.getTotal() > 0 && (context = OrgContext.getDataContext((String)orgDTO.getCategoryname())) != null) {
            page.getRs().putAll(context);
        }
        return page;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R add(OrgDTO orgDTO) {
        return this.getOrgDataService().add(orgDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    @Deprecated
    public R relAdd(OrgDTO orgDTO) {
        return this.getOrgDataService().relAdd(orgDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R update(OrgDTO orgDTO) {
        return this.getOrgDataService().update(orgDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R remove(OrgDTO orgDTO) {
        return this.getOrgDataService().remove(orgDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R batchRemove(OrgBatchOptDTO orgBatchOptDTO) {
        return this.getOrgDataService().batchRemove(orgBatchOptDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R changeState(OrgDTO orgDTO) {
        return this.getOrgDataService().changeState(orgDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R up(OrgDTO orgDTO) {
        return this.getOrgDataService().upOrDown(orgDTO, OrgConstants.UpOrDown.UP);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R down(OrgDTO orgDTO) {
        return this.getOrgDataService().upOrDown(orgDTO, OrgConstants.UpOrDown.DOWN);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R move(OrgDTO orgDTO) {
        return this.getOrgDataService().move(orgDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R sync(@RequestBody OrgBatchOptDTO orgBatchOptDTO) {
        return this.getOrgDataService().sync(orgBatchOptDTO);
    }

    public int count(@RequestBody OrgDTO orgDTO) {
        return this.getOrgDataService().count(orgDTO);
    }

    public List<OrgDO> verDiffList(@RequestBody OrgDTO orgDTO) {
        return this.getOrgDataService().verDiffList(orgDTO);
    }

    public R initCache(@RequestBody OrgCategoryDTO orgCatDTO) {
        return this.getOrgDataService().initCache(orgCatDTO);
    }

    public R syncCache(@RequestBody OrgCategoryDTO orgCatDTO) {
        return this.getOrgDataService().syncCache(orgCatDTO);
    }

    public R cleanCache(@RequestBody OrgCategoryDTO orgCatDTO) {
        return this.getOrgDataService().cleanCache(orgCatDTO);
    }
}

