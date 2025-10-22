/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.IDimensionRow;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;

public class AdjustDimensionRow
implements IDimensionRow {
    private AdjustPeriod adjustPeriod;

    public AdjustDimensionRow(AdjustPeriod adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    @Override
    public String getCode() {
        return this.adjustPeriod.getCode();
    }

    @Override
    public String getTitle() {
        return this.adjustPeriod.getTitle();
    }

    @Override
    public Object getExtAttribute(String attributeName) {
        return null;
    }
}

