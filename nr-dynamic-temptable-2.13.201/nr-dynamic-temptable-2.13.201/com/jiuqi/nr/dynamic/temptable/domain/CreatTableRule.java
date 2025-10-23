/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.domain;

public class CreatTableRule {
    int columnCount;
    int tableCount;

    public CreatTableRule(int columnCount, int tableCount) {
        this.columnCount = columnCount;
        this.tableCount = tableCount;
    }

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
}

