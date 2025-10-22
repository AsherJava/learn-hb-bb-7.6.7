/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nr.formtype.config;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;

public class FormTypeCacheConfig
implements CacheManagerConfiguration {
    public static final String NAME = "nr:param:formtype";
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
        defult.setType(CacheType.rediscaffeine);
        defult.setLevel(CacheIevel.SYSTEM);
        defult.setTtl(86400L);
        PROPERTIES = defult;
    }
}

