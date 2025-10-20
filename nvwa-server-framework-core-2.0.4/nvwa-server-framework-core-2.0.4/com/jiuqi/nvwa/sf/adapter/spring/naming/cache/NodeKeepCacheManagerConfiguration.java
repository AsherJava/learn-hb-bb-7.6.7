/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.cache;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;

public class NodeKeepCacheManagerConfiguration
implements CacheManagerConfiguration {
    public static final String DEFAULT_CACHE_MANAGER_NAME = "nodeKeepCacheManager";
    private CacheManagerProperties config = new CacheManagerProperties();

    public NodeKeepCacheManagerConfiguration() {
        this.config.setName(DEFAULT_CACHE_MANAGER_NAME);
        this.config.setTtl(30L);
        this.config.setType(CacheType.local);
        this.config.setLevel(CacheIevel.TENANT);
    }

    public String getName() {
        return DEFAULT_CACHE_MANAGER_NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.config;
    }
}

