/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData;
import java.io.Serializable;

public class FieldGrowthDataDTO
implements FieldGrowthData,
Serializable {
    private static final long serialVersionUID = 1L;
    private String fieldKey;
    private String currentValue;
    private String prevYearValue;
    private String yoyGrowthRate;
    private String prevPeriodValue;
    private String popGrowthRate;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getPrevYearValue() {
        return this.prevYearValue;
    }

    public void setPrevYearValue(String prevYearValue) {
        this.prevYearValue = prevYearValue;
    }

    public String getYoyGrowthRate() {
        return this.yoyGrowthRate;
    }

    public void setYoyGrowthRate(String yoyGrowthRate) {
        this.yoyGrowthRate = yoyGrowthRate;
    }

    public String getPrevPeriodValue() {
        return this.prevPeriodValue;
    }

    public void setPrevPeriodValue(String prevPeriodValue) {
        this.prevPeriodValue = prevPeriodValue;
    }

    public String getPopGrowthRate() {
        return this.popGrowthRate;
    }

    public void setPopGrowthRate(String popGrowthRate) {
        this.popGrowthRate = popGrowthRate;
    }
}

