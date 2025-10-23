/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nr.print.config;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;

public class ReportPrintDesignConfig
implements CacheManagerConfiguration {
    public static final String NAME = "nr-print-designer";
    private static final CacheManagerProperties PROPERTIES;

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return PROPERTIES;
    }

    static {
        CacheManagerProperties properties = new CacheManagerProperties();
        properties.setName(NAME);
        properties.setType(CacheType.local);
        properties.setLevel(CacheIevel.SYSTEM);
        properties.setTtl(172800L);
        PROPERTIES = properties;
    }
}

