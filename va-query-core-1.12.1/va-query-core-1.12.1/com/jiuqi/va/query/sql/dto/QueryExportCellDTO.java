/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.dto;

public class QueryExportCellDTO {
    private int crossRow;
    private int childrenSize;
    private int level;
    private boolean groupFlag;

    public int getCrossRow() {
        return this.crossRow;
    }

    public void setCrossRow(int crossRow) {
        this.crossRow = crossRow;
    }

    public int getChildrenSize() {
        return this.childrenSize;
    }

    public void setChildrenSize(int childrenSize) {
        this.childrenSize = childrenSize;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isGroupFlag() {
        return this.groupFlag;
    }

    public void setGroupFlag(boolean groupFlag) {
        this.groupFlag = groupFlag;
    }
}

