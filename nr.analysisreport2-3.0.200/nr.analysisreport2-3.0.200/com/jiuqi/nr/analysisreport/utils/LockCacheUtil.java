/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class LockCacheUtil {
    private static NedisCacheManager nedisCacheManager;

    public static NedisCacheManager getNedisCacheManager() {
        if (nedisCacheManager == null) {
            NedisCacheProvider nedisCacheProvider = (NedisCacheProvider)SpringBeanUtils.getBean(NedisCacheProvider.class);
            nedisCacheManager = nedisCacheProvider.getCacheManager();
        }
        return nedisCacheManager;
    }

    public static ReentrantLock getCacheLock(NpContext npContext) {
        String cacheKey = LockCacheUtil.getCacheKey(npContext);
        NedisCache cache = LockCacheUtil.getNedisCacheManager().getCache("alcn");
        ReentrantLock reentrantLock = (ReentrantLock)cache.get(cacheKey, ReentrantLock.class);
        return reentrantLock;
    }

    public static void putCacheLock(String key) {
        NedisCache cache = LockCacheUtil.getNedisCacheManager().getCache("alcn");
        cache.put(key, (Object)new ReentrantLock());
    }

    public static void removeCacheLock(String key) {
        NedisCache cache = LockCacheUtil.getNedisCacheManager().getCache("alcn");
        cache.evict(key);
    }

    public static String buildLockCacheKey(HttpServletRequest request, ReportGeneratorVO reportGeneratorVO) {
        String authorization = request.getHeader("Authorization");
        String curTimeStamp = null;
        curTimeStamp = StringUtils.isEmpty((CharSequence)reportGeneratorVO.getCurTimeStamp()) ? UUID.randomUUID().toString() : reportGeneratorVO.getCurTimeStamp();
        return authorization + curTimeStamp;
    }

    public static void putLockCacheKey(HttpServletRequest request, ReportGeneratorVO reportGeneratorVO) {
        if (NpContextHolder.getContext().getExtension("arvl").get("arvl") == null) {
            String cacheKey = LockCacheUtil.buildLockCacheKey(request, reportGeneratorVO);
            NpContextHolder.getContext().getExtension("arvl").put("arvl", (Serializable)((Object)cacheKey));
        }
    }

    public static String getCacheKey(NpContext npContext) {
        return npContext.getExtension("arvl").get("arvl").toString();
    }
}

