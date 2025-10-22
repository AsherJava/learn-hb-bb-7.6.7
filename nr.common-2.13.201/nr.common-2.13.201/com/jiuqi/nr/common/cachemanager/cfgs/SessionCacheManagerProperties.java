/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nr.common.cachemanager.cfgs;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.nr.common.cachemanager.DeployEnv;
import com.jiuqi.nr.common.cachemanager.DeployEnvCacheManagerProperties;
import org.springframework.stereotype.Component;

@Component
public class SessionCacheManagerProperties
implements DeployEnvCacheManagerProperties {
    static final String CACHEMANAGER_NAME = "session";
    private static final CacheManagerProperties PROPERTIES;

    public String getName() {
        return CACHEMANAGER_NAME;
    }

    public CacheManagerProperties getProperties() {
        return PROPERTIES;
    }

    @Override
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
        defult.setName(CACHEMANAGER_NAME);
        defult.setType(CacheType.local);
        defult.setTtl(1200L);
        defult.setLevel(CacheIevel.TENANT_SESSION);
        PROPERTIES = defult;
    }
}

