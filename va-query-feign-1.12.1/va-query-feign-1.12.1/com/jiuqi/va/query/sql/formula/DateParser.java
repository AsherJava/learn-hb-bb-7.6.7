/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.formula;

import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import java.util.Date;

public final class DateParser {
    private static final int FORMATTYPE_DATE_MASK = 4;
    private static final int FORMATTYPE_TIME_MASK = 3;
    private static final int FORMATTYPE_MS_MASK = 2;
    private static final int FORMATTYPE_AUTOMS_MASK = 1;
    public static final int FORMAT_TIME = 1;
    public static final int FORMAT_TIME_MS = 2;
    public static final int FORMAT_TIME_AUTOMS = 3;
    public static final int FORMAT_DATE = 4;
    public static final int FORMAT_DATE_TIME = 5;
    public static final int FORMAT_DATE_TIME_MS = 6;
    public static final int FORMAT_DATE_TIME_AUTOMS = 7;
    static final int[] LEAPYEAR_DAYS_COUNT_FOR_MONTH = new int[]{0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};
    static final int[] COMMONYEAR_DAYS_COUNT_FOR_MONTH = new int[]{0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    static final int[] LEAPYEAR_DAYS_FOR_MONTH = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static final int[] COMMONYEAR_DAYS_FOR_MONTH = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final byte DNATIME_MARK_END_POS = 62;
    private static final byte DNATIME_TIME_TYPE_END_POS = 59;
    private static final byte DNATIME_DATEFILED_END_POS = 32;
    private static final byte DNATIME_LEAPYEAR_MARK_END_POS = 58;
    private static final byte DNATIME_YEAR_END_POS = 44;
    private static final byte DNATIME_MONTH_END_POS = 40;
    private static final byte DNATIME_DATE_END_POS = 35;
    private static final byte DNATIME_DAY_END_POS = 32;
    private static final byte DNATIME_TIMEZONE_END_POS = 27;
    private static final byte DNATIME_HOUR_END_POS = 22;
    private static final byte DNATIME_MINUTE_END_POS = 16;
    private static final byte DNATIME_SECOND_END_POS = 10;
    private static final int DNATIME_YEAR_MASK = 16383;
    private static final int DNATIME_MONTH_MASK = 15;
    private static final int DNATIME_DATE_MASK = 31;
    private static final int DNATIME_DAY_MASK = 7;
    private static final int DNATIME_TIMEZONE_MASK = 31;
    private static final int DNATIME_HOUR_MASK = 31;
    private static final int DNATIME_MINUTE_MASK = 63;
    private static final int DNATIME_SECOND_MASK = 63;
    private static final int DNATIME_MS_MASK = 1023;

    public static long parse(String dateTimeStr) {
        return DateParser.timeAdjust(DateParser.computeJavaDateTime(dateTimeStr));
    }

    public static long parseDNADateTime(String dateTimeStr) {
        return DateParser.toDNADateTime(DateParser.parse(dateTimeStr));
    }

    public static String format(long dateTime) {
        return DateParser.format(dateTime, 7);
    }

    public static String format(long dateTime, int formatType) {
        if (!DateParser.isDNADateTime(dateTime)) {
            dateTime = DateParser.toDNADateTime(dateTime);
        }
        return new String(DateParser.formatDNADateTime(dateTime, formatType));
    }

    public static long toJavaDateTime(long dateTime) {
        if (!DateParser.isDNADateTime(dateTime)) {
            return dateTime;
        }
        return DateParser.timeAdjust(DateParser.dnaDateTimeToJavaDateTime(dateTime));
    }

    public static long toDNADateTime(long dateTime) {
        if (DateParser.isDNADateTime(dateTime)) {
            return dateTime;
        }
        long baseDNATime = 0x4000000000000000L;
        dateTime = DateParser.reverseTimeAdjust(dateTime);
        int days = (int)(dateTime / 86400000L);
        int milliSeconds = (int)(dateTime % 86400000L);
        return 0x4000000000000000L | (long)DateParser.daysToDNADate(dateTime >= 0L || milliSeconds == 0 ? days + 1 : days) << 32 | (long)DateParser.msToDNATime(milliSeconds);
    }

    public static int getTimeZone(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 27 & 0x1FL);
    }

