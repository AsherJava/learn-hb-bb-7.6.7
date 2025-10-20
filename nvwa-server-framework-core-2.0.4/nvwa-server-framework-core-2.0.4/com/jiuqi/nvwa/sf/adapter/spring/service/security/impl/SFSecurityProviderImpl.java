/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.va.feign.util.RequestContextUtil
 */
package com.jiuqi.nvwa.sf.adapter.spring.service.security.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nvwa.sf.adapter.spring.service.security.SFSecurityProvider;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SFSecurityProviderImpl
implements SFSecurityProvider {
    private NedisCacheManager cacheManager;

    public SFSecurityProviderImpl(@Autowired NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("sf_security_header");
    }

    @Override
    public void authentication(String moduleName, String headerName) throws SecurityException {
        boolean error = true;
        String header = RequestContextUtil.getHeader((String)headerName);
        NedisCache cache = this.cacheManager.getCache(moduleName);
        if (StringUtils.hasLength(header) && cache.exists(header)) {
            cache.evict(header);
            error = false;
        }
        if (error) {
            String remoteAddr = RequestContextUtil.getRemoteAddr();
            throw new SecurityException("\u975e\u6cd5\u7684\u8bf7\u6c42\u8c03\u7528\uff0c\u53ea\u5141\u8bb8\u670d\u52a1\u95f4\u8fdb\u884c\u8c03\u7528! ip:" + remoteAddr);
        }
    }

    @Override
    public Map<String, String> getSecurityHeaders(String moduleName, String headerName) {
        NedisCache cache = this.cacheManager.getCache(moduleName);
        HashMap<String, String> headers = new HashMap<String, String>();
        String key = UUID.randomUUID().toString();
        cache.put(key, (Object)1);
        headers.put(headerName, key);
        return headers;
    }
}

