/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 */
package com.jiuqi.nr.definition.cache;

import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;

public class RuntimeDefinitionPrivateCache<T extends IBaseMetaItem>
extends RuntimeDefinitionCache {
    public RuntimeDefinitionPrivateCache(NedisCacheManager cacheManger, Class<T> clazz) {
        super(cacheManger, clazz);
        if (cacheManger == null) {
            throw new IllegalArgumentException("'cacheManger' must not be null.");
        }
        this.cache = cacheManger.getCache(RuntimeDefinitionPrivateCache.createCacheName(clazz).concat("::private"));
    }
}

