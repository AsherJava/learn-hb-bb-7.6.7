/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 */
package com.jiuqi.nr.configuration.config;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class SystemOptionCacheManagerConfiguration
implements CacheManagerConfiguration {
    private volatile CacheManagerProperties cacheManagerProperties;
    public static final String CACHENAME = "SYSTEM_OPTION";

    public String getName() {
        return CACHENAME;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CacheManagerProperties getProperties() {
        if (this.cacheManagerProperties == null) {
            SystemOptionCacheManagerConfiguration systemOptionCacheManagerConfiguration = this;
            synchronized (systemOptionCacheManagerConfiguration) {
                if (this.cacheManagerProperties == null) {
                    this.cacheManagerProperties = new CacheManagerProperties();
                    this.cacheManagerProperties.setName(this.getName());
                    this.cacheManagerProperties.setLevel(CacheIevel.SYSTEM);
                }
            }
        }
        return this.cacheManagerProperties;
    }

    public static String getCacheKey(String key, String taskKey, String formSchemeKey) {
        StringBuilder cacheKey = new StringBuilder();
        cacheKey.append(key);
        if (taskKey != null) {
            cacheKey.append("_").append(taskKey);
            if (formSchemeKey != null) {
                cacheKey.append("_").append(formSchemeKey);
            }
        }
        return cacheKey.toString();
    }
}

