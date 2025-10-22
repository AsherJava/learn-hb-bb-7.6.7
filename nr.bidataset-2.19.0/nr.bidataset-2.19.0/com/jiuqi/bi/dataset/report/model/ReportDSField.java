/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.bi.dataset.report.model;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.report.model.ReportFieldType;
import com.jiuqi.bi.syntax.ast.IExpression;

public class ReportDSField
extends DSField {
    private ReportFieldType type = ReportFieldType.COMMON;
    private int index;
    private IExpression evalExpression;
    private boolean fixZb;

    public ReportFieldType getType() {
        return this.type;
    }

    public void setType(ReportFieldType type) {
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public IExpression getEvalExpression() {
        return this.evalExpression;
    }

    public void setEvalExpression(IExpression evalExpression) {
        this.evalExpression = evalExpression;
    }

    public boolean isFixZb() {
        return this.fixZb;
    }

    public void setFixZb(boolean fixZb) {
        this.fixZb = fixZb;
    }
}

