/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.singlequeryimport.service.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.QueryResultVo;
import com.jiuqi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class GroupSetQueryResultCacheHandler {
    public static final String NAME = "BMJS_FINALACCOUNT_QUERY";
    private static final String QUERY_DATA_CACHE = "GROUP_SET_DATA_CACHE";
    private NedisCacheManager cacheManager;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager(NAME);
    }

    public void setQueryDataToCache(String cacheId, QueryResultVo cacheValue) {
        NedisCache dataCache = this.cacheManager.getCache(QUERY_DATA_CACHE);
        dataCache.put(cacheId, (Object)cacheValue);
    }

    public QueryResultVo getQueryDataFromCache(String cacheId) {
        if (StringUtils.isEmpty((String)cacheId)) {
            return null;
        }
        NedisCache dataCache = this.cacheManager.getCache(QUERY_DATA_CACHE);
        Cache.ValueWrapper valueWrapper = dataCache.get(cacheId);
        if (valueWrapper != null) {
            Object result = valueWrapper.get();
            return (QueryResultVo)result;
        }
        return null;
    }
}

