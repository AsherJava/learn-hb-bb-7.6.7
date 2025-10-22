/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.gcreport.org.impl.cache.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import org.springframework.stereotype.Component;

@Component
public class GcOrgRedisCaffeineCacheConfig
implements CacheManagerConfiguration {
    private static final CacheManagerProperties PROPERTIES;

    public String getName() {
        return "cachemanage_gcreport_orgdata_caffeine";
    }

    public CacheManagerProperties getProperties() {
        return PROPERTIES;
    }

    static {
        CacheManagerProperties defult = new CacheManagerProperties();
        defult.setName("cachemanage_gcreport_orgdata_caffeine");
        defult.setType(CacheType.rediscaffeine);
        defult.setTtl(86400L);
        defult.setLevel(Constants.LEVEL_DEFAULT);
        PROPERTIES = defult;
    }
}

