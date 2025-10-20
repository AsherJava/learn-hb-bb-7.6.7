/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarBigDecimal;
import com.jiuqi.bi.variant.VarNull;
import com.jiuqi.bi.variant.VarString;
import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarDouble
implements Variant {
    private static final long serialVersionUID = 1L;
    private final double value;

    public static Variant valueOf(double value) {
        return new VarDouble(value);
    }

    public static Variant valueOf(float value) {
        return new VarDouble(value);
    }

    public VarDouble(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Variant o) {
        switch (o.getType()) {
            case NULL: {
                return 1;
            }
            case STRING: {
                return this.getString().compareTo(o.getString());
            }
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case BOOLEAN: 
            case DATETIME: {
                return Double.compare(this.value, o.getDouble());
            }
            case BIGDECIMAL: {
                return this.getBigDecimal().compareTo(o.getBigDecimal());
            }
        }
        throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)o.getType()));
    }

    @Override
    public VarType getType() {
        return VarType.DOUBLE;
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
        return Double.toString(this.value);
    }

    @Override
    public double getDouble() {
        return this.value;
    }

    @Override
    public int getInt() {
        if (this.value <= -2.147483649E9 || this.value >= 2.147483648E9) {
            throw new ArithmeticException("integer overflow");
        }
        return (int)this.value;
    }

    @Override
    public long getLong() {
        if (this.value <= -9.223372036854776E18 || this.value >= 9.223372036854776E18) {
            throw new ArithmeticException("long overflow");
        }
        return (long)this.value;
    }

    @Override
    public Date getDate() {
        return new Date(this.getLong());
    }

    @Override
    public Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getLong());
        return cal;
    }

    @Override
    public boolean getBoolean() {
        return this.value != 0.0;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return new BigDecimal(this.value, MathContext.DECIMAL64);
    }

    @Override
    public Variant abs() {
        return this.value <= 0.0 ? new VarDouble(0.0 - this.value) : this;
    }

    @Override
    public Variant negate() throws UnsupportedOperationException {
        return new VarDouble(-this.value);
    }

    @Override
    public Variant add(Variant augend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(augend);
        switch (augend.getType()) {
            case NULL: {
                return this;
            }
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case BOOLEAN: 
            case DATETIME: {
                return VarDouble.valueOf(this.value + augend.getDouble());
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
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case BOOLEAN: 
            case DATETIME: {
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
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case BOOLEAN: 
            case DATETIME: {
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
            case INTEGER: 
            case LONG: 
            case DOUBLE: 
            case BOOLEAN: 
            case DATETIME: {
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
        return this.getType().value() + 31 * Double.hashCode(this.value);
    }

    public boolean equals(Object obj) {
        if (obj instanceof VarDouble) {
            return Double.compare(this.value, ((VarDouble)obj).getDouble()) == 0;
        }
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

