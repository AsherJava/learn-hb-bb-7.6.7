/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

import com.jiuqi.bi.variant.VarType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public interface Variant
extends Comparable<Variant>,
Serializable {
    public VarType getType();

    public boolean isNull();

    public Object getValue();

    public String getString();

    public double getDouble() throws NumberFormatException;

    public int getInt() throws ArithmeticException, NumberFormatException;

    public long getLong() throws ArithmeticException, NumberFormatException;

    public Date getDate() throws ArithmeticException, NumberFormatException;

    public Calendar getCalendar() throws ArithmeticException, NumberFormatException;

    public boolean getBoolean();

    public BigDecimal getBigDecimal() throws NumberFormatException;

    public Variant abs();

    public Variant negate() throws UnsupportedOperationException;

    default public Variant plus() {
        return this;
    }

    public Variant add(Variant var1) throws ArithmeticException, UnsupportedOperationException, NullPointerException;

    public Variant subtract(Variant var1) throws ArithmeticException, UnsupportedOperationException, NullPointerException;

    public Variant multiply(Variant var1) throws ArithmeticException, UnsupportedOperationException, NullPointerException;

    public Variant divide(Variant var1) throws ArithmeticException, UnsupportedOperationException, NullPointerException;
}

