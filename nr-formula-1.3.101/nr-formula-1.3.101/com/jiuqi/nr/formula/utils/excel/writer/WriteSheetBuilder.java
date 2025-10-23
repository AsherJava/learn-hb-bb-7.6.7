/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.writer;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.writer.WriteSheet;

public final class WriteSheetBuilder {
    private WriteSheet writeSheet = new WriteSheet();

    public WriteSheetBuilder sheetIndex(int sheetIndex) {
        this.writeSheet.setSheetIndex(sheetIndex);
        return this;
    }

    public WriteSheetBuilder sheetName(String sheetName) {
        this.writeSheet.setSheetName(sheetName);
        return this;
    }

    public WriteSheet build() {
        return this.writeSheet;
    }

    public WriteSheetBuilder sheetHeadClass(Class<? extends ExcelEntity> headClass) {
        this.writeSheet.setZlass(headClass);
        return this;
    }
}

