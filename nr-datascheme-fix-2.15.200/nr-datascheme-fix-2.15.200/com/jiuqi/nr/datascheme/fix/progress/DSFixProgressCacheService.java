/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.fix.progress;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DSFixProgressCacheService {
    private static final String CACHENAME = "datashemedeployfix_progresscacheservice";
    private NedisCacheManager cacheManager;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("nr:schemefix");
    }

    public ProgressItem getProgress(String progressId) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        ProgressItem cacheItem = (ProgressItem)cache.get(progressId, ProgressItem.class);
        return cacheItem;
    }

    public void setProgress(String progressId, ProgressItem progressItem) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        cache.put(progressId, (Object)progressItem);
    }

    public void removeProgress(String progressId) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        cache.evict(progressId);
    }
}

