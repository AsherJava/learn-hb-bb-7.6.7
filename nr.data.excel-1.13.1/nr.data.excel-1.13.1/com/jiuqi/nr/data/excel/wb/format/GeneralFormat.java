/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;

public class GeneralFormat
implements CellFormat {
    public static GeneralFormat getInstance() {
        return GeneralFormatInstance.INSTANCE;
    }

    @Override
    public CellValueType getValueType() {
        return CellValueType.STRING;
    }

    @Override
    public String getFormatStr() {
        return "General";
    }

    private static class GeneralFormatInstance {
        private static final GeneralFormat INSTANCE = new GeneralFormat();

        private GeneralFormatInstance() {
        }
    }
}

