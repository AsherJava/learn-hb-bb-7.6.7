/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import java.util.Objects;

public class FormulaFilter
implements RowFilter {
    private String formula;

    private FormulaFilter() {
    }

    public FormulaFilter(String formula) {
        this.formula = formula;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean supportFormula() {
        return true;
    }

    @Override
    public String toFormula() {
        return this.formula;
    }

    @Override
    public boolean filter(String formula, IContext context) {
        throw new UnsupportedOperationException("\u666e\u901a\u516c\u5f0f\u8fc7\u6ee4\u5668\u4e0d\u652f\u6301\u8d70\u51fd\u6570\u8fc7\u6ee4");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FormulaFilter that = (FormulaFilter)o;
        return Objects.equals(this.formula, that.formula);
    }

    public int hashCode() {
        return this.formula != null ? this.formula.hashCode() : 0;
    }
}

