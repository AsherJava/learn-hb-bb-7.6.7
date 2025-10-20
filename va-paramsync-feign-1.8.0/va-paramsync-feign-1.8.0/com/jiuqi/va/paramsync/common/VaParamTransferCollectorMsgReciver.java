/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.paramsync.common;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.feign.client.VaParamTransferClient;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component(value="vaParamTransferCollectorMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaParamTransferCollectorMsgReciver
implements MessageListener {
    @Autowired(required=false)
    private List<VaParamTransferModuleIntf> modules;
    @Autowired
    private VaParamTransferClient paramTransferClient;

    public void onMessage(Message message, byte[] pattern) {
        if (message == null) {
            return;
        }
        if (this.modules != null && !this.modules.isEmpty()) {
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("modules", (Object)JSONUtil.toJSONString(this.modules));
            this.paramTransferClient.registParamTransferModule(tenant);
        }
    }
}

