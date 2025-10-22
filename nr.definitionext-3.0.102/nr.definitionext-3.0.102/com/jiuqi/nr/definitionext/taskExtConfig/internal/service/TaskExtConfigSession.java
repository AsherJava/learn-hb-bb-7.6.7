/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definitionext.taskExtConfig.internal.service;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class TaskExtConfigSession
implements Serializable {
    public static final String NAME = "REMOTE_NR";
    private NedisCacheManager cacheManager;
    private static final String CACHENAME = "TASKEXT_CONFIG";
    private static final long serialVersionUID = 1L;

    @Autowired
    public void setCacheManager(NedisCacheProvider sessionCacheProvider) {
        this.cacheManager = sessionCacheProvider.getCacheManager(NAME);
    }

    public void saveResult(String CacheKey, String extData) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.put(CacheKey, (Object)extData);
    }

    public Object getResult(String CacheKey) {
        if (StringUtils.isEmpty((String)CacheKey)) {
            return null;
        }
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        Cache.ValueWrapper valueWrapper = caffeineCache.get(CacheKey);
        if (valueWrapper != null) {
            Object result = valueWrapper.get();
            return result;
        }
        return null;
    }

    public void removeResult(String CacheKey) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.evict(CacheKey);
    }

    public void clearResult() {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.clear();
    }
}

