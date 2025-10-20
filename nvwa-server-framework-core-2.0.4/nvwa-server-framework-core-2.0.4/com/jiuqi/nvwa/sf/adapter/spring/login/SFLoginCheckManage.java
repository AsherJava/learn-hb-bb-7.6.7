/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain
 */
package com.jiuqi.nvwa.sf.adapter.spring.login;

import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SFLoginCheckManage {
    private static final String SF_LOGIN_CACHE_NAME = "NVWA_SF_LOGIN";
    private static final String SF_LOGIN_CACHE_TOKEN = "NVWA_SF_LOGIN";
    private static final String SF_LOGIN_CACHE_TIME = "NVWA_SF_LOGIN_TIME";
    private static final String SF_LOGIN_HEADER_KEY = "sf-token";
    private final NedisCache cache;

    public SFLoginCheckManage(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("sf_login_cache");
        this.cache = cacheManager.getCache("NVWA_SF_LOGIN");
    }

    public String doLogin() {
        this.cache.put(SF_LOGIN_CACHE_TIME, (Object)Instant.now());
        this.cache.putIfAbsent("NVWA_SF_LOGIN", (Object)Guid.newGuid());
        return (String)this.cache.get("NVWA_SF_LOGIN", String.class);
    }

    public boolean checkLogin(NvwaFilterChain nvwaFilterChain) {
        String sftoken = nvwaFilterChain.getHeader(SF_LOGIN_HEADER_KEY);
        if (StringUtils.isEmpty((String)sftoken)) {
            return false;
        }
        String s = (String)this.cache.get("NVWA_SF_LOGIN", String.class);
        if (sftoken.equalsIgnoreCase(s)) {
            Instant date = (Instant)this.cache.get(SF_LOGIN_CACHE_TIME, Instant.class);
            if (date == null) {
                return false;
            }
            Instant now = Instant.now();
            long between = ChronoUnit.MINUTES.between(date, now);
            if (between > 30L) {
                return false;
            }
            this.cache.put(SF_LOGIN_CACHE_TIME, (Object)now);
            return true;
        }
        return false;
    }
}

