/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.datascheme.api.core.Basic
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.internal.service.IBaseSchemeCache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;
import org.springframework.cache.support.NullValue;

public class BaseRuntimeSchemeCache<T extends Basic>
implements IBaseSchemeCache<T> {
    protected static final String CACHE_NAME_PREFIX = ":scheme:runtime:";
    protected static final String OBJECT_CACHE_KEY_PREFIX = "obj_";
    protected static final String INDEX_CACHE_KEY_PREFIX = "idx_";
    protected NedisCache cache;
    private final String defaultKey = "defaultKey";
    protected static final String EMPTY = "empty";

    public BaseRuntimeSchemeCache(NedisCacheProvider cacheProvider, Class<T> clazz) {
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.cache = cacheProvider.getCacheManager("nr:scheme:runtime").getCache(clazz.getSimpleName());
    }

    public static String createObjectCacheKey(String objectKey) {
        if (objectKey == null) {
            throw new IllegalArgumentException("'objectKey' must not be null.");
        }
        return OBJECT_CACHE_KEY_PREFIX.concat(objectKey);
    }

    @Override
    public void put(T object) {
        this.cache.put(BaseRuntimeSchemeCache.createObjectCacheKey(object.getKey()), object);
    }

    @Override
    public void puts(Collection<T> objects) {
        if (objects != null && !objects.isEmpty()) {
            Map<String, Object> entries = objects.stream().collect(Collectors.toMap(t -> BaseRuntimeSchemeCache.createObjectCacheKey(t.getKey()), t -> t, (oldValue, newValue) -> newValue));
            this.cache.mPut(entries);
        }
    }

    public void putNullObject(String objectKey) {
        if (objectKey == null) {
            throw new IllegalArgumentException("'objectKey' must not be null.");
        }
        this.cache.put(BaseRuntimeSchemeCache.createObjectCacheKey(objectKey), NullValue.INSTANCE);
    }

    @Override
    public Cache.ValueWrapper get(String objectKey) {
        return this.cache.get(BaseRuntimeSchemeCache.createObjectCacheKey(objectKey));
    }

    @Override
    public Map<String, Cache.ValueWrapper> gets(Collection<String> objectKeys) {
        if (objectKeys == null || objectKeys.isEmpty()) {
            return Collections.emptyMap();
        }
        ArrayList<String> keyList = new ArrayList<String>(objectKeys);
        List keys = objectKeys.stream().map(BaseRuntimeSchemeCache::createObjectCacheKey).collect(Collectors.toList());
        List cachedValues = this.cache.mGet(keys);
        LinkedHashMap<String, Cache.ValueWrapper> objectMap = new LinkedHashMap<String, Cache.ValueWrapper>(keyList.size());
        for (int i = 0; i < objectKeys.size(); ++i) {
            objectMap.put((String)keyList.get(i), (Cache.ValueWrapper)cachedValues.get(i));
        }
        return objectMap;
    }

    public void removeObjects(Collection<String> objectKeys) {
        if (objectKeys == null || objectKeys.isEmpty()) {
            return;
        }
        this.cache.mEvict((Collection)objectKeys.stream().map(BaseRuntimeSchemeCache::createObjectCacheKey).collect(Collectors.toList()));
    }

    public static String createIndexCacheKey(String indexName) {
        if (indexName == null) {
            throw new IllegalArgumentException("'indexName' must not be null.");
        }
        return INDEX_CACHE_KEY_PREFIX.concat(indexName);
    }

    @Override
    public void putHashIndex(String indexName, Map<?, ?> indexEntries) {
        if (indexEntries != null && !indexEntries.isEmpty()) {
            this.cache.hMSet(BaseRuntimeSchemeCache.createIndexCacheKey(indexName), indexEntries);
        } else {
            HashMap<String, String> entries = new HashMap<String, String>(1);
            entries.put("defaultKey", "nullValue");
            this.cache.hMSet(BaseRuntimeSchemeCache.createIndexCacheKey(indexName), entries);
        }
    }

    @Override
    public void putHashIndexEntry(String indexName, Object indexKey, Object indexValue) {
        if (indexKey == null) {
            throw new IllegalArgumentException("'indexKey' must not be null.");
        }
        this.cache.hSet(BaseRuntimeSchemeCache.createIndexCacheKey(indexName), indexKey, indexValue);
    }

    @Override
    public HashCacheValue<Cache.ValueWrapper> getHashIndexValue(String indexName, Object indexKey) {
        return this.cache.hGetIfExists(BaseRuntimeSchemeCache.createIndexCacheKey(indexName), indexKey);
    }

    @Override
    public HashCacheValue<List<Cache.ValueWrapper>> mGetHashIndexValue(String indexName, List<Object> indexKeys) {
        if (indexKeys == null || indexKeys.isEmpty()) {
            return HashCacheValue.valueOf(Collections.emptyList());
        }
        HashCacheValue cacheValue = this.cache.hMGetIfExists(BaseRuntimeSchemeCache.createIndexCacheKey(indexName), indexKeys);
        if (cacheValue.isPresent()) {
            List res = (List)cacheValue.get();
            return HashCacheValue.valueOf((Object)res);
        }
        return cacheValue;
    }

    @Override
    public HashCacheValue<Map<Object, Object>> mGetHashIndexValue(String indexName) {
        HashCacheValue cacheValue = this.cache.hGetAllIfExists(BaseRuntimeSchemeCache.createIndexCacheKey(indexName));
        if (cacheValue.isPresent()) {
            Map<Object, Object> res = (Map<Object, Object>)cacheValue.get();
            res = this.removeEmptyKey(res);
            return HashCacheValue.valueOf(res);
        }
        return cacheValue;
    }

    private Map<Object, Object> removeEmptyKey(Map<Object, Object> res) {
        if (res.containsKey("defaultKey")) {
            return Collections.emptyMap();
        }
        return res;
    }

    private Set<Object> removeEmptyKey(Set<Object> res) {
        if (res.contains("defaultKey")) {
            return Collections.emptySet();
        }
        return res;
    }

    @Override
    public HashCacheValue<Set<Object>> getHashIndexKeys(String indexName) {
        HashCacheValue cacheValue = this.cache.hKeysIfExists(BaseRuntimeSchemeCache.createIndexCacheKey(indexName));
        if (cacheValue.isPresent()) {
            Set<Object> res = (Set<Object>)cacheValue.get();
            res = this.removeEmptyKey(res);
            return HashCacheValue.valueOf(res);
        }
        return cacheValue;
    }

    @Override
    public void putKVIndex(String indexName, Object indexValue) {
        this.cache.put(BaseRuntimeSchemeCache.createIndexCacheKey(indexName), indexValue);
    }

    @Override
    public void mPutKVIndex(Map<String, T> indexEntries) {
        LinkedHashMap<String, T> indexName2Values = new LinkedHashMap<String, T>(indexEntries.size());
        for (Map.Entry<String, T> entry : indexEntries.entrySet()) {
            indexName2Values.put(BaseRuntimeSchemeCache.createIndexCacheKey(entry.getKey()), entry.getValue());
        }
        this.cache.mPut(indexName2Values);
    }

    @Override
    public Cache.ValueWrapper getKVIndexValue(String indexName) {
        return this.cache.get(BaseRuntimeSchemeCache.createIndexCacheKey(indexName));
    }

    @Override
    public List<Cache.ValueWrapper> mGetKVIndexValue(List<String> indexNames) {
        if (indexNames == null || indexNames.isEmpty()) {
            return Collections.emptyList();
        }
        List indexKeys = indexNames.stream().map(BaseRuntimeSchemeCache::createIndexCacheKey).collect(Collectors.toList());
        return this.cache.mGet(indexKeys);
    }

    @Override
    public void removeIndexes(Collection<String> indexNames) {
        if (indexNames == null || indexNames.isEmpty()) {
            return;
        }
        this.cache.mEvict((Collection)indexNames.stream().map(BaseRuntimeSchemeCache::createIndexCacheKey).collect(Collectors.toList()));
    }

    @Override
    public void removeIndexEntry(String indexName, Object ... hashKeys) {
        if (indexName == null || hashKeys == null) {
            return;
        }
        this.cache.hDel(BaseRuntimeSchemeCache.createIndexCacheKey(indexName), hashKeys);
    }

    public void clear() {
        this.cache.clear();
    }
}

