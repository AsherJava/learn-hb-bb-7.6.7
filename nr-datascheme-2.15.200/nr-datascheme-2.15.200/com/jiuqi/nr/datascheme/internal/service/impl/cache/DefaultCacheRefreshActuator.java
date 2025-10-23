/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.event.CacheRefreshActuator
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.nr.datascheme.api.event.CacheRefreshActuator;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DataSchemeCacheMessageSender;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultCacheRefreshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class DefaultCacheRefreshActuator
implements CacheRefreshActuator {
    @Autowired(required=false)
    private DefaultCacheRefreshService defaultCacheRefreshService;
    @Autowired
    private DataSchemeCacheMessageSender messageSender;
    private final Logger logger = LoggerFactory.getLogger(DefaultCacheRefreshActuator.class);

    DefaultCacheRefreshActuator() {
    }

    public void onClearCache() {
        RefreshCache refreshCache = new RefreshCache(true);
        this.logger.info("\u5f00\u59cb\u6e05\u9664\u5168\u90e8\u6570\u636e\u65b9\u6848\u7f13\u5b58");
        this.messageSender.send(refreshCache);
        this.defaultCacheRefreshService.onClearCache(refreshCache);
    }
}

