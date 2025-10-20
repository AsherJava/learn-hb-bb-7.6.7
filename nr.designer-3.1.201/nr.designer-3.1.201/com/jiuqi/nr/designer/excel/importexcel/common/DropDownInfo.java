/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import com.jiuqi.nr.designer.excel.importexcel.common.EnumDictInfo;

public class DropDownInfo {
    private String sourceName;
    private int sourceBeginRow;
    private int sourceEndRow;
    private int sourceCol;
    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;
    private String formKey;
    private EnumDictInfo enumDictInfo;

    public String getSourceName() {
        return this.sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public int getSourceBeginRow() {
        return this.sourceBeginRow;
    }

    public void setSourceBeginRow(int sourceBeginRow) {
        this.sourceBeginRow = sourceBeginRow;
    }

    public int getSourceEndRow() {
        return this.sourceEndRow;
    }

    public void setSourceEndRow(int sourceEndRow) {
        this.sourceEndRow = sourceEndRow;
    }

    public int getSourceCol() {
        return this.sourceCol;
    }

    public void setSourceCol(int sourceCol) {
        this.sourceCol = sourceCol;
    }

    public int getFirstRow() {
        return this.firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return this.lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getFirstCol() {
        return this.firstCol;
    }

    public void setFirstCol(int firstCol) {
        this.firstCol = firstCol;
    }

    public int getLastCol() {
        return this.lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    public EnumDictInfo getEnumDictInfo() {
        return this.enumDictInfo;
    }

    public void setEnumDictInfo(EnumDictInfo enumDictInfo) {
        this.enumDictInfo = enumDictInfo;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

