/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.va.query.cache;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import org.springframework.stereotype.Component;

@Component
public class QueryCacheManageConfig
implements CacheManagerConfiguration {
    private static final CacheManagerProperties PROPERTIES;

    public String getName() {
        return "DC_QUERY_MANAGE";
    }

    public CacheManagerProperties getProperties() {
        return PROPERTIES;
    }

    static {
        CacheManagerProperties defaultProperties = new CacheManagerProperties();
        defaultProperties.setName("DC_QUERY_MANAGE");
        defaultProperties.setType(CacheType.rediscaffeine);
        defaultProperties.setTtl(86400L);
        defaultProperties.setLevel(Constants.LEVEL_DEFAULT);
        PROPERTIES = defaultProperties;
    }
}

