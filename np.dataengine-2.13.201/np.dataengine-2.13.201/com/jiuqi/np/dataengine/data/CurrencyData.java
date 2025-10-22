/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.INumberData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import java.math.BigDecimal;

public final class CurrencyData
extends AbstractData
implements INumberData {
    private static final long serialVersionUID = 7515344406623260790L;
    private BigDecimal value;
    public static final CurrencyData NULL = new CurrencyData();

    public CurrencyData() {
        super(10, true, 0.0);
        this.value = null;
    }

    public CurrencyData(double value) {
        super(10, false, value);
        this.value = new BigDecimal(value);
    }

    public CurrencyData(double value, double maxDouble) {
        super(10, false, CurrencyData.getMaxDouble(value, maxDouble));
        this.value = new BigDecimal(value);
    }

    public CurrencyData(double value, boolean isNull) {
        super(10, isNull, value);
        this.value = isNull ? null : new BigDecimal(value);
    }

    public CurrencyData(double value, boolean isNull, double maxDouble) {
        super(10, isNull, CurrencyData.getMaxDouble(value, maxDouble));
        this.value = isNull ? null : new BigDecimal(value);
    }

    public CurrencyData(BigDecimal value) {
        super(10, value == null, value != null ? value.doubleValue() : 0.0);
        this.value = value;
    }

    public CurrencyData(BigDecimal value, double maxDouble) {
        super(10, value == null, CurrencyData.getMaxDouble(value != null ? value.doubleValue() : 0.0, maxDouble));
        this.value = value;
    }

    @Override
    public AbstractData negate() {
        return new CurrencyData(this.value != null ? this.value.negate() : null, this.maxDouble);
    }

    @Override
    public int getAsInt() throws DataTypeException {
        return this.isNull ? 0 : this.value.intValue();
    }

    @Override
    public double getAsFloat() throws DataTypeException {
        return this.isNull ? 0.0 : this.value.doubleValue();
    }

    @Override
    public BigDecimal getAsCurrency() throws DataTypeException {
        return this.value;
    }

    @Override
    public String getAsString() {
        return this.isNull ? null : this.value.toPlainString();
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return this.value;
    }

    @Override
    public AbstractData convertTo(int dataType) throws DataTypeException {
        switch (dataType) {
            case 4: {
                IntData result = new IntData(this.getAsInt(), this.isNull, this.maxDouble);
                return result;
            }
            case 10: {
                return this;
            }
            case 3: {
                FloatData result = new FloatData(this.getAsFloat(), this.isNull, this.maxDouble);
                return result;
            }
            case 6: {
                StringData result = new StringData(this.getAsString());
                return result;
            }
        }
        throw new DataTypeException();
    }

    @Override
    public int compareTo(AbstractData another) throws DataTypeException {
        BigDecimal thisValue = this.getAsNull() ? BigDecimal.ZERO : this.value;
        BigDecimal anotherValue = another.getAsNull() ? BigDecimal.ZERO : another.getAsCurrency();
        return thisValue.compareTo(anotherValue);
    }

    @Override
    public int compareSameType(AbstractData another) {
        BigDecimal thisValue = this.getAsNull() ? BigDecimal.ZERO : this.value;
        BigDecimal anotherValue = another.getAsNull() ? BigDecimal.ZERO : ((CurrencyData)another).value;
        return thisValue.compareTo(anotherValue);
    }

    public int hashCode() {
        return this.isNull ? 0 : this.value.hashCode();
    }
}

