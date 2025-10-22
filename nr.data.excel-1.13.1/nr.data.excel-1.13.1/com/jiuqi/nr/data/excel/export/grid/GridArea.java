/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.excel.export.grid;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataRegionDefine;

public class GridArea {
    public static final int FLOAT_TYPE_ROW = 0;
    public static final int FLOAT_TYPE_COL = 1;
    private String regionKey;
    private int floatType;
    private int top;
    private int left;
    private int right;
    private int bottom;
    private int rowSpan;
    private int rowCount;
    private int originalLeft;
    private int originalTop;
    private int originalRight;
    private int originalBottom;

    public GridArea() {
    }

    public GridArea(DataRegionDefine dataRegionDefine) {
        this.setRegionKey(dataRegionDefine.getKey());
        this.setFloatType(dataRegionDefine.getRegionKind());
        if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
            this.setRowSpan(dataRegionDefine.getRegionRight() - dataRegionDefine.getRegionLeft() + 1);
        } else {
            this.setRowSpan(dataRegionDefine.getRegionBottom() - dataRegionDefine.getRegionTop() + 1);
        }
        this.setOriginalRegion(dataRegionDefine.getRegionLeft(), dataRegionDefine.getRegionTop(), dataRegionDefine.getRegionRight(), dataRegionDefine.getRegionBottom());
    }

    public int getBottom() {
        return this.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getOriginalBottom() {
        return this.originalBottom;
    }

    public void setOriginalBottom(int originalBottom) {
        this.originalBottom = originalBottom;
    }

    public int getOriginalLeft() {
        return this.originalLeft;
    }

    public void setOriginalLeft(int originalLeft) {
        this.originalLeft = originalLeft;
    }

    public int getOriginalRight() {
        return this.originalRight;
    }

    public void setOriginalRight(int originalRight) {
        this.originalRight = originalRight;
    }

    public int getOriginalTop() {
        return this.originalTop;
    }

    public void setOriginalTop(int originalTop) {
        this.originalTop = originalTop;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getRowSpan() {
        return this.rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getFloatType() {
        return this.floatType;
    }

    public void setFloatType(int floatType) {
        this.floatType = floatType;
    }

    public void setFloatType(DataRegionKind kind) {
        this.floatType = kind == DataRegionKind.DATA_REGION_ROW_LIST ? 0 : 1;
    }

    public int getRowCount() {
        return Math.max(this.rowCount, 1);
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setRealRegion(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void moveDown(int num) {
        this.top += num;
        this.bottom += num;
    }

    public void setOriginalRegion(int originalLeft, int originalTop, int originalRight, int originalBottom) {
        this.originalLeft = originalLeft;
        this.originalTop = originalTop;
        this.originalRight = originalRight;
        this.originalBottom = originalBottom;
    }
}

