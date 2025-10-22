/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.splittable.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;

public class SplitGridCacheManagerConfig
implements CacheManagerConfiguration {
    public static String NAME = "REDIS_CACHE_SPLIT_GRID";
    private final String REDIS_CAFFEINE = "rediscaffeine";
    private CacheManagerProperties cacheManagerProperties = new CacheManagerProperties();

    public String getName() {
        return NAME;
    }

    public SplitGridCacheManagerConfig(String cacheType) {
        this.cacheManagerProperties.setName(NAME);
        this.cacheManagerProperties.setTtl(7200L);
        this.cacheManagerProperties.setLevel(Constants.LEVEL_DEFAULT);
        if ("rediscaffeine".equalsIgnoreCase(cacheType)) {
            this.cacheManagerProperties.setType(CacheType.redis);
        }
    }

    public CacheManagerProperties getProperties() {
        return this.cacheManagerProperties;
    }
}

