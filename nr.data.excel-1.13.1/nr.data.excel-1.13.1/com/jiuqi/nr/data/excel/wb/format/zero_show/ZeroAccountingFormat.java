/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format.zero_show;

import com.jiuqi.nr.data.excel.wb.format.AccountingFormat;
import com.jiuqi.nr.data.excel.wb.format.Currency;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroShow;

public class ZeroAccountingFormat
extends AccountingFormat
implements ZeroShow {
    private final String zeroShowStr;
    private final boolean open;

    public ZeroAccountingFormat(Currency currency, int decimal, String zeroShowStr, boolean open) {
        super(currency, decimal);
        this.zeroShowStr = zeroShowStr;
        this.open = open;
    }

    @Override
    protected String getFormat(String basicNumberFormatString, Currency currency, String actPlaceHolder) {
        if (!this.open()) {
            return super.getFormat(basicNumberFormatString, currency, actPlaceHolder);
        }
        String result = Currency.NONE == currency ? "_ * " + basicNumberFormatString + "_ ;_ * \\-" + basicNumberFormatString + "_ ;" + this.getZeroShowStr() + ";_ @_ " : (Currency.CNY == currency ? "_ [$\u00a5-804]* " + basicNumberFormatString + "_ ;_ " + "[$\u00a5-804]" + "* \\-" + basicNumberFormatString + "_ ;" + this.getZeroShowStr() + ";_ @_ " : (Currency.USD == currency ? "_-[$$-409]* " + basicNumberFormatString + "_ ;_-" + "[$$-409]" + "* \\-" + basicNumberFormatString + "\\ ;" + this.getZeroShowStr() + ";_-@_ " : (Currency.EUR == currency ? "_-* " + basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "_-;\\-* " + basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "_-;" + this.getZeroShowStr() + ";_-@_-" : "_ * " + basicNumberFormatString + "_ ;_ * \\-" + basicNumberFormatString + "_ ;" + this.getZeroShowStr() + ";_ @_ ")));
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

