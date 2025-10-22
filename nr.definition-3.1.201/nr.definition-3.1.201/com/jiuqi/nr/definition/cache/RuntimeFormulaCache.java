/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.rediscaffeine.NpRedisCaffeineCache
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject
 */
package com.jiuqi.nr.definition.cache;

import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.rediscaffeine.NpRedisCaffeineCache;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class RuntimeFormulaCache<T extends IBaseMetaItem>
extends RuntimeDefinitionCache<T> {
    public RuntimeFormulaCache(NedisCacheManager cacheManger, Class<T> clazz) {
        super(cacheManger, clazz);
    }

    public void removeObjectsLocalOnly(Collection<String> objectKeys) {
        if (CollectionUtils.isEmpty(objectKeys)) {
            return;
        }
        List keys = objectKeys.stream().map(RuntimeDefinitionCacheObject::createObjectCacheKey).collect(Collectors.toList());
        if (this.cache instanceof NpRedisCaffeineCache) {
            ((NpRedisCaffeineCache)this.cache).mEvictLocalOnly(keys);
        } else {
            this.cache.mEvict(keys);
        }
    }

    public void removeIndexsLocalOnly(Collection<String> indexNames) {
        if (CollectionUtils.isEmpty(indexNames)) {
            return;
        }
        List keys = indexNames.stream().map(RuntimeDefinitionCacheObject::createIndexCacheKey).collect(Collectors.toList());
        if (this.cache instanceof NpRedisCaffeineCache) {
            ((NpRedisCaffeineCache)this.cache).mEvictLocalOnly(keys);
        } else {
            this.cache.mEvict(keys);
        }
    }
}

