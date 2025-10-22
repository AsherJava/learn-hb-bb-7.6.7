/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.itreebase.cache.TreeCacheFilterSetOfRedis;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.io.Serializable;

public class TreeCacheContextOfRedis
extends TreeCacheFilterSetOfRedis {
    public TreeCacheContextOfRedis(NedisCacheProvider nedisCacheProvider) {
        super(nedisCacheProvider);
    }

    @Override
    public <T extends Serializable> T getCacheData(String sourceId, Class<T> clazz) {
        if (this.contains(sourceId)) {
            String objJsonStr = (String)this.getNedisCache().get(sourceId, String.class);
            return (T)((Serializable)JavaBeanUtils.toJavaBean((String)objJsonStr, clazz));
        }
        return null;
    }

    @Override
    public <T extends Serializable> void putCacheData(String sourceId, T contentData) {
        this.getNedisCache().put(sourceId, (Object)JavaBeanUtils.toJSONStr(contentData));
    }

    @Override
    protected NedisCache getNedisCache() {
        return this.nedisCacheProvider.getCacheManager("dim_tree_cache_2885918869385654218L").getCache("uselector_cache_context");
    }
}

