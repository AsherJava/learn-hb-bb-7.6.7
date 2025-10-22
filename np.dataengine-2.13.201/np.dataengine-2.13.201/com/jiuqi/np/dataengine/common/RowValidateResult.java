/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.FieldValidateResult;
import java.util.List;

public class RowValidateResult {
    private String recordKey;
    private DimensionValueSet rowKeys;
    private List<FieldValidateResult> fieldValidateResults;

    public String getRecordKey() {
        return this.recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public DimensionValueSet getRowKeys() {
        return this.rowKeys;
    }

    public void setRowKeys(DimensionValueSet rowKeys) {
        this.rowKeys = rowKeys;
    }

    public List<FieldValidateResult> getFieldValidateResults() {
        return this.fieldValidateResults;
    }

    public void setFieldValidateResults(List<FieldValidateResult> fieldValidateResults) {
        this.fieldValidateResults = fieldValidateResults;
    }
}