    public static boolean isLeapYear(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 58 & 1L) == 1;
    }

    public static int getYear(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 44 & 0x3FFFL);
    }

    public static int getMonth(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 40 & 0xFL);
    }

    public static int getDate(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 35 & 0x1FL);
    }

    public static int getDay(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 32 & 7L);
    }

    public static int getHour(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 22 & 0x1FL);
    }

    public static int getMinute(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 16 & 0x3FL);
    }

    public static int getSecond(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime >>> 10 & 0x3FL);
    }

    public static int getMilliSecond(long dnaDateTime) {
        DateParser.checkDNADateTime(dnaDateTime);
        return (int)(dnaDateTime & 0x3FFL);
    }

    private static int parseInt(String in, int len, int start, int maxCount, int minValue, int maxValue) {
        char tempChar;
        int p = start;
        int minEnd = start + 1;
        if (minEnd > len) {
            throw new DefinedQueryRuntimeException("\u6570\u503c\u4f4d\u6570\u5c11\u4e8e1\u4f4d\t" + start);
        }
        int maxEnd = start + maxCount;
        if (maxEnd > len) {
            maxEnd = len;
        }
        int result = 0;
        while (p < maxEnd && '0' <= (tempChar = in.charAt(p)) && tempChar <= '9') {
            result = result * 10 + tempChar - 48;
            ++p;
        }
        if (p < minEnd) {
            throw new DefinedQueryRuntimeException("\u6570\u503c\u4f4d\u6570\u5c11\u4e8e1\u4f4d\t" + start);
        }
        if (result < minValue || maxValue < result) {
            throw new DefinedQueryRuntimeException("\u6570\u503c\u8d85\u51fa\u4e86\u6b63\u5e38\u8303\u56f4\t" + start);
        }
        return p << 16 | result;
    }

    private static void checkDivi(String s, int len, int position) {
        if (position >= len) {
            throw new DefinedQueryRuntimeException("\u65e5\u671f\u65f6\u95f4\u4e0d\u5b8c\u6574\t");
        }
        char tempChar = s.charAt(position);
        if ('0' <= tempChar && tempChar <= '9') {
            throw new DefinedQueryRuntimeException("\u5206\u754c\u7b26\u9519\u8bef,\u6b64\u5904\u5206\u754c\u7b26\u5e94\u4e3a\u975e\u6570\u5b57\u5b57\u7b26\t" + position);
        }
    }

    private static boolean checkNumber(String s, int len, int position) {
        if (position >= len) {
            return false;
        }
        char tempChar = s.charAt(position);
        return tempChar >= '0' && '9' >= tempChar;
    }

    private static int computeJavaDate(int year, int month, int date) {
        int[] DAYS_COUNT_FOR_MONTH;
        int[] DAYS_FOR_MONTH;
        int isLeapYear;
        if (0 == year % 4 && 0 != year % 100 || 0 == year % 400) {
            isLeapYear = 1;
            DAYS_FOR_MONTH = LEAPYEAR_DAYS_FOR_MONTH;
            DAYS_COUNT_FOR_MONTH = LEAPYEAR_DAYS_COUNT_FOR_MONTH;
        } else {
            isLeapYear = 0;
            DAYS_FOR_MONTH = COMMONYEAR_DAYS_FOR_MONTH;
            DAYS_COUNT_FOR_MONTH = COMMONYEAR_DAYS_COUNT_FOR_MONTH;
        }
        if (date > DAYS_FOR_MONTH[month]) {
            throw new DefinedQueryRuntimeException(year + "\u5e74\u7684" + month + "\u6708,\u6ca1\u6709" + date + "\u5929");
        }
        int tempYear = year - 2000;
        int result = tempYear / 4 - tempYear / 100 + tempYear / 400;
        result = tempYear * 365 + result + DAYS_COUNT_FOR_MONTH[month] + (date - 1);
        return year > 2000 ? result + 1 - isLeapYear : result;
    }

    private static long computeJavaDateTime(String dateTimeStr) {
        int len = dateTimeStr.length();
        int start = DateParser.parseInt(dateTimeStr, len, 0, 4, 1, 9999);
        DateParser.checkDivi(dateTimeStr, len, 4);
        int year = start & 0xFFFF;
        start = DateParser.parseInt(dateTimeStr, len, 5, 2, 1, 12);
        int month = start & 0xFFFF;
        start >>= 16;
        DateParser.checkDivi(dateTimeStr, len, start++);
        start = DateParser.parseInt(dateTimeStr, len, start, 2, 1, 31);
        int day = start & 0xFFFF;
        long days = (long)DateParser.computeJavaDate(year, month, day) * 86400000L;
        if ((start >>= 16) == len) {
            return days;
        }
        DateParser.checkDivi(dateTimeStr, len, start++);
        if (start == len || !DateParser.checkNumber(dateTimeStr, len, dateTimeStr.charAt(start) == ' ' ? ++start : start)) {
            return days;
        }
        start = DateParser.parseInt(dateTimeStr, len, start, 2, 0, 23);
        int hour = start & 0xFFFF;
        start >>= 16;
        DateParser.checkDivi(dateTimeStr, len, start++);
        start = DateParser.parseInt(dateTimeStr, len, start, 2, 0, 59);
        int minute = start & 0xFFFF;
        start >>= 16;
        DateParser.checkDivi(dateTimeStr, len, start++);
        start = DateParser.parseInt(dateTimeStr, len, start, 2, 0, 59);
        int second = start & 0xFFFF;
        long seconds = 1000L * (long)(hour * 3600 + minute * 60 + second);
        if ((start >>= 16) == len) {
            return days + seconds;
        }
        DateParser.checkDivi(dateTimeStr, len, start++);
        if (start == len || !DateParser.checkNumber(dateTimeStr, len, start)) {
            return days + seconds;
        }
        start = DateParser.parseInt(dateTimeStr, len, start, 3, 0, 999);
        int milliSecond = start & 0xFFFF;
        if ((start >>= 16) == len) {
            return days + seconds + (long)milliSecond;
        }
        DateParser.checkDivi(dateTimeStr, len, start++);
        return days + seconds + (long)milliSecond;
    }

    private static long dnaDateTimeToJavaDateTime(long dnaDateTime) {
        int[] DAYS_COUNT_FOR_MONTH;
        int isLeapYear;
        int year = (int)(dnaDateTime >>> 44 & 0x3FFFL);
        if ((dnaDateTime >>> 58 & 1L) == 1L) {
            isLeapYear = 1;
            DAYS_COUNT_FOR_MONTH = LEAPYEAR_DAYS_COUNT_FOR_MONTH;
        } else {
            isLeapYear = 0;
            DAYS_COUNT_FOR_MONTH = COMMONYEAR_DAYS_COUNT_FOR_MONTH;
        }
        int tempYear = year - 2000;
        long result = (long)tempYear / 4L - (long)(tempYear / 100) + (long)(tempYear / 400);
        result = (long)(tempYear * 365) + result + (long)DAYS_COUNT_FOR_MONTH[(int)(dnaDateTime >>> 40 & 0xFL)] + ((dnaDateTime >>> 35 & 0x1FL) - 1L);
        if (year > 2000) {
            result = result + 1L - (long)isLeapYear;
        }
        return (result *= 86400000L) + ((dnaDateTime >>> 22 & 0x1FL) * 3600L + (dnaDateTime >>> 16 & 0x3FL) * 60L + (dnaDateTime >>> 10 & 0x3FL)) * 1000L + (dnaDateTime & 0x3FFL);
    }

    private static long timeAdjust(long time) {
        if (new Date(time += 946656000000L).getHours() != DateParser.getHourByTime(time)) {
            time -= 3600000L;
        }
        return time;
    }

    private static int getHourByTime(long time) {
        return (int)((time + 28800000L) / 3600000L) % 24;
    }

    private static long reverseTimeAdjust(long javaTime) {
        if (new Date(javaTime).getHours() != DateParser.getHourByTime(javaTime)) {
            javaTime += 3600000L;
        }
        return javaTime - 946656000000L;
    }

    /*
     * Unable to fully structure code
     */
    private static int daysToDNADate(int days) {
        weekDay = (days + 5) % 7;
        dnaDate = 0 | (weekDay < 0 ? 7 + weekDay : weekDay);
        year = days / 146097 * 400;
        if ((days %= 146097) < 0) {
            days += 146097;
            year += 1600;
        } else {
            year += 2000;
        }
        DNATIME_LEAPYEAR_MARK_REL_POS = 26;
        DNATIME_YEAR_REL_POS = 12;
        DNATIME_MONTH_REL_POS = 8;
        DNATIME_DATE_REL_POS = 3;
        if (days <= 36525) ** GOTO lbl21
        year += --days / 36524 * 100;
        if ((days %= 36524) <= 1460) {
            year += days / 365;
            days %= 365;
            DAYS_COUNT_FOR_MONTH = DateParser.COMMONYEAR_DAYS_COUNT_FOR_MONTH;
        } else {
            ++days;
lbl21:
            // 2 sources

            year += days / 1461 * 4;
            if ((days %= 1461) <= 366) {
                dnaDate |= 0x4000000;
                DAYS_COUNT_FOR_MONTH = DateParser.LEAPYEAR_DAYS_COUNT_FOR_MONTH;
            } else {
                year += --days / 365;
                days %= 365;
                DAYS_COUNT_FOR_MONTH = DateParser.COMMONYEAR_DAYS_COUNT_FOR_MONTH;
            }
        }
        if (days == 0) {
            return dnaDate | year - 1 << 12 | 3072 | 248;
        }
        month = days / 30 + 1;
        if (days <= DAYS_COUNT_FOR_MONTH[month]) {
            --month;
        }
        return dnaDate | year << 12 | month << 8 | days - DAYS_COUNT_FOR_MONTH[month] << 3;
    }

    private static int msToDNATime(int ms) {
        if (ms < 0) {
            ms += 86400000;
        }
        return 0 | ms / 3600000 << 22 | ms % 3600000 / 60000 << 16 | ms % 60000 / 1000 << 10 | ms % 1000;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static char[] formatDNADateTime(long dnaDateTime, int formatType) {
        int j;
        int temp;
        boolean fMS;
        int ms;
        boolean fTime;
        boolean fDate;
        int charlen;
        if ((formatType & 4) != 0) {
            charlen = 10;
            fDate = true;
        } else {
            charlen = -1;
            fDate = false;
        }
        if ((formatType & 3) != 0) {
            fTime = true;
            if ((formatType & 2) != 0) {
                ms = (int)dnaDateTime & 0x3FF;
                if ((formatType & 1) == 0 || ms != 0) {
                    charlen = (byte)(charlen + 13);
                    fMS = true;
                } else {
                    fMS = false;
                    charlen = (byte)(charlen + 9);
                }
            } else {
                ms = 0;
                fMS = false;
                charlen = (byte)(charlen + 9);
            }
        } else {
            if (!fDate) throw new DefinedQueryRuntimeException("\u975e\u6cd5\u53c2\u6570");
            fTime = false;
            fMS = false;
            ms = 0;
        }
        char[] dateTimeArray = new char[charlen];
        if (fDate) {
            temp = (int)(dnaDateTime >>> 44 & 0x3FFFL);
            dateTimeArray[3] = (char)(temp % 10 + 48);
            dateTimeArray[2] = (char)((temp /= 10) % 10 + 48);
            dateTimeArray[1] = (char)((temp /= 10) % 10 + 48);
            dateTimeArray[0] = (char)((temp /= 10) % 10 + 48);
            dateTimeArray[4] = 45;
            temp = (int)(dnaDateTime >>> 40 & 0xFL);
            dateTimeArray[5] = (char)(temp / 10 + 48);
            dateTimeArray[6] = (char)(temp % 10 + 48);
            dateTimeArray[7] = 45;
            temp = (int)(dnaDateTime >>> 35 & 0x1FL);
            dateTimeArray[8] = (char)(temp / 10 + 48);
            dateTimeArray[9] = (char)(temp % 10 + 48);
            if (!fTime) return dateTimeArray;
            dateTimeArray[10] = 32;
            j = 11;
        } else {
            j = 0;
        }
        temp = (int)dnaDateTime >>> 22 & 0x1F;
        dateTimeArray[j++] = (char)(temp / 10 + 48);
        dateTimeArray[j++] = (char)(temp % 10 + 48);
        dateTimeArray[j++] = 58;
        temp = (int)dnaDateTime >>> 16 & 0x3F;
        dateTimeArray[j++] = (char)(temp / 10 + 48);
        dateTimeArray[j++] = (char)(temp % 10 + 48);
        dateTimeArray[j++] = 58;
        temp = (int)dnaDateTime >>> 10 & 0x3F;
        dateTimeArray[j++] = (char)(temp / 10 + 48);
        dateTimeArray[j] = (char)(temp % 10 + 48);
        if (!fMS) return dateTimeArray;
        dateTimeArray[++j] = 46;
        dateTimeArray[++j] = (char)(ms / 100 + 48);
        dateTimeArray[++j] = (char)(ms % 100 / 10 + 48);
        dateTimeArray[++j] = (char)(ms % 10 + 48);
        return dateTimeArray;
    }

    private static boolean isDNADateTime(long dateTime) {
        return dateTime >>> 62 == 1L;
    }

    private static void checkDNADateTime(long dnaDateTime) {
        if (!DateParser.isDNADateTime(dnaDateTime)) {
            throw new DefinedQueryRuntimeException("\u975e\u6cd5\u7684DNA\u65f6\u95f4");
        }
    }

    private DateParser() {
    }
}

