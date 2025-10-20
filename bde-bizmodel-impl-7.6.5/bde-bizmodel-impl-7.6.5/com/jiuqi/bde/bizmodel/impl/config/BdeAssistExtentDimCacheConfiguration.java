/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.bde.bizmodel.impl.config;

import com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig;
import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheProperties;
import com.jiuqi.np.cache.config.CacheType;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class BdeAssistExtentDimCacheConfiguration
implements CacheManagerConfiguration {
    public static final String MANAGE_NAME = "BDE_ASSIST_EXTENT_DIM";
    public static final String CACHE_NAME = "BDE_ASSIST_EXTENT";
    private CacheManagerProperties config = null;

    public String getName() {
        return MANAGE_NAME;
    }

    public CacheManagerProperties getProperties() {
        if (this.config == null) {
            this.config = new CacheManagerProperties();
            this.config.setName(MANAGE_NAME);
            this.config.setTtl((long)BdeBizModelConfig.getValidTime().intValue());
            this.config.setType(CacheType.rediscaffeine);
            this.config.setLevel(CacheIevel.TENANT);
            LinkedList<CacheProperties> caches = new LinkedList<CacheProperties>();
            CacheProperties tokenCachePropertie = new CacheProperties();
            tokenCachePropertie.setName(CACHE_NAME);
            tokenCachePropertie.setTtl((long)BdeBizModelConfig.getValidTime().intValue());
            caches.add(tokenCachePropertie);
            this.config.setCaches(caches);
        }
        return this.config;
    }
}

