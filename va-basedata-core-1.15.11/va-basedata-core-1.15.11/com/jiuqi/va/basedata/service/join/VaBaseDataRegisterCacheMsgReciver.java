/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.basedata.service.join;

import com.jiuqi.va.basedata.domain.BaseDataRegisterCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheCoordinationService;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component(value="vaBaseDataRegisterCacheMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaBaseDataRegisterCacheMsgReciver
implements MessageListener {
    @Autowired
    private BaseDataCacheCoordinationService coordinationService;

    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        BaseDataRegisterCacheDTO bdrc = (BaseDataRegisterCacheDTO)JSONUtil.parseObject((String)msg, BaseDataRegisterCacheDTO.class);
        if (bdrc == null) {
            return;
        }
        if (EnvConfig.getCurrNodeId().equals(bdrc.getCurrNodeId())) {
            return;
        }
        this.coordinationService.registerCacheNodeInfo(bdrc);
    }
}

