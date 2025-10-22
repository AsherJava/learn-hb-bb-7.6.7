/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.CellValueType;
import com.jiuqi.nr.data.excel.wb.format.Currency;
import com.jiuqi.nr.data.excel.wb.format.FormatUtils;

public class AccountingFormat
implements CellFormat {
    protected final Currency currency;
    protected final int decimal;
    protected boolean thousands = true;

    public AccountingFormat(Currency currency, int decimal) {
        this.currency = currency;
        this.decimal = decimal;
    }

    @Override
    public CellValueType getValueType() {
        return CellValueType.DOUBLE;
    }

    @Override
    public String getFormatStr() {
        StringBuilder basicNumberFormat = FormatUtils.getBasicNumberFormat(this.decimal, this.thousands);
        String basicNumberFormatString = basicNumberFormat.toString();
        String actPlaceHolder = FormatUtils.getActPlaceHolder(this.decimal);
        return this.getFormat(basicNumberFormatString, this.currency, actPlaceHolder);
    }

    protected String getFormat(String basicNumberFormatString, Currency currency, String actPlaceHolder) {
        String result = Currency.NONE == currency ? "_ * " + basicNumberFormatString + "_ ;_ * \\-" + basicNumberFormatString + "_ ;_ * \"-\"" + actPlaceHolder + "_ ;_ @_ " : (Currency.CNY == currency ? "_ [$\u00a5-804]* " + basicNumberFormatString + "_ ;_ " + "[$\u00a5-804]" + "* \\-" + basicNumberFormatString + "_ ;_ " + "[$\u00a5-804]" + "* \"-\"" + actPlaceHolder + "_ ;_ @_ " : (Currency.USD == currency ? "_-[$$-409]* " + basicNumberFormatString + "_ ;_-" + "[$$-409]" + "* \\-" + basicNumberFormatString + "\\ ;_-" + "[$$-409]" + "* \"-\"" + actPlaceHolder + "_ ;_-@_ " : (Currency.EUR == currency ? "_-* " + basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "_-;\\-* " + basicNumberFormatString + "\\ " + "[$\u20ac-407]" + "_-;_-* \"-\"" + actPlaceHolder + "\\ " + "[$\u20ac-407]" + "_-;_-@_-" : "_ * " + basicNumberFormatString + "_ ;_ * \\-" + basicNumberFormatString + "_ ;_ * \"-\"" + actPlaceHolder + "_ ;_ @_ ")));
        return result;
    }

    public void setThousands(boolean thousands) {
        this.thousands = thousands;
    }
}

