/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.elementtable.impl;

import com.jiuqi.gcreport.common.elementtable.impl.ElementExcelRangeVO;

public class ElementTableSpanVO {
    private int rowIndex = 0;
    private int columnIndex = 0;
    private int rowspan = 1;
    private int colspan = 1;
    private ElementExcelRangeVO mergeRange;

    public ElementTableSpanVO(int rowIndex, int columnIndex, int rowspan, int colspan) {
        this.setColspan(colspan);
        this.setColumnIndex(columnIndex);
        this.setRowIndex(rowIndex);
        this.setRowspan(rowspan);
        this.initMergeRange();
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
        this.initMergeRange();
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
        this.initMergeRange();
    }

    public int getRowspan() {
        return this.rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
        this.initMergeRange();
    }

    public int getColspan() {
        return this.colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
        this.initMergeRange();
    }

    public ElementExcelRangeVO getMergeRange() {
        return this.mergeRange;
    }

    public void setMergeRange(ElementExcelRangeVO mergeRange) {
        this.mergeRange = mergeRange;
    }

    private void initMergeRange() {
        this.mergeRange = new ElementExcelRangeVO(this.columnIndex, this.rowIndex + 1, this.columnIndex + this.colspan - 1, this.rowIndex + 1 + this.rowspan - 1);
    }
}

