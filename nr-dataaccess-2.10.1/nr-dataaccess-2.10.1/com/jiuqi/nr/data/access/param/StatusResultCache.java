/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessCache;
import com.jiuqi.nr.data.access.param.StatusResultCacheKey;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StatusResultCache
implements Serializable {
    private Map<StatusResultCacheKey, AccessCache> cacheMap = new HashMap<StatusResultCacheKey, AccessCache>();

    public void put(StatusResultCacheKey key, AccessCache value) {
        this.cacheMap.put(key, value);
    }

    public AccessCache get(StatusResultCacheKey key) {
        return this.cacheMap.get(key);
    }
}

