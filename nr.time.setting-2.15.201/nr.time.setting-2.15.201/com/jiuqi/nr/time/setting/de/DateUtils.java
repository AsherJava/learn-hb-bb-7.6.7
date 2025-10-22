/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodUtil
 */
package com.jiuqi.nr.time.setting.de;

import com.jiuqi.np.period.PeriodUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getYestoday() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    public static String getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, 0);
        cal.set(5, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00\uff1a00\uff1a00";
    }

    public static String getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, cal.getActualMaximum(5));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    public static String getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(4, 0);
        cal.set(7, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
    }

    public static String getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(7, cal.getActualMaximum(7));
        cal.add(7, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    public static String getYearStart() {
        return new SimpleDateFormat("yyyy").format(new Date()) + "-01-01";
    }

    public static String getYearEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2, calendar.getActualMaximum(2));
        calendar.set(5, calendar.getActualMaximum(5));
        Date currYearLast = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(currYearLast) + " 23:59:59";
    }

    public Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        cal.set(5, cal.getActualMaximum(5));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        cal.set(5, cal.getMinimum(5));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DateUtils.getFirstDayOfQuarter(calendar.get(1), DateUtils.getQuarterOfYear(date));
    }

    public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        month = quarter == 1 ? Integer.valueOf(0) : (quarter == 2 ? Integer.valueOf(3) : (quarter == 3 ? Integer.valueOf(6) : (quarter == 4 ? Integer.valueOf(9) : Integer.valueOf(calendar.get(2)))));
        return DateUtils.getFirstDayOfMonth(year, month);
    }

    public static Date getLastDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DateUtils.getLastDayOfQuarter(calendar.get(1), DateUtils.getQuarterOfYear(date));
    }

    public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        month = quarter == 1 ? Integer.valueOf(2) : (quarter == 2 ? Integer.valueOf(5) : (quarter == 3 ? Integer.valueOf(8) : (quarter == 4 ? Integer.valueOf(11) : Integer.valueOf(calendar.get(2)))));
        return DateUtils.getLastDayOfMonth(year, month);
    }

    public static int getQuarterOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) / 3 + 1;
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), 1);
        calendar.roll(5, -1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), 1);
        return calendar.getTime();
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

    public static Date getFristDayOfTenday(Date date) {
        int dayOfMonth = DateUtils.dayOfMonth(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (dayOfMonth < 11) {
            calendar.set(5, 1);
        } else if (11 <= dayOfMonth && dayOfMonth < 21) {
            calendar.set(5, 11);
        } else {
            calendar.set(5, 21);
        }
        return calendar.getTime();
    }

    public static Date getLastDayOfTenday(Date date) {
        int dayOfMonth = DateUtils.dayOfMonth(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (dayOfMonth < 11) {
            calendar.set(5, 10);
        } else if (11 <= dayOfMonth && dayOfMonth < 21) {
            calendar.set(5, 20);
        } else {
            calendar.set(5, 1);
            calendar.add(5, -1);
        }
        return calendar.getTime();
    }

    public static int dayOfMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int a = ca.get(5);
        return a;
    }

    public static int dayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yearDay = calendar.get(6);
        return yearDay;
    }

    public static Date year(String period, String periodSub) {
        String[] split = period.split(periodSub);
        String year = split[0];
        Date yearLast = DateUtils.getYearLast(Integer.valueOf(year));
        return yearLast;
    }

    public static Date halfYear(String period, String periodSub) {
        String[] split = period.split(periodSub);
        String year = split[0];
        String date = split[1];
        if (date.substring(3, 4).equals("1")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1, Integer.valueOf(year));
            calendar.set(2, 7);
            calendar.roll(6, -1);
            return calendar.getTime();
        }
        if (date.substring(3, 4).equals("2")) {
            Date yearLast = DateUtils.getYearLast(Integer.valueOf(year));
            return yearLast;
        }
        return null;
    }

    public static Date season(String period, String periodSub) {
        String[] split = period.split(periodSub);
        String year = split[0];
        String date = split[1];
        if (date.substring(3, 4).equals("1")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 1);
            return lastDayOfQuarter;
        }
        if (date.substring(3, 4).equals("2")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 2);
            return lastDayOfQuarter;
        }
        if (date.substring(3, 4).equals("3")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 3);
            return lastDayOfQuarter;
        }
        if (date.substring(3, 4).equals("4")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 4);
            return lastDayOfQuarter;
        }
        return null;
    }

    public static Date month(String period) {
        GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)period);
        Date time = period2Calendar.getTime();
        Date lastDayOfMonth = DateUtils.getLastDayOfMonth(time);
        return lastDayOfMonth;
    }

    public static Date tenday(String period, String periodSub) {
        GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)period);
        Date lastDayOfTenday = DateUtils.getLastDayOfTenday(period2Calendar.getTime());
        return lastDayOfTenday;
    }

    public static Date day(String period, String periodSub) {
        String[] split = period.split(periodSub);
        String year = split[0];
        String day = split[1];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(year), 1, 1);
        if (Integer.valueOf(day.substring(1, 4)) >= 100) {
            calendar.add(5, Integer.valueOf(day.substring(1, 4)));
        } else {
            calendar.add(5, Integer.valueOf(day.substring(2, 4)));
        }
        return calendar.getTime();
    }

    public static Date year(String period, String periodSub, int dueDataOff) {
        String[] split = period.split(periodSub);
        String year = split[0];
        Date yearLast = DateUtils.getYearLast(Integer.valueOf(year), dueDataOff);
        return yearLast;
    }

    public static Date halfYear(String period, String periodSub, int dueDataOff) {
        String[] split = period.split(periodSub);
        String year = split[0];
        String date = split[1];
        if (date.substring(3, 4).equals("1")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1, Integer.valueOf(year));
            calendar.set(2, 7);
            calendar.set(11, 23);
            calendar.set(12, 59);
            calendar.set(13, 59);
            calendar.roll(6, -1);
            calendar.add(5, dueDataOff);
            return calendar.getTime();
        }
        if (date.substring(3, 4).equals("2")) {
            Date yearLast = DateUtils.getYearLast(Integer.valueOf(year), dueDataOff);
            return yearLast;
        }
        return null;
    }

    public static Date season(String period, String periodSub, int dueDataOff) {
        String[] split = period.split(periodSub);
        String year = split[0];
        String date = split[1];
        if (date.substring(3, 4).equals("1")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 1, dueDataOff);
            return lastDayOfQuarter;
        }
        if (date.substring(3, 4).equals("2")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 2, dueDataOff);
            return lastDayOfQuarter;
        }
        if (date.substring(3, 4).equals("3")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 3, dueDataOff);
            return lastDayOfQuarter;
        }
        if (date.substring(3, 4).equals("4")) {
            Date lastDayOfQuarter = DateUtils.getLastDayOfQuarter(Integer.valueOf(year), 4, dueDataOff);
            return lastDayOfQuarter;
        }
        return null;
    }

    public static Date month(String period, int dueDataOff) {
        GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)period);
        Date time = period2Calendar.getTime();
        Date lastDayOfMonth = DateUtils.getLastDayOfMonth(time, dueDataOff);
        return lastDayOfMonth;
    }

    public static Date tenday(String period, String periodSub, int dueDataOff) {
        GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)period);
        Date lastDayOfTenday = DateUtils.getLastDayOfTenday(period2Calendar.getTime(), dueDataOff);
        return lastDayOfTenday;
    }

    public static Date day(String period, String periodSub, int dueDataOff) {
        String[] split = period.split(periodSub);
        String year = split[0];
        String day = split[1];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(year), 1, 1);
        if (Integer.valueOf(day.substring(1, 4)) >= 100) {
            calendar.add(5, Integer.valueOf(day.substring(1, 4)) + dueDataOff);
        } else {
            calendar.add(5, Integer.valueOf(day.substring(2, 4)) + dueDataOff);
        }
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static Date getYearLast(int year, int dueDataOff) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.roll(6, -1);
        calendar.add(5, dueDataOff);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static Date getLastDayOfQuarter(Integer year, Integer quarter, int dueDataOff) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        month = quarter == 1 ? Integer.valueOf(2) : (quarter == 2 ? Integer.valueOf(5) : (quarter == 3 ? Integer.valueOf(8) : (quarter == 4 ? Integer.valueOf(11) : Integer.valueOf(calendar.get(2)))));
        return DateUtils.getLastDayOfMonth(year, month, dueDataOff);
    }

    public static Date getLastDayOfMonth(Integer year, Integer month, int dueDataOff) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(1);
        }
        if (month == null) {
            month = calendar.get(2);
        }
        calendar.set(year, month, 1);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.roll(5, -1);
        calendar.add(5, dueDataOff);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date, int dueDataOff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2), 1);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.roll(5, -1);
        calendar.add(5, dueDataOff);
        return calendar.getTime();
    }

    public static Date getLastDayOfTenday(Date date, int dueDateOff) {
        int dayOfMonth = DateUtils.dayOfMonth(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (dayOfMonth < 11) {
            calendar.set(5, 10 + dueDateOff);
        } else if (11 <= dayOfMonth && dayOfMonth < 21) {
            calendar.set(5, 20 + dueDateOff);
        } else {
            calendar.set(5, 1);
            calendar.add(5, -1);
            calendar.add(5, dueDateOff);
        }
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }
}

