/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import java.util.Calendar;

final class ExecutorByMonth
implements ITimeExecutor {
    private TimeFieldInfo yearField;
    private TimeFieldInfo monthField;
    private int fiscalMinMonth;
    private int fiscalMaxMonth;

    public ExecutorByMonth(TimeFieldInfo yearField, TimeFieldInfo monthField) {
        this.yearField = yearField;
        this.monthField = monthField;
        this.fiscalMinMonth = -1;
        this.fiscalMaxMonth = -1;
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        if (defaultMode) {
            return this.yearField.isNull();
        }
        return this.yearField.isNull() || this.monthField.isNull();
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
        }
        return true;
    }

    @Override
    public int getFiscalMinMonth() {
        return this.fiscalMinMonth;
    }

    @Override
    public int getFiscalMaxMonth() {
        return this.fiscalMaxMonth;
    }

    @Override
    public void setFiscalMonth(int minMonth, int maxMonth) {
        if (minMonth >= 0 || maxMonth >= 0) {
            if (minMonth != 0 && minMonth != 1) {
                throw new IllegalArgumentException("minMonth = " + minMonth);
            }
            if (maxMonth < 12 || maxMonth > 20) {
                throw new IllegalArgumentException("maxMonth = " + maxMonth);
            }
        }
        this.fiscalMinMonth = minMonth;
        this.fiscalMaxMonth = maxMonth;
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        int year = this.yearField.getIntValue();
        int month = defaultMode ? this.monthField.getIntValue(1) : this.monthField.getIntValue();
        Calendar date = TimeHelper.newCalendar(this.fiscalMinMonth, this.fiscalMaxMonth);
        date.set(year, month - 1, 1, 0, 0, 0);
        return date;
    }
}

