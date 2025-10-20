/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.TimeGranularityTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.types.TimeGranularityTypes;
import com.jiuqi.bi.util.FiscalCalendar;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeKeyMapper;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

public class TimeHelper {
    public static final String PATTERN_TIMEKEY = "yyyyMMdd";

    private TimeHelper() {
    }

    public static Calendar newCalendar(int fiscalMinMonth, int fiscalMaxMonth) {
        if (fiscalMinMonth < 0 || fiscalMaxMonth < 0 || fiscalMinMonth == 1 && fiscalMaxMonth == 12) {
            return Calendar.getInstance();
        }
        return new FiscalCalendar(fiscalMinMonth - 1, fiscalMaxMonth - 1);
    }

    public static int getTimeValue(Calendar date, int granularity) throws TimeCalcException {
        switch (granularity) {
            case 5: {
                return date.get(5);
            }
            case 1: {
                return date.get(2) <= 5 ? 1 : 2;
            }
            case 3: {
                return date.get(2) + 1;
            }
            case 2: {
                int month = date.get(2);
                if (month < 0) {
                    return 1;
                }
                if (month > 11) {
                    return 4;
                }
                return month / 3 + 1;
            }
            case 4: {
                int day = date.get(5);
                if (day <= 10) {
                    return 1;
                }
                if (day <= 20) {
                    return 2;
                }
                return 3;
            }
            case 0: {
                return date.get(1);
            }
            case 6: {
                Calendar firstDay = TimeHelper.getFirstWeek(date.getFirstDayOfWeek(), date.get(1));
                if (firstDay.after(date)) {
                    return 0;
                }
                int days = date.get(6) - firstDay.get(6) + 1;
                return (days + 6) / 7;
            }
        }
        throw new TimeCalcException("\u672a\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + granularity);
    }

    public static Calendar setTimeValue(Calendar date, int granularity, int value) throws TimeCalcException {
        if (value <= 0) {
            throw new IllegalArgumentException("\u9519\u8bef\u7684\u65f6\u671f\u503c\uff1a" + value);
        }
        if (date == null) {
            return date;
        }
        Calendar retDate = (Calendar)date.clone();
        switch (granularity) {
            case 0: {
                retDate.set(1, value);
                break;
            }
            case 1: {
                retDate.set(2, value == 1 ? 0 : 6);
                break;
            }
            case 2: {
                retDate.set(2, (value - 1) * 3);
                break;
            }
            case 3: {
                retDate.set(2, value - 1);
                break;
            }
            case 4: {
                retDate.set(5, (value - 1) * 10 + 1);
                break;
            }
            case 5: {
                retDate.set(5, value);
                break;
            }
            case 6: {
                retDate.set(2, 0);
                retDate.set(5, 1);
                TimeHelper.alignWeekForward(retDate);
                retDate.add(6, (value - 1) * 7);
                break;
            }
            default: {
                throw new TimeCalcException("\u672a\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + granularity);
            }
        }
        return retDate;
    }

    public static Calendar getLastDay(Calendar date, int granularity) throws TimeCalcException {
        if (date == null) {
            return null;
        }
        Calendar lastDay = (Calendar)date.clone();
        switch (granularity) {
            case 5: {
                return lastDay;
            }
            case 4: {
                int day = lastDay.get(5);
                if (day <= 10) {
                    lastDay.set(5, 10);
                } else if (day <= 20) {
                    lastDay.set(5, 20);
                } else {
                    int maxDay = lastDay.getActualMaximum(5);
                    lastDay.set(5, maxDay);
                }
                return lastDay;
            }
            case 3: {
                lastDay.add(2, 1);
                lastDay.add(5, -1);
                return lastDay;
            }
            case 2: {
                lastDay.add(2, 3);
                lastDay.add(5, -1);
                return lastDay;
            }
            case 1: {
                lastDay.add(2, 6);
                lastDay.add(5, -1);
                return lastDay;
            }
            case 0: {
                lastDay.add(1, 1);
                lastDay.add(5, -1);
                return lastDay;
            }
            case 6: {
                lastDay.add(6, 6);
                return lastDay;
            }
        }
        throw new TimeCalcException("\u672a\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + granularity);
    }

    public static int getDays(Calendar date, int granularity) throws TimeCalcException {
        if (date == null) {
            return 0;
        }
        switch (granularity) {
            case 0: {
                return date.getActualMaximum(6);
            }
            case 1: {
                if (date.get(2) <= 5) {
                    return TimeHelper.getDaysOfMonths(date, 0, 5);
                }
                return TimeHelper.getDaysOfMonths(date, 6, 11);
            }
            case 2: {
                int q = date.get(2) / 3;
                return TimeHelper.getDaysOfMonths(date, q * 3, q * 3 + 2);
            }
            case 3: {
                return date.getActualMaximum(5);
            }
            case 4: {
                if (date.get(5) > 20) {
                    return date.getActualMaximum(5) - 20;
                }
                return 10;
            }
            case 5: {
                return 1;
            }
            case 6: {
                return 7;
            }
        }
        throw new TimeCalcException("\u672a\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + granularity);
    }

    private static int getDaysOfMonths(Calendar date, int startMonth, int endMonth) {
        Calendar testDate = (Calendar)date.clone();
        testDate.set(5, 1);
        int days = 0;
        for (int month = startMonth; month <= endMonth; ++month) {
            testDate.set(2, month);
            days += testDate.getActualMaximum(5);
        }
        return days;
    }

    public static Calendar alignDate(Calendar date, int granularity) throws TimeCalcException {
        if (date == null) {
            return null;
        }
        Calendar firstDay = (Calendar)date.clone();
        switch (granularity) {
            case 5: {
                return firstDay;
            }
            case 4: {
                int day = firstDay.get(5);
                if (day <= 10) {
                    firstDay.set(5, 1);
                } else if (day <= 20) {
                    firstDay.set(5, 11);
                } else {
                    firstDay.set(5, 21);
                }
                return firstDay;
            }
            case 3: {
                firstDay.set(5, 1);
                return firstDay;
            }
            case 2: {
                int month = firstDay.get(2);
                firstDay.set(2, month / 3 * 3);
                firstDay.set(5, 1);
                return firstDay;
            }
            case 1: {
                int month = firstDay.get(2);
                firstDay.set(2, month <= 5 ? 0 : 6);
                firstDay.set(5, 1);
                return firstDay;
            }
            case 0: {
                firstDay.set(2, 0);
                firstDay.set(5, 1);
                return firstDay;
            }
            case 6: {
                int day1st = date.getFirstDayOfWeek();
                int dayOfWeek = firstDay.get(7);
                if (dayOfWeek < day1st) {
                    firstDay.add(6, day1st - dayOfWeek - 7);
                } else if (dayOfWeek > day1st) {
                    firstDay.add(6, day1st - dayOfWeek);
                }
                return firstDay;
            }
        }
        throw new TimeCalcException("\u672a\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + granularity);
    }

    public static Calendar getLastDate(Calendar curDate, int curGranularity, int nextGranularity) throws TimeCalcException {
        if (curDate == null) {
            return null;
        }
        Calendar lastDay = TimeHelper.getLastDay(curDate, curGranularity);
        return TimeHelper.alignDate(lastDay, nextGranularity);
    }

    public static Calendar offsetDate(Calendar date, int granularity, int offset) throws TimeCalcException {
        Calendar newDate = (Calendar)date.clone();
        if (offset == 0) {
            return newDate;
        }
        switch (granularity) {
            case 0: {
                newDate.add(1, offset);
                return newDate;
            }
            case 1: {
                newDate.add(2, offset * 6);
                return newDate;
            }
            case 2: {
                newDate.add(2, offset * 3);
                return newDate;
            }
            case 3: {
                newDate.add(2, offset);
                return newDate;
            }
            case 4: {
                int sign = offset < 0 ? -1 : 1;
                int offsetMonth = sign * (Math.abs(offset) / 3);
                int offsetXun = sign * (Math.abs(offset) % 3);
                if (offsetMonth != 0) {
                    newDate.add(2, offsetMonth);
                }
                if (offsetXun != 0) {
                    int day = date.get(5);
                    int xun = day <= 10 ? 0 : (day <= 20 ? 1 : 2);
                    int newXun = xun + offsetXun;
                    if (newXun >= 3) {
                        newDate.add(2, 1);
                        newXun -= 3;
                    } else if (newXun < 0) {
                        newDate.add(2, -1);
                        newXun += 3;
                    }
                    newDate.set(5, newXun * 10 + 1);
                }
                return newDate;
            }
            case 5: {
                newDate.add(5, offset);
                return newDate;
            }
            case 6: {
                newDate.add(6, offset * 7);
                return newDate;
            }
        }
        throw new TimeCalcException("\u7a0b\u5e8f\u5904\u7406\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u65f6\u671f\u7c92\u5ea6\uff1a" + TimeGranularityTypes.toString((int)granularity));
    }

    public static Calendar offsetWeekDate(Calendar date, int granularity, int offset) throws TimeCalcException {
        if (offset == 0) {
            return (Calendar)date.clone();
        }
        switch (granularity) {
            case 6: {
                Calendar newDate = (Calendar)date.clone();
                newDate.add(6, offset * 7);
                return newDate;
            }
            case 0: {
                int year = TimeHelper.getTimeValue(date, 0) + offset;
                int week = TimeHelper.getTimeValue(date, 6);
                Calendar newDate = TimeHelper.getFirstWeek(date.getFirstDayOfWeek(), year);
                newDate.add(6, (week - 1) * 7);
                while (newDate.get(1) > year) {
                    newDate.add(6, -7);
                }
                return newDate;
            }
        }
        throw new TimeCalcException("\u5728\u5468\u7c92\u5ea6\u7684\u65f6\u671f\u4e0a\uff0c\u7981\u6b62\u8fdb\u884c\u9664\u4e86\u6309\u5e74\u548c\u6309\u5468\u4e4b\u5916\u7684\u504f\u79fb\u64cd\u4f5c\uff0c\u5f53\u524d\u64cd\u4f5c\u4e3a\uff1a" + TimeGranularityTypes.toString((int)granularity));
    }

    public static int countOf(Calendar date, int granularity) throws TimeCalcException {
        if (date == null) {
            return 0;
        }
        switch (granularity) {
            case 0: {
                return 1;
            }
            case 1: {
                return 2;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return date.getActualMaximum(2) - date.getActualMinimum(2) + 1;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return date.getActualMaximum(5);
            }
            case 6: {
                Calendar firstDay = TimeHelper.getFirstWeek(date.getFirstDayOfWeek(), date.get(1));
                int days = firstDay.getActualMaximum(6) - firstDay.get(6) + 1;
                return (days + 6) / 7;
            }
        }
        throw new TimeCalcException("\u7a0b\u5e8f\u5904\u7406\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u65f6\u671f\u7c92\u5ea6\uff1a" + granularity);
    }

    public static Calendar getFirstWeek(int firstDayOfWeek, int year) {
        Calendar date = Calendar.getInstance();
        date.set(1, year);
        date.set(6, 1);
        date.setFirstDayOfWeek(firstDayOfWeek);
        date.set(11, 0);
        date.set(12, 0);
        date.set(13, 0);
        date.set(14, 0);
        TimeHelper.alignWeekForward(date);
        return date;
    }

    public static void alignWeekForward(Calendar date) {
        int dayOfWeek = date.get(7);
        if (date.getFirstDayOfWeek() < dayOfWeek) {
            date.add(6, date.getFirstDayOfWeek() - dayOfWeek + 7);
        } else if (date.getFirstDayOfWeek() > dayOfWeek) {
            date.add(6, date.getFirstDayOfWeek() - dayOfWeek);
        }
    }

    public static void alignWeekBackward(Calendar date) {
        int dayOfWeek = date.get(7);
        if (date.getFirstDayOfWeek() < dayOfWeek) {
            date.add(6, date.getFirstDayOfWeek() - dayOfWeek);
        } else if (date.getFirstDayOfWeek() > dayOfWeek) {
            date.add(6, dayOfWeek - date.getFirstDayOfWeek() - 7);
        }
    }

    public static String getDefaultDataPattern(int granularity, boolean isTimeKey) throws TimeCalcException {
        if (isTimeKey) {
            return PATTERN_TIMEKEY;
        }
        switch (granularity) {
            case 0: {
                return "yyyy";
            }
            case 1: {
                return "B";
            }
            case 2: {
                return "Q";
            }
            case 3: {
                return "MM";
            }
            case 4: {
                return "X";
            }
            case 5: {
                return "dd";
            }
            case 6: {
                return "ww";
            }
        }
        throw new TimeCalcException("\u7a0b\u5e8f\u5904\u7406\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u65f6\u671f\u7c92\u5ea6\uff01");
    }

    public static String getDefaultShowPattern(int granularity, boolean isTimeKey) throws TimeCalcException {
        switch (granularity) {
            case 0: {
                return "yyyy\u5e74";
            }
            case 1: {
                return isTimeKey ? "yyyy\u5e74BBB" : "BBB";
            }
            case 2: {
                return isTimeKey ? "yyyy\u5e74QQQ" : "QQQ";
            }
            case 3: {
                return isTimeKey ? "yyyy\u5e74MM\u6708" : "MM\u6708";
            }
            case 4: {
                return isTimeKey ? "yyyy\u5e74MM\u6708TTT" : "TTT";
            }
            case 5: {
                return isTimeKey ? "yyyy\u5e74MM\u6708dd\u65e5" : "dd\u65e5";
            }
            case 6: {
                return isTimeKey ? "yyyy\u5e74ww\u5468(MM.dd~NN.ee)" : "ww\u5468";
            }
        }
        throw new TimeCalcException("\u7a0b\u5e8f\u5904\u7406\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u65f6\u671f\u7c92\u5ea6\uff01");
    }

    public static int[] getDependGranularities(int granularity) {
        switch (granularity) {
            case 0: {
                return new int[]{0};
            }
            case 1: {
                return new int[]{0, 1};
            }
            case 2: {
                return new int[]{0, 2};
            }
            case 3: {
                return new int[]{0, 3};
            }
            case 4: {
                return new int[]{0, 3, 4};
            }
            case 5: {
                return new int[]{0, 3, 5};
            }
            case 6: {
                return new int[]{0, 6};
            }
        }
        return new int[0];
    }

    public static String getTimeKeyExpression(int granualarity, String pattern, Map<Integer, String> fieldNames) throws TimeCalcException {
        StringBuilder expr = new StringBuilder("FormatDate(");
        TimeHelper.getDateExpression(expr, granualarity, fieldNames);
        expr.append(", \"").append(StringUtils.isEmpty((String)pattern) ? PATTERN_TIMEKEY : pattern).append("\")");
        return expr.toString();
    }

    public static String getDateExpression(int granualarity, Map<Integer, String> fieldNames) throws TimeCalcException {
        StringBuilder buffer = new StringBuilder();
        TimeHelper.getDateExpression(buffer, granualarity, fieldNames);
        return buffer.toString();
    }

    private static void getDateExpression(StringBuilder buffer, int granualarity, Map<Integer, String> fieldNames) throws TimeCalcException {
        if (granualarity == 6) {
            throw new TimeCalcException("\u4e0d\u652f\u6301\u5468\u7c92\u5ea6\u7684\u5468\u671f\u503c\u8ba1\u7b97");
        }
        buffer.append("Date(");
        TimeHelper.printYearExpr(buffer, fieldNames);
        buffer.append(", ");
        TimeHelper.printMonthExpr(buffer, granualarity, fieldNames);
        buffer.append(", ");
        TimeHelper.printDayExpr(buffer, granualarity, fieldNames);
        buffer.append(")");
    }

    private static void printYearExpr(StringBuilder expr, Map<Integer, String> fieldNames) throws TimeCalcException {
        String year = TimeHelper.getTimeExpr(0, fieldNames);
        expr.append(year);
    }

    private static void printMonthExpr(StringBuilder expr, int granualarity, Map<Integer, String> fieldNames) throws TimeCalcException {
        switch (granualarity) {
            case 0: {
                expr.append('1');
                break;
            }
            case 1: {
                expr.append('(').append(TimeHelper.getTimeExpr(1, fieldNames)).append(" - 1) * 6 + 1");
                break;
            }
            case 2: {
                expr.append('(').append(TimeHelper.getTimeExpr(2, fieldNames)).append(" - 1) * 3 + 1");
                break;
            }
            default: {
                expr.append(TimeHelper.getTimeExpr(3, fieldNames));
            }
        }
    }

    private static void printDayExpr(StringBuilder expr, int granualarity, Map<Integer, String> fieldNames) throws TimeCalcException {
        switch (granualarity) {
            case 5: {
                expr.append(TimeHelper.getTimeExpr(5, fieldNames));
                break;
            }
            case 4: {
                expr.append('(').append(TimeHelper.getTimeExpr(4, fieldNames)).append(" - 1) * 10 + 1");
                break;
            }
            default: {
                expr.append('1');
            }
        }
    }

    private static String getTimeExpr(int granularity, Map<Integer, String> fieldNames) throws TimeCalcException {
        String expr = fieldNames.get(granularity);
        if (StringUtils.isEmpty((String)expr)) {
            throw new TimeCalcException("\u67e5\u627e\u65f6\u671f\u7c92\u5ea6\u8868\u8fbe\u5f0f\u4e0d\u5b58\u5728\uff1a" + (String)TimeGranularityTypes.NAMES.get(granularity));
        }
        return expr;
    }

    @Deprecated
    public static Collection<String> mapTimeKeys(Collection<String> timeKeys, int srcGranularity, String srcFormat, int destGranularity, String destFormat) throws TimeCalcException {
        return TimeHelper.mapTimeKeys(timeKeys, srcGranularity, srcFormat, destGranularity, destFormat, 2);
    }

    @Deprecated
    public static Collection<String> mapTimeKeys(Collection<String> timeKeys, int srcGranularity, String srcFormat, int destGranularity, String destFormat, int firstDayOfWeek) throws TimeCalcException {
        if (timeKeys.isEmpty()) {
            return timeKeys;
        }
        if (StringUtils.isEmpty((String)srcFormat)) {
            srcFormat = PATTERN_TIMEKEY;
        }
        if (StringUtils.isEmpty((String)destFormat)) {
            destFormat = PATTERN_TIMEKEY;
        }
        if (srcGranularity == destGranularity && srcFormat.equals(destFormat)) {
            return timeKeys;
        }
        return new TimeKeyMapper().setSrcGranularity(srcGranularity).setSrcFormat(srcFormat).setDestGranularity(destGranularity).setDestFormat(destFormat).setFirstDayOfWeek(firstDayOfWeek).map(timeKeys);
    }
}

