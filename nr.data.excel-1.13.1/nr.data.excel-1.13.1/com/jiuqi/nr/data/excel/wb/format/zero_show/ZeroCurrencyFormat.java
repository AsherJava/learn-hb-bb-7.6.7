/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format.zero_show;

import com.jiuqi.nr.data.excel.wb.format.Currency;
import com.jiuqi.nr.data.excel.wb.format.CurrencyFormat;
import com.jiuqi.nr.data.excel.wb.format.NegativeStyle;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroShow;

public class ZeroCurrencyFormat
extends CurrencyFormat
implements ZeroShow {
    private final String zeroShowStr;
    private final boolean open;

    public ZeroCurrencyFormat(Currency currency, int decimal, NegativeStyle negativeStyle, String zeroShowStr, boolean open) {
        super(currency, decimal, negativeStyle);
        this.zeroShowStr = zeroShowStr;
        this.open = open;
    }

    @Override
    protected String getNS_1Format(String basicNumberFormatString, Currency currency) {
        if (!this.open()) {
            return super.getNS_1Format(basicNumberFormatString, currency);
        }
        String result = Currency.NONE == currency ? basicNumberFormatString + "_);\\(" + basicNumberFormatString + "\\);" + this.getZeroShowStr() + ";@" : (Currency.CNY == currency ? "[$\u00a5-804]" + basicNumberFormatString + "_);\\(" + "[$\u00a5-804]" + basicNumberFormatString + "\\);" + this.getZeroShowStr() + ";@" : (Currency.USD == currency ? "[$$-409]" + basicNumberFormatString + "_);\\(" + "[$$-409]" + basicNumberFormatString + "\\);" + this.getZeroShowStr() + ";@" : (Currency.EUR == currency ? basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "_);\\(" + basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "\\);" + this.getZeroShowStr() + ";@" : basicNumberFormatString + "_);\\(" + basicNumberFormatString + "\\);" + this.getZeroShowStr() + ";@")));
        return result;
    }

    @Override
    protected String getNS_0Format(String basicNumberFormatString, Currency currency) {
        if (!this.open()) {
            return super.getNS_0Format(basicNumberFormatString, currency);
        }
        String result = Currency.NONE == currency ? basicNumberFormatString + ";\\-" + basicNumberFormatString + ";" + this.getZeroShowStr() + ";@" : (Currency.CNY == currency ? "[$\u00a5-804]" + basicNumberFormatString + ";" + "[$\u00a5-804]" + "\\-" + basicNumberFormatString + ";" + this.getZeroShowStr() + ";@" : (Currency.USD == currency ? "[$$-409]" + basicNumberFormatString + ";\\-" + "[$$-409]" + basicNumberFormatString + ";" + this.getZeroShowStr() + ";@" : (Currency.EUR == currency ? basicNumberFormatString + "\\ " + "[$\u20ac-407]" + ";\\-" + basicNumberFormatString + "\\ " + "[$\u20ac-407]" + ";" + this.getZeroShowStr() + ";@" : basicNumberFormatString + ";\\-" + basicNumberFormatString + ";" + this.getZeroShowStr() + ";@")));
        return result;
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

