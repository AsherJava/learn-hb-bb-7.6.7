/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class TreeCacheAreaOfRedisConfig
implements CacheManagerConfiguration {
    private static final CacheManagerProperties properties = new CacheManagerProperties();
    public static final String NAME = "dim_tree_cache_2885918869385654218L";
    public static final String NAME_OF_CACHE_SET = "uselector_cache_filter_set";
    public static final String NAME_OF_CONTEXT = "uselector_cache_context";
    private static final long CACHE_SET_TTL_TIME = 1800L;

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return properties;
    }

    static {
        properties.setName(NAME);
        properties.setType(CacheType.redis);
        properties.setLevel(Constants.LEVEL_DEFAULT);
        LinkedList<CacheProperties> caches = new LinkedList<CacheProperties>();
        caches.add(new CacheProperties(NAME_OF_CACHE_SET, 1800L));
        caches.add(new CacheProperties(NAME_OF_CONTEXT, 1800L));
        properties.setCaches(caches);
    }
}

