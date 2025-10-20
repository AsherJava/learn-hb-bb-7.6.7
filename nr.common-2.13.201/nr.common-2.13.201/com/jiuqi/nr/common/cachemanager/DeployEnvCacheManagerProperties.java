/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nr.common.cachemanager;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.nr.common.cachemanager.DeployEnv;

public interface DeployEnvCacheManagerProperties
extends CacheManagerConfiguration {
    default public void setProperties(CacheManagerProperties cmp, DeployEnv env) {
        switch (env) {
            case SINGLE: {
                cmp.setType(CacheType.local);
                break;
            }
            case CLUSTER: {
                cmp.setType(CacheType.rediscaffeine);
                break;
            }
            case SAAS: {
                cmp.setType(CacheType.redis);
                break;
            }
        }
    }
}

