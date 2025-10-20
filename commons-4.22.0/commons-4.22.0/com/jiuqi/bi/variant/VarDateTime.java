/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarBigDecimal;
import com.jiuqi.bi.variant.VarDouble;
import com.jiuqi.bi.variant.VarLong;
import com.jiuqi.bi.variant.VarNull;
import com.jiuqi.bi.variant.VarString;
import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarDateTime
implements Variant {
    private static final long serialVersionUID = 1L;
    private final Calendar value;

    public static Variant valueOf(Calendar value) {
        return value == null ? VarNull.NULL : new VarDateTime(value);
    }

    public static Variant valueOf(Date value) {
        return value == null ? VarNull.NULL : new VarDateTime(value);
    }

    public VarDateTime(Calendar value) {
        this.value = Objects.requireNonNull(value);
    }

    public VarDateTime(Date value) {
        this.value = Calendar.getInstance();
        this.value.setTime(value);
    }

    public VarDateTime(long millis) {
        this.value = Calendar.getInstance();
        this.value.setTimeInMillis(millis);
    }

    @Override
    public int compareTo(Variant o) {
        switch (o.getType()) {
            case NULL: {
                return 1;
            }
            case BOOLEAN: 
            case INTEGER: 
            case LONG: {
                return Long.compare(this.getLong(), o.getLong());
            }
            case DATETIME: {
                return this.value.compareTo(((VarDateTime)o).value);
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
        return VarType.DATETIME;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public Object getValue() {
        return this.value.clone();
    }

    @Override
    public String getString() {
        SimpleDateFormat fmt = this.value.get(13) == 0 && this.value.get(12) == 0 && this.value.get(11) == 0 ? new SimpleDateFormat("yyyy-MM-dd") : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(this.value.getTime());
    }

    @Override
    public double getDouble() {
        return this.value.getTimeInMillis();
    }

    @Override
    public int getInt() throws ArithmeticException {
        long val = this.value.getTimeInMillis();
        if (val < Integer.MIN_VALUE || val > Integer.MAX_VALUE) {
            throw new ArithmeticException("integer overflow");
        }
        return (int)val;
    }

    @Override
    public long getLong() throws ArithmeticException, NumberFormatException {
        return this.value.getTimeInMillis();
    }

    @Override
    public Date getDate() {
        return this.value.getTime();
    }

    @Override
    public Calendar getCalendar() {
        return (Calendar)this.value.clone();
    }

    @Override
    public boolean getBoolean() {
        return this.value.getTimeInMillis() > 0L;
    }

    @Override
    public BigDecimal getBigDecimal() throws NumberFormatException {
        return BigDecimal.valueOf(this.value.getTimeInMillis());
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
            case BOOLEAN: 
            case INTEGER: 
            case LONG: {
                return this.add(augend.getLong());
            }
            case DOUBLE: {
                return VarDouble.valueOf(this.getDouble() + augend.getDouble());
            }
            case BIGDECIMAL: {
                return VarBigDecimal.valueOf(this.getBigDecimal().add(augend.getBigDecimal()));
            }
            case DATETIME: {
                return VarLong.valueOf(BigInteger.valueOf(this.getLong()).add(BigInteger.valueOf(augend.getLong())));
            }
            case STRING: {
                return VarString.valueOf(this.getString() + augend.getString());
            }
        }
        throw new UnsupportedOperationException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + (Object)((Object)augend.getType()));
    }

    public Variant add(long delta) {
        Calendar val = (Calendar)this.value.clone();
        val.setTimeInMillis(this.value.getTimeInMillis() + delta);
        return VarDateTime.valueOf(val);
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
            case LONG: {
                return this.add(-subtrahend.getLong());
            }
            case DATETIME: {
                return VarLong.valueOf(this.getLong() - subtrahend.getLong());
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
        return this.getType().value() + 31 * this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof VarDateTime) {
            return this.value.equals(((VarDateTime)obj).value);
        }
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

