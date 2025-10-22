/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.itreebase.collection;

import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import com.jiuqi.nr.itreebase.cache.ITreeCacheAreaHelper;
import com.jiuqi.nr.itreebase.cache.ITreeCacheAreaType;
import com.jiuqi.nr.itreebase.collection.FilterStringListCache;
import com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class FilterCacheSetHelper
implements IFilterCacheSetHelper {
    @Resource
    private ITreeCacheAreaHelper cacheProvider;

    @Override
    public IFilterStringList getInstance(String sourceId) {
        ITreeCacheArea cacheArea = this.cacheProvider.getCacheArea(ITreeCacheAreaType.REDIS_CACHE_FILTER_SET);
        return new FilterStringListCache(cacheArea, sourceId);
    }
}

