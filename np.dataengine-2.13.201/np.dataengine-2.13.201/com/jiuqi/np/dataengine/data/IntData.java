/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.INumberData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import java.math.BigDecimal;

public final class IntData
extends AbstractData
implements INumberData {
    private static final long serialVersionUID = -6486494112104326727L;
    private int value;
    public static final IntData NULL = new IntData();
    public static final IntData ZERO = new IntData(0, 0.0);
    public static final IntData ONE = new IntData(1, 1.0);

    public IntData() {
        super(4, true, 0.0);
        this.value = 0;
    }

    public IntData(int value) {
        super(4, false, value);
        this.value = value;
    }

    public IntData(int value, double maxDouble) {
        super(4, false, IntData.getMaxDouble(value, maxDouble));
        this.value = value;
    }

    public IntData(int value, boolean isNull) {
        super(4, isNull, value);
        this.value = isNull ? 0 : value;
    }

    public IntData(int value, boolean isNull, double maxDouble) {
        super(4, isNull, IntData.getMaxDouble(value, maxDouble));
        this.value = isNull ? 0 : value;
    }

    @Override
    public AbstractData negate() {
        return new IntData(-this.value, this.isNull, this.value);
    }

    @Override
    public boolean getAsBool() throws DataTypeException {
        return this.value != 0;
    }

    @Override
    public BigDecimal getAsCurrency() throws DataTypeException {
        return new BigDecimal(this.value);
    }

    @Override
    public int getAsInt() throws DataTypeException {
        return this.value;
    }

    @Override
    public double getAsFloat() throws DataTypeException {
        return this.value;
    }

    @Override
    public String getAsString() {
        if (this.isNull) {
            return null;
        }
        return Integer.toString(this.value);
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return new Integer(this.value);
    }

    @Override
    public AbstractData convertTo(int dataType) throws DataTypeException {
        switch (dataType) {
            case 4: {
                return this;
            }
            case 10: {
                CurrencyData result = new CurrencyData(this.getAsCurrency(), this.maxDouble);
                return result;
            }
            case 3: {
                FloatData result = new FloatData(this.getAsFloat(), this.isNull, this.maxDouble);
                return result;
            }
            case 6: {
                StringData result = new StringData(this.getAsString(), this.maxDouble);
                return result;
            }
            case 1: {
                BoolData result = new BoolData(this.getAsInt() != 0);
                return result;
            }
        }
        throw new DataTypeException();
    }

    @Override
    public int compareTo(AbstractData another) throws DataTypeException {
        int thisValue = this.getAsNull() ? 0 : this.value;
        int anotherValue = another.getAsNull() ? 0 : another.getAsInt();
        return thisValue - anotherValue;
    }

    @Override
    public int compareSameType(AbstractData another) {
        int thisValue = this.getAsNull() ? 0 : this.value;
        int anotherValue = another.getAsNull() ? 0 : ((IntData)another).value;
        return thisValue - anotherValue;
    }

    public int hashCode() {
        return this.isNull ? 0 : this.value;
    }
}

