/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.Constants
 */
package nr.single.para.config;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.Constants;
import org.springframework.stereotype.Component;

@Component
public class SingleCacheConfig
implements CacheManagerConfiguration {
    public static final String NAME = "SINGLE_MAPPING_CACHE";
    private CacheManagerProperties cacheManagerProperties = new CacheManagerProperties();

    public SingleCacheConfig() {
        this.cacheManagerProperties.setName(NAME);
        this.cacheManagerProperties.setTtl(360L);
        this.cacheManagerProperties.setLevel(Constants.LEVEL_DEFAULT);
        this.cacheManagerProperties.setType(CacheType.rediscaffeine);
    }

    public String getName() {
        return NAME;
    }

    public CacheManagerProperties getProperties() {
        return this.cacheManagerProperties;
    }
}

