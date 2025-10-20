/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.query.util.DateCommonFormatEnum;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class DateUtils {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final ThreadLocal<DateFormat> DEFAULT_FORMATTER = ThreadLocal.withInitial(() -> DateUtils.createFormatter(DEFAULT_DATE_FORMAT));

    private DateUtils() {
    }

    public static DateFormat createFormatter(String format) {
        return new SimpleDateFormat(StringUtils.isEmpty(format) ? DEFAULT_DATE_FORMAT : format);
    }

    public static DateFormat createFormatter(DateCommonFormatEnum format) {
        return DateUtils.createFormatter(format == null ? null : format.toString());
    }

    public static DateFormat getDefaultFormatter() {
        return (DateFormat)DEFAULT_FORMATTER.get().clone();
    }

    public static Date dateOf(int year, int month, int day) {
        return DateUtils.calendarOf(year, month, day).getTime();
    }

    private static Calendar calendarOf(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.set(2, month - 1);
        calendar.set(5, day);
        return calendar;
    }

    private static Calendar calendarOf(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date firstDateOf(int year, int month) {
        return DateUtils.dateOf(year, month, 1);
    }

    public static Date lastDateOf(int year, int month) {
        Calendar calendar = DateUtils.calendarOf(year, month, 1);
        calendar.set(5, calendar.getActualMaximum(5));
        return calendar.getTime();
    }

    public static Date previousDateOf(Date date) {
        return DateUtils.shifting(date, 5, -1);
    }

    public static Date nextDateOf(Date date) {
        return DateUtils.shifting(date, 5, 1);
    }

    public static Date shifting(Date date, int field, int offset) {
        Calendar calendar = DateUtils.calendarOf(date);
        calendar.add(field, offset);
        return calendar.getTime();
    }

    public static Date firstSecondOf(Date date) {
        return DateUtils.firstSecond(date).getTime();
    }

    public static Date lastSecondOf(Date date) {
        Calendar calendar = DateUtils.firstSecond(date);
        calendar.add(5, 1);
        calendar.add(13, -1);
        return calendar.getTime();
    }

    private static Calendar firstSecond(Date date) {
        Calendar param = DateUtils.calendarOf(date);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, DateUtils.getDateFieldValue(param, 1));
        calendar.set(2, DateUtils.getDateFieldValue(param, 2) - 1);
        calendar.set(5, DateUtils.getDateFieldValue(param, 5));
        return calendar;
    }

    public static Date firstMonthDayOf(Date date) {
        Calendar param = DateUtils.calendarOf(date);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, DateUtils.getDateFieldValue(param, 1));
        calendar.set(2, DateUtils.getDateFieldValue(param, 2) - 1);
        return calendar.getTime();
    }

    public static Date firstYearDayOf(Date date) {
        Calendar param = DateUtils.calendarOf(date);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, DateUtils.getDateFieldValue(param, 1));
        return calendar.getTime();
    }

    public static int getDateFieldValue(Date date, int field) {
        if (null == date) {
            return -1;
        }
        return DateUtils.getDateFieldValue(DateUtils.calendarOf(date), field);
    }

    public static int getDateFieldValue(Calendar date, int field) {
        int result = date.get(field);
        return field == 2 ? result + 1 : result;
    }

    public static Date setDateFieldValue(Date date, int field, int value) {
        Calendar calendar = DateUtils.calendarOf(date);
        calendar.set(field, field == 2 ? value - 1 : value);
        return calendar.getTime();
    }

    public static String format(Date date) {
        return DateUtils.format(date, DEFAULT_DATE_FORMAT);
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        return DateUtils.getFormatter(format).format(date);
    }

    public static String format(Date date, DateCommonFormatEnum format) {
        return DateUtils.format(date, format.getFormat());
    }

    public static Date parse(String value) {
        return DateUtils.parse(value, null);
    }

    public static Date parse(String value, String format) {
        return DateUtils.parse(value, format, null);
    }

    public static Date parse(String value, String format, Date defaultValue) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        try {
            return DateUtils.getFormatter(format).parse(value);
        }
        catch (ParseException e) {
            return defaultValue;
        }
    }

    private static DateFormat getFormatter(String format) {
        if (StringUtils.isEmpty(format) || DEFAULT_DATE_FORMAT.equals(format)) {
            return DEFAULT_FORMATTER.get();
        }
        return DateUtils.createFormatter(format);
    }

    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    public static String nowDateStr() {
        return DateUtils.format(DateUtils.now());
    }

    public static String nowTimeStr() {
        return DateUtils.nowTimeStr(DATE_TIME_FORMAT);
    }

    public static String nowTimeStr(String format) {
        return DateUtils.format(DateUtils.now(), format);
    }

    public static boolean isToday(Date date) {
        return DateUtils.isSameDate(date, DateUtils.now());
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Assert.notNull((Object)date1, "date can not be null");
        Assert.notNull((Object)date2, "date can not be null");
        return DateUtils.firstSecondOf(date1).equals(DateUtils.firstSecondOf(date2));
    }

    public static Date nextIntegralOf(Date date, int field) {
        if (field > 12) {
            throw new IllegalArgumentException("at least minute");
        }
        Calendar param = DateUtils.calendarOf(date);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, DateUtils.getDateFieldValue(param, 1));
        calendar.set(2, field < 2 ? 0 : DateUtils.getDateFieldValue(param, 2) - 1);
        calendar.set(5, field < 5 ? 1 : DateUtils.getDateFieldValue(param, 5));
        calendar.set(11, field < 10 ? 0 : DateUtils.getDateFieldValue(param, 11));
        calendar.set(12, field < 12 ? 0 : DateUtils.getDateFieldValue(param, 12));
        calendar.add(field, 1);
        return calendar.getTime();
    }

    public static long diffOf(Date d1, Date d2, int field) {
        if (field < 5) {
            throw new IllegalArgumentException("at most date");
        }
        long diff = d2.getTime() - d1.getTime();
        if (field <= 13) {
            diff /= 1000L;
        }
        if (field <= 12) {
            diff /= 60L;
        }
        if (field <= 10) {
            diff /= 60L;
        }
        if (field == 5) {
            diff /= 24L;
        }
        return diff;
    }

    public static int compare(Date date1, Date date2, boolean nullMin) {
        if (date1 == null && date2 == null) {
            return 0;
        }
        if (date1 == null) {
            return nullMin ? -1 : 1;
        }
        if (date2 == null) {
            return nullMin ? 1 : -1;
        }
        return date1.compareTo(date2);
    }

    public static Date max(Date date1, Date date2, boolean nullMin) {
        return DateUtils.compare(date1, date2, nullMin) < 1 ? date2 : date1;
    }

    public static int getYearOfDate(Date date) {
        return DateUtils.getDateFieldValue(date, 1);
    }

    public static Date convertLDTToDate(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertLDTToDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertDateToLDT(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate convertDateToLD(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String formatSecond(long second) {
        Object[] array;
        String format;
        if (second == 0L) {
            return "0\u79d2";
        }
        String formatedTime = "";
        Double s = Long.valueOf(second).doubleValue();
        Integer hours = (int)(s / 3600.0);
        Integer minutes = (int)(s / 60.0 - (double)(hours * 60));
        Integer seconds = (int)(s - (double)(minutes * 60) - (double)(hours * 60 * 60));
        if (hours > 0) {
            format = "%1$,d\u65f6%2$,d\u5206%3$,d\u79d2";
            array = new Object[]{hours, minutes, seconds};
        } else if (minutes > 0) {
            format = "%1$,d\u5206%2$,d\u79d2";
            array = new Object[]{minutes, seconds};
        } else {
            format = "%1$,d\u79d2";
            array = new Object[]{seconds};
        }
        formatedTime = String.format(format, array);
        return formatedTime;
    }
}

