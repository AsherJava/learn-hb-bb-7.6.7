/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.scheduler;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return DateUtil.getYearLast(currentYear);
    }

    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return DateUtil.getYearFirst(currentYear);
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), 1);
        calendar.roll(5, -1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DateUtil.getFirstDayOfQuarter(calendar.get(1), DateUtil.getQuarterOfYear(date));
    }

    public static Date getLastDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DateUtil.getLastDayOfQuarter(calendar.get(1), DateUtil.getQuarterOfYear(date));
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(1);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(1);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }

    public static Date getFirstDayOfHalfYear(Date date) {
        Date fristDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        fristDate = month > 6 ? DateUtil.getFirstDayOfMonth(year, 12) : DateUtil.getFirstDayOfMonth(year, 6);
        return fristDate;
    }

    public static Date getLastDayOfHalfYear(Date date) {
        Date lastDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        lastDate = month > 6 ? DateUtil.getLastDayOfMonth(year, 12) : DateUtil.getLastDayOfMonth(year, 6);
        return lastDate;
    }

    public static Date getFristDayOfXun(Date date) {
        Date firstDay = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        if (day < 10) {
            firstDay = DateUtil.getFirstDayOfMonth(year, month);
            firstDay = calendar.getTime();
        } else if (10 < day || day < 20) {
            Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(year, month);
            calendar.setTime(firstDayOfMonth);
            calendar.add(5, 10);
            firstDay = calendar.getTime();
        } else if (day > 20) {
            Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(year, month);
            calendar.setTime(firstDayOfMonth);
            calendar.add(5, 20);
            firstDay = calendar.getTime();
        }
        return firstDay;
    }

    public static Date getLastDayOfXun(Date date) {
        Date lastDay = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        if (day < 10) {
            Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(year, month);
            calendar.setTime(firstDayOfMonth);
            calendar.add(5, 9);
            lastDay = calendar.getTime();
        } else if (10 < day || day < 20) {
            Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(year, month);
            calendar.setTime(firstDayOfMonth);
            calendar.add(5, 19);
            lastDay = calendar.getTime();
        } else if (day > 20) {
            lastDay = DateUtil.getLastDayOfMonth(year, month);
        }
        return lastDay;
    }

    public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        month = quarter == 1 ? Integer.valueOf(0) : (quarter == 2 ? Integer.valueOf(3) : (quarter == 3 ? Integer.valueOf(6) : (quarter == 4 ? Integer.valueOf(9) : Integer.valueOf(calendar.get(2)))));
        return DateUtil.getFirstDayOfMonth(year, month);
    }

    public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        month = quarter == 1 ? Integer.valueOf(2) : (quarter == 2 ? Integer.valueOf(5) : (quarter == 3 ? Integer.valueOf(8) : (quarter == 4 ? Integer.valueOf(11) : Integer.valueOf(calendar.get(2)))));
        return DateUtil.getLastDayOfMonth(year, month);
    }

    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(1);
        }
        if (month == null) {
            month = calendar.get(2);
        }
        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(1);
        }
        if (month == null) {
            month = calendar.get(2);
        }
        calendar.set(year, month, 1);
        calendar.roll(5, -1);
        return calendar.getTime();
    }

    public static int getQuarterOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) / 3 + 1;
    }

    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    private String date(boolean befored, String currTime, int dayBeforeDeadline, int dayAfterDeadline) {
        SimpleDateFormat formatterToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatterToDate.parse(currTime, pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (befored) {
            calendar.add(5, -dayBeforeDeadline);
        } else {
            calendar.add(5, dayAfterDeadline);
        }
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    private String date(boolean befored, Date currTime, int dayBeforeDeadline, int dayAfterDeadline) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTime);
        if (befored) {
            calendar.add(5, -dayBeforeDeadline);
        } else {
            calendar.add(5, dayAfterDeadline);
        }
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    private String date(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, n);
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }
}

