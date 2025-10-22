/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.np.definition.internal.service;

import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class RuntimeDefinitionCache<T extends IBaseMetaItem>
extends RuntimeDefinitionCacheObject {
    public RuntimeDefinitionCache(NedisCacheManager cacheManger, Class<T> clazz) {
        if (cacheManger == null) {
            throw new IllegalArgumentException("'cacheManger' must not be null.");
        }
        this.cache = cacheManger.getCache(RuntimeDefinitionCache.createCacheName(clazz));
    }

    public void putObject(T object) {
        this.cache.put(RuntimeDefinitionCache.createObjectCacheKey(object.getKey()), object);
    }

    public void putObjects(Collection<T> objects) {
        if (objects != null && !objects.isEmpty()) {
            Map<String, Object> entries = objects.stream().collect(Collectors.toMap(t -> RuntimeDefinitionCache.createObjectCacheKey(t.getKey()), t -> t, (oldValue, newValue) -> newValue));
            this.cache.mPut(entries);
        }
    }
}

