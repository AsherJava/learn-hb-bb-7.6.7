/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.task.form.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;

public class FormImportCacheConfiguration
implements CacheManagerConfiguration {
    public static final String NAME = "NR:FORM:IMPORT";
    private static final CacheManagerProperties PROPERTIES;

    public FormImportCacheConfiguration(CacheType cacheType) {
        PROPERTIES.setType(cacheType);
    }

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return PROPERTIES;
    }

    static {
        CacheManagerProperties properties = new CacheManagerProperties();
        properties.setName(NAME);
        properties.setType(CacheType.redis);
        properties.setTtl(7200L);
        properties.setLevel(Constants.LEVEL_DEFAULT);
        PROPERTIES = properties;
    }
}

