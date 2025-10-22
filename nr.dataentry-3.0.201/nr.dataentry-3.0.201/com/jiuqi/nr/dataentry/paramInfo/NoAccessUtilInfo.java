/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.Map;

public class NoAccessUtilInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, DimensionValue> dimensionValue;
    private String unitKey;
    private String reason;

    public NoAccessUtilInfo(Map<String, DimensionValue> dimensionValue, String unitKey, String reason) {
        this.dimensionValue = dimensionValue;
        this.unitKey = unitKey;
        this.reason = reason;
    }

    public Map<String, DimensionValue> getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(Map<String, DimensionValue> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

