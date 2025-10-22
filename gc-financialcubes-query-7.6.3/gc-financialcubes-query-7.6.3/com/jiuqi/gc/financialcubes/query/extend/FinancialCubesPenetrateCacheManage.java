/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.gc.financialcubes.query.extend;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesPenetrateCacheManage {
    private final NedisCache financialCubesPenetrateCache;

    public FinancialCubesPenetrateCacheManage(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("FINANCIALCUBES_PENETRACTE_MANAGE");
        this.financialCubesPenetrateCache = cacheManager.getCache("FINANCIALCUBES_PENETRACTE");
    }

    public String getPenetrateContext(String id) {
        Cache.ValueWrapper value = this.financialCubesPenetrateCache.get(id);
        this.delPenetrateContext(id);
        if (null != value) {
            return (String)value.get();
        }
        return "{}";
    }

    private void delPenetrateContext(String key) {
        this.financialCubesPenetrateCache.evict(key);
    }

    public String savePenetrateContext(String context) {
        String id = UUIDUtils.newUUIDStr();
        this.financialCubesPenetrateCache.put(id, (Object)context);
        return id;
    }
}

