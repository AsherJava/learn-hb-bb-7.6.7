/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 */
package com.jiuqi.bde.plugin.common.cache.memcache.service.impl;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.plugin.common.aging.fetch.AgingBalanceCacheService;
import com.jiuqi.bde.plugin.common.cache.memcache.service.AbstractMemCacheService;
import org.springframework.stereotype.Service;

@Service
public class AgingBalanceMemTableCacheService
extends AbstractMemCacheService
implements AgingBalanceCacheService {
    @Override
    public String getCacheTableName() {
        return MemoryBalanceTypeEnum.ASSAGINGBALANCE.getCode();
    }
}

