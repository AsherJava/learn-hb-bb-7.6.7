/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.definition.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;

public class PrintCacheManagerConfiguration
implements CacheManagerConfiguration {
    public static final String NAME = "REDIS_CACHE_PRINT_TEMPLATE";
    private final String REDIS_CAFFEINE = "rediscaffeine";
    public CacheManagerProperties cacheManagerProperties = new CacheManagerProperties();

    public PrintCacheManagerConfiguration(String cacheType) {
        this.cacheManagerProperties.setName(NAME);
        this.cacheManagerProperties.setTtl(7200L);
        this.cacheManagerProperties.setLevel(Constants.LEVEL_DEFAULT);
        if ("rediscaffeine".equalsIgnoreCase(cacheType)) {
            this.cacheManagerProperties.setType(CacheType.redis);
        }
    }

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.cacheManagerProperties;
    }
}

