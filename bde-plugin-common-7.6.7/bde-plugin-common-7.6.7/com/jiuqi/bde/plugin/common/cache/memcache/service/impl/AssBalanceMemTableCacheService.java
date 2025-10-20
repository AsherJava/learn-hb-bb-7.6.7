/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 */
package com.jiuqi.bde.plugin.common.cache.memcache.service.impl;

import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.plugin.common.assbalance.fetch.AssBalanceCacheService;
import com.jiuqi.bde.plugin.common.cache.memcache.service.AbstractMemCacheService;
import org.springframework.stereotype.Service;

@Service
public class AssBalanceMemTableCacheService
extends AbstractMemCacheService
implements AssBalanceCacheService {
    @Override
    public String getCacheTableName() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }

    @Override
    public String getBizDataModelEnumCode() {
        return BizDataModelEnum.ASSBALANCEMODEL.getCode();
    }
}

