/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.TimeGranularityTypes
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.text.CalendarFormatEx;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.types.TimeGranularityTypes;
import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import java.util.Calendar;

final class ExecutorByTimeKey
implements ITimeExecutor {
    private final TimeFieldInfo timekeyField;
    private int firstDayOfWeek;
    private int fiscalMinMonth;
    private int fiscalMaxMonth;

    public ExecutorByTimeKey(TimeFieldInfo timekeyField) {
        this.timekeyField = timekeyField;
        this.firstDayOfWeek = timekeyField.getGranularity() == 6 ? -1 : 0;
        this.fiscalMinMonth = -1;
        this.fiscalMaxMonth = -1;
    }

    @Override
    public void set(TimeFieldInfo field, Object value) throws TimeCalcException {
        ITimeExecutor.super.set(field, value);
        if (value == null) {
            return;
        }
        if (field.isTimeKey()) {
            Calendar date;
            if (this.firstDayOfWeek < 0) {
                date = (Calendar)field.getValue();
                this.firstDayOfWeek = date.get(7);
            }
            if (this.firstDayOfWeek > 0) {
                date = (Calendar)field.getValue();
                date.setFirstDayOfWeek(this.firstDayOfWeek);
            }
        } else {
            Calendar date = (Calendar)this.timekeyField.getValue();
            Calendar newDate = TimeHelper.setTimeValue(date, field.getGranularity(), field.getIntValue());
            this.timekeyField.setValue(newDate);
        }
    }

    @Override
    public boolean isNull(boolean defaultMode) {
        return this.timekeyField.isNull();
    }

    @Override
    public boolean isNull(int granularity) {
        if (TimeGranularityTypes.daysOf((int)granularity) < TimeGranularityTypes.daysOf((int)this.timekeyField.getGranularity())) {
            return true;
        }
        return this.timekeyField.isNull();
    }

    @Override
    public Calendar exec(boolean defaultMode) throws TimeCalcException {
        return (Calendar)this.timekeyField.getValue();
    }

    @Override
    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek <= 0 ? 2 : this.firstDayOfWeek;
    }

    @Override
    public void setFirstDayOfWeek(int firstDayOfWeek) {
        if (firstDayOfWeek < 1 || firstDayOfWeek > 7) {
            throw new IllegalArgumentException("\u975e\u6cd5\u7684\u5468\u8d77\u59cb\u65e5\uff1a" + firstDayOfWeek);
        }
        this.firstDayOfWeek = firstDayOfWeek;
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
        DateFormatEx format;
        if (this.timekeyField.getGranularity() != 3 || this.fiscalMinMonth == minMonth && this.fiscalMaxMonth == maxMonth) {
            return;
        }
        if (this.timekeyField.format() instanceof CalendarFormatEx) {
            format = ((CalendarFormatEx)this.timekeyField.format()).getDateFormat();
        } else if (this.timekeyField.format() instanceof DateFormatEx) {
            format = (DateFormatEx)this.timekeyField.format();
        } else {
            return;
        }
        Calendar rawTime = format.getCalendar();
        if (rawTime.getMinimum(2) + 1 != minMonth || rawTime.getMaximum(2) + 1 != maxMonth) {
            Calendar newTime = TimeHelper.newCalendar(minMonth, maxMonth);
            newTime.setTimeInMillis(rawTime.getTimeInMillis());
            format.setCalendar(newTime);
        }
        this.fiscalMinMonth = minMonth;
        this.fiscalMaxMonth = maxMonth;
    }
}

