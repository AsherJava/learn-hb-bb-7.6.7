/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.nr.common.cachemanager.DeployEnvCacheManagerProperties
 */
package com.jiuqi.nr.definition.config;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.nr.common.cachemanager.DeployEnvCacheManagerProperties;

public class NrDefinitionCacheConfiguration
implements DeployEnvCacheManagerProperties {
    public static final String NAME = "NR_DEFINITION_CACHE_CONFIGURATION";
    public static final CacheManagerProperties PROPERTIES;

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
        defult.setTtl(0L);
        PROPERTIES = defult;
    }
}

