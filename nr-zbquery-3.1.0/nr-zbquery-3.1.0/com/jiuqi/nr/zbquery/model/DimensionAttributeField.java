/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObjectType;

public class DimensionAttributeField
extends QueryField {
    private boolean timekey;
    private PeriodType periodType = PeriodType.DEFAULT;

    public DimensionAttributeField() {
        this.setType(QueryObjectType.DIMENSIONATTRIBUTE);
    }

    public boolean isTimekey() {
        return this.timekey;
    }

    public void setTimekey(boolean timekey) {
        this.timekey = timekey;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }
}

