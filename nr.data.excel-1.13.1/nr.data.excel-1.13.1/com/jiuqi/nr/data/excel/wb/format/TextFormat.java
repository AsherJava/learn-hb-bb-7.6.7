/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;

public class TextFormat
implements CellFormat {
    public static TextFormat getInstance() {
        return TextFormatInstance.INSTANCE;
    }

    @Override
    public CellValueType getValueType() {
        return CellValueType.STRING;
    }

    @Override
    public String getFormatStr() {
        return "@";
    }

    private static class TextFormatInstance {
        private static final TextFormat INSTANCE = new TextFormat();

        private TextFormatInstance() {
        }
    }
}

