/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.function.func.cache;

import java.util.List;
import java.util.Map;

public class SameDataCountCache {
    List<String> dimensionNames;
    Map<String, String> dimensionName2DimCode;
    Map<String, Integer> dataCounts;

    public List<String> getDimensionNames() {
        return this.dimensionNames;
    }

    public void setDimensionNames(List<String> dimensionNames) {
        this.dimensionNames = dimensionNames;
    }

    public Map<String, String> getDimensionName2DimCode() {
        return this.dimensionName2DimCode;
    }

    public void setDimensionName2DimCode(Map<String, String> dimensionName2DimCode) {
        this.dimensionName2DimCode = dimensionName2DimCode;
    }

    public Map<String, Integer> getDataCounts() {
        return this.dataCounts;
    }

    public void setDataCounts(Map<String, Integer> dataCounts) {
        this.dataCounts = dataCounts;
    }
}

