/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.openapi.mq;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;
import com.jiuqi.va.openapi.domain.OpenApiSyncCacheDTO;
import com.jiuqi.va.openapi.service.impl.help.VaOpenApiCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component(value="vaOpenApiSyncCacheMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaOpenApiSyncCacheMsgReciver
implements MessageListener {
    @Autowired
    private VaOpenApiCacheService openApiCacheService;

    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        OpenApiSyncCacheDTO dmsc = (OpenApiSyncCacheDTO)((Object)JSONUtil.parseObject((String)msg, OpenApiSyncCacheDTO.class));
        if (EnvConfig.getCurrNodeId().equals(dmsc.getCurrtNodeId())) {
            return;
        }
        OpenApiAuthDO param = dmsc.getOpenApiAuthDO();
        if (dmsc.isUpdate()) {
            this.openApiCacheService.update(param);
        } else if (dmsc.isRemove()) {
            this.openApiCacheService.remove(param);
        } else if (dmsc.isStop()) {
            this.openApiCacheService.stop(param);
        }
    }
}

