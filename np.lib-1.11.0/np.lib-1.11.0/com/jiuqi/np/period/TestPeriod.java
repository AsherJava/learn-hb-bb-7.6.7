/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period;

import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestPeriod {
    public static void main(String[] args) {
        String period = "2021Y0004";
        PeriodWrapper pw = new PeriodWrapper(period);
        PeriodType pt = PeriodType.fromType(pw.getType());
        TestPeriod.printPeriodCalendar(pw, pt);
        PeriodModifier pm = PeriodModifier.parse("-1H");
        PeriodModifier pmType = PeriodModifier.parse("H");
        pm.union(pmType);
        pm.modify(pw);
        pt = PeriodType.fromType(pw.getType());
        TestPeriod.printPeriodCalendar(pw, pt);
    }

    private static void printPeriodCalendar(PeriodWrapper pw, PeriodType pt) {
        Calendar c = pt.toCalendar(pw);
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(pw.toString() + " : " + dFormat.format(c.getTime()));
    }
}

