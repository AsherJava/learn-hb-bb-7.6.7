/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.workflow.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;

public class VaWorkflowCacheUtils {
    private static StringRedisTemplate stringRedisTemplate;
    private static Cache<String, String> importDataResultCache;

    private static void initRedisTemplate() {
        if (stringRedisTemplate == null) {
            stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
        }
    }

    public static void setImportDataResult(String key, String result) {
        if (EnvConfig.getRedisEnable()) {
            VaWorkflowCacheUtils.initRedisTemplate();
            stringRedisTemplate.opsForValue().set((Object)key, (Object)result, 60L, TimeUnit.SECONDS);
        } else {
            if (importDataResultCache == null) {
                importDataResultCache = Caffeine.newBuilder().initialCapacity(1).maximumSize(30L).expireAfterWrite(60L, TimeUnit.SECONDS).build();
            }
            importDataResultCache.put((Object)key, (Object)result);
        }
    }

    public static String getImportDataResult(String key) {
        String rs = null;
        if (EnvConfig.getRedisEnable()) {
            VaWorkflowCacheUtils.initRedisTemplate();
            rs = (String)stringRedisTemplate.opsForValue().get((Object)key);
            if (rs != null && !rs.contains("\"currIndex\"")) {
                stringRedisTemplate.delete((Object)key);
            }
        } else if (importDataResultCache != null && (rs = (String)importDataResultCache.getIfPresent((Object)key)) != null && !rs.contains("\"currIndex\"")) {
            importDataResultCache.invalidate((Object)key);
        }
        return rs;
    }
}

