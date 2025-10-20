/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.config.VaBizBindingConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bizmeta.service.join;

import com.jiuqi.va.biz.config.VaBizBindingConfig;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaSyncCacheDTO;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataInfoService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaMetaSyncCacheMsgReciver
implements MessageListener {
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private MetaDataInfoService metaDataInfoService;

    public void onMessage(Message message, byte[] pattern) {
        MetaSyncCacheDTO dmsc = (MetaSyncCacheDTO)((Object)JSONUtil.parseObject((String)Arrays.toString(message.getBody()), MetaSyncCacheDTO.class));
        Object currtNodeId = dmsc.getExtInfo("currNodeId");
        if (currtNodeId != null && currtNodeId.toString().equals(VaBizBindingConfig.getCurrNodeId())) {
            return;
        }
        String currNodeId = VaBizBindingConfig.getCurrNodeId();
        String tenantName = ShiroUtil.getTenantName();
        List popMsg = this.stringRedisTemplate.opsForSet().pop((Object)(currNodeId + "#META_SYNC#" + tenantName), 10000L);
        if (CollectionUtils.isEmpty(popMsg)) {
            return;
        }
        for (String msg : popMsg) {
            String[] split = msg.split("#");
            int metaStatus = Integer.parseInt(split[1]);
            MetaDataDTO dataDTO = this.metaDataService.getMetaDataHistoryById(UUID.fromString(split[0]));
            MetaInfoDTO metaInfoDTO = this.metaDataInfoService.findHistoryVersionById(UUID.fromString(split[0]));
            this.metaSyncCacheService.handleStatusCache(metaInfoDTO.getUniqueCode(), metaInfoDTO.getVersionNO(), metaStatus, false);
            if (MetaState.DELETED.getValue() == metaStatus) {
                this.metaSyncCacheService.updateCache(dataDTO, metaInfoDTO, MetaState.DELETED.getValue());
            } else {
                MetaInfoDO queryDO = new MetaInfoDO();
                queryDO.setUniqueCode(metaInfoDTO.getUniqueCode());
                Long maxVer = this.metaDataInfoService.findMaxVersion(queryDO);
                if (metaInfoDTO.getVersionNO().equals(maxVer)) {
                    this.metaSyncCacheService.updateCache(dataDTO, metaInfoDTO, MetaState.DEPLOYED.getValue());
                } else {
                    this.metaSyncCacheService.updateCache(dataDTO, metaInfoDTO, MetaState.MODIFIED.getValue());
                }
            }
            this.metaSyncCacheService.handleStatusCache(metaInfoDTO.getUniqueCode(), metaInfoDTO.getVersionNO(), metaStatus, true);
        }
    }
}

