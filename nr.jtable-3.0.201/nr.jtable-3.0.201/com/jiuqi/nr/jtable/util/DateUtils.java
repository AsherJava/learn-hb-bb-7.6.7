/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    public static final String YEAR_MONTH_DAY_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_SECOND_1 = "yyyy-M-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_SECOND_2 = "yyyy-MM-d HH:mm:ss";
    public static final String YEAR_MONTH_DAY_SECOND_3 = "yyyy-M-d HH:mm:ss";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_1 = "yyyy-M-dd";
    public static final String YEAR_MONTH_DAY_2 = "yyyy-MM-d";
    public static final String YEAR_MONTH_DAY_3 = "yyyy-M-d";
    public static final String HHMMSS = "HH:mm:ss";
    private static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter formatterTime_1 = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss");
    private static final DateTimeFormatter formatterTime_2 = DateTimeFormatter.ofPattern("yyyy-MM-d HH:mm:ss");
    private static final DateTimeFormatter formatterTime_3 = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-M-dd");
    private static final DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private static final DateTimeFormatter formatter_3 = DateTimeFormatter.ofPattern("yyyy-M-d");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    private static final ZoneId zoneId = ZoneId.systemDefault();
    public static final Date minDate = new Date(-30609820800000L);
    public static final Date maxDate = new Date(32503651200000L);
    private static final SimpleDateFormat COMMON_DATE_FORMAT_NY = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat COMMON_DATE_FORMAT_YR = new SimpleDateFormat("MM-dd");
    private static final SimpleDateFormat COMMON_DATE_FORMAT_Y = new SimpleDateFormat("MM");

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String dateToStrinMM(Date date) {
        SimpleDateFormat simpleDateFormat = COMMON_DATE_FORMAT_Y;
        synchronized (simpleDateFormat) {
            String updateDate = COMMON_DATE_FORMAT_Y.format(date);
            return updateDate;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String dateToStrinMMDD(Date date) {
        SimpleDateFormat simpleDateFormat = COMMON_DATE_FORMAT_YR;
        synchronized (simpleDateFormat) {
            String updateDate = COMMON_DATE_FORMAT_YR.format(date);
            return updateDate;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String dateToStringYYYYMM(Date date) {
        SimpleDateFormat simpleDateFormat = COMMON_DATE_FORMAT_NY;
        synchronized (simpleDateFormat) {
            String updateDate = COMMON_DATE_FORMAT_NY.format(date);
            return updateDate;
        }
    }

    public static String dateToStringTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        String updateDate = formatterTime.format(localDateTime);
        return updateDate;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        LocalDate localDateTime = instant.atZone(zoneId).toLocalDate();
        String updateDate = formatter.format(localDateTime);
        return updateDate;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String dateToTime(Date date) {
        SimpleDateFormat simpleDateFormat = time;
        synchronized (simpleDateFormat) {
            if (date == null) {
                return null;
            }
            String updateDate = time.format(date);
            return updateDate;
        }
    }

    public static Date stringTimeToDate(String dateStr) {
        String[] splits = dateStr.split("-");
        DateTimeFormatter temp = null;
        temp = splits[1].length() == 1 && splits[2].length() == 11 ? formatterTime_1 : (splits[1].length() == 2 && splits[2].length() == 10 ? formatterTime_2 : (splits[1].length() == 1 && splits[2].length() == 10 ? formatterTime_3 : formatterTime));
        LocalDateTime date = LocalDateTime.parse(dateStr, temp);
        Instant instant = date.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    public static Date stringToDate(String dateStr) {
        String[] splits = dateStr.split("-");
        DateTimeFormatter temp = null;
        temp = splits[1].length() == 1 && splits[2].length() == 2 ? formatter_1 : (splits[1].length() == 2 && splits[2].length() == 1 ? formatter_2 : (splits[1].length() == 1 && splits[2].length() == 1 ? formatter_3 : formatter));
        LocalDate date = LocalDate.parse(dateStr, temp);
        Instant instant = date.atStartOfDay().atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Date timeToDate(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = time;
        synchronized (simpleDateFormat) {
            return time.parse(dateStr);
        }
    }

    public static boolean checkDate(Date date) {
        if (minDate.compareTo(date) > 0) {
            return false;
        }
        return maxDate.compareTo(date) >= 0;
    }

    public static void main(String[] strs) {
    }
}

