/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFormStruct;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class BlobTableAndFieldSession
implements Serializable {
    public static final String NAME = "REMOTE_NR";
    private NedisCacheManager cacheManager;
    private static final String CACHENAME = "BLOBITEMS_PARAM";
    private static final long serialVersionUID = 1L;

    @Autowired
    public void setCacheManager(NedisCacheProvider sessionCacheProvider) {
        this.cacheManager = sessionCacheProvider.getCacheManager(NAME);
    }

    public void saveResult(String CacheKey, List<BlobFormStruct> items) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.put(CacheKey, items);
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
}

