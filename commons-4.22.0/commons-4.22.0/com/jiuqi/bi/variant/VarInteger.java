/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarBigDecimal;
import com.jiuqi.bi.variant.VarDateTime;
import com.jiuqi.bi.variant.VarDouble;
import com.jiuqi.bi.variant.VarLong;
import com.jiuqi.bi.variant.VarNull;
import com.jiuqi.bi.variant.VarString;
import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarInteger
implements Variant {
    private static final long serialVersionUID = 1L;
    private final int value;

    public static Variant valueOf(int value) {
        return new VarInteger(value);
    }

    public static Variant valueOf(short value) {
        return new VarInteger(value);
    }

    public static Variant valueOf(byte value) {
        return new VarInteger(value);
    }

    public static Variant valueOf(long value) throws ArithmeticException {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new ArithmeticException("integer overflow");
        }
        return new VarInteger((int)value);
    }

    public VarInteger(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Variant o) {
        switch (o.getType()) {
            case NULL: {
                return 1;
            }
            case BOOLEAN: 
            case INTEGER: {
                return Integer.compare(this.value, o.getInt());
            }
            case LONG: 
            case DATETIME: {
                return Long.compare(this.getLong(), o.getLong());
            }
            case DOUBLE: {
                return Double.compare(this.getDouble(), o.getDouble());
            }
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
        return VarType.INTEGER;
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
        return Integer.toString(this.value);
    }

    @Override
    public double getDouble() {
        return this.value;
    }

    @Override
    public int getInt() {
        return this.value;
    }

    @Override
    public long getLong() {
        return this.value;
    }

    @Override
    public Date getDate() {
        return new Date(this.value);
    }

    @Override
    public Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.value);
        return cal;
    }

    @Override
    public boolean getBoolean() {
        return this.value != 0;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return BigDecimal.valueOf(this.value);
    }

    @Override
    public Variant abs() {
        return this.value < 0 ? new VarInteger(-this.value) : this;
    }

    @Override
    public Variant negate() throws UnsupportedOperationException {
        return this.value == 0 ? this : new VarInteger(-this.value);
    }

    @Override
    public Variant add(Variant augend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(augend);
        switch (augend.getType()) {
            case NULL: {
                return this;
            }
            case BOOLEAN: 
            case INTEGER: {
                return VarInteger.valueOf(this.getLong() + augend.getLong());
            }
            case LONG: {
                return VarLong.valueOf(BigInteger.valueOf(this.getLong()).add(BigInteger.valueOf(augend.getLong())));
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() + augend.getDouble());
            }
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().add(augend.getBigDecimal()));
            }
            case DATETIME: {
                return ((VarDateTime)augend).add(this.getLong());
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
            case INTEGER: {
                return VarInteger.valueOf(this.getLong() - subtrahend.getLong());
            }
            case LONG: 
            case DATETIME: {
                return VarLong.valueOf(BigInteger.valueOf(this.getLong()).subtract(BigInteger.valueOf(subtrahend.getLong())));
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() - subtrahend.getDouble());
            }
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().subtract(subtrahend.getBigDecimal()));
            }
            case STRING: {
                throw new UnsupportedOperationException("\u5b57\u7b26\u4e32\u4e0d\u652f\u6301\u51cf\u64cd\u4f5c");
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
            case INTEGER: {
                return VarInteger.valueOf(this.getLong() * multiplicand.getLong());
            }
            case LONG: 
            case DATETIME: {
                return VarLong.valueOf(BigInteger.valueOf(this.getLong()).multiply(BigInteger.valueOf(multiplicand.getLong())));
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() * multiplicand.getDouble());
            }
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
            case INTEGER: {
                return VarInteger.valueOf(this.getInt() / divisor.getInt());
            }
            case LONG: 
            case DATETIME: {
                return VarLong.valueOf(this.getLong() / divisor.getLong());
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() / divisor.getDouble());
            }
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
        return this.getType().value() + 31 * Integer.hashCode(this.value);
    }

    public boolean equals(Object obj) {
        if (obj instanceof VarInteger) {
            return this.value == ((VarInteger)obj).getInt();
        }
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

