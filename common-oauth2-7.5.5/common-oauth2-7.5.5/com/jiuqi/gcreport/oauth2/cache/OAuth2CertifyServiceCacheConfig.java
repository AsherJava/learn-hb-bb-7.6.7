/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.gcreport.oauth2.cache;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import org.springframework.stereotype.Component;

@Component
public class OAuth2CertifyServiceCacheConfig
implements CacheManagerConfiguration {
    private CacheManagerProperties config = new CacheManagerProperties();

    public OAuth2CertifyServiceCacheConfig() {
        this.config.setName("GC_OAUTH2_CERITY_CACHE_MANAGE");
        this.config.setTtl(36000L);
        this.config.setType(CacheType.redis);
    }

    public String getName() {
        return "GC_OAUTH2_CERITY_CACHE_MANAGE";
    }

    public CacheManagerProperties getProperties() {
        return this.config;
    }
}

