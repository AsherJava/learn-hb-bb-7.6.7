/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.efdc.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import org.springframework.stereotype.Component;

@Component
public class EFDCCacheConfig
implements CacheManagerConfiguration {
    public static final String NAME = "EFDC_CACHE";
    public CacheManagerProperties cacheManagerProperties = new CacheManagerProperties();

    public EFDCCacheConfig() {
        this.cacheManagerProperties.setName(NAME);
        this.cacheManagerProperties.setTtl(0L);
        this.cacheManagerProperties.setLevel(Constants.LEVEL_DEFAULT);
        this.cacheManagerProperties.setType(CacheType.redis);
    }

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return new CacheManagerProperties();
    }
}

