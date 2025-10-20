/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.common.cache.remote;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class CacheObjectResourceRemote
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String NAME = "REMOTE_NR";
    private static final String CACHENAME = "CACHE_OBJECT";
    private NedisCacheManager cacheManager;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager(NAME);
    }

    public Object find(Object id) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        Object object = null;
        Cache.ValueWrapper valueWrapper = caffeineCache.get(id.toString());
        if (valueWrapper != null) {
            object = valueWrapper.get();
        }
        return object;
    }

    public void create(Object id, Object object) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.put(id.toString(), object);
    }
}

