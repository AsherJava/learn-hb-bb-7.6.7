/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDetailDO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataDetailClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.join;

import com.jiuqi.va.basedata.service.BaseDataDetailService;
import com.jiuqi.va.domain.basedata.BaseDataDetailDO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataDetailClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component(value="vaCoreBaseDataDetailClientImpl")
public class BaseDataDetailClientImpl
implements BaseDataDetailClient {
    private BaseDataDetailService baseDataDetailService;

    public BaseDataDetailService getBaseDataDetailService() {
        if (this.baseDataDetailService == null) {
            this.baseDataDetailService = (BaseDataDetailService)ApplicationContextRegister.getBean(BaseDataDetailService.class);
        }
        return this.baseDataDetailService;
    }

    public List<BaseDataDetailDO> list(BaseDataDetailDO param) {
        return this.getBaseDataDetailService().list(param);
    }

    public List<BaseDataDetailDO> get(BaseDataDetailDO param) {
        return this.getBaseDataDetailService().get(param);
    }

    public R add(BaseDataDetailDO param) {
        return this.getBaseDataDetailService().add(param);
    }

    public R delete(BaseDataDetailDO param) {
        return this.getBaseDataDetailService().delete(param);
    }
}

