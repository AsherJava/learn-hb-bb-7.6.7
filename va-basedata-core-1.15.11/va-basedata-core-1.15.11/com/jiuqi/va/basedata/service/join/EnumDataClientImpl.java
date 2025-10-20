/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.basedata.service.join;

import com.jiuqi.va.basedata.service.EnumDataService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component(value="vaCoreEnumDataClientImpl")
public class EnumDataClientImpl
implements EnumDataClient {
    private EnumDataService enumDataService;

    public EnumDataService getEnumDataService() {
        if (this.enumDataService == null) {
            this.enumDataService = (EnumDataService)ApplicationContextRegister.getBean(EnumDataService.class);
        }
        return this.enumDataService;
    }

    public List<EnumDataDO> list(EnumDataDTO param) {
        return this.getEnumDataService().list(param);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R add(EnumDataDO param) {
        return this.getEnumDataService().add(param);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R update(EnumDataDO param) {
        return this.getEnumDataService().update(param);
    }

    public List<EnumDataDO> listBiztype(EnumDataDTO param) {
        return this.getEnumDataService().listBiztype(param);
    }
}

