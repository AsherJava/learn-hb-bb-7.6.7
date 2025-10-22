/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FormAccessCache {
    private Map<DimensionValueSet, Map<String, String>> cacheMap = new HashMap<DimensionValueSet, Map<String, String>>();
    private AccessType accessType = AccessType.WRITE;

    public Map<DimensionValueSet, Map<String, String>> getCacheMap() {
        if (this.cacheMap == null) {
            this.cacheMap = new HashMap<DimensionValueSet, Map<String, String>>();
        }
        return this.cacheMap;
    }

    public Map<String, String> matchCacheValue(DimensionValueSet masterKey) {
        if (this.cacheMap == null) {
            return Collections.emptyMap();
        }
        return this.cacheMap.get(masterKey);
    }

    public void setCacheMap(Map<DimensionValueSet, Map<String, String>> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public AccessType getAccessType() {
        return this.accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}

