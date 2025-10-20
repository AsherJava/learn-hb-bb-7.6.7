/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.biz.impl.join;

import com.jiuqi.va.biz.config.VaBizBindingConfig;
import com.jiuqi.va.biz.synccache.VaBizBindCacheHandler;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaBizBindingMsgReciver
implements MessageListener {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private List<VaBizBindCacheHandler> sortedBindCacheHandlers;

    @Autowired
    public void setBindCacheHandlers(List<VaBizBindCacheHandler> bindCacheHandlers) {
        this.sortedBindCacheHandlers = CollectionUtils.isEmpty(bindCacheHandlers) ? new ArrayList<VaBizBindCacheHandler>() : bindCacheHandlers.stream().sorted(Comparator.comparingInt(VaBizBindCacheHandler::getOrder)).collect(Collectors.toList());
    }

    public void onMessage(Message message, byte[] pattern) {
        TenantDO redisMsgParam = (TenantDO)JSONUtil.parseObject((String)new String(message.getBody()), TenantDO.class);
        Object sourceNodeId = redisMsgParam.getExtInfo().get("currNodeId");
        String currNodeId = VaBizBindingConfig.getCurrNodeId();
        boolean isCurrNode = sourceNodeId != null && sourceNodeId.toString().equals(currNodeId);
        String tenantName = ShiroUtil.getTenantName();
        List popMsg = this.stringRedisTemplate.opsForSet().pop((Object)(currNodeId + "#META_ALL_SYNC#" + tenantName), 10000L);
        if (!CollectionUtils.isEmpty(popMsg)) {
            for (String msg : popMsg) {
                Map msgObj = JSONUtil.parseMap((String)msg);
                for (VaBizBindCacheHandler bindCacheHandler : this.sortedBindCacheHandlers) {
                    if (isCurrNode && !bindCacheHandler.enableWhileIsCurrNode()) continue;
                    bindCacheHandler.handle(msgObj, tenantName);
                }
            }
        }
    }
}

