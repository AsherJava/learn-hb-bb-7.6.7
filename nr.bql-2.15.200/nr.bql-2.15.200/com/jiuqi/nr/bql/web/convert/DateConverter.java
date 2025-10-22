/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.web.convert;

import com.jiuqi.nr.bql.web.convert.IDataConverter;
import java.util.Date;

public class DateConverter
implements IDataConverter {
    private int index;

    public DateConverter(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public Object convert(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            Date dateValue = (Date)value;
            return dateValue.getTime();
        }
        return value;
    }
}

