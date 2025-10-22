/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

import com.jiuqi.nr.query.block.FilterSymbols;

public class QueryFilterDefine {
    private FilterSymbols symbol;
    private Object value;

    public FilterSymbols getSymbol() {
        return this.symbol;
    }

    public void setSymbol(FilterSymbols symbol) {
        this.symbol = symbol;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

