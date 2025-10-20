/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import org.springframework.stereotype.Component;

@Component
public class ReportCatalogCacheConfiguration
implements CacheManagerConfiguration {
    public static final String MANAGE_NAME = "report_catalog_cache_manager";
    private CacheManagerProperties config = new CacheManagerProperties();

    public ReportCatalogCacheConfiguration() {
        this.config.setName(MANAGE_NAME);
        this.config.setTtl(1800L);
        this.config.setType(CacheType.redis);
    }

    public String getName() {
        return MANAGE_NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.config;
    }
}

