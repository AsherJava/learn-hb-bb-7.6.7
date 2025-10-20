/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class DateTimeUtil {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    private DateTimeUtil() {
    }

    public static double getUtcMillisFromEpoch(Calendar calendar) {
        if (calendar == null) {
            calendar = new GregorianCalendar();
        }
        return calendar.getTimeInMillis();
    }

    public static Calendar getCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar getCurrentTimeCalendar() {
        return new GregorianCalendar();
    }

    public static Date getCurrentTimeDate() {
        return new Date();
    }

    public static Calendar addDaysToCalendar(Calendar calendar, int days) {
        calendar.add(6, days);
        return calendar;
    }

    public static boolean isInPast(Date date) {
        return date.before(DateTimeUtil.getCurrentTimeDate());
    }

    public static long getRelativeTime(Date date) {
        return date.getTime();
    }

    public static Date addMillisToDate(Date date, long millis) {
        return new Date(DateTimeUtil.getRelativeTime(date) + millis);
    }

    public static Date addDaysToDate(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        ((Calendar)cal).add(6, days);
        return cal.getTime();
    }

    public static Date addYearsToDate(Date date, int years) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        ((Calendar)cal).add(1, years);
        return cal.getTime();
    }

    public static Date parseWithDefaultPattern(String date) {
        return DateTimeUtil.parse(date, DEFAULT_PATTERN);
    }

    public static Date parse(String date, String format) {
        try {
            return DateTimeUtil.initParserSDF(format).parse(date);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatWithDefaultPattern(Date date) {
        return DateTimeUtil.format(date, DEFAULT_PATTERN);
    }

    public static String format(Date date, String pattern) {
        return DateTimeUtil.initParserSDF(pattern).format(date);
    }

    public static long getCurrentTimeZoneOffset(Date date) {
        TimeZone tz = TimeZone.getDefault();
        return tz.getOffset(date.getTime());
    }

    public static String dateToString(Calendar date) {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").format(date.getTime());
    }

    private static DateFormat initParserSDF(String pattern) {
        SimpleDateFormat parserSDF = new SimpleDateFormat(pattern);
        parserSDF.setCalendar(new GregorianCalendar());
        return parserSDF;
    }
}

