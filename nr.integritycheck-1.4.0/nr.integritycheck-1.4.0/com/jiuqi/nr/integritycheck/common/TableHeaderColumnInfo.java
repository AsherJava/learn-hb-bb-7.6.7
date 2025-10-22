/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class TableHeaderColumnInfo {
    private String title;
    private final List<TableHeaderColumnInfo> childCols;
    private int rowSpan;

    public TableHeaderColumnInfo(String title) {
        this(title, 1);
    }

    public TableHeaderColumnInfo(String title, int rowSpan) {
        this.title = title;
        this.rowSpan = rowSpan;
        this.childCols = new ArrayList<TableHeaderColumnInfo>();
    }

    public int getOccuRowCount() {
        return this.getDepth() + 1;
    }

    private int getDepth() {
        if (CollectionUtils.isEmpty(this.childCols)) {
            return 0;
        }
        int depth = 0;
        for (TableHeaderColumnInfo childCol : this.childCols) {
            depth = Math.max(depth, childCol.getDepth() + 1);
        }
        return depth;
    }

    public int calcRealColCount() {
        if (CollectionUtils.isEmpty(this.childCols)) {
            return 1;
        }
        int count = 0;
        for (TableHeaderColumnInfo childCol : this.childCols) {
            count += childCol.calcRealColCount();
        }
        return count;
    }

    public TableHeaderColumnInfo addChildCol(TableHeaderColumnInfo columnInfo) {
        this.getChildCols().add(columnInfo);
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TableHeaderColumnInfo> getChildCols() {
        return this.childCols;
    }

    public int getRowSpan() {
        return this.rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }
}

