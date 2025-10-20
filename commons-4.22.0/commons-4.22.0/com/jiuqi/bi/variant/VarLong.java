/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarBigDecimal;
import com.jiuqi.bi.variant.VarDateTime;
import com.jiuqi.bi.variant.VarDouble;
import com.jiuqi.bi.variant.VarNull;
import com.jiuqi.bi.variant.VarString;
import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarLong
implements Variant {
    private static final long serialVersionUID = 1L;
    private final long value;

    public static Variant valueOf(long value) {
        return new VarLong(value);
    }

    public static Variant valueOf(BigInteger value) throws ArithmeticException {
        if (value.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0 || value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            throw new ArithmeticException("long overflow");
        }
        return new VarLong(value.longValue());
    }

    public static Variant valueOf(double value) throws ArithmeticException {
        if (value <= -9.223372036854776E18 || value >= 9.223372036854776E18) {
            throw new ArithmeticException("long overflow");
        }
        return new VarLong((long)value);
    }

    public VarLong(long value) {
        this.value = value;
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
            case DATETIME: {
                return Long.compare(this.value, o.getLong());
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
        return VarType.LONG;
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
        return Long.toString(this.value);
    }

    @Override
    public double getDouble() {
        return this.value;
    }

    @Override
    public int getInt() {
        if (this.value < Integer.MIN_VALUE || this.value > Integer.MAX_VALUE) {
            throw new ArithmeticException("integer overflow");
        }
        return (int)this.value;
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
        return this.value != 0L;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return BigDecimal.valueOf(this.value);
    }

    @Override
    public Variant abs() {
        return this.value < 0L ? new VarLong(-this.value) : this;
    }

    @Override
    public Variant negate() throws UnsupportedOperationException {
        return this.value == 0L ? this : new VarLong(-this.value);
    }

    @Override
    public Variant add(Variant augend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(augend);
        switch (augend.getType()) {
            case NULL: {
                return this;
            }
            case BOOLEAN: 
            case INTEGER: 
            case LONG: {
                return VarLong.valueOf(BigInteger.valueOf(this.value).add(BigInteger.valueOf(augend.getLong())));
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() + augend.getDouble());
            }
            case DATETIME: {
                return ((VarDateTime)augend).add(this.getLong());
            }
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().add(augend.getBigDecimal()));
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
            case INTEGER: 
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
            case INTEGER: 
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
        return this.getType().value() + 31 * Long.hashCode(this.value);
    }

    public boolean equals(Object obj) {
        if (obj instanceof VarLong) {
            return this.value == ((VarLong)obj).getLong();
        }
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

