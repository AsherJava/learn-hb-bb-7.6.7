/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarBigDecimal;
import com.jiuqi.bi.variant.VarBoolean;
import com.jiuqi.bi.variant.VarDateTime;
import com.jiuqi.bi.variant.VarDouble;
import com.jiuqi.bi.variant.VarInteger;
import com.jiuqi.bi.variant.VarLong;
import com.jiuqi.bi.variant.VarNull;
import com.jiuqi.bi.variant.VarString;
import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Variants {
    private Variants() {
    }

    public static Variant NULL() {
        return VarNull.NULL;
    }

    public static Variant valueOf(String value) {
        return VarString.valueOf(value);
    }

    public static Variant valueOf(float value) {
        return VarDouble.valueOf(value);
    }

    public static Variant valueOf(double value) {
        return VarDouble.valueOf(value);
    }

    public static Variant valueOf(byte value) {
        return VarInteger.valueOf(value);
    }

    public static Variant valueOf(short value) {
        return VarInteger.valueOf(value);
    }

    public static Variant valueOf(int value) {
        return VarInteger.valueOf(value);
    }

    public static Variant valueOf(long value) {
        return VarLong.valueOf(value);
    }

    public static Variant valueOf(Calendar value) {
        return VarDateTime.valueOf(value);
    }

    public static Variant valueOf(Date value) {
        return VarDateTime.valueOf(value);
    }

    public static Variant valueOf(boolean value) {
        return VarBoolean.valueOf(value);
    }

    public static Variant valueOf(BigDecimal value) {
        return VarBigDecimal.valueOf(value);
    }

    public static Variant valueOf(BigInteger value) {
        return VarBigDecimal.valueOf(value);
    }

    public static Variant valueOf(Object value) throws IllegalArgumentException {
        if (value == null) {
            return Variants.NULL();
        }
        if (value instanceof Variant) {
            return (Variant)value;
        }
        if (value instanceof String) {
            return Variants.valueOf((String)value);
        }
        if (value instanceof Double || value instanceof Float) {
            return Variants.valueOf(((Number)value).doubleValue());
        }
        if (value instanceof Integer || value instanceof Short || value instanceof Byte) {
            return Variants.valueOf(((Number)value).intValue());
        }
        if (value instanceof Long) {
            return Variants.valueOf((Long)value);
        }
        if (value instanceof BigDecimal) {
            return Variants.valueOf((BigDecimal)value);
        }
        if (value instanceof BigInteger) {
            return Variants.valueOf(new BigDecimal((BigInteger)value));
        }
        if (value instanceof Boolean) {
            return Variants.valueOf((Boolean)value);
        }
        if (value instanceof Date) {
            return Variants.valueOf((Date)value);
        }
        if (value instanceof Calendar) {
            return Variants.valueOf((Calendar)value);
        }
        throw new NumberFormatException();
    }

    public static List<Variant> asList(String ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(float ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(double ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(byte ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(short ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(int ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(long ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(Calendar ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(Date ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(boolean ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(BigDecimal ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(BigInteger ... arr) {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static List<Variant> asList(Object ... arr) throws NumberFormatException {
        Variant[] vars = new Variant[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            vars[i] = Variants.valueOf(arr[i]);
        }
        return Arrays.asList(vars);
    }

    public static Variant convertTo(Variant value, VarType toType) {
        if (value == null || value.getType() == toType || value.isNull()) {
            return value;
        }
        switch (toType) {
            case STRING: {
                return Variants.valueOf(value.getString());
            }
            case BOOLEAN: {
                return Variants.valueOf(value.getBoolean());
            }
            case INTEGER: {
                return Variants.valueOf(value.getInt());
            }
            case LONG: {
                return Variants.valueOf(value.getLong());
            }
            case DOUBLE: {
                return Variants.valueOf(value.getDouble());
            }
            case BIGDECIMAL: {
                return Variants.valueOf(value.getBigDecimal());
            }
            case DATETIME: {
                return Variants.valueOf(value.getCalendar());
            }
        }
        throw new IllegalArgumentException("\u9519\u8bef\u7684\u8f6c\u6362\u7c7b\u578b\uff1a" + (Object)((Object)toType));
    }

    public static String join(CharSequence delimiter, Variant ... elements) throws NullPointerException {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Variant element : elements) {
            joiner.add(element == null ? "" : element.toString());
        }
        return joiner.toString();
    }

    public static String join(CharSequence delimiter, Iterable<? extends Variant> elements) throws NullPointerException {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Variant variant : elements) {
            joiner.add(variant == null ? "" : variant.toString());
        }
        return joiner.toString();
    }
}

