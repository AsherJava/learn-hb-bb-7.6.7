/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  io.netty.util.internal.StringUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.query.service.IQueryCacheManager;
import io.netty.util.internal.StringUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

public class QueryCacheManager
implements IQueryCacheManager,
InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(QueryCacheManager.class);
    private NedisCacheManager cacheManager;
    private final String CACHENAME = "DATAQUERY";
    public static final String CACHETYPE_ENUMVALUE = "ENUMVALUE";
    public static final String CACHETYPE_BLOCK_DIMITMES = "DIMITMES";
    public static final String CACHETYPE_BLOCK_SELECTFIELDS = "SELECTFIELDS";
    public static final String CACHETYPE_BLOCK_SELECTFIELDDEFINES = "SELECTFIELDDEFINES";
    public static final String CACHETYPE_BLOCK_ISFILTERMODE = "ISFILTERMODE";
    public static final String CACHETYPE_BLOCK_MASTERDIMENSIONS = "MASTERDIMENSIONS";
    public static final String CACHETYPE_DIMENSIONNAMES = "DIMENSIONNAMES";
    public static final String CACHETYPE_CURRENT_THREADID = "CURRENTTHREADID";
    public static final String CACHETYPE_DIMCACHE_THREADID = "DIMCACHETHREADID";
    public static final String CACHETYPE_DIMCACHE_FUTURE = "CACHETYPE_DIMCACHE_FUTURE";
    public static final String CACHETYPE_FIELDCACHE_FUTURE = "CACHETYPE_FIELDCACHE_FUTURE";
    public static final String CACHETYPE_DIMCACHE_INDEX = "DIMINDEX";
    public static final String CACHETYPE_PAGEINFO = "PAGEINFO";
    public static final String CACHETYPE_QUERYDEFINA = "QUERYDEFINA";

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setItem(String userKey, String blockKey, String cacheType, Object value) {
        try {
            JSONObject blockCache;
            if (StringUtil.isNullOrEmpty((String)userKey) || StringUtil.isNullOrEmpty((String)blockKey) || StringUtil.isNullOrEmpty((String)cacheType)) {
                return;
            }
            NedisCache queryCache = this.cacheManager.getCache("DATAQUERY");
            Cache.ValueWrapper cacheTemp = queryCache.get(userKey);
            JSONObject userCache = new JSONObject();
            if (cacheTemp != null) {
                userCache = (JSONObject)cacheTemp.get();
            }
            if (userCache == null) {
                userCache = new JSONObject();
            }
            JSONObject jSONObject = blockCache = userCache.has(blockKey) ? (JSONObject)userCache.get(blockKey) : new JSONObject();
            if (blockCache == null) {
                blockCache = new JSONObject();
            }
            blockCache.put(cacheType, value);
            userCache.put(blockKey, (Object)blockCache);
            queryCache.put(userKey, (Object)userCache);
        }
        catch (Exception ex) {
            log.error("\u8bbe\u7f6e\u67e5\u8be2\u7f13\u5b58\u5931\u8d25", ex);
        }
    }

    @Override
    public void setItem(String userKey, String cacheType, Object value) {
        try {
            NedisCache queryCache = this.cacheManager.getCache("DATAQUERY");
            Cache.ValueWrapper cacheTemp = queryCache.get(userKey);
            JSONObject userCache = new JSONObject();
            if (cacheTemp != null) {
                userCache = (JSONObject)cacheTemp.get();
            }
            userCache.put(cacheType, value);
            queryCache.put(userKey, (Object)userCache);
        }
        catch (Exception ex) {
            log.error("\u8bbe\u7f6e\u67e5\u8be2\u7f13\u5b58\u5931\u8d25", ex);
        }
    }

    @Override
    public Object getCache(String userKey, String blockKey, String cacheType) {
        try {
            if (StringUtil.isNullOrEmpty((String)userKey) || StringUtil.isNullOrEmpty((String)blockKey) || StringUtil.isNullOrEmpty((String)cacheType)) {
                return null;
            }
            NedisCache queryCache = this.cacheManager.getCache("DATAQUERY");
            Cache.ValueWrapper cacheTemp = queryCache.get(userKey);
            if (cacheTemp != null) {
                JSONObject userCache = (JSONObject)cacheTemp.get();
                if (userCache == null || !userCache.has(blockKey)) {
                    return null;
                }
                JSONObject blockCache = userCache.getJSONObject(blockKey);
                if (blockCache == null) {
                    return null;
                }
                Object cache = blockCache.get(cacheType);
                return cache;
            }
        }
        catch (Exception ex) {
            log.info("\u672a\u83b7\u53d6\u5230\u67e5\u8be2\u7f13\u5b58" + ex.getMessage());
        }
        return null;
    }

    @Override
    public Object getCache(String userKey, String cacheType) {
        try {
            if (StringUtil.isNullOrEmpty((String)userKey) || StringUtil.isNullOrEmpty((String)cacheType)) {
                return null;
            }
            NedisCache queryCache = this.cacheManager.getCache("DATAQUERY");
            Cache.ValueWrapper cacheTemp = queryCache.get(userKey);
            if (cacheTemp != null) {
                JSONObject userCache = (JSONObject)cacheTemp.get();
                if (userCache == null || !userCache.has(cacheType)) {
                    return null;
                }
                Object cache = userCache.get(cacheType);
                return cache;
            }
        }
        catch (Exception ex) {
            log.info("\u672a\u83b7\u53d6\u5230\u67e5\u8be2\u7f13\u5b58" + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean reSetCache(String userKey, String blockKey, String cacheType) {
        try {
            NedisCache queryCache = this.cacheManager.getCache("DATAQUERY");
            Cache.ValueWrapper cacheTemp = queryCache.get(userKey);
            if (cacheTemp == null) {
                return true;
            }
            JSONObject userCache = (JSONObject)cacheTemp.get();
            if (userCache == null || !userCache.has(blockKey)) {
                return true;
            }
            JSONObject blockCache = userCache.getJSONObject(blockKey);
            if (blockCache == null) {
                return true;
            }
            blockCache.remove(cacheType);
        }
        catch (Exception ex) {
            log.error(userKey + blockKey + cacheType + "\u7f13\u5b58\u91cd\u7f6e\u5f02\u5e38\uff1a" + ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean reSetCache(String userKey, String blockKey) {
        try {
            NedisCache queryCache = this.cacheManager.getCache("DATAQUERY");
            Cache.ValueWrapper cacheTemp = queryCache.get(userKey);
            if (cacheTemp == null) {
                return true;
            }
            JSONObject userCache = (JSONObject)cacheTemp.get();
            if (userCache == null || !userCache.has(blockKey)) {
                return true;
            }
            userCache.remove(blockKey);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public void clearCache() {
        try {
            this.cacheManager.getCache("DATAQUERY").clear();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

