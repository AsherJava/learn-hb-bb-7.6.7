/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataFieldDefaultValueCache
implements ApplicationListener<DataSchemeDeployEvent> {
    private static final String CACHE_NAME = "datafield:dbdefaultvalue";
    private NedisCache cache;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager("nr:scheme").getCache(CACHE_NAME);
    }

    public void setDefaultValue(String dataSchemeKey, String dataFieldKey, String value) {
        this.cache.hSet(dataSchemeKey, (Object)dataFieldKey, (Object)value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getDefaultValue(String dataSchemeKey, String dataFieldKey) {
        if (Objects.isNull(dataSchemeKey) || Objects.isNull(dataFieldKey)) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.cache.hGet(dataSchemeKey, (Object)dataFieldKey);
        if (valueWrapper != null) {
            return (String)valueWrapper.get();
        }
        DataFieldDefaultValueCache dataFieldDefaultValueCache = this;
        synchronized (dataFieldDefaultValueCache) {
            Cache.ValueWrapper wrapper = this.cache.hGet(dataSchemeKey, (Object)dataFieldKey);
            if (wrapper != null) {
                return (String)wrapper.get();
            }
            this.cache.hSet(dataSchemeKey, (Object)dataFieldKey, null);
            return null;
        }
    }

    public void clear(String dataSchemeKey, String dataFieldKey) {
        this.cache.hDel(dataSchemeKey, new Object[]{dataFieldKey});
    }

    public void clear(String dataSchemeKey) {
        this.cache.evict(dataSchemeKey);
    }

    @Override
    public void onApplicationEvent(DataSchemeDeployEvent event) {
        this.clear(event.getSource().getDataSchemeKey());
    }
}

