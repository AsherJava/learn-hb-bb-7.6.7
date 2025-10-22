/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;

public class PeriodSchemeInfo {
    private String periodKey;
    private String schemeKey;
    private Map<String, DimensionValue> dimensions;

    public String getPeriodKey() {
        return this.periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public Map<String, DimensionValue> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(Map<String, DimensionValue> dimensions) {
        this.dimensions = dimensions;
    }
}

