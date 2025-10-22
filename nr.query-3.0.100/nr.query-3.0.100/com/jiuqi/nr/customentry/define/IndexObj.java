/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.customentry.define;

public class IndexObj {
    private Integer rowIndex;
    private String rowKeys;
    private Integer listIndex;

    public IndexObj() {
    }

    public IndexObj(Integer rowIndex, String rowKeys, Integer listIndex) {
        this.rowIndex = rowIndex;
        this.rowKeys = rowKeys;
        this.listIndex = listIndex;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getRowKeys() {
        return this.rowKeys;
    }

    public void setRowKeys(String rowKeys) {
        this.rowKeys = rowKeys;
    }

    public Integer getListIndex() {
        return this.listIndex;
    }

    public void setListIndex(Integer listIndex) {
        this.listIndex = listIndex;
    }
}

