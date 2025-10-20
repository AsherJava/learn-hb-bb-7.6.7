/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarNull;
import com.jiuqi.bi.variant.VarString;
import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarBigDecimal
implements Variant {
    private static final long serialVersionUID = 1L;
    private final BigDecimal value;

    public static Variant valueOf(BigDecimal value) {
        return new VarBigDecimal(value);
    }

    public static Variant valueOf(BigInteger value) {
        return new VarBigDecimal(value);
    }

    public VarBigDecimal(BigDecimal value) {
        this.value = value;
    }

    public VarBigDecimal(BigInteger value) {
        this.value = new BigDecimal(value);
    }

    @Override
    public int compareTo(Variant o) {
        switch (o.getType()) {
            case NULL: {
                return 1;
            }
            case BOOLEAN: 
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case DATETIME: 
            case BIGDECIMAL: {
                return this.getBigDecimal().compareTo(o.getBigDecimal());
            }
            case STRING: {
                return this.getString().compareTo(o.getString());
            }
        }
        throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)o.getType()));
    }

    @Override
    public VarType getType() {
        return VarType.BIGDECIMAL;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getString() {
        return this.value.toPlainString();
    }

    @Override
    public double getDouble() throws NumberFormatException {
        return this.value.doubleValue();
    }

    @Override
    public int getInt() throws ArithmeticException, NumberFormatException {
        BigDecimal minVal = BigDecimal.valueOf(Integer.MIN_VALUE).subtract(BigDecimal.ONE);
        if (this.value.compareTo(minVal) <= 0) {
            throw new ArithmeticException("integer overflow");
        }
        BigDecimal maxVal = BigDecimal.valueOf(Integer.MAX_VALUE).add(BigDecimal.ONE);
        if (this.value.compareTo(maxVal) >= 0) {
            throw new ArithmeticException("integer overflow");
        }
        return this.value.intValue();
    }

    @Override
    public long getLong() throws ArithmeticException {
        BigDecimal minVal = BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE);
        if (this.value.compareTo(minVal) <= 0) {
            throw new ArithmeticException("long overflow");
        }
        BigDecimal maxVal = BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE);
        if (this.value.compareTo(maxVal) >= 0) {
            throw new ArithmeticException("long overflow");
        }
        return this.value.longValue();
    }

    @Override
    public Date getDate() throws ArithmeticException {
        long val = this.getLong();
        return new Date(val);
    }

    @Override
    public Calendar getCalendar() throws ArithmeticException {
        long val = this.getLong();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(val);
        return cal;
    }

    @Override
    public boolean getBoolean() {
        return this.value.compareTo(BigDecimal.ZERO) != 0;
    }

    @Override
    public BigDecimal getBigDecimal() throws NumberFormatException {
        return this.value;
    }

    @Override
    public Variant abs() {
        BigDecimal v = this.value.abs();
        return v == this.value ? this : new VarBigDecimal(v);
    }

    @Override
    public Variant negate() throws UnsupportedOperationException {
        return new VarBigDecimal(this.value.negate());
    }

    @Override
    public Variant add(Variant augend) {
        Objects.requireNonNull(augend);
        switch (augend.getType()) {
            case NULL: {
                return this;
            }
            case BOOLEAN: 
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case DATETIME: 
            case BIGDECIMAL: {
                return new VarBigDecimal(this.value.add(augend.getBigDecimal()));
            }
            case STRING: {
                return VarString.valueOf(this.getString() + augend.getString());
            }
        }
        throw new UnsupportedOperationException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)augend.getType()));
    }

    @Override
    public Variant subtract(Variant subtrahend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(subtrahend);
        switch (subtrahend.getType()) {
            case NULL: {
                return this;
            }
            case BOOLEAN: 
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case DATETIME: 
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().subtract(subtrahend.getBigDecimal()));
            }
            case STRING: {
                throw new UnsupportedOperationException("\u5b57\u7b26\u4e32\u4e0d\u652f\u6301\u51cf\u8fd0\u7b97");
            }
        }
        throw new UnsupportedOperationException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)subtrahend.getType()));
    }

    @Override
    public Variant multiply(Variant multiplicand) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(multiplicand);
        switch (multiplicand.getType()) {
            case NULL: {
                return VarNull.NULL;
            }
            case BOOLEAN: 
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case DATETIME: 
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().multiply(multiplicand.getBigDecimal()));
            }
            case STRING: {
                throw new UnsupportedOperationException("\u5b57\u7b26\u4e32\u4e0d\u652f\u6301\u4e58\u8fd0\u7b97");
            }
        }
        throw new UnsupportedOperationException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)multiplicand.getType()));
    }

    @Override
    public Variant divide(Variant divisor) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(divisor);
        switch (divisor.getType()) {
            case NULL: {
                return VarNull.NULL;
            }
            case BOOLEAN: 
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case DATETIME: 
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().divide(divisor.getBigDecimal()));
            }
            case STRING: {
                throw new UnsupportedOperationException("\u5b57\u7b26\u4e32\u4e0d\u652f\u6301\u9664\u8fd0\u7b97");
            }
        }
        throw new UnsupportedOperationException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)divisor.getType()));
    }

    public int hashCode() {
        return this.getType().value() + 31 * this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof VarBigDecimal) {
            return this.value.equals(((VarBigDecimal)obj).value);
        }
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

