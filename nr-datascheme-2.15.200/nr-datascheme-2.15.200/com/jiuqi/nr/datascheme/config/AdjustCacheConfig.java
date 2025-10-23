/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nr.datascheme.config;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;

public class AdjustCacheConfig
implements CacheManagerConfiguration {
    public static final String NAME = "nr:scheme:adjust";
    private final CacheManagerProperties properties = new CacheManagerProperties();

    public AdjustCacheConfig(String cacheType) {
        this.properties.setName(NAME);
        this.properties.setLevel(CacheIevel.SYSTEM);
        this.properties.setTtl(0L);
        if (CacheType.rediscaffeine.getName().equals(cacheType)) {
            this.properties.setType(CacheType.rediscaffeine);
        } else {
            this.properties.setType(CacheType.local);
        }
    }

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.properties;
    }
}

