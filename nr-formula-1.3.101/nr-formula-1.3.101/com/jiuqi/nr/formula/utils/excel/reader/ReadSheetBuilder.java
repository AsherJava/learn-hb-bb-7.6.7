/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.reader.ReadSheet;

public final class ReadSheetBuilder {
    private int sheetIndex;
    private String sheetName;
    private Class<? extends ExcelEntity> headClass;

    private ReadSheetBuilder() {
    }

    public static ReadSheetBuilder aReadSheet() {
        return new ReadSheetBuilder();
    }

    public ReadSheetBuilder sheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
        return this;
    }

    public ReadSheetBuilder sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public ReadSheetBuilder headClass(Class<? extends ExcelEntity> headClass) {
        this.headClass = headClass;
        return this;
    }

    public ReadSheet build() {
        ReadSheet readSheet = new ReadSheet();
        readSheet.setSheetIndex(this.sheetIndex);
        readSheet.setSheetName(this.sheetName);
        readSheet.setHeadClass(this.headClass);
        return readSheet;
    }
}

