/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.bi.syntax.ast.IExpression;

public class SummaryCondition {
    public static final String TYPE_REPORT = "report";
    public static final String TYPE_ROW_FILTER = "rowFilter";
    public static final String TYPE_COL_FILTER = "colFilter";
    public static final String TYPE_ROW_CALIBER = "rowCaliber";
    public static final String TYPE_COL_CALIBER = "colCaliber";
    private String key;
    private int index;
    private String sourceFormula;
    private IExpression expression;

    public SummaryCondition(String key, int index, String sourceFormula, IExpression expression) {
        this.key = key;
        this.index = index;
        this.sourceFormula = sourceFormula;
        this.expression = expression;
    }

    public String getKey() {
        return this.key;
    }

    public int getIndex() {
        return this.index;
    }

    public String getSourceFormula() {
        return this.sourceFormula;
    }

    public void setSourceFormula(String sourceFormula) {
        this.sourceFormula = sourceFormula;
    }

    public IExpression getExpression() {
        return this.expression;
    }

    public String toString() {
        return "SummaryCondition [key=" + this.key + ", index=" + this.index + ", sourceFormula=" + this.sourceFormula + "]";
    }
}

