/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 */
package com.jiuqi.nr.todo.cache;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import org.springframework.stereotype.Component;

@Component
public class TodoCacheManagerConfiguration
implements CacheManagerConfiguration {
    private volatile CacheManagerProperties cacheManagerProperties;

    public String getName() {
        return "todo_cache_manager";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CacheManagerProperties getProperties() {
        if (this.cacheManagerProperties == null) {
            TodoCacheManagerConfiguration todoCacheManagerConfiguration = this;
            synchronized (todoCacheManagerConfiguration) {
                if (this.cacheManagerProperties == null) {
                    this.cacheManagerProperties = new CacheManagerProperties();
                    this.cacheManagerProperties.setName(this.getName());
                    this.cacheManagerProperties.setLevel(CacheIevel.SYSTEM);
                }
            }
        }
        return this.cacheManagerProperties;
    }
}

