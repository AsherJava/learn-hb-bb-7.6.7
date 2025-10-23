/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.singlequeryimport.service.cache.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import org.springframework.stereotype.Component;

@Component
public class GroupSetQueryResultCacheConfiguration
implements CacheManagerConfiguration {
    public static final String NAME = "BMJS_FINALACCOUNT_QUERY";
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
        defult.setTtl(1800L);
        defult.setLevel(Constants.LEVEL_DEFAULT);
        PROPERTIES = defult;
    }
}

