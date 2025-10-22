/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.List;

public class RowExpressionValidResult {
    private String recordKey;
    private DimensionValueSet rowKeys;
    private List<IParsedExpression> errorExpressions;

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

    public List<IParsedExpression> getErrorExpressions() {
        return this.errorExpressions;
    }

    public void setErrorExpressions(List<IParsedExpression> errorExpressions) {
        this.errorExpressions = errorExpressions;
    }
}

