/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.DataConvert
 */
package com.jiuqi.np.dataengine.data;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DateTimeData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.util.DataConvert;
import java.util.Calendar;
import java.util.Date;

public final class DateData
extends AbstractData {
    private static final long serialVersionUID = 6056787037486580196L;
    private long value;
    public static final DateData NULL = new DateData();

    public DateData() {
        super(5, true);
        this.value = 0L;
    }

    public DateData(long value) {
        super(5, false);
        this.value = value;
    }

    public DateData(long value, boolean isNull) {
        super(5, isNull);
        this.value = isNull ? 0L : value;
    }

    public DateData(Date value) {
        super(5, value == null);
        this.value = value == null ? 0L : value.getTime();
    }

    @Override
    public long getAsDate() throws DataTypeException {
        return this.value;
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
        return DataConvert.valueToString((Object)new Date(this.value));
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return new Date(this.value);
    }

    @Override
    public AbstractData convertTo(int dataType) throws DataTypeException {
        switch (dataType) {
            case 5: {
                return this;
            }
            case 2: {
                DateTimeData result = this.isNull ? DateTimeData.NULL : new DateTimeData(this.getAsDateTime());
                return result;
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
        long result = this.value - another.getAsDate();
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
        long result = this.value - ((DateData)another).value;
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

    public static long currentDate() {
        return DateData.toDate(System.currentTimeMillis());
    }

    public static DateData today() {
        return new DateData(DateData.currentDate());
    }

    public static long toDate(long time) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);
        date.set(10, 0);
        date.set(12, 0);
        date.set(13, 0);
        date.set(14, 0);
        long result = date.getTimeInMillis();
        return result;
    }
}

