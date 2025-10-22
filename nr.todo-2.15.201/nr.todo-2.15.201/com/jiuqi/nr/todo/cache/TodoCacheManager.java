/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.CacheProvider
 */
package com.jiuqi.nr.todo.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.CacheProvider;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component(value="com.jiuqi.nr.todo.cache.CacheManager")
public class TodoCacheManager {
    public static final String CACHE_MANAGER_NAME = "todo_cache_manager";
    private static final String KEY_NEW_TODO_TIME = "com:jiuqi:nr:todo:new";
    private final CacheProvider cacheProvider;
    private NedisCacheManager cacheManager;
    private NedisCache cache;

    public TodoCacheManager(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public void updateTodoTime(List<String> participantIds, long unixTimestamp) {
        NedisCache cache = this.getCache();
        Map cacheEntity = participantIds.stream().collect(Collectors.toMap(Function.identity(), x -> unixTimestamp, (o1, o2) -> o1));
        cache.hMSet(KEY_NEW_TODO_TIME, cacheEntity);
    }

    public long getLatestTodoTime(List<String> participantId) {
        NedisCache cache = this.getCache();
        List cacheValues = cache.hMGet(KEY_NEW_TODO_TIME, participantId);
        Long max = null;
        for (Cache.ValueWrapper valueWrapper : cacheValues) {
            if (valueWrapper == null) continue;
            Long current = (Long)valueWrapper.get();
            if (max == null) {
                max = current;
                continue;
            }
            if (current <= max) continue;
            max = current;
        }
        return max == null ? -1L : max;
    }

    private NedisCacheManager getTodoCacheManager() {
        if (this.cacheManager == null) {
            this.cacheManager = this.cacheProvider.getCacheManager(CACHE_MANAGER_NAME);
        }
        return this.cacheManager;
    }

    private NedisCache getCache() {
        if (this.cache == null) {
            this.cache = this.cacheProvider.getCacheManager(CACHE_MANAGER_NAME).getCache(KEY_NEW_TODO_TIME);
        }
        return this.cache;
    }
}

