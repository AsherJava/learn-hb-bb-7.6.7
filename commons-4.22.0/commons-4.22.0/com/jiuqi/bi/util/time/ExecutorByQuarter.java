/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Calendar;

final class ExecutorByQuarter
implements ITimeExecutor {
    private TimeFieldInfo yearField;
    private TimeFieldInfo quarterField;

    public ExecutorByQuarter(TimeFieldInfo yearField, TimeFieldInfo quarterField) {
        this.yearField = yearField;
        this.quarterField = quarterField;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        if (defaultMode) {
            return this.yearField.isNull();
        }
        return this.yearField.isNull() || this.quarterField.isNull();
    }

    @Override
    public boolean isNull(int granularity) {
        switch (granularity) {
            case 0: {
                return this.yearField.isNull();
            }
            case 2: {
                return this.yearField.isNull() || this.quarterField.isNull();
            }
        }
        return true;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        int quarter = defaultMode ? this.quarterField.getIntValue(1) : this.quarterField.getIntValue();
        Calendar date = Calendar.getInstance();
        date.set(year, (quarter - 1) * 3, 1, 0, 0, 0);
        return date;
    }
}

