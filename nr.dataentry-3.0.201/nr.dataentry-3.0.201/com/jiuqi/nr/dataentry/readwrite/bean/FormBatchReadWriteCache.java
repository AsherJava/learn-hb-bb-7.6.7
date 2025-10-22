/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FormBatchReadWriteCache {
    private boolean canAccess;
    private Map<DimensionCacheKey, HashSet<String>> cacheMap;
    private List<String> dimKeys;

    public boolean isCanAccess() {
        return this.canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }

    public Map<DimensionCacheKey, HashSet<String>> getCacheMap() {
        return this.cacheMap;
    }

    public void setCacheMap(Map<DimensionCacheKey, HashSet<String>> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public boolean canAccess(Map<String, DimensionValue> dimensionSet, String formKey) {
        DimensionCacheKey cacheKey = new DimensionCacheKey(dimensionSet);
        HashSet<String> formCache = this.cacheMap.get(cacheKey);
        if (formCache == null) {
            return true;
        }
        if (!formCache.contains(formKey)) {
            return true;
        }
        return this.canAccess;
    }

    public List<String> getDimKeys() {
        return this.dimKeys;
    }

    public void setDimKeys(List<String> dimKeys) {
        this.dimKeys = dimKeys;
    }
}

