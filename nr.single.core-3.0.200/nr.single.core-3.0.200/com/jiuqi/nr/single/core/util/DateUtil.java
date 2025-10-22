/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util;

import java.util.Date;

public class DateUtil {
    public static int[] MONTHDAY1 = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int[] MONTHDAY2 = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final Date DATE = new Date();
    public static final long LOCALOFFSET = (long)DATE.getTimezoneOffset() * 60000L;

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static double dateToDouble(int year, int month, int day) {
        int i;
        int[] monthDays = MONTHDAY1;
        if (DateUtil.isLeapYear(year)) {
            monthDays = MONTHDAY2;
        }
        double dateValue = 0.0;
        int days = day;
        for (i = 1; i < month; ++i) {
            days += monthDays[i];
        }
        i = year - 1;
        int dateDelta = 693594;
        int dateValue1 = i * 365 + i / 4 - i / 100 + i / 400 + days + dateDelta;
        dateValue = dateValue1;
        return dateValue;
    }

    public static double timeToDouble(int hour, int min, int sec, int mSec) {
        if (hour < 24 && min < 60 && sec < 60 && mSec < 1000) {
            int SecsPerDay = 86400;
            int MSecsPerDay = SecsPerDay * 1000;
            return (double)(hour * 3600000 + min * 60000 + sec * 1000 + mSec) / (double)MSecsPerDay;
        }
        return 0.0;
    }

    public static Date getDateByDouble(double num) {
        Date date = new Date();
        date.setTime((long)((num - 25569.0) * 24.0 * 360.0 * 100.0 + (double)LOCALOFFSET));
        return date;
    }

    public static double getDoubleByDate(Date date) {
        double dd = (double)(date.getTime() - LOCALOFFSET) / 24.0 / 360.0 / 1000.0 + 25569.0;
        return dd;
    }
}

