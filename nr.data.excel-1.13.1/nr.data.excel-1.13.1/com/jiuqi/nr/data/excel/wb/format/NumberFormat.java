/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;
import com.jiuqi.nr.data.excel.wb.format.FormatUtils;
import com.jiuqi.nr.data.excel.wb.format.NegativeStyle;

public class NumberFormat
implements CellFormat {
    private final int decimal;
    private final boolean thousands;
    private final NegativeStyle negativeStyle;

    public NumberFormat(int decimal, boolean thousands, NegativeStyle negativeStyle) {
        this.decimal = decimal;
        this.thousands = thousands;
        this.negativeStyle = negativeStyle;
    }

    @Override
    public CellValueType getValueType() {
        return CellValueType.DOUBLE;
    }

    @Override
    public String getFormatStr() {
        StringBuilder basicNumberFormat = FormatUtils.getBasicNumberFormat(this.decimal, this.thousands);
        if (NegativeStyle.NS_0 == this.negativeStyle) {
            return this.getNS_0Format(basicNumberFormat);
        }
        if (NegativeStyle.NS_1 == this.negativeStyle) {
            return this.getNS_1Format(basicNumberFormat);
        }
        return this.getNS_0Format(basicNumberFormat);
    }

    protected String getNS_1Format(StringBuilder basicNumberFormat) {
        String basicNumberFormatString = basicNumberFormat.toString();
        return basicNumberFormat.append("_);\\(").append(basicNumberFormatString).append("\\)").toString();
    }

    protected String getNS_0Format(StringBuilder basicNumberFormat) {
        return basicNumberFormat.append("_ ").toString();
    }
}

