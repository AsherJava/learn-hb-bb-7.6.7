/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.param;

import java.util.List;

public class DataFieldMovePM {
    private List<String> keys;
    private boolean moveUp;
    private Integer pageCount;
    private String table;
    private Integer skip;
    private Integer limit;

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public boolean isMoveUp() {
        return this.moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getSkip() {
        return this.skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String toString() {
        return "DataFieldMovePM{keys=" + this.keys + ", moveUp=" + this.moveUp + ", pageCount=" + this.pageCount + ", table='" + this.table + '\'' + ", skip=" + this.skip + ", limit=" + this.limit + '}';
    }
}

