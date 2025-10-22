/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodModifier;

public interface IQueryField {
    public String getTableName();

    public String getFieldName();

    public int getDataType();

    public boolean getIsLj();

    public int getFractionDigits();

    public int getFieldSize();

    public DimensionSet getTableDimensions();

    public PeriodModifier getPeriodModifier();

    public DimensionValueSet getDimensionRestriction();
}

