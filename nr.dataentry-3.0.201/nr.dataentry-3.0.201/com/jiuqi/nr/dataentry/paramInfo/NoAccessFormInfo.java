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

public class NoAccessFormInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, DimensionValue> dimensionValue;
    private String formKey;
    private String reason;
    public static final String CONDITIONREASON = "\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6";

    public NoAccessFormInfo(Map<String, DimensionValue> dimensionValue, String formKey, String reason) {
        this.dimensionValue = dimensionValue;
        this.formKey = formKey;
        this.reason = reason;
    }

    public Map<String, DimensionValue> getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(Map<String, DimensionValue> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

