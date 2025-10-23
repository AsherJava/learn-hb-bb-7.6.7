/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.datascheme.fix.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;

public class DataSchemeFixCacheManagerConfiguration
implements CacheManagerConfiguration {
    public static final String NAME = "nr:schemefix";
    private static final CacheManagerProperties PROPERTIES;

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return PROPERTIES;
    }

    static {
        CacheManagerProperties defult = new CacheManagerProperties();
        defult.setName(NAME);
        defult.setType(CacheType.redis);
        defult.setTtl(7200L);
        defult.setLevel(Constants.LEVEL_DEFAULT);
        PROPERTIES = defult;
    }
}

