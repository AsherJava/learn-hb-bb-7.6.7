/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    public static final String DATE_FORMAT_YMDHMS_WITH_T = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final long getMinTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static final long getMaxTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(9999, 11, 31, 0, 0, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        return DateTimeUtil.dateStr(date, DATE_FORMAT_YMDHMS);
    }

    @Deprecated
    public static String objToStr(Object obj) {
        if (obj == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        try {
            Date date = format.parse(obj.toString());
            return DateTimeUtil.dateToStr(date);
        }
        catch (ParseException e) {
            Date date;
            format = new SimpleDateFormat(DATE_FORMAT_YMDHMS_WITH_T);
            try {
                date = format.parse(obj.toString());
            }
            catch (ParseException e1) {
                return null;
            }
            return DateTimeUtil.dateToStr(date);
        }
    }

    public static String dateStr(Date date, String f) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(f);
        return format.format(date);
    }

    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date strToDate(String str, String f) {
        if (str == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(f);
        try {
            return format.parse(str);
        }
        catch (ParseException e) {
            return null;
        }
    }
}

