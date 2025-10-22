/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.np.sql.type.ValueConvertException
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.np.sql.type.ValueConvertException;
import com.jiuqi.nr.entity.engine.common.DataEngineConsts;
import com.jiuqi.nr.entity.engine.data.BinaryData;
import com.jiuqi.nr.entity.engine.data.BoolData;
import com.jiuqi.nr.entity.engine.data.CurrencyData;
import com.jiuqi.nr.entity.engine.data.DataTypes;
import com.jiuqi.nr.entity.engine.data.DateData;
import com.jiuqi.nr.entity.engine.data.DateTimeData;
import com.jiuqi.nr.entity.engine.data.ErrorData;
import com.jiuqi.nr.entity.engine.data.FloatData;
import com.jiuqi.nr.entity.engine.data.GuidData;
import com.jiuqi.nr.entity.engine.data.IntData;
import com.jiuqi.nr.entity.engine.data.ObjectData;
import com.jiuqi.nr.entity.engine.data.StringData;
import com.jiuqi.nr.entity.engine.data.TimeData;
import com.jiuqi.nr.entity.engine.data.VoidData;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public abstract class AbstractData
implements Comparable,
Serializable {
    private static final long serialVersionUID = -8574561862357013644L;
    public final int dataType;
    public final boolean isNull;
    public final double maxDouble;

    public static boolean isNull(AbstractData data) {
        return data == null || data.isNull;
    }

    protected AbstractData(int dataType, boolean isNull) {
        this(dataType, isNull, 0.0);
    }

    protected AbstractData(int dataType, boolean isNull, double maxDouble) {
        this.dataType = dataType;
        this.isNull = isNull;
        this.maxDouble = maxDouble;
    }

    public boolean getAsNull() {
        return this.isNull;
    }

    public static double getMaxDouble(double one, double other) {
        return Math.max(Math.abs(one), Math.abs(other));
    }

    public boolean getAsBool() throws DataTypeException {
        throw new DataTypeException();
    }

    public int getAsInt() throws DataTypeException {
        throw new DataTypeException();
    }

    public double getAsFloat() throws DataTypeException {
        throw new DataTypeException();
    }

    public BigDecimal getAsCurrency() throws DataTypeException {
        throw new DataTypeException();
    }

    public long getAsDate() throws DataTypeException {
        throw new DataTypeException();
    }

    public final Date getAsDateObj() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return new Date(this.getAsDate());
    }

    public long getAsDateTime() throws DataTypeException {
        throw new DataTypeException();
    }

    public final Time getAsDateTimeObj() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return new Time(this.getAsDateTime());
    }

    public String getAsString() {
        return null;
    }

    public Object getAsObject() throws DataTypeException {
        throw new DataTypeException();
    }

    public AbstractData convertTo(int dataType) throws DataTypeException {
        throw new DataTypeException();
    }

    public int compareTo(AbstractData another) throws DataTypeException {
        throw new DataTypeException();
    }

    public int compareSameType(AbstractData another) {
        return 0;
    }

    public int compareTo(Object another) {
        if (another instanceof AbstractData && ((AbstractData)another).dataType == this.dataType) {
            return this.compareSameType((AbstractData)another);
        }
        return another != null ? this.getClass().hashCode() - another.getClass().hashCode() : 1;
    }

    public boolean equals(Object obj) {
        if (obj instanceof AbstractData && ((AbstractData)obj).dataType == this.dataType) {
            return this.compareSameType((AbstractData)obj) == 0;
        }
        return false;
    }

    public static AbstractData empty() {
        return new ObjectData();
    }

    public static AbstractData valueOf(double value) {
        return new FloatData(value);
    }

    public static AbstractData valueOf(String value) {
        return new StringData(value);
    }

    public static AbstractData valueOf(int value) {
        return new IntData(value);
    }

    public static AbstractData valueOf(boolean value) {
        return new BoolData(value);
    }

    public static AbstractData valueOfDate(long value) {
        return new DateData(value);
    }

    public static AbstractData valueOfDateTime(long value) {
        return new DateData(value);
    }

    public static AbstractData valueOf(Object value, int dataType) throws DataTypeException {
        return AbstractData.convertData(value, dataType);
    }

    private static AbstractData convertData(Object value, int dataType) throws DataTypeException {
        if (value == null) {
            return DataTypes.getNullValue(dataType);
        }
        try {
            Object dataValue = value;
            switch (dataType) {
                case 1: {
                    return new BoolData(Convert.toBoolean((Object)dataValue));
                }
                case 10: {
                    return new CurrencyData(Convert.toBigDecimal((Object)dataValue));
                }
                case 5: {
                    return new DateData(Convert.toDate((Object)dataValue));
                }
                case 2: {
                    return new DateTimeData(Convert.toDate((Object)dataValue));
                }
                case 8: {
                    return new TimeData(Convert.toDate((Object)dataValue));
                }
                case 6: {
                    return new StringData(Convert.toString((Object)dataValue));
                }
                case 3: {
                    AbstractData floatData = null;
                    try {
                        floatData = new FloatData(Convert.toDouble((Object)dataValue));
                    }
                    catch (ValueConvertException e) {
                        floatData = new CurrencyData(Convert.toBigDecimal((Object)dataValue));
                    }
                    return floatData;
                }
                case 7: {
                    return new StringData(Convert.toString((Object)dataValue));
                }
                case 4: {
                    AbstractData intData = null;
                    try {
                        intData = new IntData(Convert.toInt((Object)dataValue));
                    }
                    catch (ValueConvertException e) {
                        intData = new CurrencyData(Convert.toBigDecimal((Object)dataValue));
                    }
                    return intData;
                }
                case 34: {
                    return new StringData(Convert.toString((Object)dataValue));
                }
                case 33: {
                    return new GuidData(Convert.toUUID((Object)dataValue));
                }
                case 35: 
                case 36: {
                    return new StringData(Convert.toString((Object)dataValue));
                }
                case 37: {
                    return new BinaryData(DataEngineConsts.toBytes(dataValue));
                }
                case -1: {
                    return new ErrorData();
                }
                case -2: {
                    return new VoidData();
                }
                case 0: {
                    return new ObjectData(dataValue);
                }
            }
            throw new DataTypeException();
        }
        catch (Exception e) {
            throw new DataTypeException(e);
        }
    }

    public static final boolean canAutoConvert(int fromType, int toType) {
        switch (fromType) {
            case 1: {
                switch (toType) {
                    case 1: 
                    case 4: {
                        return true;
                    }
                }
                return false;
            }
            case 4: {
                switch (toType) {
                    case 3: 
                    case 4: 
                    case 10: {
                        return true;
                    }
                }
                return false;
            }
            case 10: {
                switch (toType) {
                    case 3: 
                    case 10: {
                        return true;
                    }
                }
                return false;
            }
            case 3: {
                switch (toType) {
                    case 3: 
                    case 10: {
                        return true;
                    }
                }
                return false;
            }
            case 2: 
            case 5: 
            case 8: {
                switch (toType) {
                    case 2: 
                    case 5: 
                    case 8: {
                        return true;
                    }
                }
                return false;
            }
            case 6: {
                switch (toType) {
                    case 2: 
                    case 5: 
                    case 6: 
                    case 8: {
                        return true;
                    }
                }
                return false;
            }
            case 0: {
                switch (toType) {
                    case 0: {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static final boolean canConvert(int fromType, int toType) {
        switch (fromType) {
            case 1: {
                switch (toType) {
                    case 1: 
                    case 4: 
                    case 6: {
                        return true;
                    }
                }
                return false;
            }
            case 4: {
                switch (toType) {
                    case 1: 
                    case 3: 
                    case 4: 
                    case 6: 
                    case 10: {
                        return true;
                    }
                }
                return false;
            }
            case 3: 
            case 10: {
                switch (toType) {
                    case 3: 
                    case 4: 
                    case 6: 
                    case 10: {
                        return true;
                    }
                }
                return false;
            }
            case 2: 
            case 5: 
            case 8: {
                switch (toType) {
                    case 2: 
                    case 5: 
                    case 6: 
                    case 8: {
                        return true;
                    }
                }
                return false;
            }
            case 6: {
                switch (toType) {
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                    case 8: 
                    case 10: {
                        return true;
                    }
                }
                return false;
            }
            case 0: {
                switch (toType) {
                    case 0: 
                    case 6: {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public String toString() {
        return this.getClass().getName() + ": " + this.getAsString();
    }
}

