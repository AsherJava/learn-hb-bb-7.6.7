/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.domain;

public interface FieldGrowthData {
    public String getFieldKey();

    public String getCurrentValue();

    public String getPrevYearValue();

    public String getYoyGrowthRate();

    public String getPrevPeriodValue();

    public String getPopGrowthRate();
}

