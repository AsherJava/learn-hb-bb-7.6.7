/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.domain;

public class DynamicTempTableType {
    int columnCount;
    int tableCount;
    int avaliableCount;

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getTableCount() {
        return this.tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public int getAvaliableCount() {
        return this.avaliableCount;
    }

    public void setAvaliableCount(int avaliableCount) {
        this.avaliableCount = avaliableCount;
    }

    public String getTypeName() {
        return "NR-TEMPTABLE-" + this.columnCount + "-Column";
    }
}

