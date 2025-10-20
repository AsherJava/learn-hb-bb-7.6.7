/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.internal.redis.NpRedisCache
 *  com.jiuqi.np.cache.internal.redis.NpRedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider
 */
package com.jiuqi.common.base.util;

import com.jiuqi.np.cache.internal.redis.NpRedisCache;
import com.jiuqi.np.cache.internal.redis.NpRedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    public static final String CACHENAME_GCCOMMON = "COMMON_REDISTEMPLATE_BEAN_NAME";
    private NpRedisCacheManager manager;
    public static final long DEFAULT_EXPIRE = 86400L;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    public NpRedisCacheManager getManager() {
        if (this.manager == null) {
            this.manager = (NpRedisCacheManager)DefaultCacheProvider.getCacheManager((String)CACHENAME_GCCOMMON);
        }
        return this.manager;
    }

    private NpRedisCache getCache(String key) {
        return this.getCache(key, -1L);
    }

    private NpRedisCache getCache(String key, long time) {
        if (this.manager == null) {
            this.manager = (NpRedisCacheManager)DefaultCacheProvider.getCacheManager((String)CACHENAME_GCCOMMON);
        }
        if (time > 0L) {
            // empty if block
        }
        return (NpRedisCache)this.getManager().getCache(key);
    }

    public boolean hasKey(String key) {
        return this.getCache(CACHENAME_GCCOMMON).exists(key);
    }

    public void del(String ... keys) {
        if (keys != null && keys.length > 0) {
            this.getCache(CACHENAME_GCCOMMON).mEvict(Arrays.asList(keys));
        }
    }

    public Object get(String key) {
        Cache.ValueWrapper valueWrapper = this.getCache(CACHENAME_GCCOMMON).get(key);
        if (valueWrapper != null) {
            return valueWrapper.get();
        }
        return null;
    }

    public boolean set(String key, Object value) {
        this.getCache(CACHENAME_GCCOMMON).put(key, value);
        return true;
    }

    public boolean set(String key, Object value, long time) {
        return this.set(key, value);
    }

    public Object hGet(String key, String item) {
        Cache.ValueWrapper value = this.getCache(CACHENAME_GCCOMMON).hGet(key, (Object)item);
        if (value != null) {
            return value.get();
        }
        return null;
    }

    public Map<Object, Object> hmGet(String key) {
        return this.getCache(CACHENAME_GCCOMMON).hGetAll(key);
    }

    public boolean hmSet(String key, Map<Object, Object> map) {
        this.getCache(CACHENAME_GCCOMMON).hMSet(key, map);
        return true;
    }

    public boolean hmSet(String key, Map<Object, Object> map, long time) {
        return this.hmSet(key, map);
    }

    public boolean hSet(String key, String item, Object value) {
        this.getCache(CACHENAME_GCCOMMON).hSet(key, (Object)item, value);
        return true;
    }

    public boolean hSet(String key, String item, Object value, long time) {
        return this.hSet(key, item, value);
    }

    public void hDel(String key, Object ... item) {
        this.getCache(CACHENAME_GCCOMMON).hDel(key, item);
    }

    public Set<Object> keys(String key) {
        Set keys = this.getCache(CACHENAME_GCCOMMON).hKeys(key);
        return keys;
    }
}

