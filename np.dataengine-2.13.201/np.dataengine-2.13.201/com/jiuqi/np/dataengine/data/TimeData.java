/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DateData;
import com.jiuqi.np.dataengine.data.DateTimeData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeData
extends AbstractData {
    private static final long serialVersionUID = 6994614711823245387L;
    private long value;
    public static final DateTimeData NULL = new DateTimeData();

    public TimeData() {
        super(2, true);
        this.value = 0L;
    }

    public TimeData(long value) {
        super(2, false);
        this.value = value;
    }

    public TimeData(long value, boolean isNull) {
        super(2, isNull);
        this.value = isNull ? 0L : value;
    }

    public TimeData(Date value) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Time(this.value));
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return new Time(this.value);
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
        long result = this.value - ((TimeData)another).value;
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

