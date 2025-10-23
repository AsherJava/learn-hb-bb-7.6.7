/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.ConditionOperation;
import java.util.List;

public class ConditionOperationItem {
    private ConditionOperation operation;
    private List<String> values;

    public ConditionOperation getOperation() {
        return this.operation;
    }

    public void setOperation(ConditionOperation operation) {
        this.operation = operation;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

