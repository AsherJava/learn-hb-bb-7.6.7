/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CacheAbleProvider {
    protected transient Map<String, List<String>> valueCache;

    public List<String> getValues(VariableDimensionValue variableDimensionValue, String dw, String period) {
        String cacheKey = this.getCacheKey(variableDimensionValue, dw, period);
        if (this.valueCache == null) {
            this.valueCache = new HashMap<String, List<String>>();
        }
        return this.valueCache.get(cacheKey);
    }

    protected boolean hasKey(String cacheKey) {
        if (this.valueCache == null) {
            this.valueCache = new HashMap<String, List<String>>();
        }
        return this.valueCache.containsKey(cacheKey);
    }

    protected abstract String getCacheKey(VariableDimensionValue var1, String var2, String var3);
}

