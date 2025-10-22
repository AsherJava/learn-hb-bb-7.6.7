/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package com.jiuqi.nr.attachment.transfer.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import org.springframework.stereotype.Component;

@Component
public class TransferCacheConfiguration
implements CacheManagerConfiguration {
    public static final String NAME = "ATTACHMENT_TRANSFER_CACHE";
    public static final String CACHE_NAME = "FILE_TOKEN";
    private CacheManagerProperties cacheManagerProperties = new CacheManagerProperties();

    public TransferCacheConfiguration() {
        this.cacheManagerProperties.setName(NAME);
        this.cacheManagerProperties.setTtl(86400L);
        this.cacheManagerProperties.setLevel(Constants.LEVEL_DEFAULT);
        this.cacheManagerProperties.setType(CacheType.redis);
    }

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.cacheManagerProperties;
    }
}

