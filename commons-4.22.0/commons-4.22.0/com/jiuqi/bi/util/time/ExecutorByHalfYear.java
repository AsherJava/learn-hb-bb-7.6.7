/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Calendar;

final class ExecutorByHalfYear
implements ITimeExecutor {
    private TimeFieldInfo yearField;
    private TimeFieldInfo halfYearField;

    public ExecutorByHalfYear(TimeFieldInfo yearField, TimeFieldInfo halfYearField) {
        this.yearField = yearField;
        this.halfYearField = halfYearField;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        if (defaultMode) {
            return this.yearField.isNull();
        }
        return this.yearField.isNull() || this.halfYearField.isNull();
    }

    @Override
    public boolean isNull(int granularity) {
        switch (granularity) {
            case 0: {
                return this.yearField.isNull();
            }
            case 1: {
                return this.yearField.isNull() || this.halfYearField.isNull();
            }
        }
        return true;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        int halfyear = defaultMode ? this.halfYearField.getIntValue(1) : this.halfYearField.getIntValue();
        Calendar date = Calendar.getInstance();
        date.set(year, halfyear == 1 ? 0 : 6, 1, 0, 0, 0);
        return date;
    }
}

