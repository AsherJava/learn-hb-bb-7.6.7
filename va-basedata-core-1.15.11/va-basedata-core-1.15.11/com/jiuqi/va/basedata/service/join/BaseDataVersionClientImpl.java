/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataVersionClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.basedata.service.join;

import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataVersionClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component(value="vaCoreBaseDataVersionClientImpl")
public class BaseDataVersionClientImpl
implements BaseDataVersionClient {
    private BaseDataVersionService baseDataVersionService;

    public BaseDataVersionService getBaseDataVersionService() {
        if (this.baseDataVersionService == null) {
            this.baseDataVersionService = (BaseDataVersionService)ApplicationContextRegister.getBean(BaseDataVersionService.class);
        }
        return this.baseDataVersionService;
    }

    public BaseDataVersionDO get(BaseDataVersionDTO param) {
        return this.getBaseDataVersionService().get(param);
    }

    public PageVO<BaseDataVersionDO> list(BaseDataVersionDTO param) {
        return this.getBaseDataVersionService().list(param);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R add(BaseDataVersionDO baseDataVersionDO) {
        return this.getBaseDataVersionService().add(baseDataVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R update(BaseDataVersionDO baseDataVersionDO) {
        return this.getBaseDataVersionService().update(baseDataVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R split(BaseDataVersionDO baseDataVersionDO) {
        return this.getBaseDataVersionService().split(baseDataVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R remove(BaseDataVersionDO baseDataVersionDO) {
        return this.getBaseDataVersionService().remove(baseDataVersionDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R changeStatus(List<BaseDataVersionDO> dataList) {
        return this.getBaseDataVersionService().changeStatus(dataList);
    }

    public R syncCache(BaseDataVersionDO baseDataVersionDO) {
        return this.getBaseDataVersionService().syncCache(baseDataVersionDO);
    }
}

