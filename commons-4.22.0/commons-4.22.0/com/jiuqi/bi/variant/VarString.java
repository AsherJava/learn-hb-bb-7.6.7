/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.text.DateParser;
import com.jiuqi.bi.variant.VarNull;
import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarString
implements Variant {
    private static final long serialVersionUID = 1L;
    private final String value;

    public static Variant valueOf(String value) {
        return value == null ? VarNull.NULL : new VarString(value);
    }

    public VarString(String value) throws NullPointerException {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public int compareTo(Variant o) {
        if (o.isNull()) {
            return 1;
        }
        return this.value.compareTo(o.getString());
    }

    @Override
    public VarType getType() {
        return VarType.STRING;
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
        return this.value;
    }

    @Override
    public double getDouble() {
        return Double.parseDouble(this.value);
    }

    @Override
    public int getInt() {
        return Integer.parseInt(this.value);
    }

    @Override
    public long getLong() {
        return Long.parseLong(this.value);
    }

    @Override
    public Date getDate() {
        Date date = DateParser.toDate(this.value);
        if (date == null) {
            throw new NumberFormatException("\u65e5\u671f\u683c\u5f0f\u9519\u8bef\uff1a" + this.value);
        }
        return date;
    }

    @Override
    public Calendar getCalendar() {
        Date date = this.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Override
    public boolean getBoolean() {
        if ("true".equalsIgnoreCase(this.value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(this.value)) {
            return false;
        }
        try {
            int v = Integer.parseInt(this.value);
            return v != 0;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    @Override
    public BigDecimal getBigDecimal() {
        return new BigDecimal(this.value, MathContext.DECIMAL64);
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
        return augend.isNull() ? this : VarString.valueOf(this.value + augend.getString());
    }

    @Override
    public Variant subtract(Variant subtrahend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        throw new UnsupportedOperationException("\u5b57\u7b26\u4e32\u4e0d\u652f\u6301\u51cf\u8fd0\u7b97");
    }

    @Override
    public Variant multiply(Variant multiplicand) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(multiplicand);
        throw new UnsupportedOperationException("\u5b57\u7b26\u4e32\u4e0d\u652f\u6301\u4e58\u8fd0\u7b97");
    }

    @Override
    public Variant divide(Variant divisor) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(divisor);
        throw new UnsupportedOperationException("\u5b57\u7b26\u4e32\u4e0d\u652f\u6301\u9664\u8fd0\u7b97");
    }

    public int hashCode() {
        return this.getType().value() + 31 * this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof VarString) {
            return this.value.equals(((VarString)obj).getString());
        }
        return false;
    }

    public String toString() {
        return this.value;
    }
}

