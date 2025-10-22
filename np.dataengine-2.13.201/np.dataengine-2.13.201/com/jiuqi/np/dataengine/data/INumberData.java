/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import java.math.BigDecimal;

public interface INumberData {
    public AbstractData negate();

    public AbstractData convertTo(int var1) throws DataTypeException;

    public BigDecimal getAsCurrency() throws DataTypeException;

    public int getAsInt() throws DataTypeException;

    public double getAsFloat() throws DataTypeException;
}

