/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ExecutorOfWeek;
import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import java.util.Calendar;

final class ExecutorByWeekDay
extends ExecutorOfWeek
implements ITimeExecutor {
    private TimeFieldInfo yearField;
    private TimeFieldInfo weekField;
    private TimeFieldInfo dayField;

    public ExecutorByWeekDay(TimeFieldInfo yearField, TimeFieldInfo weekField, TimeFieldInfo dayField) {
        this.yearField = yearField;
        this.weekField = weekField;
        this.dayField = dayField;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        if (defaultMode) {
            return this.yearField.isNull();
        }
        return this.yearField.isNull() || this.weekField.isNull() || this.dayField.isNull();
    }

    @Override
    public boolean isNull(int granularity) {
        switch (granularity) {
            case 0: {
                return this.yearField.isNull();
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 6: {
                return this.yearField.isNull() || this.weekField.isNull();
            }
            case 5: {
                return this.yearField.isNull() || this.weekField.isNull() || this.dayField.isNull();
            }
        }
        return true;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        int week = defaultMode ? this.weekField.getIntValue(1) : this.weekField.getIntValue();
        int day = defaultMode ? this.dayField.getIntValue(-1) : this.dayField.getIntValue();
        Calendar date = TimeHelper.getFirstWeek(this.getFirstDayOfWeek(), year);
        date.add(6, (week - 1) * 7);
        int curDay = date.get(5);
        int maxDay = date.getActualMaximum(5);
        if (day > curDay && day <= maxDay) {
            date.set(5, day);
        } else if (day > 0 && day < curDay) {
            date.add(2, 1);
            date.set(5, day);
        }
        return date;
    }
}

