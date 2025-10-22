/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UnitAccessCache {
    private Map<DimensionValueSet, String> cacheMap = new HashMap<DimensionValueSet, String>();
    private AccessType accessType = AccessType.WRITE;

    public Map<DimensionValueSet, String> getCacheMap() {
        if (this.cacheMap == null) {
            this.cacheMap = new HashMap<DimensionValueSet, String>();
        }
        return this.cacheMap;
    }

    public Optional<String> matchCacheValue(DimensionValueSet masterKey) {
        if (this.cacheMap == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.cacheMap.get(masterKey));
    }

    public void setCacheMap(Map<DimensionValueSet, String> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public AccessType getAccessType() {
        return this.accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}

