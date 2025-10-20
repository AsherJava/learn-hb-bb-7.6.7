/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.paramsync.feign.client.VaParamTransferClient
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 */
package com.jiuqi.va.paramsync.service.join;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.feign.client.VaParamTransferClient;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class VaParamSyncFeignClientImpl
implements VaParamTransferClient {
    @Autowired
    private VaParamTransferInit paramTransferInit;

    public R registParamTransferModule(TenantDO tenantDO) {
        Object modules = tenantDO.getExtInfo("modules");
        if (modules != null) {
            this.paramTransferInit.registModules(JSONUtil.parseArray((String)modules.toString(), VaParamTransferModuleIntf.class));
        }
        return R.ok();
    }
}

