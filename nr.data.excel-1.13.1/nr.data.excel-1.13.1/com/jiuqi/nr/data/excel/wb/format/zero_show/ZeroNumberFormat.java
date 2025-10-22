/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format.zero_show;

import com.jiuqi.nr.data.excel.wb.format.NegativeStyle;
import com.jiuqi.nr.data.excel.wb.format.NumberFormat;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroShow;

public class ZeroNumberFormat
extends NumberFormat
implements ZeroShow {
    private final String zeroShowStr;
    private final boolean open;

    public ZeroNumberFormat(int decimal, boolean thousands, NegativeStyle negativeStyle, String zeroShowStr, boolean open) {
        super(decimal, thousands, negativeStyle);
        this.zeroShowStr = zeroShowStr;
        this.open = open;
    }

    @Override
    protected String getNS_1Format(StringBuilder basicNumberFormat) {
        if (!this.open()) {
            return super.getNS_1Format(basicNumberFormat);
        }
        String basicNumberFormatString = basicNumberFormat.toString();
        return basicNumberFormat.append("_);\\(").append(basicNumberFormatString).append("\\)").append(";").append(this.getZeroShowStr()).append(";@").toString();
    }

    @Override
    protected String getNS_0Format(StringBuilder basicNumberFormat) {
        if (!this.open()) {
            return super.getNS_0Format(basicNumberFormat);
        }
        String basicNumberFormatString = basicNumberFormat.toString();
        return basicNumberFormatString + "_ ;\\-" + basicNumberFormatString + ";" + this.getZeroShowStr() + ";@";
    }

    @Override
    public boolean open() {
        return this.open;
    }

    @Override
    public String getZeroShowStr() {
        return this.zeroShowStr;
    }
}

