/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.util.FiscalCalendar;

@Deprecated
public class FinancialCalendar
extends FiscalCalendar {
    private static final long serialVersionUID = -548288508103983940L;

    public FinancialCalendar(int fiscalNum) {
        super(fiscalNum);
    }

    public FinancialCalendar(int year, int month, int dayOfMonth, int fiscalNum) {
        super(year, month, dayOfMonth, fiscalNum);
    }
}

