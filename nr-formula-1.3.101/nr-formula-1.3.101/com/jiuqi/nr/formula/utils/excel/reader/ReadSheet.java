/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;

public class ReadSheet {
    private int sheetIndex;
    private String sheetName;
    private Class<? extends ExcelEntity> headClass;

    public Class<? extends ExcelEntity> getHeadClass() {
        return this.headClass;
    }

    public void setHeadClass(Class<? extends ExcelEntity> headClass) {
        this.headClass = headClass;
    }

    public int getSheetIndex() {
        return this.sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}

