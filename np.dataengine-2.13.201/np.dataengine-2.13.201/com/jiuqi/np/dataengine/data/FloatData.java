/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.DataConvert
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.INumberData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.util.DataConvert;
import java.math.BigDecimal;

public final class FloatData
extends AbstractData
implements INumberData {
    private static final long serialVersionUID = -258338405412372189L;
    private double value;
    public static final FloatData NULL = new FloatData();
    public static final FloatData ZERO = new FloatData(0.0);

    public FloatData() {
        super(3, true, 0.0);
        this.value = 0.0;
    }

    public FloatData(double value) {
        super(3, false, value);
        this.value = value;
    }

    public FloatData(double value, double maxDouble) {
        super(3, false, FloatData.getMaxDouble(value, maxDouble));
        this.value = value;
    }

    public FloatData(double value, boolean isNull) {
        super(3, isNull, value);
        this.value = isNull ? 0.0 : value;
    }

    public FloatData(double value, boolean isNull, double maxDouble) {
        super(3, isNull, FloatData.getMaxDouble(value, maxDouble));
        this.value = isNull ? 0.0 : value;
    }

    @Override
    public AbstractData negate() {
        return new FloatData(-this.value, this.isNull, this.value);
    }

    @Override
    public int getAsInt() throws DataTypeException {
        return (int)Math.round(this.value);
    }

    @Override
    public BigDecimal getAsCurrency() throws DataTypeException {
        return new BigDecimal(String.valueOf(this.value));
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
        return DataConvert.valueToString((Object)this.value);
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return new Double(this.value);
    }

    @Override
    public AbstractData convertTo(int dataType) throws DataTypeException {
        switch (dataType) {
            case 4: {
                IntData result = new IntData(this.getAsInt(), this.isNull, this.maxDouble);
                return result;
            }
            case 10: {
                CurrencyData result = new CurrencyData(this.getAsCurrency(), this.maxDouble);
                return result;
            }
            case 3: {
                return this;
            }
            case 6: {
                StringData result = new StringData(this.getAsString(), this.maxDouble);
                return result;
            }
        }
        throw new DataTypeException();
    }

    @Override
    public int compareTo(AbstractData another) throws DataTypeException {
        double thisValue = this.getAsNull() ? 0.0 : this.value;
        double anotherValue = another.getAsNull() ? 0.0 : another.getAsFloat();
        return Double.compare(thisValue, anotherValue);
    }

    @Override
    public int compareSameType(AbstractData another) {
        double thisValue = this.getAsNull() ? 0.0 : this.value;
        double anotherValue = another.getAsNull() ? 0.0 : ((FloatData)another).value;
        return Double.compare(thisValue, anotherValue);
    }

    public int hashCode() {
        return this.isNull ? 0 : (int)this.value;
    }
}

