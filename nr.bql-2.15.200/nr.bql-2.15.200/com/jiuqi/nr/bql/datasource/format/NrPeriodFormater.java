/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.time.IFormatProvider$Formatter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.nr.bql.datasource.format;

import com.jiuqi.bi.util.time.IFormatProvider;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.util.Calendar;
import java.util.Locale;

public class NrPeriodFormater
implements IFormatProvider.Formatter {
    private IPeriodProvider provider;
    private Locale locale;

    public NrPeriodFormater(IPeriodProvider provider, Locale locale) {
        this.provider = provider;
        this.locale = locale;
    }

    public String format(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            PeriodWrapper pw = this.provider.getPeriodEntity().getPeriodType().fromCalendar((Calendar)value);
            return this.provider.getPeriodTitle(pw);
        }
        return value.toString();
    }
}

