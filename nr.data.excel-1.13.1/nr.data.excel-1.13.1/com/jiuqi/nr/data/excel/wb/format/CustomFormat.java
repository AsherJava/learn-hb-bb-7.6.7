/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;

public class CustomFormat
implements CellFormat {
    private final CellValueType cellValueType;
    private final String formatString;

    public CustomFormat(CellValueType cellValueType, String formatString) {
        this.cellValueType = cellValueType;
        this.formatString = formatString;
    }

    @Override
    public CellValueType getValueType() {
        return this.cellValueType;
    }

    @Override
    public String getFormatStr() {
        return this.formatString;
    }
}

