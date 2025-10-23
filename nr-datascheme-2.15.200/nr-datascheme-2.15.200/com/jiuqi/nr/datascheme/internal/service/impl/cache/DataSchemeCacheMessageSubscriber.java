/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DefaultCacheRefreshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Subscriber(channels={"com.jiuqi.nr.datascheme.runtime"})
@Service
public class DataSchemeCacheMessageSubscriber
implements MessageSubscriber {
    private final Logger logger = LoggerFactory.getLogger(DataSchemeCacheMessageSubscriber.class);
    @Autowired
    private DefaultCacheRefreshService defaultCacheRefreshService;

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        RefreshCache refreshCache;
        this.logger.debug("\u8ba2\u9605\u5230Redis \u6d88\u606f\uff0c\u5237\u65b0\u6570\u636e\u65b9\u6848\u53c2\u6570\u7f13\u5b58{}", message);
        if (fromThisInstance) {
            this.logger.debug("\u5237\u65b0\u7f13\u5b58\u6d88\u606f\u662f\u5426\u7531\u5f53\u524d\u670d\u52a1\u53d1\u51fa,\u4e0d\u9700\u8981\u5904\u7406 {}", message);
            return;
        }
        if (message == null) {
            this.logger.debug("\u6d88\u606f\u4f53\u4e3a\u7a7a,\u4e0d\u9700\u8981\u5904\u7406");
            return;
        }
        RefreshCache refreshCache2 = refreshCache = message instanceof RefreshCache ? (RefreshCache)message : null;
        if (refreshCache == null) {
            this.logger.debug("\u6d88\u606f\u7c7b\u578b\u9519\u8bef,\u4e0d\u9700\u8981\u5904\u7406 {}", message);
            return;
        }
        this.logger.debug("\u5237\u65b0\u6570\u636e\u65b9\u6848\u53c2\u6570\u7f13\u5b58{}", (Object)refreshCache);
        this.defaultCacheRefreshService.onClearCache(refreshCache);
    }
}

