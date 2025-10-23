/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DataSchemeCacheMessageSender;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultCacheRefreshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
class CacheEventListener
implements ApplicationListener<RefreshSchemeCacheEvent> {
    @Autowired
    private DefaultCacheRefreshService defaultCacheRefreshService;
    private final Logger logger = LoggerFactory.getLogger(CacheEventListener.class);
    @Autowired
    private DataSchemeCacheMessageSender messageSender;

    CacheEventListener() {
    }

    @Override
    public void onApplicationEvent(@NonNull RefreshSchemeCacheEvent event) {
        RefreshCache source = event.getSource();
        this.logger.info("\u5237\u65b0\u6570\u636e\u65b9\u6848\u53c2\u6570\u7f13\u5b58{}", (Object)source);
        this.messageSender.send(source);
        this.defaultCacheRefreshService.onClearCache(source);
    }
}

