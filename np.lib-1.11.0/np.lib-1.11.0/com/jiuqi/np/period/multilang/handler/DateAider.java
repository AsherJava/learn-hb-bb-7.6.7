/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.multilang.handler;

import java.util.Calendar;

public class DateAider {
    public static boolean isLeapYear(int year) {
        if ((year & 3) != 0) {
            return false;
        }
        if (year % 100 != 0) {
            return true;
        }
        return year % 400 == 0;
    }

    public static final int[] calcMonthDay(int year, int dayInYear) {
        int[] result = new int[2];
        Calendar date = Calendar.getInstance();
        date.set(1, year);
        date.set(6, dayInYear);
        result[0] = date.get(2) + 1;
        result[1] = date.get(5);
        return result;
    }

    public static final int[] calcMonthTenDay(int year, int tenDayInYear) {
        int[] result = new int[]{1 + (tenDayInYear - 1) / 3, 1 + (tenDayInYear - 1) % 3};
        return result;
    }

    public static final int calcDayInYear(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(1, year);
        date.set(2, month - 1);
        date.set(5, day);
        return date.get(6);
    }
}

