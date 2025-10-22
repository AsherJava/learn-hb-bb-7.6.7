/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;

public class DimensionCacheKey {
    private Map<String, DimensionValue> dimensionSet;

    public DimensionCacheKey(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.dimensionSet == null ? 0 : this.dimensionSet.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DimensionCacheKey other = (DimensionCacheKey)obj;
        return !(this.dimensionSet == null ? other.dimensionSet != null : !this.dimensionSet.equals(other.dimensionSet));
    }
}

