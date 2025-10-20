/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nvwa.sf.adapter.spring.login;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import org.springframework.stereotype.Component;

@Component
public class SFRedisCacheConfig
implements CacheManagerConfiguration {
    public static final String CACHE_NAME = "sf_login_cache";
    private CacheManagerProperties config = new CacheManagerProperties();

    public SFRedisCacheConfig() {
        this.config.setName(CACHE_NAME);
        this.config.setTtl(1800L);
        this.config.setType(CacheType.redis);
    }

    public String getName() {
        return CACHE_NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.config;
    }
}

