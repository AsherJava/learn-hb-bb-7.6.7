/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 *  com.jiuqi.nr.common.cachemanager.DeployEnv
 *  com.jiuqi.nr.common.cachemanager.DeployEnvCacheManagerProperties
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import com.jiuqi.nr.common.cachemanager.DeployEnv;
import com.jiuqi.nr.common.cachemanager.DeployEnvCacheManagerProperties;
import org.springframework.stereotype.Component;

@Component
public class LocalCacheManagerConfiguration
implements DeployEnvCacheManagerProperties {
    public static final String NAME = "LOCAL_QUERY";
    private static final CacheManagerProperties PROPERTIES;

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return PROPERTIES;
    }

    public void setProperties(CacheManagerProperties cmp, DeployEnv env) {
        switch (env) {
            case SINGLE: {
                cmp.setType(CacheType.local);
                break;
            }
            case CLUSTER: {
                cmp.setType(CacheType.redis);
                break;
            }
            case SAAS: {
                cmp.setType(CacheType.redis);
                break;
            }
        }
    }

    static {
        CacheManagerProperties defult = new CacheManagerProperties();
        defult.setName(NAME);
        defult.setType(CacheType.local);
        defult.setTtl(3600L);
        defult.setLevel(Constants.LEVEL_DEFAULT);
        PROPERTIES = defult;
    }
}

