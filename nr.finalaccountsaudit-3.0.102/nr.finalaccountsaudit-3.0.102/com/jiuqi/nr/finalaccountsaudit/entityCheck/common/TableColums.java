/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

public class TableColums {
    private String columnKey;
    private String title;
    private String slot;
    private int colIdx;
    private int width;

    public TableColums(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnKey() {
        return this.columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getColIdx() {
        return this.colIdx;
    }

    public void setColIdx(int colIdx) {
        this.colIdx = colIdx;
    }

    public TableColums(String columnKey, String title, int width) {
        this.columnKey = columnKey;
        this.title = title;
        this.width = width;
    }

    public TableColums(String columnKey, String title, String slot, int colIdx) {
        this.columnKey = columnKey;
        this.title = title;
        this.slot = slot;
        this.colIdx = colIdx;
    }

    public TableColums(String columnKey, String title, String slot, int colIdx, int width) {
        this.columnKey = columnKey;
        this.title = title;
        this.slot = slot;
        this.colIdx = colIdx;
        this.width = width;
    }

    public TableColums(String columnKey, String title, String slot) {
        this.columnKey = columnKey;
        this.title = title;
        this.slot = slot;
    }

    public String getSlot() {
        return this.slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public TableColums(String columnKey, String title) {
        this.columnKey = columnKey;
        this.title = title;
    }

    public TableColums() {
    }
}

