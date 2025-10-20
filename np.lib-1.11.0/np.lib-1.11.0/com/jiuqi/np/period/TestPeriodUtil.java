/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class TestPeriodUtil {
    public static void main(String[] args) {
        PeriodWrapper pw1 = new PeriodWrapper("2022Z0001");
        PeriodWrapper pw2 = new PeriodWrapper("2022Z0003");
        System.out.println(Arrays.toString(PeriodUtil.getTimesArr(pw1, pw2)));
        PeriodWrapper pw3 = new PeriodWrapper("2020R0002");
        PeriodType pt = PeriodType.fromType(pw3.getType());
        Calendar calendar = pt.toCalendar(pw3);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        System.out.println(calendar.getTime());
    }

    public static String[] getTimesArr(PeriodWrapper pw) {
        String[] arr = new String[2];
        PeriodType pt = PeriodType.fromType(pw.getType());
        Calendar c = pt.toCalendar(pw);
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dFormat.format(c.getTime()));
        c.add(6, 6);
        dFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dFormat.format(c.getTime()));
        return arr;
    }
}

