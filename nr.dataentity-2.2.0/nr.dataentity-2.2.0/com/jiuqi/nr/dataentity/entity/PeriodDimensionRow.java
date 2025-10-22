/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.IDimensionRow;
import com.jiuqi.nr.period.modal.IPeriodRow;

public class PeriodDimensionRow
implements IDimensionRow {
    private IPeriodRow periodRow;

    public PeriodDimensionRow(IPeriodRow periodRow) {
        this.periodRow = periodRow;
    }

    @Override
    public String getCode() {
        return this.periodRow.getCode();
    }

    @Override
    public String getTitle() {
        return this.periodRow.getTitle();
    }

    @Override
    public Object getExtAttribute(String attributeName) {
        return null;
    }
}

