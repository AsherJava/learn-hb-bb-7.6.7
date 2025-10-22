/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellValueType;

public interface CellFormat {
    public CellValueType getValueType();

    public String getFormatStr();
}

