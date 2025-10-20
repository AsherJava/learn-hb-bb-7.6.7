/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarBigDecimal;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarBoolean
implements Variant {
    private static final long serialVersionUID = 1L;
    private final boolean value;
    public static final VarBoolean TRUE = new VarBoolean(true);
    public static final VarBoolean FALSE = new VarBoolean(false);

    public static Variant valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }

    public VarBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public int compareTo(Variant o) {
        switch (o.getType()) {
            case NULL: {
                return 1;
            }
            case BOOLEAN: {
                return Boolean.compare(this.getBoolean(), o.getBoolean());
            }
            case INTEGER: {
                return Integer.compare(this.getInt(), o.getInt());
            }
            case LONG: {
                return Long.compare(this.getLong(), o.getLong());
            }
            case DOUBLE: {
                return Double.compare(this.getDouble(), o.getDouble());
            }
            case BIGDECIMAL: {
                return this.getBigDecimal().compareTo(o.getBigDecimal());
            }
            case DATETIME: {
                return -1;
            }
            case STRING: {
                return this.getString().compareTo(o.getString());
            }
        }
        throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)o.getType()));
    }

    @Override
    public VarType getType() {
        return VarType.BOOLEAN;
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
        return Boolean.toString(this.value);
    }

    @Override
    public double getDouble() throws NumberFormatException {
        return this.value ? 1.0 : 0.0;
    }

    @Override
    public int getInt() throws ArithmeticException, NumberFormatException {
        return this.value ? 1 : 0;
    }

    @Override
    public long getLong() throws ArithmeticException, NumberFormatException {
        return this.value ? 1L : 0L;
    }

    @Override
    public Date getDate() throws NumberFormatException {
        throw new NumberFormatException("failed to cast to date");
    }

    @Override
    public Calendar getCalendar() throws NumberFormatException {
        throw new NumberFormatException("failed to cast to calendar");
    }

    @Override
    public boolean getBoolean() {
        return this.value;
    }

    @Override
    public BigDecimal getBigDecimal() throws NumberFormatException {
        return this.value ? BigDecimal.ONE : BigDecimal.ZERO;
    }

    @Override
    public Variant abs() {
        return this;
    }

    @Override
    public Variant negate() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Variant add(Variant augend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(augend);
        switch (augend.getType()) {
            case NULL: {
                return this;
            }
            case BOOLEAN: {
                return VarInteger.valueOf(this.getInt() + augend.getInt());
            }
            case INTEGER: {
                return VarInteger.valueOf(this.getLong() + augend.getLong());
            }
            case LONG: {
                BigInteger val = BigInteger.valueOf(augend.getLong());
                return VarLong.valueOf(this.value ? val.add(BigInteger.ONE) : val);
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() + augend.getDouble());
            }
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().add(augend.getBigDecimal()));
            }
            case DATETIME: {
                return this.value ? ((VarDateTime)augend).add(1L) : augend;
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
                BigInteger val = BigInteger.valueOf(subtrahend.getLong());
                return VarLong.valueOf(this.value ? BigInteger.ONE.subtract(val) : val.negate());
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() - subtrahend.getDouble());
            }
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
            case BOOLEAN: {
                return VarInteger.valueOf(this.getInt() * multiplicand.getInt());
            }
            case INTEGER: {
                return this.value ? multiplicand : VarInteger.valueOf(0);
            }
            case LONG: {
                return this.value ? multiplicand : VarLong.valueOf(0L);
            }
            case DOUBLE: {
                return this.value ? multiplicand : VarDouble.valueOf(0.0);
            }
            case BIGDECIMAL: {
                return this.value ? multiplicand : VarBigDecimal.valueOf(BigDecimal.ZERO);
            }
            case DATETIME: {
                return VarLong.valueOf(this.value ? 0L : multiplicand.getLong());
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
        return this.getType().value() + 31 * Boolean.hashCode(this.value);
    }

    public boolean equals(Object obj) {
        if (obj instanceof VarBoolean) {
            return this.value == ((VarBoolean)obj).value;
        }
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

