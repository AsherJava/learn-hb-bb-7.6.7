/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.datastatus.internal.obj;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.HashSet;
import java.util.Set;

public class DataDimensionSplit {
    private Set<DimensionValueSet> tzDimensionValueSet = new HashSet<DimensionValueSet>();
    private Set<DimensionValueSet> ftzDimensionValueSet = new HashSet<DimensionValueSet>();
    public static int STATUS_TZ = 1;
    public static int STATUS_FTZ = 2;
    public static String STATUS_TAG_COL = "TAG";

    public Set<DimensionValueSet> getTzDimensionValueSet() {
        return this.tzDimensionValueSet;
    }

    public void setTzDimensionValueSet(Set<DimensionValueSet> tzDimensionValueSet) {
        this.tzDimensionValueSet = tzDimensionValueSet;
    }

    public Set<DimensionValueSet> getFtzDimensionValueSet() {
        return this.ftzDimensionValueSet;
    }

    public void setFtzDimensionValueSet(Set<DimensionValueSet> ftzDimensionValueSet) {
        this.ftzDimensionValueSet = ftzDimensionValueSet;
    }
}

