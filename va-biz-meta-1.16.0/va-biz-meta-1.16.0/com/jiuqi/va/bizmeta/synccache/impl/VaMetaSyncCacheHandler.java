/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.synccache.VaBizBindCacheHandler
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 */
package com.jiuqi.va.bizmeta.synccache.impl;

import com.jiuqi.va.biz.synccache.VaBizBindCacheHandler;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataInfoService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaMetaSyncCacheHandler
implements VaBizBindCacheHandler {
    private static final Logger logger = LoggerFactory.getLogger(VaMetaSyncCacheHandler.class);
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private MetaDataInfoService metaDataInfoService;

    public boolean enableWhileIsCurrNode() {
        return false;
    }

    public int getOrder() {
        return 1;
    }

    public void handle(Map<String, Object> messageObj, String tenantName) {
        String msg = (String)messageObj.get("META_CACHE");
        if (msg == null) {
            return;
        }
        try {
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
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u5143\u6570\u636e\u7f13\u5b58\u5931\u8d25\uff08{}\uff09\uff0c{}", msg, e.getMessage(), e);
        }
    }
}

