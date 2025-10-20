/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.bde.penetrate.impl.service.impl;

import com.jiuqi.bde.penetrate.impl.service.BdePenetrateCacheManageService;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class BdePenetrateCacheManageServiceImpl
implements BdePenetrateCacheManageService {
    private final NedisCache bdePenetrateCache;

    public BdePenetrateCacheManageServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_PENETRACTE_MANAGE");
        this.bdePenetrateCache = cacheManager.getCache("BDE_PENETRACTE");
    }

    @Override
    public String getPenetrateContext(String id) {
        Cache.ValueWrapper value = this.bdePenetrateCache.get(id);
        this.delPenetrateContext(id);
        if (null != value) {
            return (String)value.get();
        }
        return "{}";
    }

    private void delPenetrateContext(String key) {
        this.bdePenetrateCache.evict(key);
    }

    @Override
    public String savePenetrateContext(String context) {
        String id = UUIDUtils.newUUIDStr();
        this.bdePenetrateCache.put(id, (Object)context);
        return id;
    }
}

