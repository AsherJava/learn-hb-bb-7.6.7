/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 */
package com.jiuqi.va.paramsync.config;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import com.jiuqi.va.paramsync.feign.client.VaParamTransferClient;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VaParamTransferRegister
implements ApplicationListener<StorageSyncFinishedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VaParamTransferRegister.class);
    @Autowired(required=false)
    private List<VaParamTransferModuleIntf> modules;
    @Autowired
    private VaParamTransferClient paramTransferClient;

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        try {
            if (this.modules != null && !this.modules.isEmpty()) {
                TenantDO tenant = new TenantDO();
                tenant.addExtInfo("modules", (Object)JSONUtil.toJSONString(this.modules));
                R rs = this.paramTransferClient.registParamTransferModule(tenant);
                if (rs.getCode() != 0) {
                    LOGGER.error("\u53c2\u6570\u540c\u6b65\u6ce8\u518c\u5f02\u5e38\uff1a" + rs.getMsg());
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("\u53c2\u6570\u540c\u6b65\u6ce8\u518c\u5f02\u5e38\uff1a", e);
        }
    }
}

