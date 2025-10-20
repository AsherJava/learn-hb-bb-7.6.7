/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.time.ITimeReader
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeCalculator
 *  com.jiuqi.bi.util.time.TimeFieldInfo
 *  com.jiuqi.bi.util.time.TimeHelper
 *  org.antlr.v4.runtime.misc.ParseCancellationException
 */
package com.jiuqi.bi.dataset.manager;

import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.time.ITimeReader;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeCalculator;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class TimeKeyHelper {
    private TimeKeyHelper() {
    }

    public static String convertTo(String timeKey, int granularity) throws ParseException {
        return TimeKeyHelper.convertTo(timeKey, TimeGranularity.valueOf(granularity));
    }

    public static String convertTo(String timeKey, TimeGranularity granularity) throws ParseException {
        if (granularity == TimeGranularity.DAY) {
            return timeKey;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = format.parse(timeKey);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return TimeKeyHelper.convertTo(cal, granularity);
    }

    public static int get(String timeKey, TimeGranularity granularity) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = format.parse(timeKey);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (granularity == TimeGranularity.YEAR) {
            return cal.get(1);
        }
        if (granularity == TimeGranularity.QUARTER) {
            int month = cal.get(2);
            return month / 3 + 1;
        }
        if (granularity == TimeGranularity.MONTH) {
            return cal.get(2) + 1;
        }
        if (granularity == TimeGranularity.DAY) {
            return cal.get(5);
        }
        if (granularity == TimeGranularity.WEEK) {
            try {
                return TimeHelper.getTimeValue((Calendar)cal, (int)granularity.value());
            }
            catch (TimeCalcException e) {
                throw new ParseCancellationException(e.getMessage(), (Throwable)e);
            }
        }
        return -1;
    }

    public static String convertTo(Calendar date, TimeGranularity granularity) {
        int year = date.get(1);
        switch (granularity) {
            case YEAR: {
                return Integer.toString(year) + "0101";
            }
            case HALFYEAR: {
                int month = date.get(2);
                return Integer.toString(year) + (month <= 5 ? "0101" : "0701");
            }
            case QUARTER: {
                int month = date.get(2);
                if (month < 3) {
                    return Integer.toString(year) + "0101";
                }
                if (month < 6) {
                    return Integer.toString(year) + "0401";
                }
                if (month < 9) {
                    return Integer.toString(year) + "0701";
                }
                return Integer.toString(year) + "1001";
            }
            case MONTH: {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMM01");
                return format.format(date.getTime());
            }
            case XUN: {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
                int day = date.get(5);
                if (day <= 10) {
                    return format.format(date.getTime()) + "01";
                }
                if (day <= 20) {
                    return format.format(date.getTime()) + "11";
                }
                return format.format(date.getTime()) + "21";
            }
            case WEEK: {
                TimeFieldInfo timeField = new TimeFieldInfo("date", granularity.value(), "yyyyMMdd", false);
                try {
                    TimeCalculator calculator = TimeCalculator.createCalculator((TimeFieldInfo[])new TimeFieldInfo[]{timeField});
                    ITimeReader reader = calculator.read(date);
                    return reader.getTimeKey();
                }
                catch (TimeCalcException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            case DAY: {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                return format.format(date.getTime());
            }
        }
        throw new RuntimeException("\u7a0b\u5e8f\u5904\u7406\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u65f6\u671f\u7c92\u5ea6\uff01");
    }

    public static String getDefaultDatePattern(int granularity) {
        return TimeKeyHelper.getDefaultDatePattern(TimeGranularity.valueOf(granularity));
    }

    public static String getDefaultDatePattern(TimeGranularity granularity) {
        switch (granularity) {
            case YEAR: {
                return "yyyy";
            }
            case HALFYEAR: {
                return "B";
            }
            case QUARTER: {
                return "Q";
            }
            case MONTH: {
                return "MM";
            }
            case XUN: {
                return "X";
            }
            case WEEK: {
                return "w";
            }
            case DAY: {
                return "dd";
            }
        }
        throw new RuntimeException("\u7a0b\u5e8f\u5904\u7406\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u65f6\u671f\u7c92\u5ea6\uff01");
    }

    public static String getDefaultShowDatePattern(int granularity) {
        return TimeKeyHelper.getDefaultShowDatePattern(TimeGranularity.valueOf(granularity));
    }

    public static String getDefaultShowDatePattern(TimeGranularity granularity) {
        switch (granularity) {
            case YEAR: {
                return "yyyy\u5e74";
            }
            case HALFYEAR: {
                return "yyyy\u5e74BBB";
            }
            case QUARTER: {
                return "yyyy\u5e74QQQ";
            }
            case MONTH: {
                return "yyyy\u5e74MM\u6708";
            }
            case XUN: {
                return "yyyy\u5e74MM\u6708XXX";
            }
            case WEEK: {
                return "\u7b2cw\u5468";
            }
            case DAY: {
                return "yyyy\u5e74MM\u6708dd\u65e5";
            }
        }
        throw new RuntimeException("\u7a0b\u5e8f\u5904\u7406\u65f6\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u65f6\u671f\u7c92\u5ea6\uff01");
    }

    public static void main(String[] args) {
        Date d = new Date();
        DateFormatEx df = new DateFormatEx(TimeKeyHelper.getDefaultShowDatePattern(TimeGranularity.XUN));
        System.out.println(df.format(d));
    }
}

