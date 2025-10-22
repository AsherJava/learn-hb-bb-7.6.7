/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.DataConvert
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.DateData;
import com.jiuqi.np.dataengine.data.DateTimeData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.util.DataConvert;
import java.math.BigDecimal;
import java.util.Date;

public final class StringData
extends AbstractData {
    private static final long serialVersionUID = 1706112396369796864L;
    private String value;
    private boolean notNum;
    public static final StringData NULL = new StringData();
    public static final StringData EMPTY = new StringData("", 0.0);

    public StringData() {
        super(6, true, 0.0);
        this.value = null;
    }

    public StringData(String value) {
        super(6, value == null || value.length() == 0);
        this.value = value;
    }

    public StringData(String value, double maxDouble) {
        super(6, value == null || value.length() == 0, maxDouble);
        this.value = value;
    }

    public StringData(String value, boolean notNum) {
        super(6, value == null || value.length() == 0, 0.0);
        this.value = value;
        this.notNum = true;
    }

    @Override
    public boolean getAsBool() throws DataTypeException {
        return DataConvert.stringToBool((String)this.value);
    }

    @Override
    public int getAsInt() throws DataTypeException {
        if (this.isNull) {
            return 0;
        }
        try {
            return (Integer)DataConvert.stringToValue((String)this.value, (int)4);
        }
        catch (NumberFormatException ex) {
            throw new DataTypeException(ex);
        }
    }

    @Override
    public double getAsFloat() throws DataTypeException {
        if (this.isNull) {
            return 0.0;
        }
        try {
            return (Double)DataConvert.stringToValue((String)this.value, (int)2);
        }
        catch (NumberFormatException ex) {
            throw new DataTypeException(ex);
        }
    }

    @Override
    public BigDecimal getAsCurrency() throws DataTypeException {
        if (this.isNull || this.notNum) {
            return null;
        }
        try {
            return new BigDecimal(this.value);
        }
        catch (NumberFormatException ex) {
            throw new DataTypeException(ex);
        }
    }

    @Override
    public long getAsDate() throws DataTypeException {
        if (this.isNull) {
            return 0L;
        }
        Date date = (Date)DataConvert.stringToValue((String)this.value, (int)91);
        if (date == null) {
            throw new DataTypeException("\u65e0\u6548\u7684\u65e5\u671f\u503c:" + this.value);
        }
        return date.getTime();
    }

    @Override
    public long getAsDateTime() throws DataTypeException {
        if (this.isNull) {
            return 0L;
        }
        Date date = (Date)DataConvert.stringToValue((String)this.value, (int)93);
        if (date == null) {
            throw new DataTypeException("\u65e0\u6548\u7684\u65f6\u95f4\u503c:" + this.value);
        }
        return date.getTime();
    }

    @Override
    public String getAsString() {
        return this.value;
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
            case 1: {
                BoolData result = new BoolData(this.getAsBool(), this.isNull, this.maxDouble);
                return result;
            }
            case 4: {
                IntData result = new IntData(this.getAsInt(), this.isNull, this.maxDouble);
                return result;
            }
            case 10: {
                CurrencyData result = new CurrencyData(this.getAsCurrency(), this.maxDouble);
                return result;
            }
            case 3: {
                FloatData result = new FloatData(this.getAsFloat(), this.isNull, this.maxDouble);
                return result;
            }
            case 5: {
                DateData result = this.isNull ? DateData.NULL : new DateData(this.getAsDate());
                return result;
            }
            case 2: {
                DateTimeData result = this.isNull ? DateTimeData.NULL : new DateTimeData(this.getAsDateTime());
                return result;
            }
            case 6: {
                return this;
            }
        }
        throw new DataTypeException();
    }

    @Override
    public int compareTo(AbstractData another) throws DataTypeException {
        if (this.getAsNull() && another.getAsNull()) {
            return 0;
        }
        if (this.getAsNull()) {
            return -1;
        }
        if (another.getAsNull()) {
            return 1;
        }
        return this.value.compareTo(another.getAsString());
    }

    @Override
    public int compareSameType(AbstractData another) {
        if (this.getAsNull() && another.getAsNull()) {
            return 0;
        }
        if (this.getAsNull()) {
            return -1;
        }
        if (another.getAsNull()) {
            return 1;
        }
        return this.value.compareTo(((StringData)another).value);
    }

    public int hashCode() {
        return this.isNull ? 0 : this.value.hashCode();
    }
}

