/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.DataConvert
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.np.util.DataConvert;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.DateData;
import com.jiuqi.nr.entity.engine.data.StringData;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;
import java.sql.Timestamp;
import java.util.Date;

public final class DateTimeData
extends AbstractData {
    private static final long serialVersionUID = -6025069577488241132L;
    private long value;
    public static final DateTimeData NULL = new DateTimeData();

    public DateTimeData() {
        super(2, true);
        this.value = 0L;
    }

    public DateTimeData(long value) {
        super(2, false);
        this.value = value;
    }

    public DateTimeData(long value, boolean isNull) {
        super(2, isNull);
        this.value = isNull ? 0L : value;
    }

    public DateTimeData(Date value) {
        super(2, value == null);
        this.value = value == null ? 0L : value.getTime();
    }

    @Override
    public long getAsDate() throws DataTypeException {
        return DateData.toDate(this.value);
    }

    @Override
    public long getAsDateTime() throws DataTypeException {
        return this.value;
    }

    @Override
    public String getAsString() {
        if (this.isNull) {
            return null;
        }
        return DataConvert.valueToString((Object)new Timestamp(this.value));
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return new Timestamp(this.value);
    }

    @Override
    public AbstractData convertTo(int dataType) throws DataTypeException {
        switch (dataType) {
            case 5: {
                DateData result = this.isNull ? DateData.NULL : new DateData(this.getAsDate());
                return result;
            }
            case 2: {
                return this;
            }
            case 6: {
                StringData result = this.isNull ? StringData.NULL : new StringData(this.getAsString(), this.maxDouble);
                return result;
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
        long result = this.value - another.getAsDateTime();
        if (result > 0L) {
            result = 1L;
        } else if (result < 0L) {
            result = -1L;
        }
        return (int)result;
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
        long result = this.value - ((DateTimeData)another).value;
        if (result > 0L) {
            result = 1L;
        } else if (result < 0L) {
            result = -1L;
        }
        return (int)result;
    }

    public int hashCode() {
        return this.isNull ? 0 : (int)(this.value & 0xFFFFFFFFFFFFFFFFL);
    }
}

