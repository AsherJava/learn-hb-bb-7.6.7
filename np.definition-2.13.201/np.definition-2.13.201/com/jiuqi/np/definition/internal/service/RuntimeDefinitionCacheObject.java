/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.np.cache.NedisCache
 */
package com.jiuqi.np.definition.internal.service;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.np.cache.NedisCache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;

public class RuntimeDefinitionCacheObject {
    static final String CACHE_NAME_PREFIX = "::definition::runtime::";
    static final String OBJECT_CACHE_KEY_PREFIX = "obj_";
    static final String INDEX_CACHE_KEY_PREFIX = "idx_";
    protected NedisCache cache;
    private final Object cacheLock = new Object();

    public static String createCacheName(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("'clazz' must not be null.");
        }
        String className = clazz.getName();
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1).toLowerCase();
        String pre = "np";
        String pkgName = clazz.getPackage().getName();
        String[] arr = pkgName.split("\\.");
        if (arr.length > 2) {
            pre = arr[2];
        }
        return pre + CACHE_NAME_PREFIX.concat(simpleClassName);
    }

    public static String createObjectCacheKey(String objectKey) {
        if (objectKey == null) {
            throw new IllegalArgumentException("'objectKey' must not be null.");
        }
        return OBJECT_CACHE_KEY_PREFIX.concat(objectKey.toString());
    }

    public void putNullObject(String objectKey) {
        if (objectKey == null) {
            throw new IllegalArgumentException("'objectKey' must not be null.");
        }
        this.cache.put(RuntimeDefinitionCacheObject.createObjectCacheKey(objectKey), null);
    }

    public Cache.ValueWrapper getObject(String objectKey) {
        return this.cache.get(RuntimeDefinitionCacheObject.createObjectCacheKey(objectKey));
    }

    public Map<String, Cache.ValueWrapper> getObjects(Collection<String> objectKeys) {
        if (objectKeys == null || objectKeys.isEmpty()) {
            return Collections.emptyMap();
        }
        ArrayList<String> keyList = new ArrayList<String>(objectKeys);
        List keys = objectKeys.stream().map(t -> RuntimeDefinitionCacheObject.createObjectCacheKey(t)).collect(Collectors.toList());
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
        this.cache.mEvict((Collection)objectKeys.stream().map(t -> RuntimeDefinitionCacheObject.createObjectCacheKey(t)).collect(Collectors.toList()));
    }

    public static String createIndexCacheKey(String indexName) {
        if (indexName == null) {
            throw new IllegalArgumentException("'indexName' must not be null.");
        }
        return INDEX_CACHE_KEY_PREFIX.concat(indexName);
    }

    public void putHashIndex(String indexName, Map<? extends Object, ? extends Object> indexEntries) {
        if (indexEntries != null && !indexEntries.isEmpty()) {
            this.cache.hMSet(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName), indexEntries);
        } else {
            HashMap<String, String> entrys = new HashMap<String, String>();
            entrys.put("defaultKey", "nullValue");
            this.cache.hMSet(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName), entrys);
        }
    }

    public void putHashIndexEntry(String indexName, Object indexKey, Object indexValue) {
        if (indexKey == null) {
            throw new IllegalArgumentException("'indexKey' must not be null.");
        }
        this.cache.hSet(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName), indexKey, indexValue);
    }

    public HashCacheValue<Cache.ValueWrapper> getHashIndexValue(String indexName, Object indexKey) {
        return this.cache.hGetIfExists(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName), indexKey);
    }

    public HashCacheValue<List<Cache.ValueWrapper>> mGetHashIndexValue(String indexName, List<Object> indexKeys) {
        if (indexKeys == null || indexKeys.isEmpty()) {
            return HashCacheValue.valueOf(Collections.emptyList());
        }
        HashCacheValue cacheValue = this.cache.hMGetIfExists(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName), indexKeys);
        if (cacheValue.isPresent()) {
            List res = (List)cacheValue.get();
            return HashCacheValue.valueOf((Object)res);
        }
        return cacheValue;
    }

    public HashCacheValue<Map<Object, Object>> mGetHashIndexValue(String indexName) {
        HashCacheValue cacheValue = this.cache.hGetAllIfExists(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName));
        if (cacheValue.isPresent()) {
            Map<Object, Object> res = (Map<Object, Object>)cacheValue.get();
            res = this.removeEmptyKey(res);
            return HashCacheValue.valueOf(res);
        }
        return cacheValue;
    }

    private Map<Object, Object> removeEmptyKey(Map<Object, Object> res) {
        if (res.containsKey("defaultKey")) {
            return new HashMap<Object, Object>();
        }
        return res;
    }

    private Set<Object> removeEmptyKey(Set<Object> res) {
        if (res.contains("defaultKey")) {
            return new HashSet<Object>();
        }
        return res;
    }

    public HashCacheValue<Set<Object>> getHashIndexKeys(String indexName) {
        HashCacheValue cacheValue = this.cache.hKeysIfExists(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName));
        if (cacheValue.isPresent()) {
            Set<Object> res = (Set<Object>)cacheValue.get();
            res = this.removeEmptyKey(res);
            return HashCacheValue.valueOf(res);
        }
        return cacheValue;
    }

    public void putKVIndex(String indexName, Object indexValue) {
        this.cache.put(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName), indexValue);
    }

    public void mPutKVIndex(Map<String, ? extends Object> indexEntries) {
        LinkedHashMap<String, Object> indexName2Values = new LinkedHashMap<String, Object>(indexEntries.size());
        for (Map.Entry<String, ? extends Object> entry : indexEntries.entrySet()) {
            indexName2Values.put(RuntimeDefinitionCacheObject.createIndexCacheKey(entry.getKey()), entry.getValue());
        }
        this.cache.mPut(indexName2Values);
    }

    public Cache.ValueWrapper getKVIndexValue(String indexName) {
        return this.cache.get(RuntimeDefinitionCacheObject.createIndexCacheKey(indexName));
    }

    public List<Cache.ValueWrapper> mGetKVIndexValue(List<String> indexNames) {
        if (indexNames == null || indexNames.isEmpty()) {
            return Collections.emptyList();
        }
        List indexKeys = indexNames.stream().map(t -> RuntimeDefinitionCacheObject.createIndexCacheKey(t)).collect(Collectors.toList());
        return this.cache.mGet(indexKeys);
    }

    public void removeIndexs(Collection<String> indexNames) {
        if (indexNames == null || indexNames.isEmpty()) {
            return;
        }
        this.cache.mEvict((Collection)indexNames.stream().map(t -> RuntimeDefinitionCacheObject.createIndexCacheKey(t)).collect(Collectors.toList()));
    }

    public void clear() {
        this.cache.clear();
    }

    public <R> R synchronizedRun(Supplier<R> runner) {
        return RuntimeDefinitionCacheObject.synchronizedRun(this.cacheLock, runner);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void synchronizedRun(String key, Runnable runner) {
        Object object = this.cacheLock;
        synchronized (object) {
            runner.run();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <R> R synchronizedRun(Object lock, Supplier<R> runner) {
        Object object = lock;
        synchronized (object) {
            return runner.get();
        }
    }
}

