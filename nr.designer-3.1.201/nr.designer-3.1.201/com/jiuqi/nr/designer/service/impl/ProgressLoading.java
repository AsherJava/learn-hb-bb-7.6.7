/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.designer.service.impl.Progress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgressLoading {
    private NedisCacheManager cacheManager;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    public Progress getProgress(String taskId) {
        NedisCache cache = this.cacheManager.getCache("NR_PROGRESS");
        return (Progress)cache.get(taskId, Progress.class);
    }

    public synchronized boolean setProgress(String taskId, Progress progress) {
        NedisCache cache = this.cacheManager.getCache("NR_PROGRESS");
        cache.put(taskId, (Object)progress);
        return true;
    }

    public void removeProgress(String taskId) {
        NedisCache cache = this.cacheManager.getCache("NR_PROGRESS");
        cache.evict(taskId);
    }
}

