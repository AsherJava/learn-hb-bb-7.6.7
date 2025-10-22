/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.datacheck.fmlcheck.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacheck.fmlcheck.vo.CheckResultContext;
import java.io.Serializable;
import java.util.Map;

public class DimDropdownQueryPar
extends CheckResultContext
implements Serializable {
    private static final long serialVersionUID = 6795396657530472689L;
    private Map<String, DimensionValue> dimensionSet;

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }
}

