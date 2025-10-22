/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format.zero_show;

import com.jiuqi.nr.data.excel.wb.format.FormatUtils;
import com.jiuqi.nr.data.excel.wb.format.PermillageFormat;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroShow;

public class ZeroPermillageFormat
extends PermillageFormat
implements ZeroShow {
    private final String zeroShowStr;
    private final boolean open;

    public ZeroPermillageFormat(int decimal, String zeroShowStr, boolean open) {
        super(decimal);
        this.zeroShowStr = zeroShowStr;
        this.open = open;
    }

    @Override
    public String getFormatStr() {
        if (!this.open()) {
            return super.getFormatStr();
        }
        StringBuilder basicNumberFormat = FormatUtils.getBasicNumberFormat(this.decimal, false);
        String basicNumberFormatString = basicNumberFormat.toString();
        return basicNumberFormatString + "\u2030;\\-" + basicNumberFormatString + "\u2030;" + this.getZeroShowStr() + ";@";
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

