/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.text;

import com.jiuqi.np.period.text.DateTimeFormatFactory;
import com.jiuqi.np.period.text.NumberFormatFactory;
import com.jiuqi.np.period.text.StringHelper;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DataConvert {
    public static final String[] BoolTrueCaptions = new String[]{"\u662f", "\u771f", "True", "T", "1"};
    public static final String[] BoolFalseCaptions = new String[]{"\u5426", "\u5047", "False", "F", "0"};
    public static final String default_date_format = "yyyy-MM-dd";
    public static final String default_time_format = "yyyy-MM-dd HH:mm:ss";
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static boolean stringToBool(String data) {
        if (data == null) {
            return false;
        }
        for (int i = 0; i < BoolTrueCaptions.length; ++i) {
            if (!data.equalsIgnoreCase(BoolTrueCaptions[i])) continue;
            return true;
        }
        return false;
    }

    public static int stringToInt(String data) {
        try {
            return data == null || data.equals("") ? 0 : Integer.parseInt(data);
        }
        catch (NumberFormatException ex) {
            return Double.valueOf(data).intValue();
        }
    }

    public static String removeNumberGroup(String number) {
        if (number == null || number.indexOf(44) < 0) {
            return number;
        }
        StringBuffer buffer = new StringBuffer(number.length());
        int c = number.length();
        for (int i = 0; i < c; ++i) {
            char ch = number.charAt(i);
            if (ch == ',') continue;
            buffer.append(ch);
        }
        return buffer.toString();
    }

    public static double stringToFloat(String data) {
        if (data == null || data.equals("")) {
            return 0.0;
        }
        data = DataConvert.removeNumberGroup(data);
        return Double.parseDouble(data);
    }

    public static Date stringToDate(String dateString, String format) {
        try {
            if (format == null) {
                format = default_date_format;
            }
            DateFormat dateFormat = DateTimeFormatFactory.createDateTimeFormat(format);
            Date result = dateFormat.parse(dateString);
            DateTimeFormatFactory.recycleDateTimeFormat(dateFormat);
            return result;
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static Date stringToDate(String dateString) {
        Date result = DataConvert.stringToDate(dateString, default_date_format);
        if (result == null) {
            result = DataConvert.stringToDate(dateString, "yyyy.MM.dd");
        }
        if (result == null) {
            result = DataConvert.stringToDate(dateString, "yyyy/MM/dd");
        }
        if (result == null) {
            result = DataConvert.stringToDate(dateString, "yyyyMMdd");
        }
        return result;
    }

    public static String dateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null) {
            format = default_date_format;
        }
        DateFormat dateFormat = DateTimeFormatFactory.createDateTimeFormat(format);
        String result = dateFormat.format(date);
        DateTimeFormatFactory.recycleDateTimeFormat(dateFormat);
        return result;
    }

    public static Date stringToTime(String timeString, String format) {
        try {
            if (format == null) {
                format = default_time_format;
            }
            DateFormat timeFormat = DateTimeFormatFactory.createDateTimeFormat(format);
            Date result = timeFormat.parse(timeString);
            DateTimeFormatFactory.recycleDateTimeFormat(timeFormat);
            return result;
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static Date stringToTime(String timeString) {
        Date result = DataConvert.stringToTime(timeString, default_time_format);
        if (result == null) {
            result = DataConvert.stringToTime(timeString, "yyyy-MM-dd HH:mm");
        }
        if (result == null) {
            result = DataConvert.stringToTime(timeString, "yyyy.MM.dd HH:mm");
        }
        if (result == null) {
            result = DataConvert.stringToTime(timeString, "yyyy.MM.dd HH:mm:ss");
        }
        if (result == null) {
            result = DataConvert.stringToTime(timeString, "yyyy/MM/dd HH:mm");
        }
        if (result == null) {
            result = DataConvert.stringToTime(timeString, "yyyy/MM/dd HH:mm:ss");
        }
        if (result == null) {
            result = DataConvert.stringToTime(timeString, "yyyyMMdd HH:mm");
        }
        if (result == null) {
            result = DataConvert.stringToTime(timeString, "yyyyMMdd HH:mm:ss");
        }
        if (result == null) {
            result = DataConvert.stringToDate(timeString);
        }
        return result;
    }

    public static String timeToString(Date time, String format) {
        if (time == null) {
            return null;
        }
        if (format == null) {
            format = default_time_format;
        }
        DateFormat timeFormat = DateTimeFormatFactory.createDateTimeFormat(format);
        String result = timeFormat.format(time);
        DateTimeFormatFactory.recycleDateTimeFormat(timeFormat);
        return result;
    }

    public static String valueToString(boolean value) {
        return value ? BoolTrueCaptions[0] : BoolFalseCaptions[0];
    }

    public static String valueToString(int value) {
        return String.valueOf(value);
    }

    public static String valueToString(double value) {
        return Double.toString(value);
    }

    public static String valueToString(Object value) {
        return DataConvert.valueToString(value, false);
    }

    public static String valueToString(Object value, boolean quoteString) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double) {
            return value.toString();
        }
        if (value instanceof Integer) {
            return value.toString();
        }
        if (value instanceof Boolean) {
            return (Boolean)value != false ? "1" : "0";
        }
        if (value instanceof String) {
            if (!quoteString) {
                return (String)value;
            }
            return StringHelper.quote((String)value, '\"');
        }
        if (value instanceof Time) {
            return DataConvert.timeToString((Time)value, null);
        }
        if (value instanceof Timestamp) {
            return DataConvert.timeToString((Timestamp)value, null);
        }
        if (value instanceof Date) {
            return DataConvert.dateToString((Date)value, null);
        }
        if (value instanceof Calendar) {
            return DataConvert.dateToString(((Calendar)value).getTime(), null);
        }
        if (value instanceof Object[]) {
            Object[] values = (Object[])value;
            String result = "";
            for (int i = 0; i < values.length; ++i) {
                result = result + DataConvert.valueToString(values[i], quoteString) + ';';
            }
            return result;
        }
        return value.toString();
    }

    public static String toHexString(int iv) {
        char[] buf = new char[8];
        int charPos = 7;
        while (iv != 0) {
            buf[charPos--] = HEX_CHARS[iv & 0xF];
            iv >>>= 4;
        }
        while (charPos >= 0) {
            buf[charPos--] = HEX_CHARS[0];
        }
        return new String(buf);
    }

    public static boolean isHexData(String data) {
        for (int i = 0; i < data.length(); ++i) {
            char ch = data.charAt(i);
            if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'F') continue;
            return false;
        }
        return true;
    }

    public static String toHexString(long lv) {
        char[] buf = new char[16];
        int charPos = 15;
        while (lv != 0L) {
            buf[charPos--] = HEX_CHARS[(int)lv & 0xF];
            lv >>>= 4;
        }
        while (charPos >= 0) {
            buf[charPos--] = HEX_CHARS[0];
        }
        return new String(buf);
    }

    public static String bytesToHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(data.length * 2);
        for (int i = 0; i < data.length; ++i) {
            int b = data[i];
            if (b < 0) {
                b += 256;
            }
            if (b < 16) {
                result.append('0');
            }
            result.append(Integer.toHexString(b));
        }
        return result.toString().toUpperCase();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null) {
            return null;
        }
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < result.length; ++i) {
            result[i] = Integer.decode("0x" + hexString.substring(2 * i, 2 * i + 2)).byteValue();
        }
        return result;
    }

    public static String toFixedString(long iv, int width) {
        NumberFormat fmt = NumberFormatFactory.createIntegerFormat(width);
        String result = fmt.format(iv);
        NumberFormatFactory.recycleNumberFormat(fmt);
        return result;
    }

    public static String formatNumber(long iv, boolean seperator) {
        NumberFormat fmt = NumberFormatFactory.createNumberFormat(0, seperator);
        String result = fmt.format(iv);
        NumberFormatFactory.recycleNumberFormat(fmt);
        return result;
    }

    public static String formatNumber(double value, boolean seperator) {
        NumberFormat fmt = NumberFormatFactory.createNumberFormat(seperator ? "#,##0.##########" : "0.##########");
        String result = fmt.format(value);
        NumberFormatFactory.recycleNumberFormat(fmt);
        if ("?".equals(result)) {
            result = "NaN";
        }
        return result;
    }

    public static String formatNumber(double value, int decimal, boolean seperator) {
        NumberFormat fmt = NumberFormatFactory.createNumberFormat(decimal, seperator);
        String result = fmt.format(value);
        NumberFormatFactory.recycleNumberFormat(fmt);
        if ("?".equals(result)) {
            result = "NaN";
        }
        return result;
    }

    public static String formatNumber(double value, int decimal, int integer, boolean seperator) {
        NumberFormat fmt = NumberFormatFactory.createNumberFormat(decimal, seperator);
        String result = fmt.format(value);
        NumberFormatFactory.recycleNumberFormat(fmt);
        if ("?".equals(result)) {
            result = "NaN";
        }
        return result;
    }

    public static int[] stringToColRow(String str) {
        int[] pos = new int[]{0, 0};
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch < 'A' || ch > 'Z') {
                try {
                    pos[1] = Integer.parseInt(str.substring(i));
                }
                catch (NumberFormatException ex) {
                    pos[1] = 0;
                }
                break;
            }
            pos[0] = pos[0] * 26 + (byte)ch - 65 + 1;
        }
        if (pos[0] > 0 && pos[1] > 0) {
            return pos;
        }
        return null;
    }

    public static String colRowToString(int x, int y) {
        char[] chrArray = new char[64];
        int len = 0;
        while (x > 0) {
            int ch = x % 26 + 65 - 1;
            x /= 26;
            if (ch < 65) {
                ch = 90;
                --x;
            }
            chrArray[len] = ch;
            ++len;
        }
        String colStr = "";
        for (int i = 0; i < len; ++i) {
            colStr = colStr + chrArray[len - i - 1];
        }
        return colStr + Integer.toString(y);
    }

    public static Date stringToDateTime(String datetimeString) {
        if (datetimeString == null) {
            return null;
        }
        if (datetimeString.indexOf(58) == -1) {
            return DataConvert.stringToDate(datetimeString);
        }
        return DataConvert.stringToTime(datetimeString);
    }

    public static Date stringToDateTime(String datetimeStr, boolean[] hasTime) {
        if (datetimeStr == null) {
            return null;
        }
        if (datetimeStr.indexOf(58) == -1) {
            hasTime[0] = false;
            return DataConvert.stringToDate(datetimeStr);
        }
        hasTime[0] = true;
        return DataConvert.stringToTime(datetimeStr);
    }
}

