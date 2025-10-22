/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheIevel
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 */
package com.jiuqi.nr.portal.news2.cache;

import com.jiuqi.np.cache.config.CacheIevel;
import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PortalCacheManager
implements CacheManagerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(PortalCacheManager.class);
    public static final String PORTAL_CACHE_MANAGE = "PORTAL_CACHE_MANAGE";
    public static final String PORTAL_CACHE_MANAGE_BASE_INFO = "PORTAL_CACHE_MANAGE_BASE_INFO";
    private static volatile CacheManagerProperties cacheManagerProperties = null;

    public String getName() {
        return PORTAL_CACHE_MANAGE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CacheManagerProperties getProperties() {
        if (cacheManagerProperties != null) {
            return cacheManagerProperties;
        }
        PortalCacheManager portalCacheManager = this;
        synchronized (portalCacheManager) {
            if (cacheManagerProperties == null) {
                cacheManagerProperties = new CacheManagerProperties();
                cacheManagerProperties.setName(this.getName());
                cacheManagerProperties.setType(CacheType.rediscaffeine);
                cacheManagerProperties.setLevel(CacheIevel.SYSTEM);
            }
        }
        return cacheManagerProperties;
    }

    public static String getPortalCacheKey(String mid, String portalId, String type) {
        String key = mid + ":" + portalId + ":" + type;
        return key;
    }
}

