/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.np.definition.internal.service;

import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject;

public class RuntimeDefinitionOrdinaryCache
extends RuntimeDefinitionCacheObject {
    public RuntimeDefinitionOrdinaryCache(NedisCacheManager cacheManger, Class<?> clazz) {
        if (cacheManger == null) {
            throw new IllegalArgumentException("'cacheManger' must not be null.");
        }
        this.cache = cacheManger.getCache(RuntimeDefinitionOrdinaryCache.createCacheName(clazz));
    }
}

