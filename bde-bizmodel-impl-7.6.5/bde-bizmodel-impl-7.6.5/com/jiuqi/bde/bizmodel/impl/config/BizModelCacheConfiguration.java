/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.bde.bizmodel.impl.config;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheProperties;
import com.jiuqi.np.cache.config.CacheType;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class BizModelCacheConfiguration
implements CacheManagerConfiguration {
    public static final String MANAGE_NAME = "BDE_BIZMODEL_MANAGE";
    public static final String CACHE_NAME = "BDE_BIZMODEL";
    public static final String ALL_BIZMODEL_CACHE_KEY = "ALL_BDE_BIZMODEL_CACHE_ID";
    public static final String FINDATA_BIZMODEL_CACHE_KEY = "BDE_FINDATA_BIZMODEL_CACHE_ID";
    public static final String BASEDATA_BIZMODEL_CACHE_KEY = "BDE_BASEDATA_BIZMODEL_CACHE_ID";
    public static final String CUSTOM_BIZMODEL_CACHE_KEY = "BDE_CUSTOM_BIZMODEL_CACHE_ID";
    private static final long VALID_TIME = 3600L;
    private CacheManagerProperties config = null;

    public String getName() {
        return MANAGE_NAME;
    }

    public CacheManagerProperties getProperties() {
        if (this.config == null) {
            this.config = new CacheManagerProperties();
            this.config.setName(MANAGE_NAME);
            this.config.setTtl(3600L);
            this.config.setType(CacheType.rediscaffeine);
            this.config.setLevel(CacheIevel.TENANT);
            LinkedList<CacheProperties> caches = new LinkedList<CacheProperties>();
            CacheProperties tokenCachePropertie = new CacheProperties();
            tokenCachePropertie.setName(CACHE_NAME);
            tokenCachePropertie.setTtl(3600L);
            caches.add(tokenCachePropertie);
            this.config.setCaches(caches);
        }
        return this.config;
    }
}

