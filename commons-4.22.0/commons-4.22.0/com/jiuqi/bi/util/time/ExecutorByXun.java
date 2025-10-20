/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.util.Calendar;

final class ExecutorByXun
implements ITimeExecutor {
    private TimeFieldInfo yearField;
    private TimeFieldInfo monthField;
    private TimeFieldInfo xunField;

    public ExecutorByXun(TimeFieldInfo yearField, TimeFieldInfo monthField, TimeFieldInfo xunField) {
        this.yearField = yearField;
        this.monthField = monthField;
        this.xunField = xunField;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        if (defaultMode) {
            return this.yearField.isNull();
        }
        return this.yearField.isNull() || this.monthField.isNull() || this.xunField.isNull();
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
            case 4: {
                return this.yearField.isNull() || this.monthField.isNull() || this.xunField.isNull();
            }
        }
        return true;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        int month = defaultMode && this.xunField.isNull() ? this.monthField.getIntValue(1) : this.monthField.getIntValue();
        int xun = defaultMode ? this.xunField.getIntValue(1) : this.xunField.getIntValue();
        int day = (xun - 1) * 10 + 1;
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day, 0, 0, 0);
        return date;
    }
}

