/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;

public class DateFormat
implements CellFormat {
    private String formatString;

    @Override
    public CellValueType getValueType() {
        return CellValueType.DATE;
    }

    @Override
    public String getFormatStr() {
        return this.formatString;
    }
}

