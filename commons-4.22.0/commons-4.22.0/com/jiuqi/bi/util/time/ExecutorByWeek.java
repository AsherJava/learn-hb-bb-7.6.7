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

final class ExecutorByWeek
extends ExecutorOfWeek
implements ITimeExecutor {
    private TimeFieldInfo yearField;
    private TimeFieldInfo weekField;

    public ExecutorByWeek(TimeFieldInfo yearField, TimeFieldInfo weekField) {
        this.yearField = yearField;
        this.weekField = weekField;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        if (defaultMode) {
            return this.yearField.isNull();
        }
        return this.yearField.isNull() || this.weekField.isNull();
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
        }
        return true;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        int week = defaultMode ? this.weekField.getIntValue(1) : this.weekField.getIntValue();
        int day1st = this.getFirstDayOfWeek();
        Calendar date = TimeHelper.getFirstWeek(day1st, year);
        date.add(6, (week - 1) * 7);
        while (date.get(1) > year) {
            date.add(6, -7);
        }
        return date;
    }
}

