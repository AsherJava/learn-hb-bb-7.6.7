/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class DimensionMapInfo {
    private DimensionValueSet source;
    private DimensionValueSet target;

    public DimensionMapInfo() {
    }

    public DimensionMapInfo(DimensionValueSet source, DimensionValueSet target) {
        this.source = source;
        this.target = target;
    }

    public DimensionValueSet getSource() {
        return this.source;
    }

    public void setSource(DimensionValueSet source) {
        this.source = source;
    }

    public DimensionValueSet getTarget() {
        return this.target;
    }

    public void setTarget(DimensionValueSet target) {
        this.target = target;
    }
}

