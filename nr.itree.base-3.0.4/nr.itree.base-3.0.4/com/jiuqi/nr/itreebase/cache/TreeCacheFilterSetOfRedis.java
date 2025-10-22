/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import java.io.Serializable;

public class TreeCacheFilterSetOfRedis
implements ITreeCacheArea {
    protected final NedisCacheProvider nedisCacheProvider;

    public TreeCacheFilterSetOfRedis(NedisCacheProvider nedisCacheProvider) {
        this.nedisCacheProvider = nedisCacheProvider;
    }

    @Override
    public boolean contains(String sourceId) {
        return StringUtils.isNotEmpty((String)sourceId) && this.getNedisCache().exists(sourceId);
    }

    @Override
    public <T extends Serializable> T getCacheData(String sourceId, Class<T> clazz) {
        if (this.contains(sourceId)) {
            return (T)((Serializable)this.getNedisCache().get(sourceId, clazz));
        }
        return null;
    }

    @Override
    public <T extends Serializable> void putCacheData(String sourceId, T contentData) {
        this.getNedisCache().put(sourceId, contentData);
    }

    protected NedisCache getNedisCache() {
        return this.nedisCacheProvider.getCacheManager("dim_tree_cache_2885918869385654218L").getCache("uselector_cache_filter_set");
    }
}

