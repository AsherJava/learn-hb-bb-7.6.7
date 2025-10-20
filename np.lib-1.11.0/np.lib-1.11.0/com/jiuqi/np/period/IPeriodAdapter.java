/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period;

import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import java.text.ParseException;
import java.util.Date;

public interface IPeriodAdapter {
    public String getPeriodTitle(String var1);

    public Date[] getPeriodDateRegion(String var1) throws ParseException;

    public String[] getPeriodDateStrRegion(String var1) throws ParseException;

    public String modify(String var1, PeriodModifier var2);

    public String modify(String var1, PeriodModifier var2, IPeriodAdapter var3);

    public int getPeriodType(String var1);

    public boolean modify(PeriodWrapper var1, PeriodModifier var2);

    public boolean modify(PeriodWrapper var1, PeriodModifier var2, IPeriodAdapter var3);

    public boolean modifyYear(PeriodWrapper var1, int var2);

    public boolean modifyPeriod(PeriodWrapper var1, int var2);

    public String getPeriodTitle(PeriodWrapper var1);

    public Date[] getPeriodDateRegion(PeriodWrapper var1) throws ParseException;

    public String[] getPeriodDateStrRegion(PeriodWrapper var1) throws ParseException;

    public boolean priorYear(PeriodWrapper var1);

    public boolean nextYear(PeriodWrapper var1);

    public boolean priorPeriod(PeriodWrapper var1);

    public boolean nextPeriod(PeriodWrapper var1);

    public int getPeriodType(PeriodWrapper var1);

    public String getStandardPeriodStr(String var1);

    public String getCustomPeriodStr(String var1);
}

