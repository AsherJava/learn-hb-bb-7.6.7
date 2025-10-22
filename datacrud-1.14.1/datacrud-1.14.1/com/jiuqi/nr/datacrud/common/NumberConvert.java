/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.type.ReadableValue
 *  com.jiuqi.np.sql.type.ValueConvertException
 */
package com.jiuqi.nr.datacrud.common;

import com.jiuqi.np.sql.type.ReadableValue;
import com.jiuqi.np.sql.type.ValueConvertException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

public class NumberConvert {
    private NumberConvert() {
    }

    public static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        Class<?> c = value.getClass();
        if (c == BigDecimal.class) {
            return (BigDecimal)value;
        }
        if (c == Long.class) {
            return BigDecimal.valueOf((Long)value);
        }
        if (c == BigInteger.class) {
            return new BigDecimal((BigInteger)value);
        }
        if (c == String.class) {
            return new BigDecimal((String)value);
        }
        return BigDecimal.valueOf(NumberConvert.toDouble(value));
    }

    public static double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Number) {
            return ((Number)value).doubleValue();
        }
        if (value instanceof String) {
            return NumberConvert.toDouble(value.toString());
        }
        if (value instanceof Enum) {
            return ((Enum)value).ordinal();
        }
        if (value instanceof Character) {
            return ((Character)value).charValue();
        }
        if (value instanceof Boolean) {
            return Boolean.TRUE.equals(value) ? 1.0 : 0.0;
        }
        if (value instanceof Date) {
            return ((Date)value).getTime();
        }
        if (value instanceof GregorianCalendar) {
            return ((GregorianCalendar)value).getTimeInMillis();
        }
        if (value instanceof ReadableValue) {
            return ((ReadableValue)value).getDouble();
        }
        throw new ValueConvertException();
    }
}

