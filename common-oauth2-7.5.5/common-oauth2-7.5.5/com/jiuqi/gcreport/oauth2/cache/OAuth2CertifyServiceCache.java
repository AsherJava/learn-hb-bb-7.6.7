/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.gcreport.oauth2.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class OAuth2CertifyServiceCache {
    public static final String CACHE_MANAGER_NAME = "GC_OAUTH2_CERITY_CACHE_MANAGE";
    private NedisCache sessionFingerprintCache;

    public OAuth2CertifyServiceCache(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager(CACHE_MANAGER_NAME);
        this.sessionFingerprintCache = cacheManager.getCache("GC_OAUTH2_SESSION_FINGERPRINT_CACHE");
    }

    public void putCerCode(String fingerprint, String certifyCode) {
        this.sessionFingerprintCache.put(fingerprint, (Object)certifyCode);
    }

    public String getCerCodeByFingerprint(String fingerprint) {
        Cache.ValueWrapper valueWrapper = this.sessionFingerprintCache.get(fingerprint);
        if (null != valueWrapper) {
            return (String)valueWrapper.get();
        }
        return "";
    }
}

