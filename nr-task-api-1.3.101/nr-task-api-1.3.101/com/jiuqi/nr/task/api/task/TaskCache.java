/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.task.api.task;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class TaskCache {
    private static final String NAME = "NR_TASK_CACHE";
    private static final String ASYNC_TASK_CACHE = "ASYNC_TASK_CACHE";
    private static final String ASYNC_TASK_ID = "TASK_ID";
    private NedisCacheManager cacheManager;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager(NAME);
    }

    public synchronized List<String> getAsyncTask() {
        NedisCache cache = this.cacheManager.getCache(ASYNC_TASK_CACHE);
        Cache.ValueWrapper valueWrapper = cache.get(ASYNC_TASK_ID);
        if (valueWrapper == null) {
            return null;
        }
        return (List)valueWrapper.get();
    }

    public synchronized void addAsyncTak(String taskId) {
        NedisCache cache = this.cacheManager.getCache(ASYNC_TASK_CACHE);
        Cache.ValueWrapper valueWrapper = cache.get(ASYNC_TASK_ID);
        List ids = valueWrapper == null ? new ArrayList() : (List)valueWrapper.get();
        ids.add(taskId);
        cache.put(ASYNC_TASK_CACHE, ids);
    }
}

