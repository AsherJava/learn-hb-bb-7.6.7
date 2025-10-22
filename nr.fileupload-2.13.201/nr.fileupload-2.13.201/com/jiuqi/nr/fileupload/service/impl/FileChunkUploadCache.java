/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.nr.fileupload.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.fileupload.FileChunkCacheInfo;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class FileChunkUploadCache
implements Serializable {
    private NedisCacheManager cacheManager;
    private static final String CACHENAME = "FILE_CHUNK_UPLOAD";
    public static final int LOCK_EXPIRE = 1000;
    @Autowired(required=false)
    private RedisTemplate<Object, Object> redisTemplate;
    private ConcurrentHashMap<Integer, ReentrantLock> lockMap = null;
    private static final int LOCK_COUNT = 16;

    @Autowired
    public void setCacheManager(NedisCacheProvider sessionCacheProvider) {
        this.cacheManager = sessionCacheProvider.getCacheManager("FILE_UPLOAD");
    }

    public void saveChunkCache(String key, FileChunkCacheInfo fileChunkCacheInfo) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        caffeineCache.put(key, (Object)fileChunkCacheInfo);
    }

    public FileChunkCacheInfo getChunkCache(String key) {
        if (StringUtils.isEmpty((String)key)) {
            return null;
        }
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        Cache.ValueWrapper valueWrapper = caffeineCache.get(key);
        if (valueWrapper != null) {
            FileChunkCacheInfo chunkCache = (FileChunkCacheInfo)valueWrapper.get();
            return chunkCache;
        }
        return null;
    }

    public void clearChunkCache(String key) {
        NedisCache caffeineCache = this.cacheManager.getCache(CACHENAME);
        if (StringUtils.isEmpty((String)key) || null == caffeineCache.get(key)) {
            return;
        }
        caffeineCache.evict(key);
    }

    public boolean lock(String key) {
        if (null != this.redisTemplate) {
            String lock = CACHENAME + key;
            return (Boolean)this.redisTemplate.execute(connection -> {
                long expireTime;
                long expireAt = System.currentTimeMillis() + 1000L + 1L;
                Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
                if (acquire.booleanValue()) {
                    return true;
                }
                byte[] value = connection.get(lock.getBytes());
                if (Objects.nonNull(value) && value.length > 0 && (expireTime = Long.parseLong(new String(value))) < System.currentTimeMillis()) {
                    byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + 1000L + 1L).getBytes());
                    return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                }
                return false;
            });
        }
        if (this.lockMap == null) {
            this.lockMap = new ConcurrentHashMap(16);
            for (int i = 0; i < 16; ++i) {
                this.lockMap.put(i, new ReentrantLock());
            }
        }
        return this.lockMap.get(key.hashCode() >>> 1).tryLock();
    }

    public void unLock(String key) {
        if (null != this.redisTemplate) {
            this.redisTemplate.delete((Object)key);
        } else {
            if (this.lockMap == null) {
                this.lockMap = new ConcurrentHashMap(16);
                for (int i = 0; i < 16; ++i) {
                    this.lockMap.put(i, new ReentrantLock());
                }
            }
            this.lockMap.get(key.hashCode() >>> 1).unlock();
        }
    }
}

