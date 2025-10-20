/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Calendar;

final class ExecutorByYear
implements ITimeExecutor {
    private TimeFieldInfo yearField;

    public ExecutorByYear(TimeFieldInfo yearField) {
        this.yearField = yearField;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        return this.yearField.isNull();
    }

    @Override
    public boolean isNull(int granularity) {
        return granularity == 0 ? this.yearField.isNull() : true;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        Calendar date = Calendar.getInstance();
        date.set(year, 0, 1, 0, 0, 0);
        return date;
    }
}

