/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nvwa.sf.adapter.spring.service.security.cache;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import org.springframework.stereotype.Component;

@Component
public class SFSecurityHeaderCacheConfiguration
implements CacheManagerConfiguration {
    public static final String MANAGE_NAME = "sf_security_header";
    private CacheManagerProperties config = new CacheManagerProperties();

    public SFSecurityHeaderCacheConfiguration() {
        this.config.setName(MANAGE_NAME);
        this.config.setTtl(60L);
        this.config.setType(CacheType.redis);
        this.config.setLevel(CacheIevel.TENANT);
    }

    public String getName() {
        return MANAGE_NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.config;
    }
}

