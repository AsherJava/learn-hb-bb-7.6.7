/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.writer;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;

public class WriteSheet {
    private int sheetIndex;
    private String sheetName;
    private Class<? extends ExcelEntity> zlass;

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

    public Class<? extends ExcelEntity> getZlass() {
        return this.zlass;
    }

    public void setZlass(Class<? extends ExcelEntity> zlass) {
        this.zlass = zlass;
    }
}

