/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather.util;

import com.jiuqi.nr.data.engine.gather.param.FieldAndGroupKeyInfo;

public class TempConvertObject {
    private String UintKey;
    private String PeriodCode;
    private FieldAndGroupKeyInfo fieldAndGroupKeyInfo;

    public String getUintKey() {
        return this.UintKey;
    }

    public void setUintKey(String uintKey) {
        this.UintKey = uintKey;
    }

    public String getPeriodCode() {
        return this.PeriodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.PeriodCode = periodCode;
    }

    public FieldAndGroupKeyInfo getFieldAndGroupKeyInfo() {
        return this.fieldAndGroupKeyInfo;
    }

    public void setFieldAndGroupKeyInfo(FieldAndGroupKeyInfo fieldAndGroupKeyInfo) {
        this.fieldAndGroupKeyInfo = fieldAndGroupKeyInfo;
    }
}

