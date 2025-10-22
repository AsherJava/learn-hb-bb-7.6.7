/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.QueryField
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.nr.data.engine.analysis.define.OrderField;

public class ParsedOrderField
extends OrderField {
    private QueryField field;

    public ParsedOrderField(String expression, boolean desc) {
        super(expression, desc);
    }

    public ParsedOrderField(OrderField define, QueryField field) {
        super(define.getExpression(), define.isDesc());
        this.field = field;
    }

    public QueryField getField() {
        return this.field;
    }

    public void setField(QueryField field) {
        this.field = field;
    }
}

