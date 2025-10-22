/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;
import com.jiuqi.nr.data.excel.wb.format.FormatUtils;

public class PermillageFormat
implements CellFormat {
    protected final int decimal;

    public PermillageFormat(int decimal) {
        this.decimal = decimal;
    }

    @Override
    public CellValueType getValueType() {
        return CellValueType.DOUBLE;
    }

    @Override
    public String getFormatStr() {
        StringBuilder basicNumberFormat = FormatUtils.getBasicNumberFormat(this.decimal, false);
        basicNumberFormat.append("\u2030");
        return basicNumberFormat.toString();
    }
}

