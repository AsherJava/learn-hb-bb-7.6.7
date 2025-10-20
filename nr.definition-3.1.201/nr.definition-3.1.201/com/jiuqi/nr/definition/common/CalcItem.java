/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class CalcItem {
    private String linkId;
    private DimensionValueSet dimValues;

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public DimensionValueSet getDimValues() {
        return this.dimValues;
    }

    public void setDimValues(DimensionValueSet dimValues) {
        this.dimValues = dimValues;
    }
}

