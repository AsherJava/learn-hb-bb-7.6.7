/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarType;
import com.jiuqi.bi.variant.Variant;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class VarNull
implements Variant {
    private static final long serialVersionUID = 1L;
    public static final VarNull NULL = new VarNull();

    @Override
    public int compareTo(Variant o) {
        if (o.isNull()) {
            return 0;
        }
        return -1;
    }

    @Override
    public VarType getType() {
        return VarType.NULL;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getString() {
        return null;
    }

    @Override
    public double getDouble() {
        return 0.0;
    }

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public long getLong() {
        return 0L;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Calendar getCalendar() {
        return null;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return BigDecimal.ZERO;
    }

    @Override
    public Variant abs() {
        return this;
    }

    @Override
    public Variant negate() throws UnsupportedOperationException {
        return this;
    }

    @Override
    public Variant add(Variant augend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        return Objects.requireNonNull(augend);
    }

    @Override
    public Variant subtract(Variant subtrahend) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        return Objects.requireNonNull(subtrahend).negate();
    }

    @Override
    public Variant multiply(Variant multiplicand) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(multiplicand);
        return NULL;
    }

    @Override
    public Variant divide(Variant divisor) throws ArithmeticException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(divisor);
        return NULL;
    }

    public int hashCode() {
        return this.getType().value();
    }

    public boolean equals(Object obj) {
        return obj instanceof VarNull;
    }

    public String toString() {
        return "NULL";
    }
}

