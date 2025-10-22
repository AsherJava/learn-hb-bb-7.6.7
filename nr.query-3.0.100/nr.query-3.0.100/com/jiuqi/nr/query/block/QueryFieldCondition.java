/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

import com.jiuqi.nr.query.block.FilterSymbols;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryFieldCondition {
    private static final Logger log = LoggerFactory.getLogger(QueryFieldCondition.class);
    public static final String QUERYFIELDCONDITION_SYMBOL = "symbol";
    public static final String QUERYFIELDCONDITION_VALUE = "value";
    public static final String QUERYFIELDCONDITION_ISNUM = "isNum";
    private FilterSymbols symbol;
    private Object value;
    private Boolean isdefault;
    private Boolean isNum;

    public FilterSymbols getSymbol() {
        return this.symbol;
    }

    public void setSymbol(FilterSymbols symbol) {
        this.symbol = symbol;
    }

    public void setSymbol(String symbol) {
        FilterSymbols filterSymbols = null;
        try {
            filterSymbols = FilterSymbols.valueOf(symbol);
        }
        catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        this.symbol = filterSymbols;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean getIsdefault() {
        return this.isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Boolean getNum() {
        return this.isNum;
    }

    public void setNum(Boolean num) {
        this.isNum = num;
    }
}

