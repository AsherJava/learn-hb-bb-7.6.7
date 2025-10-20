/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.paramsync.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.multipart.MultipartFile;

public class VaParamSyncCacheUtil {
    private static final long DEFUALT_VALIDITY_TIME = 2L;
    private static RedisTemplate<String, byte[]> fileRedisTemplate;
    private static Cache<String, byte[]> fileCache;
    private static StringRedisTemplate stringRedisTemplate;
    private static Cache<String, String> importDataResultCache;

    public static void initRedisTemplate() {
        if (fileRedisTemplate == null) {
            fileRedisTemplate = (RedisTemplate)ApplicationContextRegister.getBean((String)"VaParamSyncRedisTemplate");
        }
        if (stringRedisTemplate == null) {
            stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
        }
    }

    public static void setFileCache(String key, MultipartFile multipartFile) throws IOException {
        byte[] value = multipartFile.getBytes();
        if (VaParamSyncConfig.isRedisEnable()) {
            VaParamSyncCacheUtil.initRedisTemplate();
            fileRedisTemplate.opsForValue().set((Object)key, (Object)value, 2L, TimeUnit.HOURS);
        } else {
            if (fileCache == null) {
                fileCache = Caffeine.newBuilder().initialCapacity(1).maximumSize(100L).expireAfterAccess(2L, TimeUnit.HOURS).build();
            }
            fileCache.put((Object)key, (Object)value);
        }
    }

    public static boolean refreshFileCacheTime(String key) {
        if (VaParamSyncConfig.isRedisEnable()) {
            VaParamSyncCacheUtil.initRedisTemplate();
            if (Boolean.TRUE.equals(fileRedisTemplate.hasKey((Object)key))) {
                fileRedisTemplate.expire((Object)key, 2L, TimeUnit.HOURS);
                return true;
            }
        } else if (fileCache != null) {
            return fileCache.getIfPresent((Object)key) != null;
        }
        return false;
    }

    public static byte[] getFileCache(String key) {
        byte[] rs = null;
        if (VaParamSyncConfig.isRedisEnable()) {
            VaParamSyncCacheUtil.initRedisTemplate();
            rs = (byte[])fileRedisTemplate.opsForValue().get((Object)key);
        } else if (fileCache != null) {
            rs = (byte[])fileCache.getIfPresent((Object)key);
        }
        return rs;
    }

    public static void removeFileCache(String key) {
        if (VaParamSyncConfig.isRedisEnable()) {
            VaParamSyncCacheUtil.initRedisTemplate();
            fileRedisTemplate.delete((Object)key);
        } else if (fileCache != null) {
            fileCache.invalidate((Object)key);
        }
    }

    public static void setImportResult(String key, String result) {
        if (VaParamSyncConfig.isRedisEnable()) {
            VaParamSyncCacheUtil.initRedisTemplate();
            stringRedisTemplate.opsForValue().set((Object)key, (Object)result, 2L, TimeUnit.HOURS);
        } else {
            if (importDataResultCache == null) {
                importDataResultCache = Caffeine.newBuilder().initialCapacity(1).maximumSize(100L).expireAfterWrite(2L, TimeUnit.HOURS).build();
            }
            importDataResultCache.put((Object)key, (Object)result);
        }
    }

    public static String getImportResult(String key) {
        String rs = null;
        if (VaParamSyncConfig.isRedisEnable()) {
            VaParamSyncCacheUtil.initRedisTemplate();
            rs = (String)stringRedisTemplate.opsForValue().get((Object)key);
        } else if (importDataResultCache != null) {
            rs = (String)importDataResultCache.getIfPresent((Object)key);
        }
        return rs;
    }

    public static void removeImportResult(String key) {
        if (VaParamSyncConfig.isRedisEnable()) {
            VaParamSyncCacheUtil.initRedisTemplate();
            stringRedisTemplate.delete((Object)key);
        } else if (importDataResultCache != null) {
            importDataResultCache.invalidate((Object)key);
        }
    }
}

