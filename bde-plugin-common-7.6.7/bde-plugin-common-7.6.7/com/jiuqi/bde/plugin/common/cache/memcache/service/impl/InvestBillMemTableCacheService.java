/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.cache.memcache.service.impl;

import com.jiuqi.bde.plugin.common.cache.memcache.service.AbstractMemCacheService;
import com.jiuqi.bde.plugin.common.invest.fetch.InvestBillCacheService;
import org.springframework.stereotype.Service;

@Service
public class InvestBillMemTableCacheService
extends AbstractMemCacheService
implements InvestBillCacheService {
    @Override
    public String getCacheTableName() {
        return "BDE_INVESTBILL";
    }
}

