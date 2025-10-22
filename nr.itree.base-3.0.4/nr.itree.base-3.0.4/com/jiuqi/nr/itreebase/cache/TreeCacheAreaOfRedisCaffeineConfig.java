/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class TreeCacheAreaOfRedisCaffeineConfig
implements CacheManagerConfiguration {
    private static final CacheManagerProperties properties = new CacheManagerProperties();
    public static final String name = "dim_tree_cache_8070815796873628132L";
    private static final long cacheSet_ttl_time = 86400L;

    public String getName() {
        return name;
    }

    public CacheManagerProperties getProperties() {
        return properties;
    }

    static {
        properties.setName(name);
        properties.setType(CacheType.rediscaffeine);
        properties.setLevel(Constants.LEVEL_DEFAULT);
        LinkedList caches = new LinkedList();
        properties.setCaches(caches);
    }
}

