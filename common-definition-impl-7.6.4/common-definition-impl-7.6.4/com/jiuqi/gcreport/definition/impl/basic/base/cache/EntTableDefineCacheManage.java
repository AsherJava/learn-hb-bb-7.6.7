/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider
 */
package com.jiuqi.gcreport.definition.impl.basic.base.cache;

import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class EntTableDefineCacheManage {
    public static final String CACHE_KEY_DEFINE_MAIN_CACHE = "KEY_DEFINE_MAIN_CACHE";
    public static final String CACHE_KEY_DEFINE_TABLE = "KEY_DEFINE_TABLE";
    private static final Logger logger = LoggerFactory.getLogger(EntTableDefineCacheManage.class);
    private NedisCacheManager manager = DefaultCacheProvider.getCacheManager((String)"KEY_DEFINE_MAIN_CACHE");
    private IEntTableDefineProvider provider;

    public void addTableCache(EntTableDefine table) {
        NedisCache cache = this.manager.getCache(CACHE_KEY_DEFINE_TABLE);
        String key = table.getCode().toUpperCase();
        cache.put(key, (Object)table);
    }

    public EntTableDefine getTable(String tableName) {
        NedisCache cache = this.manager.getCache(CACHE_KEY_DEFINE_TABLE);
        String key = tableName.toUpperCase();
        Cache.ValueWrapper valueWrapper = cache.get(key);
        EntTableDefine table = null;
        if (valueWrapper != null) {
            table = (EntTableDefine)valueWrapper.get();
        }
        if (table == null) {
            // empty if block
        }
        return table;
    }

    public void clearOneKey(String cacheName, String key) {
        NedisCache cache = this.manager.getCache(cacheName);
        cache.evict(key);
    }

    public void clearCache(String cacheName) {
        this.manager.getCache(cacheName).clear();
    }

    public void clearAllCache() {
        this.manager.getCacheNames().forEach(v -> this.manager.getCache(v).clear());
    }
}

