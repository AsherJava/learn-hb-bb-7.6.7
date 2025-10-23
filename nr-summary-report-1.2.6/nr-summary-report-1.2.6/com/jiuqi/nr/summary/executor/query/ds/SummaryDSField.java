/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 */
package com.jiuqi.nr.summary.executor.query.ds;

import com.jiuqi.bi.dataset.model.field.DSField;

public class SummaryDSField
extends DSField {
    private String zbKey;
    private String expression;

    public String getZbKey() {
        return this.zbKey;
    }

    public void setZbKey(String zbKey) {
        this.zbKey = zbKey;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

