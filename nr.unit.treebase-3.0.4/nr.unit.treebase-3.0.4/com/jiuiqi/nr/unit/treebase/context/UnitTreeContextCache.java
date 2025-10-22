/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.cache.ITreeCacheArea
 *  com.jiuqi.nr.itreebase.cache.ITreeCacheAreaHelper
 *  com.jiuqi.nr.itreebase.cache.ITreeCacheAreaType
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.context;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextCache;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.nr.itreebase.cache.ITreeCacheArea;
import com.jiuqi.nr.itreebase.cache.ITreeCacheAreaHelper;
import com.jiuqi.nr.itreebase.cache.ITreeCacheAreaType;
import java.io.Serializable;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeContextCache
implements IUnitTreeContextCache {
    @Resource
    private ITreeCacheAreaHelper cacheAreaHelper;

    @Override
    public UnitTreeContextData getUnitTreeContextData(String contextId) {
        ITreeCacheArea cacheArea = this.getTreeCacheArea();
        return (UnitTreeContextData)((Object)cacheArea.getCacheData(contextId, UnitTreeContextData.class));
    }

    @Override
    public void putContextData(String contextId, UnitTreeContextData contextData) {
        ITreeCacheArea cacheArea = this.getTreeCacheArea();
        cacheArea.putCacheData(contextId, (Serializable)((Object)contextData));
    }

    private ITreeCacheArea getTreeCacheArea() {
        return this.cacheAreaHelper.getCacheArea(ITreeCacheAreaType.REDIS_CACHE_CONTEXT_DATA);
    }
}

