/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;
import com.jiuqi.nr.data.excel.wb.format.Currency;
import com.jiuqi.nr.data.excel.wb.format.FormatUtils;
import com.jiuqi.nr.data.excel.wb.format.NegativeStyle;

public class CurrencyFormat
implements CellFormat {
    protected final Currency currency;
    protected final int decimal;
    protected final NegativeStyle negativeStyle;
    protected boolean thousands = true;

    public CurrencyFormat(Currency currency, int decimal, NegativeStyle negativeStyle) {
        this.currency = currency;
        this.decimal = decimal;
        this.negativeStyle = negativeStyle;
    }

    @Override
    public CellValueType getValueType() {
        return CellValueType.DOUBLE;
    }

    @Override
    public String getFormatStr() {
        StringBuilder basicNumberFormat = FormatUtils.getBasicNumberFormat(this.decimal, this.thousands);
        String basicNumberFormatString = basicNumberFormat.toString();
        String result = NegativeStyle.NS_0 == this.negativeStyle ? this.getNS_0Format(basicNumberFormatString, this.currency) : (NegativeStyle.NS_1 == this.negativeStyle ? this.getNS_1Format(basicNumberFormatString, this.currency) : this.getNS_0Format(basicNumberFormatString, this.currency));
        return result;
    }

    protected String getNS_1Format(String basicNumberFormatString, Currency currency) {
        String result = Currency.NONE == currency ? basicNumberFormatString + "_);\\(" + basicNumberFormatString + "\\)" : (Currency.CNY == currency ? "[$\u00a5-804]" + basicNumberFormatString + "_);\\(" + "[$\u00a5-804]" + basicNumberFormatString + "\\)" : (Currency.USD == currency ? "[$$-409]" + basicNumberFormatString + "_);\\(" + "[$$-409]" + basicNumberFormatString + "\\)" : (Currency.EUR == currency ? basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "_);\\(" + basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "\\)" : basicNumberFormatString + "_);\\(" + basicNumberFormatString + "\\)")));
        return result;
    }

    protected String getNS_0Format(String basicNumberFormatString, Currency currency) {
        String result = Currency.NONE == currency ? basicNumberFormatString : (Currency.CNY == currency ? "[$\u00a5-804]" + basicNumberFormatString + ";" + "[$\u00a5-804]" + "\\-" + basicNumberFormatString : (Currency.USD == currency ? "[$$-409]" + basicNumberFormatString + ";\\-" + "[$$-409]" + basicNumberFormatString : (Currency.EUR == currency ? basicNumberFormatString + "\\ " + "[$\u20ac-407]" + ";\\-" + basicNumberFormatString + "\\ " + "[$\u20ac-407]" : basicNumberFormatString)));
        return result;
    }

    public void setThousands(boolean thousands) {
        this.thousands = thousands;
    }
}

