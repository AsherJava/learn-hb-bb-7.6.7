/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.formula;

import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.sql.formula.DateParser;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class QueryFormulaConvertUtil {
    private QueryFormulaConvertUtil() {
    }

    public static int dateToInt(long value) {
        if (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) {
            return (int)value;
        }
        throw new DefinedQueryRuntimeException("[" + value + "]\u8f6cint\u5931\u8d25\uff0c\u8d85\u51faint\u8303\u56f4");
    }

    public static int toInt(double value) {
        if (-2.147483648E9 <= value && value <= 2.147483647E9) {
            return (int)value;
        }
        throw new DefinedQueryRuntimeException("[" + value + "]\u8f6cint\u5931\u8d25\uff0c\u8d85\u51faint\u8303\u56f4");
    }

    public static int toInt(String value) {
        return value != null && value.length() != 0 ? Integer.parseInt(value) : 0;
    }

    public static int toInt(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (Integer)value;
        }
        if (value instanceof Number) {
            return QueryFormulaConvertUtil.toInt(((Number)value).doubleValue());
        }
        if (value instanceof String) {
            return QueryFormulaConvertUtil.toInt(value.toString());
        }
        if (value instanceof Enum) {
            return ((Enum)value).ordinal();
        }
        if (value instanceof Character) {
            return ((Character)value).charValue();
        }
        if (value instanceof Boolean) {
            return (Boolean)value != false ? 1 : 0;
        }
        if (value instanceof Date) {
            return QueryFormulaConvertUtil.dateToInt(((Date)value).getTime());
        }
        if (value instanceof GregorianCalendar) {
            return QueryFormulaConvertUtil.dateToInt(((GregorianCalendar)value).getTimeInMillis());
        }
        throw new DefinedQueryRuntimeException("[" + value + "]\u8f6cint\u5931\u8d25");
    }

    public static double toDouble(String value) {
        return value != null && value.length() != 0 ? Double.parseDouble(value) : 0.0;
    }

    public static double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Number) {
            return ((Number)value).doubleValue();
        }
        if (value instanceof String) {
            return QueryFormulaConvertUtil.toDouble(value.toString());
        }
        if (value instanceof Enum) {
            return ((Enum)value).ordinal();
        }
        if (value instanceof Character) {
            return ((Character)value).charValue();
        }
        if (value instanceof Boolean) {
            return (Boolean)value != false ? 1.0 : 0.0;
        }
        if (value instanceof Date) {
            return ((Date)value).getTime();
        }
        if (value instanceof GregorianCalendar) {
            return ((GregorianCalendar)value).getTimeInMillis();
        }
        throw new DefinedQueryRuntimeException("[" + value + "]\u8f6cdouble\u5931\u8d25");
    }

    public static long toDate(String value) {
        return value == null || value.length() == 0 ? 0L : DateParser.parse(value);
    }

    public static long toDate(Object value) {
        if (value == null) {
            return 0L;
        }
        if (value instanceof Date) {
            return ((Date)value).getTime();
        }
        if (value instanceof Long || value instanceof Byte || value instanceof Short || value instanceof Integer) {
            return ((Number)value).longValue();
        }
        if (value instanceof Character) {
            return ((Character)value).charValue();
        }
        if (value instanceof Float) {
            return QueryFormulaConvertUtil.toLong(((Float)value).floatValue());
        }
        if (value instanceof Double) {
            return QueryFormulaConvertUtil.toLong((Double)value);
        }
        if (value instanceof CharSequence) {
            return QueryFormulaConvertUtil.toDate(value.toString());
        }
        if (value instanceof Calendar) {
            return ((Calendar)value).getTimeInMillis();
        }
        throw new DefinedQueryRuntimeException("[" + value + "]\u8f6cDate\u5931\u8d25");
    }

    public static long toLong(float value) {
        if (-9.223372E18f <= value && value <= 9.223372E18f) {
            return (long)value;
        }
        throw new DefinedQueryRuntimeException("[" + value + "]\u8f6cLong\u5931\u8d25\uff0c\u8d85\u51faint\u8303\u56f4");
    }

    public static long toLong(double value) {
        if (-9.223372036854776E18 <= value && value <= 9.223372036854776E18) {
            return (long)value;
        }
        throw new DefinedQueryRuntimeException("[" + value + "]\u8f6cLong\u5931\u8d25\uff0c\u8d85\u51faint\u8303\u56f4");
    }

    public static String toString(UUID value) {
        return value == null ? null : value.toString();
    }

    public static String toString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String)value;
        }
        if (value instanceof Character) {
            return ((Character)value).toString();
        }
        if (value instanceof Number || value instanceof CharSequence || value instanceof Date || value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof UUID) {
            return QueryFormulaConvertUtil.toString((UUID)value);
        }
        if (value instanceof byte[]) {
            return new String((byte[])value);
        }
        return value.toString();
    }

    public static boolean toBoolean(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Character) {
            return ((Character)value).charValue() != '\u0000';
        }
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        if (value instanceof Number) {
            return ((Number)value).doubleValue() != 0.0;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean(value.toString());
        }
        throw new DefinedQueryRuntimeException();
    }
}

