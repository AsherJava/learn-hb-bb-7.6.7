/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 */
package com.jiuqi.va.biz.impl.join;

import com.jiuqi.va.biz.config.VaBizBindingConfig;
import com.jiuqi.va.biz.domain.ModelDefineSyncCacheDTO;
import com.jiuqi.va.biz.impl.model.ModelDefineCacheServiceImpl;
import com.jiuqi.va.biz.intf.model.BillFrontDefineService;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaSyncCacheMsgReciver
implements MessageListener {
    @Autowired
    ModelDefineCacheServiceImpl modelDefineCacheService;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired(required=false)
    private BillFrontDefineService billFrontDefineService;

    public void onMessage(Message message, byte[] pattern) {
        ModelDefineSyncCacheDTO cacheDTO;
        String currNodeId = VaBizBindingConfig.getCurrNodeId();
        if (currNodeId.equals((cacheDTO = (ModelDefineSyncCacheDTO)((Object)JSONUtil.parseObject((String)new String(message.getBody()), ModelDefineSyncCacheDTO.class))).getCurrNodeId())) {
            return;
        }
        this.modelDefineService.clearLocalBillCache(cacheDTO.getTenantName(), cacheDTO.getDefineName());
        if (this.billFrontDefineService != null) {
            this.billFrontDefineService.clearCache(cacheDTO.getTenantName(), cacheDTO.getDefineName());
        }
    }
}

