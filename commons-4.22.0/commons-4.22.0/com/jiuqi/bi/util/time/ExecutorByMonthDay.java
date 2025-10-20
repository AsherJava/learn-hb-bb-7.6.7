/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Calendar;

final class ExecutorByMonthDay
implements ITimeExecutor {
    private TimeFieldInfo yearField;
    private TimeFieldInfo monthField;
    private TimeFieldInfo dayField;

    public ExecutorByMonthDay(TimeFieldInfo yearField, TimeFieldInfo monthField, TimeFieldInfo dayField) {
        this.yearField = yearField;
        this.monthField = monthField;
        this.dayField = dayField;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        if (defaultMode) {
            return this.yearField.isNull();
        }
        return this.yearField.isNull() || this.monthField.isNull() || this.dayField.isNull();
    }

    @Override
    public boolean isNull(int granularity) {
        switch (granularity) {
            case 0: {
                return this.yearField.isNull();
            }
            case 1: 
            case 2: 
            case 3: {
                return this.yearField.isNull() || this.monthField.isNull();
            }
            case 4: 
            case 5: 
            case 6: {
                return this.yearField.isNull() || this.monthField.isNull() || this.dayField.isNull();
            }
        }
        return true;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        int month = defaultMode && this.dayField.isNull() ? this.monthField.getIntValue(1) : this.monthField.getIntValue();
        int day = defaultMode ? this.dayField.getIntValue(1) : this.dayField.getIntValue();
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day, 0, 0, 0);
        return date;
    }
}

