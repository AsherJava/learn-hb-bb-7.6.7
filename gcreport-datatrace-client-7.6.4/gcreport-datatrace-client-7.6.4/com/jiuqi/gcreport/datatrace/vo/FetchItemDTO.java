/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.datatrace.vo;

public class FetchItemDTO {
    private String formula;
    private Object fetchItem;

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Object getFetchItem() {
        return this.fetchItem;
    }

    public void setFetchItem(Object fetchItem) {
        this.fetchItem = fetchItem;
    }

    public FetchItemDTO() {
    }

    public FetchItemDTO(String formula, Object fetchItem) {
        this.formula = formula;
        this.fetchItem = fetchItem;
    }

    public String toString() {
        return "FetchItemDTO{formula='" + this.formula + '\'' + ", fetchItem=" + this.fetchItem + '}';
    }
}

